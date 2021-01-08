package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue

class VisibilityTest extends AbstractClass2CompTest {

	@Test
	def void testVisibilityChange() {
		val umlClass = createClass(CLASS_NAME, 0)
		umlClass.visibility = VisibilityKind.PRIVATE_LITERAL
		saveAndSynchronizeWithInteractions(umlClass)
		assertVisibility(umlClass)

		umlClass.visibility = VisibilityKind.PUBLIC_LITERAL
		saveAndSynchronizeWithInteractions(umlClass)
		assertVisibility(umlClass)
	}

	private def void assertVisibility(NamedElement element) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[element]).flatten
		for (EObject corrElement : correspondingElements) {
			assertTrue((corrElement as NamedElement).visibility == element.visibility)
		}

	}

}
