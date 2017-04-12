package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test
import tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.Strategy.UmlComponentIntegrationStrategy
import org.eclipse.uml2.uml.PackageableElement
import static org.junit.Assert.*
import org.eclipse.uml2.uml.Model

class TestConstructionTest extends ModelConstructionTest {
		
	private def void checkClass(PackageableElement umlComp, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTrue(umlClass instanceof org.eclipse.uml2.uml.Class)
		assertEquals(name, (umlClass as org.eclipse.uml2.uml.Class).name)
	}
	
	@Test
	def void firstTest() {
		val integrationStrategy = new UmlComponentIntegrationStrategy()
		val Resource resource = integrationStrategy.loadModel("TestModels/TestModel1.uml")
		val changes = integrationStrategy.createChangeModels(null, resource)
		createAndSynchronizeModel("model/model.uml", resource.contents.get(0))
		//testUserInteractor.addNextSelections(0,0,0,0)
		triggerSynchronization(changes)
		val Model umlModel = resource.contents.get(0) as Model //RootElement is Model
		val umlComp1 = umlModel.packagedElements.get(0)
		checkClass(umlComp1, "Test Component 2")
		val umlComp2 = umlModel.packagedElements.get(1)
		checkClass(umlComp2, "Test Component")
	}

}