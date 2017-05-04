package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.PackageableElement
import org.junit.Test
import tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.strategy.UmlComponentIntegrationStrategy

import static org.junit.Assert.*

class TestConstructionTest extends AbstractConstructionTest {
			
	/********
	*Helper:*
	*********/
	
	private def void checkClass(PackageableElement umlComp, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTrue(umlClass instanceof Class)
		assertEquals(name, (umlClass as Class).name)
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
	
	private def Resource constructionTest(String fileName) { //TODO remove fully?
		
		val fileResource = getTestModelResource(fileName)
   		
   		val integrationStrategy = new UmlComponentIntegrationStrategy()
		val changes = integrationStrategy.createChangeModels(null, fileResource) //TODO fix this
		
		val inputResource = createAndSynchronizeModelWithReturnedResource(OUTPUT_NAME, fileResource.allContents.head) 
		
		//TODO User Interaction might be needed in the future		
		//testUserInteractor.addNextSelections(0,0,0,0)
		
		triggerSynchronization(changes) //TODO fix this
		
		return inputResource
	}
	
	/*******
	*Tests:*
	********/
	
	@Test //TODO Temp, remove
	def void naiiveConstructionTest2Components() {
		val Resource inputResource = getTestModelResource("TestModel2Components.uml")
		createAndSynchronizeModel(OUTPUT_NAME, inputResource.allContents.head)
		
		val allCorr = correspondenceModel.allCorrespondences.get(0)
		val model1 = allCorr.getAs().get(0)
		val model2 = correspondenceModel.getCorrespondingEObjects(#[model1]).flatten.get(0)
		val test = "No Results here"
	}
		
	@Test //TODO Temp, remove
	def void integrationConstructionTest2Components() {		
		val integrationStrategy = new UmlComponentIntegrationStrategy()		
		val Resource inputResource = integrationStrategy.loadModel("TestModels/TestModel2Components.uml")
		val changes = integrationStrategy.createChangeModels(null, inputResource)
		createAndSynchronizeModel(OUTPUT_NAME, inputResource.allContents.head)
		triggerSynchronization(changes) 
		
		val allCorr = correspondenceModel.allCorrespondences.get(0)
		val model1 = allCorr.getAs().get(0)
		val model2 = correspondenceModel.getCorrespondingEObjects(#[model1]).flatten.get(0)
		val test = "No Results here"
	}
	
	@Test
	def void constructionTest2Components() {		
		
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