package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource

class ModelTest extends AbstractClass2CompTest {

	@Test
	def void testModelCreation() {
		assertThat(getUri(MODEL_NAME.projectModelPath), isResource)
		rootElement // No exception when retrieving root model
	}

}
