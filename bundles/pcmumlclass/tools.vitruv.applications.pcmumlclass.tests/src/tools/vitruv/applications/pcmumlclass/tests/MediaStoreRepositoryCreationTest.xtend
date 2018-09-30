package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.common.util.URI
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Ignore
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals

import static org.junit.Assert.*

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
class MediaStoreRepositoryCreationTest extends PcmUmlClassApplicationTest {

//	private static val PCM_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms.repository"
	// all SEFFs removed because the TUID-generator failed for ResourceDemandParameters 
	private static val PCM_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_noSEFF.repository" 
	private static val UML_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_repository_noSEFF_unedited.uml"
	 
	private static val UML_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.uml"
	private static val PCM_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.repository"
	private static val UML_GENERATED_MEDIA_STORE_MODEL_PATH_2 = "model-gen/ms_repository_backward.uml"
	private static val PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2 = "model-gen/ms_repository_backward.repository"

	@Test
	@Ignore //skip until needed because of performance
	def void testMediaStoreRepositoryCreation_PCM2UML() {
		val uri = URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH)
		val pcmMsRepositoryResource = uri.testResource
		assertNotNull(pcmMsRepositoryResource)
		val pcmMsRepository = pcmMsRepositoryResource.contents.head as Repository
		assertNotNull(pcmMsRepository)
		
		simulateRepositoryInsertion_PCM(pcmMsRepository, PCM_GENERATED_MEDIA_STORE_MODEL_PATH, UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
	}
	
	@Test
	@Ignore //skip until needed because of performance
	def void testMediaStoreRepositoryCreation_UML2PCM() {
		val uri = URI.createURI(UML_MEDIA_STORE_REPOSITORY_PATH)
		val umlMsRepositoryResource = uri.testResource
		assertNotNull(umlMsRepositoryResource)
		val umlMsModel = umlMsRepositoryResource.contents.head as Model
		assertNotNull(umlMsModel)
		simulateRepositoryInsertion_UML(umlMsModel, UML_GENERATED_MEDIA_STORE_MODEL_PATH, PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
	}
	
	@Test
	@Ignore //skip until needed because of performance
	def void testMediaStoreRepositoryCreation_PCM2UML2PCM() {
		// forwards
		val uri = URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH)
		val pcmMsRepositoryResource = uri.testResource
		assertNotNull(pcmMsRepositoryResource)
		val pcmMsRepository = pcmMsRepositoryResource.contents.head as Repository
		assertNotNull(pcmMsRepository)
		
		simulateRepositoryInsertion_PCM(pcmMsRepository, PCM_GENERATED_MEDIA_STORE_MODEL_PATH, UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		
		// backwards 
		val umlMsRepositoryResource = UML_GENERATED_MEDIA_STORE_MODEL_PATH.modelVuri.EMFUri.testResource
		assertNotNull(umlMsRepositoryResource)
		val umlMsModel = umlMsRepositoryResource.contents.head as Model
		assertNotNull(umlMsModel)
		simulateRepositoryInsertion_UML(umlMsModel, UML_GENERATED_MEDIA_STORE_MODEL_PATH_2, PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		
		val comparison = compare(
			URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH), 
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2.modelVuri.EMFUri
		)
		assertEquals("Encountered differences after round-trip batch creation. That was kind of expected."
			+ "\nLast manual check looked good with 245 differences", 0, comparison.differences.size
		)
		// expected (and found deltas):
		//	- each PCM element has a different id since it is unique and those are newly generated elements
		//	- FailureType is lost, because it is not propagated (limited scope of this master's thesis)
	}
	
	@Test
	@Ignore
	def void testMinimalRepositoryWithCompositeTypeRoundtrip_PCM2UML2PCM() {
		var pcmRepo_forward = RepositoryFactory.eINSTANCE.createRepository
		pcmRepo_forward.entityName = "TestRepository"
		val pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmRepo_forward.dataTypes__Repository += pcmCompositeType
		pcmCompositeType.entityName = "TestCompositeType"
		val pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmInnerDeclaration
		pcmInnerDeclaration.entityName = "testAttribute"
		pcmInnerDeclaration.datatype_InnerDeclaration = helper.PCM_INT
		
		pcmRepo_forward = simulateRepositoryInsertion_PCM(pcmRepo_forward, PCM_GENERATED_MEDIA_STORE_MODEL_PATH, UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		
		val umlRepo_backward = UML_GENERATED_MEDIA_STORE_MODEL_PATH.modelVuri.EMFUri.testResource.contents.head as Model
		simulateRepositoryInsertion_UML(umlRepo_backward, UML_GENERATED_MEDIA_STORE_MODEL_PATH_2, PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		
		//expect 3 differences because of id changes
		val comparison = compare(PCM_GENERATED_MEDIA_STORE_MODEL_PATH, PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertEquals("Encountered differences after round-trip batch creation (that was kind of expected).", 3, comparison.differences.size)
	}
	
	@Test
	@Ignore
	def void testMinimalRepositoryWithCompositeTypeRoundtrip_UML2PCM2UML() {
		var umlRepo_forward = UMLFactory.eINSTANCE.createModel
		umlRepo_forward.name = "umlrootmodel"
		val umlRepoPkg = umlRepo_forward.createNestedPackage("testRepository")
		val umlDatatypesPkg = umlRepoPkg.createNestedPackage(DefaultLiterals.DATATYPES_PACKAGE_NAME)
		umlRepoPkg.createNestedPackage(DefaultLiterals.CONTRACTS_PACKAGE_NAME)
		val umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass("TestCompositeType", false)
		umlCompositeTypeClass.createOwnedAttribute("testAttribute", helper.UML_INT)
		
		umlRepo_forward = simulateRepositoryInsertion_UML(umlRepo_forward, UML_GENERATED_MEDIA_STORE_MODEL_PATH, PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		
		val pcmRepo_forward = PCM_GENERATED_MEDIA_STORE_MODEL_PATH.modelVuri.EMFUri.testResource.contents.head as Repository
		simulateRepositoryInsertion_PCM(pcmRepo_forward, PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2, UML_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		
		//expect 0 differences because there are no changed IDs on the uml side
		val comparison = compare(UML_GENERATED_MEDIA_STORE_MODEL_PATH, UML_GENERATED_MEDIA_STORE_MODEL_PATH_2)
		assertEquals("Encountered differences after round-trip batch creation (that was kind of expected).", 0, comparison.differences.size)
	}
	
}
