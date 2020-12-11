package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import java.nio.file.Path

class ModelTest extends AbstractComp2ClassTest {
	
	/*******
	*Tests:*
	********/	
	
	@Test
	//This test covers usage of one as well as two Models
	def void testModelCreation() {
		//Check Model:
		val modelUri = getPlatformModelUri(Path.of(FOLDER_NAME).resolve(MODEL_NAME + "." + MODEL_FILE_EXTENSION))
		assertThat(modelUri, isResource);
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