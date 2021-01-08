package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Model

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import java.nio.file.Path

class ModelTest extends AbstractClass2CompTest {

	/*******
	 * Model:*
	 ********/
	// Model creation currently unused due to usage of one singular Model
	@Test
	def void testModelCreation() {
		val modelUri = getUri(Path.of(FOLDER_NAME).resolve(MODEL_NAME + "." + MODEL_FILE_EXTENSION))
		assertThat(modelUri, isResource)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		assertEquals(1, correspondingElements.size)
		val umlModel = correspondingElements.get(0)
		assertTypeAndName(umlModel, Model, MODEL_NAME)
	}

}
