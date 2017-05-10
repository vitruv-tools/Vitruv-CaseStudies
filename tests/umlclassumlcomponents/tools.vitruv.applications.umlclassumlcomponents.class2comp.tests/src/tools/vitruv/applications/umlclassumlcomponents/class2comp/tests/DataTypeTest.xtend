package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*

class DataTypeTest extends AbstractClass2CompTest {
		
   	/***********
	*DataTypes:*
	************/
	
    @Test
    public def void testCreateDataTypeForClass() {
    	val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten
		assertEquals(1, correspondingElements.size)
		val compDataType = correspondingElements.get(0)
		assertTypeAndName(compDataType, DataType, CLASS_NAME)
	}

	@Test
	public def void testAddPropertyToDataType() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classProperty = UMLFactory.eINSTANCE.createProperty()
		classProperty.name = PROPERTY_NAME
		classDataType.ownedAttributes += classProperty
		saveAndSynchronizeChanges(classDataType)
		
		assertEquals(1, compDataType.ownedAttributes.size)		
		val compProperty = compDataType.ownedAttributes.get(0)
		assertTypeAndName(compProperty, Property, PROPERTY_NAME)				
	}
	
	@Test
    public def void testRenameDataTypeProperty() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classProperty = UMLFactory.eINSTANCE.createProperty()
		classProperty.name = "Old"
		classDataType.ownedAttributes += classProperty
		saveAndSynchronizeChanges(classDataType)
		
		//Change name:
		classProperty.name = "New"
		saveAndSynchronizeChanges(classDataType)
		
		//Check if rename happened in Component Property:
		val compProperty = compDataType.ownedAttributes.get(0)
		assertEquals("New", (compProperty as Property).name)
    }
	
	@Test
	public def void testAddOperationToDataType(){
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classOperation = UMLFactory.eINSTANCE.createOperation()
		classOperation.name = OPERATION_NAME
		classDataType.ownedOperations += classOperation
		saveAndSynchronizeChanges(classDataType)		
		
		assertEquals(1, compDataType.ownedOperations.size)		
		val compOperation = compDataType.ownedOperations.get(0)
		assertTypeAndName(compOperation, Operation, OPERATION_NAME)			
	}
		
	@Test
    public def void testRenameDataTypeOperation() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classOperation = UMLFactory.eINSTANCE.createOperation()
		classOperation.name = "Old"
		classDataType.ownedOperations += classOperation
		saveAndSynchronizeChanges(classDataType)
		
		//Change name:
		classOperation.name = "New"
		saveAndSynchronizeChanges(classOperation)
		
		//Check if rename happened in Component Operation:
		val compOperation = compDataType.ownedOperations.get(0)
		assertEquals("New", (compOperation as Operation).name)
    }   
    
     			
	/*****************
	*Creation Helper:*
	******************/		
		
    private def Class createDataTypeClass(String name) {
		val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE, 0, 0)
    	val classDataType = createClassWithoutInteraction(name, dataTypePackage)
		return classDataType
	}
}