package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.*

class ModelTest extends AbstractUmlPcmTest {

	@Test
	def void testRepositoryCreation() {
		assertModelExists("repository/" + MODEL_NAME + ".repository")
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(1, correspondingElements.size)
		val pcmRepository = correspondingElements.get(0)
		assertTrue(pcmRepository instanceof Repository)
		assertEquals(MODEL_NAME, (pcmRepository as Repository).entityName)
	}

	@Test
	def void testRepositoryRename() {
		val newName = 'foo'
		rootElement.name = newName
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		val pcmRepository = correspondingElements.get(0)
		assertEquals(newName, (pcmRepository as Repository).entityName);
	}
}
