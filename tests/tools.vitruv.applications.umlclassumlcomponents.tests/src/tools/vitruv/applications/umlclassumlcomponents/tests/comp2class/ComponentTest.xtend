package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse

class ComponentTest extends AbstractComp2ClassTest {

	/*******
	 * Tests:*
	 ********/
	@Test
	def void testCreateClassForComponent() {
		val umlComp = createComponent(COMP_NAME)
		propagate

		assertClassAndPackage(umlComp, COMP_NAME)
	}

	@Test
	def void testRenameComponent() {
		val umlComp = createComponent("Old")
		propagate

		// Change name:
		umlComp.name = "New"
		propagate

		// Check if rename happened in Class & Package:
		assertClassAndPackage(umlComp, "New")
	}

	@Test
	def void testDeleteComponentWithPackage() {
		val umlComp = createComponent(COMP_NAME)
		propagate

		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = umlClass.package
		val classModel = umlClass.model

		// Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		propagate

		// Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
	}

	@Test
	def void testDeleteComponentWithoutPackage() {
		val umlComp = createComponent(COMP_NAME)
		propagate

		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = umlClass.package
		val classModel = umlClass.model

		// Package gets more members:
		val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		// Add new Class to Package in transaction:
		virtualModel.executeCommand([
			classPackage.packagedElements += umlClass2
			return null
		])

		// Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains("Delete the corresponding Package") && message.contains("and all its contained elements")
		].respondWith("No")
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeWithInteractions(rootElement)

		// Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertTrue(classModel.packagedElements.contains(classPackage))
		assertTrue(classPackage.packagedElements.contains(umlClass2))
	}

	@Test
	def void testDeleteComponentWithPackageAndContents() {
		val umlComp = createComponent(COMP_NAME)
		propagate

		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = umlClass.package
		val classModel = umlClass.model

		// Package gets more members:
		val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		// Add new Class to Package in transaction:
		virtualModel.executeCommand([
			classPackage.packagedElements += umlClass2
			return null
		])

		// Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains("Delete the corresponding Package") && message.contains("and all its contained elements")
		].respondWith("Yes")
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeWithInteractions(rootElement)

		// Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
		assertFalse(classModel.packagedElements.contains(umlClass2))
	}

	@Test
	def void testCreate2ClassFor2Component() {
		val umlComp1 = createComponent(COMP_NAME)
		val umlComp2 = createComponent(COMP_NAME2)
		propagate
		assertClassAndPackage(umlComp1, COMP_NAME)
		assertClassAndPackage(umlComp2, COMP_NAME2)
	}
}
