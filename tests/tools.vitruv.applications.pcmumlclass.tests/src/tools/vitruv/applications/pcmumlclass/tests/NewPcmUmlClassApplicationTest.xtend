package tools.vitruv.applications.pcmumlclass.tests

import java.nio.file.Path
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.Comparison
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest

import static com.google.common.base.Preconditions.checkNotNull
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource

import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider

abstract class NewPcmUmlClassApplicationTest extends ViewBasedVitruvApplicationTest {

	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"

	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	protected var extension PcmUmlclassViewFactory viewFactory
	protected var PcmUmlClassApplicationTestHelper helper
	protected var ResourceSet testResourceSet

	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
	}
	
	private def patchDomains() {
		new PcmDomainProvider().domain.enableTransitiveChangePropagation
		new UmlDomainProvider().domain.enableTransitiveChangePropagation
	}

	protected def getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true)
	}

	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeUmlView [
			val umlModel = UMLFactory.eINSTANCE.createModel
			createAndRegisterRoot(umlModel, MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)
		]
	}

	protected def void createPcmRepository(String packageName) {
		changePcmView [
			val repository = RepositoryFactory.eINSTANCE.createRepository => [
				it.entityName = packageName
			]
			createAndRegisterRoot(repository, MODEL_NAME.projectModelPath.uri)
		]
	}

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}

	protected def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(MODEL_NAME)
	}

	protected def getDefaultPcmRepository(View view) {
		view.claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)
	}
	


	protected def void changeUmlModel((Model)=>void modelModification) {
		changeUmlView [
			modelModification.apply(defaultUmlModel)
		]
	}

	protected def void createUmlRootPackage(String packageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}
	
	protected def getRootPackage(View view) {
		view.defaultUmlModel.claimPackage(PACKAGE_NAME)
	}

	@BeforeEach
	def void setup() {
		patchDomains
		viewFactory = new PcmUmlclassViewFactory(virtualModel)
		createUmlModel[name = MODEL_NAME]
	}

	@AfterEach
	def protected void cleanup() {
	}

	def protected void assertModelExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isResource)
	}

	def protected void assertModelNotExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isNoResource)
	}

	/**
	 * Compare the root elements of the Resources at the specified project-relative paths, 
	 * by first loading them into a temporary ResourceSet to make sure they are consistent with the disk state.
	 * 
	 * @param originalWithinProjektPath
	 * 		the path of the resource to compare against
	 * @param generatedWithinProjektPath
	 * 		the path of the generated resource
	 * @return
	 * 		the Comparison produced by the default EMFCompare configuration (EMFCompare.builder.build)
	 */
	def Comparison compare(String originalWithinProjektPath, String generatedWithinProjektPath) {
		val originalUri = getUri(Path.of(originalWithinProjektPath))
		val generatedUri = getUri(Path.of(generatedWithinProjektPath))
		return compare(originalUri, generatedUri)
	}

	/**
	 * Compare the root elements at the specified URIs, by first loading them into a temporary ResourceSet 
	 * to make sure they are consistent with the disk state.
	 * 
	 * @param originalUri
	 * 		the URI of the resource to compare against
	 * @param generatedUri
	 * 		the URI of the generated resource
	 * @return
	 * 		the Comparison produced by the default EMFCompare configuration (EMFCompare.builder.build)
	 */
	def static Comparison compare(URI originalUri, URI generatedUri) {
		val resourceSet = new ResourceSetImpl()
		val original = resourceSet.getResource(originalUri, true).contents.head
		val generated = resourceSet.getResource(generatedUri, true).contents.head
		return compare(original, generated)
	}

	/**
	 * This directly applies the default EMFCompare comparator to the passed elements. 
	 * It does not ensure that the compared elements are in sync with the disk state.  
	 */
	def static Comparison compare(Notifier original, Notifier generated) {
		val comparator = EMFCompare.builder().build()
		val scope = new DefaultComparisonScope(original, generated, original)
		return comparator.compare(scope)
	}

	/**
	 * Copy the attributes and move the references from the original element to the target element.
	 * The container feature is always ignored, to avoid moving the target element to the original's container.
	 * 
	 * @param original
	 * 		the original element with the values to set on the target
	 * @param target 
	 * 		the target element on which the values are to be set
	 * @param skipFeatures
	 * 		the names of the features that should be ignored
	 */
	def static mergeElements(EObject original, EObject generated, String ... skipFeatures) {
		val relevantFeatures = original.eClass.EAllStructuralFeatures.filter [
			isResolveAndMergeRelevantFeature(original, skipFeatures)
		]
		for (feature : relevantFeatures) {
			generated.eSet(feature, original.eGet(feature))
		}
	}

	private def resolveElements(EObject original, Resource generatedResource, String ... skipFeatures) {
		val relevantReferences = original.eClass.EAllReferences.filter [
			isResolveAndMergeRelevantFeature(original, skipFeatures)
		]
		for (reference : relevantReferences) {
			val originalValue = original.eGet(reference)
			if (!reference.many) {
				original.eSet(reference, (originalValue as EObject).resolve(generatedResource))
			} else {
				original.eSet(reference, (originalValue as List<EObject>).map[resolve(generatedResource)])
			}
		}
	}

	private def static boolean isResolveAndMergeRelevantFeature(EStructuralFeature feature, EObject object,
		String ... skipFeatures) {
		return !feature.derived && feature.changeable && object.eIsSet(feature) &&
			!skipFeatures.contains(feature.name) && if(feature instanceof EReference) !feature.isContainer else true
	}

	private def static resolve(EObject original, Resource in) {
		checkNotNull(in.resourceSet.getEObject(in.URI.appendFragment(original.hierarchicUriFragment), true),
			"resolved object for %s", original)
	}

}
