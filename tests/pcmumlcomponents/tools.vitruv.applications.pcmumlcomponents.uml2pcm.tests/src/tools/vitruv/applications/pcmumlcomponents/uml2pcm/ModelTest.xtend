package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.junit.Test

import static org.junit.Assert.*

class ModelTest extends AbstractUmlPcmTest {

	@Test
	def void testRepositoryCreation() {
		assertModelExists("repository/" + MODEL_NAME + ".repository");
		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(MODEL_NAME, pcmRepository.entityName);
	}

	@Test
	def void testRepositoryRename() {
		val newName = 'foo';
		rootElement.name = newName;
		saveAndSynchronizeChanges(rootElement);
		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(newName, pcmRepository.entityName);
	}
}