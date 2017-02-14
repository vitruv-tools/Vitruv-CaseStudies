package tool.vitruv.applications.pcmumlcomp.uml2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.*

class UmlToPcmTest extends AbstractUmlPcmTest {

	@Test
	public def void testRepositoryCreation() {
		assertModelExists("repository/" + MODEL_NAME + ".repository");
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(1, correspondingElements.size);
		val pcmRepository = correspondingElements.get(0);
		assertTrue(pcmRepository instanceof Repository);
		assertEquals(MODEL_NAME, (pcmRepository as Repository).entityName);
	}
}