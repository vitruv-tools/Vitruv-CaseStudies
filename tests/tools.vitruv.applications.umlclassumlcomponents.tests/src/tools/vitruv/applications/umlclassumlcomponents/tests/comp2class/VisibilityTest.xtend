package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.VisibilityKind

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component

class VisibilityTest extends AbstractComp2ClassTest {

	@Test
	def void testVisibilityChange() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				visibility = VisibilityKind.PRIVATE_LITERAL
			]
		]
		
		var componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		assertEquals(VisibilityKind.PRIVATE_LITERAL, componentPackage.visibility)
		assertEquals(VisibilityKind.PRIVATE_LITERAL, componentPackage.claimPackagedElementWithName(Class, COMP_NAME).visibility)
		
		rootElement.claimPackagedElementWithName(Component, COMP_NAME).propagate [
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
		
		componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		assertEquals(VisibilityKind.PUBLIC_LITERAL, componentPackage.visibility)
		assertEquals(VisibilityKind.PUBLIC_LITERAL, componentPackage.claimPackagedElementWithName(Class, COMP_NAME).visibility)
		
	}

}

