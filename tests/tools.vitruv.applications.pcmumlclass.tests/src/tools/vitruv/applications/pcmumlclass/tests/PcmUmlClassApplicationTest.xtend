package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.DataType
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.Comparison
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.EReference
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import org.eclipse.uml2.uml.Interface
import java.util.HashMap
import java.util.List

import java.util.ArrayList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import java.nio.file.Path
import tools.vitruv.testutils.LegacyVitruvApplicationTest

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource

abstract class PcmUmlClassApplicationTest extends LegacyVitruvApplicationTest {
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

	protected var PcmUmlClassApplicationTestHelper helper
	protected var ResourceSet testResourceSet

	protected def getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true)
	}

	@BeforeEach
	def protected void setup() {
		patchDomains
		helper = new PcmUmlClassApplicationTestHelper(correspondenceModel, [uri|uri.resourceAt])
		testResourceSet = new ResourceSetImpl()
	}

	@AfterEach
	def protected void cleanup() {
		testResourceSet = null
		helper = null
	}

	def protected void assertModelExists(String modelPathWithinProject) {
		val modelUri = getUri(Path.of(modelPathWithinProject))
		assertThat(modelUri, isResource)
	}

	def protected void assertModelNotExists(String modelPathWithinProject) {
		val modelUri = getUri(Path.of(modelPathWithinProject))
		assertThat(modelUri, isNoResource)
	}

	/**
	 * Clears all test resources, reloads the resource of the given root element and returns the reloaded root element.
	 * <br><br>
	 * Changes to a resource that are resolved by the change propagation framework, may not reflect in local instances.
	 * The VSUM lives in its own ResourceSet and works on its own copies of the instances. 
	 * When an instance in the VSUM is changed by the change propagation, the instance in the local ResourceSet of the Test can become desynchronized 
	 * if those changes diverge from the manual changes performed by the developer (e.g. round-trip changes to the transformation's source).
	 * <br><br>
	 * WARNING: This invalidates all {@link EObject} instances in the {@link Resource} of the passed element (they turn into proxy elements).
	 * To do anything with them, best re-retrieve them from the reloaded resource by traversing the references of the returned root element, 
	 * or via the element's URI, however, the proxy's URI will not work, if the element was desynchronized before the reload.
	 * 
	 * @param modelElement
	 * 		any eObject in the Resource you want to reload  
	 * @return the root element in the reloaded Resource, or null if none is present
	 */
	protected def <O extends EObject> O clearResourcesAndReloadRoot(O modelElement) {
		stopRecordingChanges(modelElement.eResource)
		val resourceURI = modelElement.eResource.URI
		renewResourceCache

		val rootElement = EObject.from(resourceURI) as O
		if (rootElement !== null) {
			startRecordingChanges(rootElement)
		}
		return rootElement
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b) {
		return cm.getCorrespondingEObjects(#[a]).exists(corrSet|EcoreUtil.equals(corrSet.head, b))
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag) {
		return EcoreUtil.equals(b,
			ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}

	// DataType consistency constraints defined here because it is used in multiple tests
	private static def boolean isCorrectSimpleTypeCorrespondence(
		CorrespondenceModel correspondenceModel,
		DataType pcmDatatype,
		Type umlType,
		int lower,
		int upper
	) {
		val correspondingPrimitiveType = corresponds(correspondenceModel, pcmDatatype, umlType,
			TagLiterals.DATATYPE__TYPE)
		val correspondingCompositeType = corresponds(correspondenceModel, pcmDatatype, umlType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
		return (correspondingPrimitiveType || correspondingCompositeType) // inner collection types are not supported
		&& upper == 1 // && lower == 1 // lower could also be 0
	}

	private static def boolean isCorrectCollectionTypeCorrespondence(
		CorrespondenceModel correspondenceModel,
		CollectionDataType pcmCollection,
		Type umlType,
		int lower,
		int upper
	) {
		return lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED &&
			isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmCollection.innerType_CollectionDataType, umlType,
				1, 1)
	}

	def protected static isCorrect_DataType_Property_Correspondence(CorrespondenceModel correspondenceModel,
		DataType pcmDatatype, Property umlProperty) {
		if (pcmDatatype === null || umlProperty.type === null) {
			return pcmDatatype === null && umlProperty.type === null
		}

		val simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmDatatype,
			umlProperty.type, umlProperty.lower, umlProperty.upper)
		val collectionTypeCorrespondenceExists = corresponds(correspondenceModel, pcmDatatype, umlProperty,
			TagLiterals.COLLECTION_DATATYPE__PROPERTY)
		val collectionTypeCorrespondenceIsCorrect = if (pcmDatatype instanceof CollectionDataType)
				isCorrectCollectionTypeCorrespondence(correspondenceModel, pcmDatatype, umlProperty.type,
					umlProperty.lower, umlProperty.upper)
			else
				null
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect)
	}

	def protected static isCorrect_DataType_Parameter_Correspondence(CorrespondenceModel correspondenceModel,
		DataType pcmDatatype, Parameter umlParam) {
		if (pcmDatatype === null || umlParam.type === null) {
			return pcmDatatype === null && umlParam.type === null
		}
		val simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmDatatype,
			umlParam.type, umlParam.lower, umlParam.upper)
		val collectionTypeCorrespondenceExists = corresponds(correspondenceModel, pcmDatatype, umlParam,
			TagLiterals.COLLECTION_DATATYPE__PARAMETER)
		val collectionTypeCorrespondenceIsCorrect = if (pcmDatatype instanceof CollectionDataType)
				isCorrectCollectionTypeCorrespondence(correspondenceModel, pcmDatatype, umlParam.type, umlParam.lower,
					umlParam.upper)
			else
				null
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect)
	}

	/* Because of transitive change propagation between the Pcm and Uml domains, it is necessary to stepwise simulate
	 * the insertion of Uml-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
	 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
	 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
	 * This might make it necessary to provide the VM that runs the tests with additional heap space.
	 */
	protected def simulateRepositoryInsertion_PCM(Repository originalRepository, String pcmOutputPath,
		String umlOutputPath) {
		userInteraction.addNextTextInput(umlOutputPath) // answers where to save the corresponding .uml model
		resourceAt(Path.of(pcmOutputPath)).startRecordingChanges => [
			contents += originalRepository
		]
		propagate
		var generatedRepository = originalRepository.clearResourcesAndReloadRoot
		return generatedRepository
	}

	private def simulateDataTypeInsertion_UML(Model umlRepositoryModel, PackageableElement umlDataType) {
		val repositoryPackage = umlRepositoryModel.nestedPackages.head
		assertNotNull(repositoryPackage)
		val datatypesPackage = repositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME
		]
		assertNotNull(datatypesPackage)

		datatypesPackage.packagedElements += umlDataType
		propagate
		return umlRepositoryModel.clearResourcesAndReloadRoot
	}

	private def simulateContractsPackageElementInsertion_UML(Model umlRepositoryModel,
		PackageableElement originalContractsPackageElement) {
		var repositoryPackage = umlRepositoryModel.nestedPackages.head
		assertNotNull(repositoryPackage)
		var contractsPackage = repositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
		]
		assertNotNull(contractsPackage)

		if (!(originalContractsPackageElement instanceof Interface)) {
			contractsPackage.packagedElements += originalContractsPackageElement
			return umlRepositoryModel
		}
		val originalInterface = originalContractsPackageElement as Interface

		// add each operation without its parameters because at least the returnParameters will be already generated
		val originalOperationParameterMapping = new HashMap<String, List<Parameter>>()
		for (originalOperation : originalInterface.ownedOperations) {
			originalOperationParameterMapping.put(originalOperation.name,
				originalOperation.ownedParameters.clone.toList)
			originalOperation.ownedParameters.clear
		}
		contractsPackage.packagedElements += originalInterface
		propagate
		var generatedModel = umlRepositoryModel.clearResourcesAndReloadRoot

		// retrieve elements after reload
		repositoryPackage = generatedModel.nestedPackages.head
		assertNotNull(repositoryPackage)
		contractsPackage = repositoryPackage.nestedPackages.findFirst[it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME]
		assertNotNull(contractsPackage)
		var generatedInterface = contractsPackage.packagedElements.filter(Interface).findFirst [
			it.name == originalInterface.name
		]
		assertNotNull(generatedInterface)

		// merge each Parameter from the originalOperation with the generated Operation's parameters
		for (generatedOperation : generatedInterface.ownedOperations) {
			for (originalParameter : originalOperationParameterMapping.getOrDefault(generatedOperation.name,
				new ArrayList<Parameter>)) {
				val generatedParameter = generatedOperation.ownedParameters.findFirst[it.name == originalParameter.name]
				if (generatedParameter !== null) {
					mergeElements(originalParameter, generatedParameter, "name")
				} else {
					generatedOperation.ownedParameters += originalParameter
				}
			}
		}

		// propagate
		return generatedModel.clearResourcesAndReloadRoot
	}

	private def simulateComponentInsertion_UML(Model inUmlRepositoryModel,
		org.eclipse.uml2.uml.Package originalComponentPackage, int userDisambigutationComponentType) {
		var generatedModel = inUmlRepositoryModel
		var generatedRepositoryPackage = generatedModel.nestedPackages.head
		assertNotNull(generatedRepositoryPackage)

		// Simulate adding a Component via round-trip by adding the components package.
		// For that, first remove the implementation from the original package to avoid duplication.
		val originalComponentImpl = originalComponentPackage.packagedElements.filter(org.eclipse.uml2.uml.Class).head
		assertNotNull(originalComponentImpl)
		originalComponentPackage.packagedElements -= originalComponentImpl
		assertTrue(originalComponentPackage.packagedElements.empty)

		userInteraction.addNextSingleSelection(userDisambigutationComponentType)
		generatedRepositoryPackage.nestedPackages += originalComponentPackage // throws UUID error when tested on its own
		propagate // should generate generatedComponentImpl
		generatedModel = generatedModel.clearResourcesAndReloadRoot

		generatedRepositoryPackage = generatedModel.nestedPackages.head
		assertNotNull(generatedRepositoryPackage)
		var generatedComponentPackage = generatedRepositoryPackage.nestedPackages.findFirst [
			it.name == originalComponentPackage.name
		]
		assertNotNull(generatedComponentPackage)
		var generatedComponentImpl = generatedComponentPackage.packagedElements.filter(org.eclipse.uml2.uml.Class).
			findFirst[it.name == originalComponentImpl.name]
		assertNotNull(generatedComponentImpl)

		// merge everything except operations which are separately handled, because some of the constructor parameters will be generated.
		mergeElements(originalComponentImpl, generatedComponentImpl, "ownedOperation", "interfaceRealization")
		for (originalRealization : originalComponentImpl.interfaceRealizations.clone) {
			originalComponentImpl.interfaceRealizations -= originalRealization
			originalRealization.clients -= originalComponentImpl // needs to be manually cleared, or else originalComponentImpl is still set as a client and causes UUID error
			generatedComponentImpl.interfaceRealizations += originalRealization
		}

		propagate // should generate the constructor Parameters corresponding to RequiredRoles
		generatedModel = generatedModel.clearResourcesAndReloadRoot
		generatedRepositoryPackage = generatedModel.nestedPackages.head
		assertNotNull(generatedRepositoryPackage)
		generatedComponentPackage = generatedRepositoryPackage.nestedPackages.findFirst [
			it.name == originalComponentPackage.name
		]
		assertNotNull(generatedComponentPackage)
		generatedComponentImpl = generatedComponentPackage.packagedElements.filter(org.eclipse.uml2.uml.Class).findFirst [
			it.name == originalComponentImpl.name
		]
		assertNotNull(generatedComponentImpl)

		// merge the original with the generated constructor
		val originalConstructor = originalComponentImpl.ownedOperations.findFirst[it.name == originalComponentImpl.name]
		val generatedConstructor = generatedComponentImpl.ownedOperations.findFirst [
			it.name == originalComponentImpl.name
		]
		assertNotNull(originalConstructor)
		assertNotNull(generatedConstructor)
		mergeElements(originalConstructor, generatedConstructor, "ownedParameter")
		for (originalParameter : originalConstructor.ownedParameters.clone) {
			val generatedParameter = generatedConstructor.ownedParameters.findFirst[it.name == originalParameter.name]
			if (generatedParameter !== null) {
				mergeElements(originalParameter, generatedParameter, "name")
			} else {
				generatedConstructor.ownedParameters += originalParameter
			}
		}
		// move/copy the remaining operations
		for (originalOperation : originalComponentImpl.ownedOperations.clone.filter [
			it.name != originalComponentImpl.name
		]) {
			generatedComponentImpl.ownedOperations += originalOperation
		}

		propagate
		generatedModel = generatedModel.clearResourcesAndReloadRoot
		return generatedModel
	}

	protected def simulateRepositoryInsertion_UML(Model originalRepositoryModel, String umlOutputPath,
		String pcmOutputPath) {
		assertNotNull(originalRepositoryModel)
		val umlRepositoryPackage = originalRepositoryModel.nestedPackages.head
		assertNotNull(umlRepositoryPackage)
		val originalContractsPackage = umlRepositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
		]
		val originalDatatypesPackage = umlRepositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME
		]
		val originalComponentPackages = umlRepositoryPackage.nestedPackages.filter [
			it !== originalContractsPackage && it !== originalDatatypesPackage
		].toList
		umlRepositoryPackage.nestedPackages.clear

		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY) // rootelement is supposed to be a repository
		userInteraction.addNextTextInput(pcmOutputPath) // answers where to save the corresponding .pcm model
		resourceAt(Path.of(umlOutputPath)).startRecordingChanges => [
			contents += originalRepositoryModel
		]
		propagate
		var generatedModel = originalRepositoryModel.clearResourcesAndReloadRoot

		// Create copies of the lists outside the model so that containment is irrelevant
		// and that the loops do not iterate over a changing List.
		for (originalDatatype : originalDatatypesPackage.packagedElements.clone) {
			generatedModel = simulateDataTypeInsertion_UML(generatedModel, originalDatatype)
		}
		for (originalContractsPackageElement : originalContractsPackage.packagedElements.clone) {
			generatedModel = simulateContractsPackageElementInsertion_UML(generatedModel,
				originalContractsPackageElement)
		}
		for (originalComponentPackage : originalComponentPackages) {
			generatedModel = simulateComponentInsertion_UML(generatedModel, originalComponentPackage,
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
		}
		return generatedModel
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
	def Comparison compare(URI originalUri, URI generatedUri) {
		val resourceSet = new ResourceSetImpl()
		val original = resourceSet.getResource(originalUri, true).contents.head
		val generated = resourceSet.getResource(generatedUri, true).contents.head
		return compare(original, generated)
	}

	/**
	 * This directly applies the default EMFCompare comparator to the passed elements. 
	 * It does not ensure that the compared elements are in sync with the disk state.  
	 */
	def Comparison compare(Notifier original, Notifier generated) {
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
	def mergeElements(EObject original, EObject generated, String ... skipFeatures) {
		for (feature : original.eClass.EAllStructuralFeatures) {
			if (!feature.derived && feature.changeable && original.eIsSet(feature) &&
				!(feature instanceof EReference && (feature as EReference).isContainer) &&
				!skipFeatures.contains(feature.name)) {
				generated.eSet(feature, original.eGet(feature))
			}
		}
	}

}
