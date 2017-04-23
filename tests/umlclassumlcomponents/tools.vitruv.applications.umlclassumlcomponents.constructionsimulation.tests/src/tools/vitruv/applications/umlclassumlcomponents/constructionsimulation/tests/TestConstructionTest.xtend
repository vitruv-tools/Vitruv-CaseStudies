package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test
import tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.strategy.UmlComponentIntegrationStrategy
import org.eclipse.uml2.uml.PackageableElement
import static org.junit.Assert.*
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.DataType

class TestConstructionTest extends AbstractConstructionTest {
			
	/********
	*Helper:*
	*********/
	
	private def void checkClass(PackageableElement umlComp, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTrue(umlClass instanceof org.eclipse.uml2.uml.Class)
		assertEquals(name, (umlClass as org.eclipse.uml2.uml.Class).name)
	}
	
	private def void checkComponent(PackageableElement umlComponent, String name) {
		assertTrue(umlComponent instanceof Component)
		assertEquals(name, (umlComponent as Component).name)
	}
	
	private def void checkInterface(PackageableElement umlInterface, String name) {
		assertTrue(umlInterface instanceof Interface)
		assertEquals(name, (umlInterface as Interface).name)
	}
	
	private def void checkDataType(PackageableElement umlDataType, String name) {
		assertTrue(umlDataType instanceof DataType)
		assertEquals(name, (umlDataType as DataType).name)
	}
	
	private def Resource getTestModelResource(String fileName) {
		val integrationStrategy = new UmlComponentIntegrationStrategy()
		return integrationStrategy.loadModel("TestModels/" + fileName)
	} 
	
	private def Resource constructionTest(String fileName) {
		val fileResource = getTestModelResource(fileName)
   		
		//val changes = integrationStrategy.createChangeModels(null, resource) //TODO fix this
		
		val inputResource = createAndSynchronizeModel2("model/model.uml", fileResource.allContents.head)
		
		//TODO User Interaction might be needed in the future		
		//testUserInteractor.addNextSelections(0,0,0,0)
		
		//triggerSynchronization(changes) //TODO fix this
		
		return inputResource
	}
	
	/*******
	*Tests:*
	********/
	
	@Test
	def void constructionTest2Components(){
		val inputResource = constructionTest("TestModel2Components.uml")
		
		//Check inputResource (Component Diagram)
		val Model inModel = inputResource.contents.get(0) as Model //RootElement is Model
		checkComponent(inModel.packagedElements.get(0), "Test Component")
		checkComponent(inModel.packagedElements.get(1), "Test Component 2")
		
		
		//Check resultResource (Class Diagram)
		/*
		//TODO resultResource = ??? 
		val Model outModel = resultResource.contents.get(0) as Model //RootElement is Model
		checkClass(outModel.packagedElements.get(0), "Test Component")
		checkClass(outModel.packagedElements.get(1), "Test Component 2")
		*/
		
		//Check propagated changes
		//TODO implement this?		
	}
	
	@Test
	def void constructionTestWithDataType(){
		val inputResource = constructionTest("TestModelWithDataType.uml")
		
		//Test inputResource (Component Diagram)
		val Model inModel = inputResource.contents.get(0) as Model //RootElement is Model
		checkComponent(inModel.packagedElements.get(0), "Test Component")
		checkDataType(inModel.packagedElements.get(1), "Test Data Type")
		
		//TODO Check for results here, too
	}
	
	
	

}