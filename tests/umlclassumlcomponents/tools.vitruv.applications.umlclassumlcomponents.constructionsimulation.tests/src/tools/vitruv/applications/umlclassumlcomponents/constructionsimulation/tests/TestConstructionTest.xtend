package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test

class TestConstructionTest extends ModelConstructionTest {
	
	@Test
	def void dataTypeTest() {
		val integrationStrategy = new UmlIntegrationStrategy()
		val Resource resource = integrationStrategy.loadModel("model/datatype.uml")
		val changes = integrationStrategy.createChangeModels(null, resource)
		createAndSynchronizeModel("model/model.uml", resource.contents.get(0))
		testUserInteractor.addNextSelections(1)
		triggerSynchronization(changes)
}
}