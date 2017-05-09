package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*

class ModelTest extends AbstractComp2ClassTest {
	
	/*******
	*Tests:*
	********/	
	
	@Test
	//This test covers usage of one as well as two Models
	public def void testModelCreation() {
		//Check Model:
		assertModelExists(FOLDER_NAME + MODEL_NAME + "." + MODEL_FILE_EXTENSION)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		val classModel = correspondingElements.filter(Model).get(0)
		assertTypeAndName(classModel, Model, MODEL_NAME)
		
		//Check DataType Package
		val packagedElements = (classModel as Model).packagedElements
		assertEquals(1, packagedElements.size)
		val dataTypePackage = packagedElements.get(0)
		assertTypeAndName(dataTypePackage, Package, CLASS_DATATYPES_PACKAGE_NAME)
	}
	
		
}