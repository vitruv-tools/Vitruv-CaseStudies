package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import static org.junit.Assert.*
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.PackageableElement

class Comp2ClassTest extends AbstractComp2ClassTest {
	private static val COMP_NAME = "TestUmlComp"
	private static val COMP_NAME2 = "TestUmlComp2"
	private static val DATATYPE_NAME = "TestDataType"
	private static val PROPERTY_NAME = "TestProperty"
	private static val OPERATION_NAME = "TestOperation"

	@Test
		public def void testModelCreation() {
			assertModelExists("model/" + MODEL_NAME + ".uml")
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTrue(umlModel instanceof Model)
			assertEquals(MODEL_NAME, (umlModel as Model).name)
	}
	
	/***********
	*Component:*
	***********/
	private def org.eclipse.uml2.uml.Component createComponent(String name) {
		val umlComponent = UMLFactory.eINSTANCE.createComponent()
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		return umlComponent
	}
	
	private def void checkClass(PackageableElement umlComp, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTrue(umlClass instanceof org.eclipse.uml2.uml.Class)
		assertEquals(name, (umlClass as org.eclipse.uml2.uml.Class).name)
	}
	
	@Test
	public def void testCreateClassForComponent() {
		val umlComp = createComponent(COMP_NAME)
		saveAndSynchronizeChanges(umlComp)
		checkClass(umlComp, COMP_NAME)
	}
	
	@Test
    public def void testRenameComponent() {
    	val umlComp = createComponent("Old")
		saveAndSynchronizeChanges(umlComp)
		//change name:
		umlComp.name = "New"
		saveAndSynchronizeChanges(umlComp)
		//check if rename happened in class:
		checkClass(umlComp, "New")
    }
    
	@Test
    public def void testDeleteComponent() {
    	val umlComp = createComponent(COMP_NAME)	 
		saveAndSynchronizeChanges(umlComp)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		val umlClass = correspondingElements.get(0)		
		//remove component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()		
		assertFalse(rootElement.packagedElements.contains(umlComp))
		//rootElement.packagedElements.remove(umlComp)		
		saveAndSynchronizeChanges(rootElement)
		//check if class exists:		
		assertFalse(rootElement.packagedElements.contains(umlClass))
    }
	
	@Test
	public def void testCreate2ClassFor2Component() {
		val umlComp1 = createComponent(COMP_NAME)
		val umlComp2 = createComponent(COMP_NAME2)
		saveAndSynchronizeChanges(rootElement)
		checkClass(umlComp1, COMP_NAME)
		checkClass(umlComp2, COMP_NAME2)
	}
	
    /**********
	*Datatype:*
	**********/
	@Test
	public def void testCreateClassForDataType() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		checkClass(compDataType, DATATYPE_NAME)		
	}
	
    private def org.eclipse.uml2.uml.DataType createDataType(String name, int createClass) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		this.testUserInteractor.addNextSelections(createClass); //Decide to create corresponding class
		dataType.name = name
		rootElement.packagedElements += dataType
		return dataType
	}
	
	@Test
	public def void testAddPropertyToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as org.eclipse.uml2.uml.Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = PROPERTY_NAME
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		assertEquals(1, umlClass.ownedAttributes.size)
		val classProperty = umlClass.ownedAttributes.get(0)
		assertTrue(classProperty instanceof org.eclipse.uml2.uml.Property)
		assertEquals(PROPERTY_NAME, (classProperty as org.eclipse.uml2.uml.Property).name)				
	}
	
	@Test
    public def void testRenameDataTypeProperty() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as org.eclipse.uml2.uml.Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = "Old"
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		//change name:
		compProperty.name = "New"
		saveAndSynchronizeChanges(compProperty)
		//check if rename happened in class property:
		val classProperty = umlClass.ownedAttributes.get(0)
		assertEquals("New", (classProperty as org.eclipse.uml2.uml.Property).name)
    }
	
	@Test
	public def void testAddOperationToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as org.eclipse.uml2.uml.Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = OPERATION_NAME
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)
		assertEquals(1, umlClass.ownedOperations.size)
		val classOperation = umlClass.ownedOperations.get(0)
		assertTrue(classOperation instanceof org.eclipse.uml2.uml.Operation)
		assertEquals(OPERATION_NAME, (classOperation as org.eclipse.uml2.uml.Operation).name)				
	}
		
	@Test
    public def void testRenameDataTypeOperation() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as org.eclipse.uml2.uml.Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = "Old"
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)
		//change name:
		compOperation.name = "New"
		saveAndSynchronizeChanges(compOperation)
		//check if rename happened in class operation:
		val classOperation = umlClass.ownedOperations.get(0)
		assertEquals("New", (classOperation as org.eclipse.uml2.uml.Operation).name)
    }
	
}

