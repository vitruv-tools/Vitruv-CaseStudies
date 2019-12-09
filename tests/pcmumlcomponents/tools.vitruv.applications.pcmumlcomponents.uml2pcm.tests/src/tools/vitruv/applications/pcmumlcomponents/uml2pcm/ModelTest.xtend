package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.*

class ModelTest extends AbstractUmlPcmTest {

	@Test
	public def void testRepositoryCreation() {
		assertModelExists("repository/" + MODEL_NAME + ".repository");
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(2, correspondingElements.size); // repository AND model tag
		val correspondingRepositories = correspondingElements.filter[it instanceof Repository].toList
		assertEquals(1, correspondingRepositories.size); // but should be only one repository
		val pcmRepository = correspondingRepositories.get(0);
		assertEquals(MODEL_NAME, (pcmRepository as Repository).entityName);
	}

	@Test
	public def void testRepositoryRename() {
		val newName = 'foo';
		rootElement.name = newName;
		saveAndSynchronizeChanges(rootElement);
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		val pcmRepository = correspondingElements.get(0);
		assertEquals(newName, (pcmRepository as Repository).entityName);
	}
}