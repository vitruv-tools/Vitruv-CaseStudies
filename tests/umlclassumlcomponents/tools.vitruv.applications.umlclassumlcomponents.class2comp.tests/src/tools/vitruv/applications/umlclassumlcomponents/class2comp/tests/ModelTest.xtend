package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.uml2.uml.Model
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*

class ModelTest extends AbstractClass2CompTest {
	
	/*******
	*Model:*
	********/
	//Model creation currently unused due to usage of one singular Model
	
	@Test
		public def void testModelCreation() {
			assertModelExists(FOLDER_NAME + MODEL_NAME + "." + MODEL_FILE_EXTENSION)
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTypeAndName(umlModel, Model, MODEL_NAME)
	}
	
}