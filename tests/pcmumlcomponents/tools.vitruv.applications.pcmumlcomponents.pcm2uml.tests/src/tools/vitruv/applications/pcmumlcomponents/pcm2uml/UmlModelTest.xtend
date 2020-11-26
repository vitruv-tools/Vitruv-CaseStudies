package tools.vitruv.applications.pcmumlcomponents.pcm2uml

import org.eclipse.uml2.uml.Model
import org.junit.Test

import static org.junit.Assert.*

class UmlModelTest extends AbstractPcmUmlTest {

	@Test
	def void testModelCreation() {
		assertModelExists("model/" + MODEL_NAME + ".uml");
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(1, correspondingElements.size);
		val umlModel = correspondingElements.get(0);
		assertTrue(umlModel instanceof Model);
		assertEquals(MODEL_NAME, (umlModel as Model).name);
	}
	
	@Test
	def void testModelRename() {
		val newName = 'foo';
		rootElement.entityName = newName;
		saveAndSynchronizeChanges(rootElement);
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(newName, (correspondingElements.get(0) as Model).name);
	}
}