package tools.vitruv.applications.pcmumlclass.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlclass.DefaultLiterals

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled

/**
 * Model creation tests, to test the set of transformations as a whole. 
 * The tests mainly try to validate if the transformations terminate. To see if the results are correct manual verification of the output is necessary.
 */
class MediaStoreRepositoryCreationTest extends PcmUmlClassApplicationTest {
	// all SEFFs removed because the TUID-generator failed for ResourceDemandParameters 
	static val PCM_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/ms_noSEFF.repository"
	static val UML_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/ms_repository_noSEFF_unedited.uml"

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
	@Disabled
	// Previous implementations simulateRepositoryInsertion_UML relied on stepwise simulation of the insertion of the model.
	// As stepwise simulation isn't the target state and heavily depends on details of the reactions it has been removed.
	// This test case is disabled as the current implementation can't handle a complete insertion at once.
	def void testMediaStoreRepositoryCreation_UML2PCM() {
		var umlMsModel = loadUmlMsModel
				
		simulateRepositoryInsertion_UML(umlMsModel, UML_GENERATED_MEDIA_STORE_MODEL_PATH,
			PCM_GENERATED_MEDIA_STORE_MODEL_PATH)

		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
	}

	// --- helper ---
	
	private static def Resource loadResource(URI uri) {
		val resourceSet = withGlobalFactories(new ResourceSetImpl())
		return resourceSet.getResource(uri, true)
	}

	private def static <T extends EObject> T from(Class<T> clazz, Resource resource) {
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
			for(var i = 0; i < 20; i++){
				userInteraction.addNextSingleSelection(
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT)
			}
			createAndRegisterRoot(originalRepositoryModel, Path.of(umlOutputPath).uri)
		]
	}
}