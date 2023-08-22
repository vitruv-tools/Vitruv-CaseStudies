package tools.vitruv.applications.pcmumlclass.tests

import java.nio.file.Path
import java.util.Comparator
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.Comparison
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.testutility.uml.UmlQueryUtil

import static com.google.common.base.Preconditions.checkNotNull
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

import static extension tools.vitruv.change.atomic.hid.ObjectResolutionUtil.getHierarchicUriFragment
import java.util.Map

/**
 * Model creation tests, to test the set of transformations as a whole. 
 * The tests mainly try to validate if the transformations terminate. To see if the results are correct manual verification of the output is necessary.
 * 
 * Because of transitive change propagation between the Pcm and Uml domains, it is necessary to stepwise simulate
 * the insertion of Uml-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
 * This might make it necessary to provide the VM that runs the tests with additional heap space.
 */
class PackageableElementComperator implements Comparator<PackageableElement> {
	override int compare(PackageableElement e1, PackageableElement e2) {
		return e1.name.compareTo(e2.name)
	}
}

class MediaStoreRepositoryCreationTest extends PcmUmlClassApplicationTest {

	// all SEFFs removed because the TUID-generator failed for ResourceDemandParameters 
	static val PCM_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_noSEFF.repository"
	static val UML_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_repository_noSEFF_unedited.uml"

	static val UML_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.uml"
	static val PCM_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.repository"

	@BeforeEach
	override void setup() {
		viewFactory = new PcmUmlClassViewFactory(virtualModel)
	}

	@Test
	def void testMediaStoreRepositoryCreation_PCM2UML() {
		var pcmMsRepository = loadPcmMsRepository
		simulateRepositoryInsertion_PCM(pcmMsRepository, PCM_GENERATED_MEDIA_STORE_MODEL_PATH,
			UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
	}

	@Test
	def void testMediaStoreRepositoryCreation_UML2PCM() {
		var umlMsModel = removePrimitiveTypes(loadUmlMsModel)
		
		//(umlMsModel.packagedElements.get(0) as Package).packagedElements.removeIf[!it.name.equalsIgnoreCase("datatypes")]
		
		simulateRepositoryInsertion_UML(umlMsModel, UML_GENERATED_MEDIA_STORE_MODEL_PATH,
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
	}

	@Test
	def void testMediaStoreRepositoryCreation_PCM2UML2PCM() {
		// forwards
		var pcmMsRepository = loadPcmMsRepository

		simulateRepositoryInsertion_PCM(pcmMsRepository, PCM_GENERATED_MEDIA_STORE_MODEL_PATH,
			UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		val safePcmRepoAfterForeward = Repository.from(
			getUri(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		val umlRepo_backward = Model.from(getUri(Path.of(UML_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		changePcmView[
			userInteraction.addNextConfirmationInput(true)
			EcoreUtil.delete(PcmQueryUtil.claimPcmRepository(it, "DefaultRepository"), false)
		]
		changeUmlView[
			EcoreUtil.delete(UmlQueryUtil.claimUmlModel(it, "model"), false)
		]

		removePrimitiveTypes(umlRepo_backward)
		simulateRepositoryInsertion_UML(umlRepo_backward, UML_GENERATED_MEDIA_STORE_MODEL_PATH,
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		validateUmlView[
			it.rootObjects.get(0).eResource.save(Map.of())
		]
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		val safePcmRepoAfterBackward = Repository.from(
			getUri(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)

		val comparison = compare(safePcmRepoAfterForeward, safePcmRepoAfterBackward)
		assertEquals(
			0,
			comparison.differences.size,
			"Encountered differences after round-trip batch creation. That was kind of expected." +
				"\nLast manual check looked good with 245 differences"
		)
	// expected (and found deltas):
	// - each PCM element has a different id since it is unique and those are newly generated elements
	// - FailureType is lost, because it is not propagated (limited scope of this master's thesis)
	}

	@Test
	def void testMinimalRepositoryWithCompositeTypeRoundtrip_PCM2UML2PCM() {
		var pcmRepo_forward = RepositoryFactory.eINSTANCE.createRepository
		pcmRepo_forward.entityName = "TestRepository"
		val pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmRepo_forward.dataTypes__Repository += pcmCompositeType
		pcmCompositeType.entityName = "TestCompositeType"
		val pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmInnerDeclaration
		pcmInnerDeclaration.entityName = "testAttribute"
		// TODO: fix handling of Primitive Types
		// pcmInnerDeclaration.datatype_InnerDeclaration = helper.PCM_INT
		simulateRepositoryInsertion_PCM(pcmRepo_forward, PCM_GENERATED_MEDIA_STORE_MODEL_PATH,
			UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		val safePcmRepoAfterForeward = Repository.from(
			getUri(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		val umlRepo_backward = Model.from(getUri(Path.of(UML_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		changePcmView[
			userInteraction.addNextConfirmationInput(true)
			EcoreUtil.delete(PcmQueryUtil.claimPcmRepository(it, "TestRepository"), false)
		]
		changeUmlView[
			EcoreUtil.delete(UmlQueryUtil.claimUmlModel(it, "model"), false)
		]

		simulateRepositoryInsertion_UML(umlRepo_backward, UML_GENERATED_MEDIA_STORE_MODEL_PATH,
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		validateUmlView[
			it.rootObjects.get(0).eResource.save(Map.of())
		]
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		val safePcmRepoAfterBackward = Repository.from(
			getUri(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)

		// expect 3 differences because of id changes
		val comparison = compare(safePcmRepoAfterForeward, safePcmRepoAfterBackward)
		assertEquals(3, comparison.differences.size,
			"Encountered differences after round-trip batch creation (that was kind of expected).")
	}

	@Test
	def void testMinimalRepositoryWithCompositeTypeRoundtrip_UML2PCM2UML() {
		var umlRepo_forward = UMLFactory.eINSTANCE.createModel
		umlRepo_forward.name = "umlrootmodel"
		val umlRepoPkg = umlRepo_forward.createNestedPackage("testRepository")
		val umlDatatypesPkg = umlRepoPkg.createNestedPackage(DefaultLiterals.DATATYPES_PACKAGE_NAME)
		umlRepoPkg.createNestedPackage(DefaultLiterals.CONTRACTS_PACKAGE_NAME)
		// TODO: fix handing of Primitive Types
		umlDatatypesPkg.createOwnedClass("TestCompositeType", false) // .createOwnedAttribute("testAttribute", helper.UML_INT)
		simulateRepositoryInsertion_UML(umlRepo_forward, UML_GENERATED_MEDIA_STORE_MODEL_PATH,
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		val saveUmlAfterForward = Model.from(getUri(Path.of(UML_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		val pcmRepo_forward = Repository.from(getUri(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)
		changeUmlView[
			EcoreUtil.delete(UmlQueryUtil.claimUmlModel(it, "umlrootmodel"), false)
		]

		simulateRepositoryInsertion_PCM(pcmRepo_forward, PCM_GENERATED_MEDIA_STORE_MODEL_PATH,
			UML_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		val saveUmlAfterBackward = Model.from(getUri(Path.of(UML_GENERATED_MEDIA_STORE_MODEL_PATH)).loadResource)

		// expect 0 differences because there are no changed IDs on the uml side
		val comparison = compare(saveUmlAfterForward, saveUmlAfterBackward)
		assertEquals(0, comparison.differences.size,
			"Encountered differences after round-trip batch creation (that was kind of expected).")
	}

	// --- helper ---
	def static Resource loadResource(URI uri) {
		val resourceSet = withGlobalFactories(new ResourceSetImpl())
		return resourceSet.getResource(uri, true)
	}

	def static <T extends EObject> T from(java.lang.Class<T> clazz, Resource resource) {
		return clazz.cast(resource.contents.get(0))
	}

	private static def loadPcmMsRepository() {
		val uri = URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH)
		val pcmMsRepositoryResource = loadResource(uri)
		assertNotNull(pcmMsRepositoryResource)
		val pcmMsRepository = Repository.from(pcmMsRepositoryResource)
		assertNotNull(pcmMsRepository)
		return pcmMsRepository
	}

	private static def loadUmlMsModel() {
		val uri = URI.createURI(UML_MEDIA_STORE_REPOSITORY_PATH)
		val umlMsRepositoryResource = loadResource(uri)
		assertNotNull(umlMsRepositoryResource)
		val umlMsModel = umlMsRepositoryResource.contents.head as Model
		return umlMsModel
	}

	// TODO: fix handling of PrimitiveTypes
	private static def removePrimitiveTypes(Model umlModel) {
		umlModel.eAllContents.forEach [
			val typeFeatures = it.eClass.EAllStructuralFeatures.filter[it.name == "type"].toList
			if (!typeFeatures.empty) {
				val typeFeature = typeFeatures.get(0)
				val type = it.eGet(typeFeature)
				if (type instanceof PrimitiveType) {
					it.eSet(typeFeature, null)
				}
			}
		]
		return umlModel
	}

	def Comparison compare(String originalWithinProjektPath, String generatedWithinProjektPath) {
		val originalUri = getUri(Path.of(originalWithinProjektPath))
		val generatedUri = getUri(Path.of(generatedWithinProjektPath))
		return compare(originalUri, generatedUri)
	}

	def static Comparison compare(URI originalUri, URI generatedUri) {
		val resourceSet = new ResourceSetImpl()
		val original = resourceSet.getResource(originalUri, true).contents.head
		val generated = resourceSet.getResource(generatedUri, true).contents.head
		return compare(original, generated)
	}

	def static Comparison compare(Notifier original, Notifier generated) {
		val comparator = EMFCompare.builder().build()
		val scope = new DefaultComparisonScope(original, generated, original)
		return comparator.compare(scope)
	}

	// --- insertion simulation ---
	/* Because of transitive change propagation between the Pcm and Uml domains, it is necessary to stepwise simulate
	 * the insertion of Uml-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
	 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
	 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
	 * This might make it necessary to provide the VM that runs the tests with additional heap space.
	 */
	protected def simulateRepositoryInsertion_PCM(Repository originalRepository, String pcmOutputPath,
		String umlOutputPath) {

		changePcmView[
			userInteraction.addNextTextInput(umlOutputPath) // answers where to save the corresponding .uml model
			registerRoot(originalRepository, Path.of(pcmOutputPath).uri)
		]
	}

	protected def void simulateRepositoryInsertion_UML(Model originalRepositoryModel, String umlOutputPath,
		String pcmOutputPath) {
		changeUmlView[
			userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
			userInteraction.addNextTextInput(pcmOutputPath)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			createAndRegisterRoot(originalRepositoryModel, Path.of(umlOutputPath).uri)
		]
	}

	private def simulateDataTypeInsertion_UML(String modelName, PackageableElement umlDataType) {
		changeUmlView [
			val repositoryPackage = UmlQueryUtil.claimUmlModel(it, modelName).nestedPackages.head
			assertNotNull(repositoryPackage)
			val datatypesPackage = repositoryPackage.nestedPackages.findFirst [
				it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME
			]
			assertNotNull(datatypesPackage)

			datatypesPackage.packagedElements += umlDataType
		]
	}

	private def simulateContractsPackageElementInsertion_UML(String modelName,
		PackageableElement originalContractsPackageElement) {
		if (originalContractsPackageElement instanceof Interface) {
			simulateInterfaceInsertion(modelName, originalContractsPackageElement)
		} else {
			simulateAnyContractsPackageElementInsertion(modelName, originalContractsPackageElement)
		}
	}

	private def simulateInterfaceInsertion(String modelName, Interface originalInterface) {
		val originalOperations = originalInterface.ownedOperations.clone

		changeUmlView[
			val repositoryPackage = UmlQueryUtil.claimUmlModel(it, modelName).nestedPackages.head
			assertNotNull(repositoryPackage)

			var contractsPackage = repositoryPackage.nestedPackages.findFirst [
				it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
			]
			assertNotNull(contractsPackage)

			// add each operation without its parameters because at least the returnParameters will be already generated
			for (operation : originalInterface.ownedOperations) {
				operation.ownedParameters.clear
			}
			contractsPackage.packagedElements += originalInterface
		]

		try {
			changeUmlView[
				val repositoryPackage = UmlQueryUtil.claimUmlModel(it, modelName).nestedPackages.head
				assertNotNull(repositoryPackage)
				var contractsPackage = repositoryPackage.nestedPackages.findFirst [
					it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
				]
				assertNotNull(contractsPackage)
				var generatedInterface = contractsPackage.packagedElements.filter(Interface).findFirst [
					it.name == originalInterface.name
				]
				assertNotNull(generatedInterface)

				for (originalOperation : originalOperations) {
					var generatedOperation = generatedInterface.operations.findFirst[it.name == originalOperation.name]

					// remove already generated parameters
					// generatedOperation.ownedParameters.clear
					for (originalParameter : originalOperation.ownedParameters) {
						val generatedParameter = generatedOperation.ownedParameters.findFirst [
							it.name == originalParameter.name
						]
						resolveElements(originalParameter, UmlQueryUtil.claimUmlModel(it, modelName).eResource, "name")
						if (generatedParameter !== null) {
							UmlQueryUtil.claimUmlModel(it, modelName)
						} else {
							generatedOperation.ownedParameters += originalParameter
						}

					/* 
					 * val insertedParameter = EcoreUtil.copy(originalParameter)
					 * insertedParameter.type = originalParameter.type === null
					 * 	? null
					 * 	: UmlQueryUtil.claimUmlModel(it, modelName).eAllContents.toList.filter[it instanceof Type].map [
					 * 	it as Type
					 * ].findFirst [
					 * 	it.name == originalParameter.type.name
					 * ]
					 * generatedOperation.ownedParameters += insertedParameter
					 */
					}
				}
			]
		} catch (IllegalArgumentException e) {
			if (!e.message.startsWith("This change contains no concrete change:")) {
				throw e
			}
		}
	}

	private def simulateAnyContractsPackageElementInsertion(String modelName,
		PackageableElement originalContractsPackageElement) {
		changeUmlView[
			val repositoryPackage = UmlQueryUtil.claimUmlModel(it, modelName).nestedPackages.head
			assertNotNull(repositoryPackage)

			var contractsPackage = repositoryPackage.nestedPackages.findFirst [
				it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
			]
			assertNotNull(contractsPackage)
			assertTrue(!(originalContractsPackageElement instanceof Interface))

			contractsPackage.packagedElements += originalContractsPackageElement

		]
	}

	private def simulateComponentInsertion_UML(String modelName, Package originalComponentPackage,
		int userDisambigutationComponentType) {
		val originalComponentImpl = originalComponentPackage.packagedElements.filter(Class).head
		assertNotNull(originalComponentImpl)

		// should generate generatedComponentImpl
		changeUmlView [
			var generatedModel = UmlQueryUtil.claimUmlModel(it, modelName)
			var generatedRepositoryPackage = generatedModel.nestedPackages.head
			assertNotNull(generatedRepositoryPackage)

			// Simulate adding a Component via round-trip by adding the components package.
			// For that, first remove the implementation from the original package to avoid duplication.
			originalComponentPackage.packagedElements -= originalComponentImpl
			assertTrue(originalComponentPackage.packagedElements.empty)

			userInteraction.addNextSingleSelection(userDisambigutationComponentType)
			generatedRepositoryPackage.nestedPackages += originalComponentPackage // throws UUID error when tested on its own
		]

		// should generate the constructor Parameters corresponding to RequiredRoles
		changeUmlView [
			var generatedModel = UmlQueryUtil.claimUmlModel(it, modelName)
			var generatedRepositoryPackage = generatedModel.nestedPackages.head
			assertNotNull(generatedRepositoryPackage)
			var generatedComponentPackage = generatedRepositoryPackage.nestedPackages.findFirst [
				it.name == originalComponentPackage.name
			]
			assertNotNull(generatedComponentPackage)
			var generatedComponentImpl = generatedComponentPackage.packagedElements.filter(Class).findFirst [
				it.name == originalComponentImpl.name
			]
			assertNotNull(generatedComponentImpl)

			// merge everything except operations which are separately handled, because some of the constructor parameters will be generated.
			resolveElements(originalComponentImpl, generatedModel.eResource, "ownedOperation", "interfaceRealization")
			mergeElements(originalComponentImpl, generatedComponentImpl, "ownedOperation", "interfaceRealization")
			for (originalRealization : originalComponentImpl.interfaceRealizations.clone) {
				resolveElements(originalRealization, generatedModel.eResource)
				originalComponentImpl.interfaceRealizations -= originalRealization
				originalRealization.clients -= originalComponentImpl // needs to be manually cleared, or else originalComponentImpl is still set as a client and causes UUID error
				generatedComponentImpl.interfaceRealizations += originalRealization
			}
		]

		// handle errors that are produced if the changeUmlView doesn't produce an actual change
		try {
			changeUmlView [
				var generatedModel = UmlQueryUtil.claimUmlModel(it, modelName)
				var generatedRepositoryPackage = generatedModel.nestedPackages.head
				assertNotNull(generatedRepositoryPackage)
				var generatedComponentPackage = generatedRepositoryPackage.nestedPackages.findFirst [
					it.name == originalComponentPackage.name
				]
				assertNotNull(generatedComponentPackage)
				var generatedComponentImpl = generatedComponentPackage.packagedElements.filter(Class).findFirst [
					it.name == originalComponentImpl.name
				]
				assertNotNull(generatedComponentImpl)

				// merge the original with the generated constructor
				val originalConstructor = originalComponentImpl.ownedOperations.findFirst [
					it.name == originalComponentImpl.name
				]
				val generatedConstructor = generatedComponentImpl.ownedOperations.findFirst [
					it.name == originalComponentImpl.name
				]
				assertNotNull(originalConstructor)
				assertNotNull(generatedConstructor)
				resolveElements(originalConstructor, generatedModel.eResource, "ownedParameter")
				mergeElements(originalConstructor, generatedConstructor, "ownedParameter")
				for (originalParameter : originalConstructor.ownedParameters.clone) {
					val generatedParameter = generatedConstructor.ownedParameters.findFirst [
						it.name == originalParameter.name
					]
					resolveElements(originalParameter, generatedModel.eResource, "name")
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
			]
		} catch (IllegalArgumentException e) {
			if (!e.message.startsWith("This change contains no concrete change:")) {
				throw e
			}
		}
	}

	protected def void simulateRepositoryInsertion_UML_STEPWISE(Model originalRepositoryModel, String umlOutputPath,
		String pcmOutputPath) {
		assertNotNull(originalRepositoryModel)
		val umlRepositoryPackage = originalRepositoryModel.nestedPackages.head
		assertNotNull(umlRepositoryPackage)
		val originalDatatypesPackage = umlRepositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME
		]
		val originalContractsPackage = umlRepositoryPackage.nestedPackages.findFirst [
			it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
		]
		val originalComponentPackages = umlRepositoryPackage.nestedPackages.filter [
			it !== originalContractsPackage && it !== originalDatatypesPackage
		].toList

		val insertedRepositoryModel = EcoreUtil.copy(originalRepositoryModel)
		val insertedUmlRepositoryPackage = insertedRepositoryModel.nestedPackages.head
		insertedUmlRepositoryPackage.nestedPackages.removeIf [
			!#[DefaultLiterals.DATATYPES_PACKAGE_NAME, DefaultLiterals.CONTRACTS_PACKAGE_NAME].contains(it.name)
		]
		insertedUmlRepositoryPackage.nestedPackages.forEach[it.packagedElements.clear]

		changeUmlView[
			userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY) // rootelement is supposed to be a repository
			userInteraction.addNextTextInput(pcmOutputPath) // answers where to save the corresponding .pcm model
			registerRoot(insertedRepositoryModel, Path.of(umlOutputPath).uri)
		]

		// Create copies of the lists outside the model so that containment is irrelevant
		// and that the loops do not iterate over a changing List.
		for (originalDatatype : originalDatatypesPackage.packagedElements.clone) {
			simulateDataTypeInsertion_UML(originalRepositoryModel.name, originalDatatype)

		}
		for (originalContractsPackageElement : originalContractsPackage.packagedElements.clone) {
			simulateContractsPackageElementInsertion_UML(originalRepositoryModel.name, originalContractsPackageElement)
		}

		for (originalComponentPackage : originalComponentPackages) {
			simulateComponentInsertion_UML(originalRepositoryModel.name, originalComponentPackage,
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
		}
	}

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

	private def static isResolveAndMergeRelevantFeature(EStructuralFeature feature, EObject object,
		String ... skipFeatures) {
		return !feature.derived && feature.changeable && object.eIsSet(feature) &&
			!skipFeatures.contains(feature.name) && if(feature instanceof EReference) !feature.isContainer else true
	}

	private def static resolve(EObject original, Resource in) {
		if (original.eResource === null) {
			val a = 1
		}

		checkNotNull(in.resourceSet.getEObject(in.URI.appendFragment(original.hierarchicUriFragment), true),
			"resolved object for %s", original)
	}
}