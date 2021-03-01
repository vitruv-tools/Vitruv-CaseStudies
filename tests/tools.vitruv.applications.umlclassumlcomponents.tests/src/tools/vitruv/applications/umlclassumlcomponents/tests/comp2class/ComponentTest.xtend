package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.UMLFactory

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package

class ComponentTest extends AbstractComp2ClassTest {

	@Test
	def void testCreateClassForComponent() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME)
	}

	@Test
	def void testRenameComponent() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
		]

		val changedCompName = COMP_NAME + "New"
		rootElement.claimPackagedElementWithName(Component, COMP_NAME).propagate [
			name = changedCompName
		]

		rootElement.claimNoPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimNoPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Component, changedCompName)
		rootElement.claimPackagedElementWithName(Package, changedCompName + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, changedCompName)
	}

	@Test
	def void testDeleteComponentWithPackage() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME)

		// Remove Component		
		rootElement.propagate [
			claimPackagedElementWithName(Component, COMP_NAME).destroy()
		]

		rootElement.claimNoPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimNoPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
	}

	@Test
	def void testDeleteComponentWithoutPackage() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME)

		rootElement.getPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).propagate [
			// Package gets more members:
			val umlClass2 = UMLFactory.eINSTANCE.createClass()
			umlClass2.name = CLASS_NAME2
			// Add new Class to Package in transaction:
			packagedElements += umlClass2
		]

		// Remove Component		
		rootElement.propagate [
			userInteraction.onMultipleChoiceSingleSelection [
				message.contains("Delete the corresponding Package") &&
					message.contains("and all its contained elements")
			].respondWith("No")
			claimPackagedElementWithName(Component, COMP_NAME).destroy()
		]

		userInteraction.assertAllInteractionsOccurred()

		rootElement.claimNoPackagedElementWithName(Component, COMP_NAME)
		val package = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		package.claimNoPackagedElementWithName(Class, COMP_NAME)
		package.claimPackagedElementWithName(Class, CLASS_NAME2)
	}

	@Test
	def void testDeleteComponentWithPackageAndContents() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME)

		rootElement.getPackagedElementWithName(Package, COMP_NAME + " Package").propagate [
			// Package gets more members:
			val umlClass2 = UMLFactory.eINSTANCE.createClass()
			umlClass2.name = CLASS_NAME2
			// Add new Class to Package in transaction:
			packagedElements += umlClass2
		]

		// Remove Component		
		rootElement.propagate [
			userInteraction.onMultipleChoiceSingleSelection [
				message.contains("Delete the corresponding Package") &&
					message.contains("and all its contained elements")
			].respondWith("Yes")
			claimPackagedElementWithName(Component, COMP_NAME).destroy()
		]
		userInteraction.assertAllInteractionsOccurred()

		rootElement.claimNoPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimNoPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
	}

	@Test
	def void testCreate2ClassFor2Component() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
			]
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME2
			]
		]
		rootElement.claimPackagedElementWithName(Component, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME)
		rootElement.claimPackagedElementWithName(Package, COMP_NAME2 + PACKAGE_SUFFIX).
			claimPackagedElementWithName(Class, COMP_NAME2)
	}
}
