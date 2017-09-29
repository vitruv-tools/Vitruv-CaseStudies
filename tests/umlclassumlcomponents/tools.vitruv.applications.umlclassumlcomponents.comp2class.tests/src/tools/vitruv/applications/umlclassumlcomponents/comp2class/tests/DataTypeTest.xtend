package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import org.junit.Ignore

class DataTypeTest extends AbstractComp2ClassTest{
	
    /*******
	*Tests:*
	********/
	
	@Test
	public def void testCreateClassForDataType() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		assertClassAndPackage(compDataType, DATATYPE_NAME, CLASS_DATATYPES_PACKAGE_NAME, false)			
	}
	
	@Test
	public def void testAddPropertyToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = PROPERTY_NAME
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		
		assertEquals(1, umlClass.ownedAttributes.size)		
		val classProperty = umlClass.ownedAttributes.get(0)
		assertTypeAndName(classProperty, Property, PROPERTY_NAME)				
	}
	
	@Ignore
	@Test
    public def void testRenameDataTypeProperty() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = "Old"
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		
		//Change name:
		compProperty.name = "New"
		saveAndSynchronizeChanges(compProperty)
		
		//Check if rename happened in Class Property:
		val classProperty = umlClass.ownedAttributes.get(0)
		assertEquals("New", (classProperty as Property).name)
    }
	
	@Test
	public def void testAddOperationToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = OPERATION_NAME
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)		
		
		assertEquals(1, umlClass.ownedOperations.size)		
		val classOperation = umlClass.ownedOperations.get(0)
		assertTypeAndName(classOperation, Operation, OPERATION_NAME)			
	}
	
	@Ignore	
	@Test
    public def void testRenameDataTypeOperation() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = "Old"
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)
		
		//Change name:
		compOperation.name = "New"
		saveAndSynchronizeChanges(compOperation)
		
		//Check if rename happened in Class Operation:
		val classOperation = umlClass.ownedOperations.get(0)
		assertEquals("New", (classOperation as Operation).name)
    }
    
    		
	/*****************
	*Creation Helper:*
	******************/		
		
    private def DataType createDataType(String name, int createClass) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		this.userInteractor.addNextSelections(createClass) //Decide to create corresponding Class
		dataType.name = name
		rootElement.packagedElements += dataType
		return dataType
	}		
}