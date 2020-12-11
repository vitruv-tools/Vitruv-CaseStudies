package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.tests.util.UserInteractionTestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse

class ComponentTest extends AbstractComp2ClassTest{
	
	/*******
	*Tests:*
	********/
	
	@Test
	def void testCreateClassForComponent() {
		val umlComp = createComponent(COMP_NAME)
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
	}
	
	@Test
    def void testRenameComponent() {
    	val umlComp = createComponent("Old")
		saveAndSynchronizeChanges(rootElement)
		
		//Change name:
		umlComp.name = "New"
		saveAndSynchronizeChanges(rootElement)
		
		//Check if rename happened in Class & Package:
		assertClassAndPackage(umlComp, "New")
    }
    
	@Test
    def void testDeleteComponentWithPackage() {
    	val umlComp = createComponent(COMP_NAME)	 
		saveAndSynchronizeChanges(umlComp)
		
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeChanges(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
    }
	
	@Test
    def void testDeleteComponentWithoutPackage() {
    	val umlComp = createComponent(COMP_NAME)    	
		saveAndSynchronizeChanges(umlComp)
		
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Package gets more members:
    	val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		//Add new Class to Package in transaction:
		virtualModel.executeCommand([
			classPackage.packagedElements += umlClass2
			return null
		]) 
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		queueUserInteractionSelections(1) //Decide to not delete Package
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertTrue(classModel.packagedElements.contains(classPackage))
		assertTrue(classPackage.packagedElements.contains(umlClass2))		
    }
    
	@Test
    def void testDeleteComponentWithPackageAndContents() {
    	val umlComp = createComponent(COMP_NAME)    	
		saveAndSynchronizeChanges(umlComp)
		
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Package gets more members:
    	val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		//Add new Class to Package in transaction:
		virtualModel.executeCommand([
			classPackage.packagedElements += umlClass2 
			return null
		])
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		queueUserInteractionSelections(0) //Decide to delete Package and Contents
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
		assertFalse(classModel.packagedElements.contains(umlClass2))		
    }
    
	@Test
	def void testCreate2ClassFor2Component() {
		val umlComp1 = createComponent(COMP_NAME)
		val umlComp2 = createComponent(COMP_NAME2)
		saveAndSynchronizeChanges(rootElement)
		assertClassAndPackage(umlComp1, COMP_NAME)
		assertClassAndPackage(umlComp2, COMP_NAME2)
	}	
}