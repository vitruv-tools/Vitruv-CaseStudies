package tools.vitruv.applications.pcmumlclass.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource

import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*

abstract class PcmUmlClassApplicationTest extends ViewBasedVitruvApplicationTest {

	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"

	static protected val PACKAGE_NAME = "rootpackage"
	static protected val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val CONTRACTS_PACKAGE = "contracts"
	
	public static val PCM_MODEL_FILE = "model/Repository.repository"
	public static val PCM_MODEL_SYSTEM_FILE = "model/System.system"
	public static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION

	protected var extension PcmUmlClassViewFactory viewFactory
	protected var LegacyPcmUmlClassApplicationTestHelper helper
	protected var ResourceSet testResourceSet

	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
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

	def void initPCMRepository() {
		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)
	}

	def void initUMLModel() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)
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

	protected def getUmlRootPackage(View view) {
		view.defaultUmlModel.claimPackage(PACKAGE_NAME)
	}

	protected def getUmlContractsPackage(View view) {
		view.defaultUmlModel.getNestedPackages.findFirst[it.name.equals(PACKAGE_NAME)].nestedPackages.findFirst [
			it.name.equals(CONTRACTS_PACKAGE)
		]
	}

	@BeforeEach
	def void setup() {
		viewFactory = new PcmUmlClassViewFactory(virtualModel)
		createUmlModel[name = MODEL_NAME]
	}

	def protected void assertModelExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isResource)
	}

	def protected void assertModelNotExists(String modelPath) {
		val modelUri = getUri(Path.of(modelPath))
		assertThat(modelUri, isNoResource)
	}
}
