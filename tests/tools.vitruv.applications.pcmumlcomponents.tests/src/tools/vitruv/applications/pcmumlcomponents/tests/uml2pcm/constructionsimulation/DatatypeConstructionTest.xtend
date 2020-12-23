package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.constructionsimulation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.jupiter.api.Test

class DatatypeConstructionTest extends ModelConstructionTest {
		
	@Test
	def void dataTypeTest() {
		val Resource resource = loadModel("model/datatype.uml")
		userInteraction.addNextSingleSelection(1)
		createAndSynchronizeModel("model/" + TARGET_MODEL_NAME, resource.rootElement)
	}
	
}