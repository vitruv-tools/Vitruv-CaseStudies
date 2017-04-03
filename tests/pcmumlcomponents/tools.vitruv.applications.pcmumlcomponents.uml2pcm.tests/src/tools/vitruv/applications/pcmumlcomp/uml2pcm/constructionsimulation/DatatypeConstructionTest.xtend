package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import org.apache.log4j.Level
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test

class DatatypeConstructionTest extends ModelConstructionTest {
	
	@Test
	def void dataTypeTest() {
		logger.level = Level.ALL
		val integrationStrategy = new UmlIntegrationStrategy()
		val Resource resource = integrationStrategy.loadModel("model/datatype.uml")
		val changes = integrationStrategy.createChangeModels(null, resource)
		createAndSynchronizeModel("model/model.uml", resource.contents.get(0))
		testUserInteractor.addNextSelections(1)
		triggerSynchronization(changes)
	}
	
}