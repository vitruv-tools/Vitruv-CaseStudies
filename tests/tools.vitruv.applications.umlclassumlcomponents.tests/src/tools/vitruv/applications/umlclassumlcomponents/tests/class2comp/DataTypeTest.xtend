package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertEquals

class DataTypeTest extends AbstractClass2CompTest {
		
   	/***********
	*DataTypes:*
	************/
	
    @Test
    def void testCreateDataTypeForClass() {
    	val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten
		assertEquals(1, correspondingElements.size)
		val compDataType = correspondingElements.get(0)
		assertTypeAndName(compDataType, DataType, CLASS_NAME)
	}

	@Test
	def void testAddPropertyToDataType() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classProperty = UMLFactory.eINSTANCE.createProperty()
		classProperty.name = PROPERTY_NAME
		classDataType.ownedAttributes += classProperty
		propagate
		
		assertEquals(1, compDataType.ownedAttributes.size)		
		val compProperty = compDataType.ownedAttributes.get(0)
		assertTypeAndName(compProperty, Property, PROPERTY_NAME)				
	}
	
	@Test
    def void testRenameDataTypeProperty() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classProperty = UMLFactory.eINSTANCE.createProperty()
		classProperty.name = "Old"
		classDataType.ownedAttributes += classProperty
		propagate
		
		//Change name:
		classProperty.name = "New"
		propagate
		
		//Check if rename happened in Component Property:
		val compProperty = compDataType.ownedAttributes.get(0)
		assertEquals("New", compProperty.name)
    }
	
	@Test
	def void testAddOperationToDataType(){
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classOperation = UMLFactory.eINSTANCE.createOperation()
		classOperation.name = OPERATION_NAME
		classDataType.ownedOperations += classOperation
		propagate		
		
		assertEquals(1, compDataType.ownedOperations.size)		
		val compOperation = compDataType.ownedOperations.get(0)
		assertTypeAndName(compOperation, Operation, OPERATION_NAME)			
	}
		
	@Test
    def void testRenameDataTypeOperation() {
		val classDataType = createDataTypeClass(CLASS_NAME)
		saveAndSynchronizeWithInteractions(classDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classDataType]).flatten		
		val compDataType = (correspondingElements.get(0) as DataType)
		val classOperation = UMLFactory.eINSTANCE.createOperation()
		classOperation.name = "Old"
		classDataType.ownedOperations += classOperation
		propagate
		
		//Change name:
		classOperation.name = "New"
		propagate
		
		//Check if rename happened in Component Operation:
		val compOperation = compDataType.ownedOperations.get(0)
		assertEquals("New", compOperation.name)
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