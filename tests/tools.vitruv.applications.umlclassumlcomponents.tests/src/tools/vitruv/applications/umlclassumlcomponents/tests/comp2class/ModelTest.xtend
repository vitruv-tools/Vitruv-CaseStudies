package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Package

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource

class ModelTest extends AbstractComp2ClassTest {

	@Test
	def void testModelCreation() {
		assertThat(getUri(MODEL_NAME.projectModelPath), isResource)
		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
	}

}
