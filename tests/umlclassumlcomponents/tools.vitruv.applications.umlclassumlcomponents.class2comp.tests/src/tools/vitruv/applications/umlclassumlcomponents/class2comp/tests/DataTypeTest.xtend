package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.uml2.uml.DataType
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
    	val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE, 0, 0)
    	val umlClass = createClassWithoutInteraction(CLASS_NAME, dataTypePackage)
		saveAndSynchronizeWithInteractions(umlClass)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val compDataType = correspondingElements.get(0)
		assertTypeAndName(compDataType, DataType, CLASS_NAME)
	}
	
	
	//TODO further tests with tags, property ect
	    	
}