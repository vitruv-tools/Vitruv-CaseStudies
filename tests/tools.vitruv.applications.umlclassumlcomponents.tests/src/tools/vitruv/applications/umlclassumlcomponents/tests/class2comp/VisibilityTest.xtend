package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Component

class VisibilityTest extends AbstractClass2CompTest {

	@Test
	def void testVisibilityChange() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					visibility = VisibilityKind.PRIVATE_LITERAL
				]
			]
		]

		var component = rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		assertEquals(VisibilityKind.PRIVATE_LITERAL, component.visibility)

		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX).propagate [
			claimPackagedElementWithName(Class, CLASS_NAME) => [
				visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]

		component = rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		assertEquals(VisibilityKind.PUBLIC_LITERAL, component.visibility)
	}

}
