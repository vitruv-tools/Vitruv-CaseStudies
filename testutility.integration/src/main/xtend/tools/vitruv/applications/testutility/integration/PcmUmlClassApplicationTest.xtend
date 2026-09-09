package tools.vitruv.applications.testutility.integration

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
import org.eclipse.xtext.xbase.lib.Pair
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import java.nio.file.Path
import tools.vitruv.framework.testutils.integration.VitruvApplicationTest
import tools.vitruv.framework.vsum.internal.InternalVirtualModel

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isNoResource
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull
import static extension tools.vitruv.change.atomic.hid.ObjectResolutionUtil.getHierarchicUriFragment
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone

@ExtendWith(RegisterMetamodelsInStandalone)
abstract class PcmUmlClassApplicationTest extends VitruvApplicationTest implements CorrespondenceRetriever {
	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
	}

	protected var PcmUmlClassApplicationTestHelper helper
	protected var ResourceSet testResourceSet

	protected def getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true)
	}

	@BeforeEach
	def protected void setup() {
		helper = new PcmUmlClassApplicationTestHelper(this, [uri|resourceAt(uri)])
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

	private def getInternalVirtualModel() {
		return virtualModel as InternalVirtualModel
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type, String tag) {
		checkArgument(object !== null, "object must not be null")
		val resolvedObject = object.resolveInVirtualModel
		if (resolvedObject === null) {
			return emptyList
		} else {
			return internalVirtualModel.correspondenceModel.getCorrespondingEObjects(resolvedObject, tag).filter(type)
		}
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type) {
		getCorrespondingEObjects(object, type, null)
	}

	private def dispatch EObject resolveInVirtualModel(EObject object) {
		if (object.eResource !== null) {
			internalVirtualModel.getModelInstance(object.eResource.URI).resource.getEObject(
				object.hierarchicUriFragment)
		}
	}

	private def dispatch EObject resolveInVirtualModel(org.eclipse.emf.ecore.EClass eClass) {
		eClass
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
	 * <p>
	 * The view's resources are already cleared automatically after every {@code propagate} call, so this only needs
	 * to reload the resource at the previous URI.
	 *
	 * @param modelElement
	 * 		any eObject in the Resource you want to reload
	 * @return the root element in the reloaded Resource, or null if none is present
	 */
	protected def <O extends EObject> O clearResourcesAndReloadRoot(O modelElement) {
		val resourceURI = modelElement.eResource.URI
		return from(EObject, resourceURI) as O
	}

	def protected corresponds(EObject a, EObject b) {
		return getCorrespondingEObjects(a, EObject).exists[EcoreUtil.equals(it, b)]
	}

	def protected corresponds(EObject a, EObject b, String tag) {
		return getCorrespondingEObjects(a, b.class, tag).exists[EcoreUtil.equals(it, b)]
	}

	// DataType consistency constraints defined here because it is used in multiple tests
	private def boolean isCorrectSimpleTypeCorrespondence(
		DataType pcmDatatype,
		Type umlType,
		int lower,
		int upper
	) {
		val correspondingPrimitiveType = corresponds(pcmDatatype, umlType, TagLiterals.DATATYPE__TYPE) || corresponds(pcmDatatype, umlType, TagLiterals.DATATYPE__TYPE__ALTERNATIVE)
		val correspondingCompositeType = corresponds(pcmDatatype, umlType, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		return (correspondingPrimitiveType || correspondingCompositeType) // inner collection types are not supported
		&& upper == 1 // && lower == 1 // lower could also be 0
	}

	private def boolean isCorrectCollectionTypeCorrespondence(
		CollectionDataType pcmCollection,
		Type umlType,
		int lower,
		int upper
	) {
		return lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED &&
			isCorrectSimpleTypeCorrespondence(pcmCollection.innerType_CollectionDataType, umlType, 1, 1)
	}

	def protected isCorrect_DataType_Property_Correspondence(DataType pcmDatatype, Property umlProperty) {
		if (pcmDatatype === null || umlProperty.type === null) {
			return pcmDatatype === null && umlProperty.type === null
		}

		val simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(pcmDatatype, umlProperty.type,
			umlProperty.lower, umlProperty.upper)
		val collectionTypeCorrespondenceExists = corresponds(pcmDatatype, umlProperty,
			TagLiterals.COLLECTION_DATATYPE__PROPERTY)
		val collectionTypeCorrespondenceIsCorrect = if (pcmDatatype instanceof CollectionDataType)
				isCorrectCollectionTypeCorrespondence(pcmDatatype, umlProperty.type, umlProperty.lower,
					umlProperty.upper)
			else
				null
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect)
	}

	def protected isCorrect_DataType_Parameter_Correspondence(DataType pcmDatatype, Parameter umlParam) {
		if (pcmDatatype === null || umlParam.type === null) {
			return pcmDatatype === null && umlParam.type === null
		}
		val simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(pcmDatatype, umlParam.type, umlParam.lower,
			umlParam.upper)
		val collectionTypeCorrespondenceExists = corresponds(pcmDatatype, umlParam,
			TagLiterals.COLLECTION_DATATYPE__PARAMETER)
		val collectionTypeCorrespondenceIsCorrect = if (pcmDatatype instanceof CollectionDataType)
				isCorrectCollectionTypeCorrespondence(pcmDatatype, umlParam.type, umlParam.lower, umlParam.upper)
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
		propagate(resourceAt(Path.of(pcmOutputPath))) [
			contents += originalRepository
		]
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

		propagate(datatypesPackage) [
			packagedElements += umlDataType
		]
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
			propagate(contractsPackage) [
				packagedElements += originalContractsPackageElement
			]
			return umlRepositoryModel
		}
		val originalInterface = originalContractsPackageElement as Interface
		resolveElements(originalInterface, umlRepositoryModel.eResource, emptyList)

		// add each operation without its parameters because at least the returnParameters will be already generated
		val originalOperationParameterMapping = new HashMap<String, List<Parameter>>()
		for (originalOperation : originalInterface.ownedOperations) {
			originalOperationParameterMapping.put(originalOperation.name,
				originalOperation.ownedParameters.clone.toList)
			originalOperation.ownedParameters.clear
		}
		propagate(contractsPackage) [
			packagedElements += originalInterface
		]
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
				resolveElements(originalParameter, generatedModel.eResource, "name")
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
		propagate(generatedRepositoryPackage) [ // throws UUID error when tested on its own
			nestedPackages += originalComponentPackage
		] // should generate generatedComponentImpl
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
		resolveElements(originalComponentImpl, generatedModel.eResource, "ownedOperation", "interfaceRealization")
		val realizationsToMove = originalComponentImpl.interfaceRealizations.clone
		for (originalRealization : realizationsToMove) {
			resolveElements(originalRealization, generatedModel.eResource)
			originalComponentImpl.interfaceRealizations -= originalRealization
			originalRealization.clients -= originalComponentImpl // needs to be manually cleared, or else originalComponentImpl is still set as a client and causes UUID error
		}
		propagate(generatedComponentImpl) [ org.eclipse.uml2.uml.Class comp | // should generate the constructor Parameters corresponding to RequiredRoles
			mergeElements(originalComponentImpl, comp, "ownedOperation", "interfaceRealization")
			for (originalRealization : realizationsToMove) {
				comp.interfaceRealizations += originalRealization
			}
		]
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
		resolveElements(originalConstructor, generatedModel.eResource, "ownedParameter")
		val parametersToMerge = new ArrayList<Pair<Parameter, Parameter>>()
		val parametersToMove = new ArrayList<Parameter>()
		for (originalParameter : originalConstructor.ownedParameters.clone) {
			val generatedParameter = generatedConstructor.ownedParameters.findFirst[it.name == originalParameter.name]
			resolveElements(originalParameter, generatedModel.eResource, "name")
			if (generatedParameter !== null) {
				parametersToMerge += originalParameter -> generatedParameter
			} else {
				parametersToMove += originalParameter
			}
		}
		// remaining operations to move/copy
		val operationsToMove = originalComponentImpl.ownedOperations.clone.filter [
			it.name != originalComponentImpl.name
		].toList

		propagate(generatedComponentImpl) [ org.eclipse.uml2.uml.Class comp |
			mergeElements(originalConstructor, generatedConstructor, "ownedParameter")
			for (mergePair : parametersToMerge) {
				mergeElements(mergePair.key, mergePair.value, "name")
			}
			for (originalParameter : parametersToMove) {
				generatedConstructor.ownedParameters += originalParameter
			}
			for (originalOperation : operationsToMove) {
				comp.ownedOperations += originalOperation
			}
		]
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
		propagate(resourceAt(Path.of(umlOutputPath))) [
			contents += originalRepositoryModel
		]
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
