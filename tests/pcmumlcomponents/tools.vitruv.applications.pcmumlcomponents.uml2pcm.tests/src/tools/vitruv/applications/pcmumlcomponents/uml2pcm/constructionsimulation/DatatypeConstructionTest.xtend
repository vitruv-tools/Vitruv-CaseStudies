package tools.vitruv.applications.pcmumlcomponents.uml2pcm.constructionsimulation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test

class DatatypeConstructionTest extends ModelConstructionTest {
		
	@Test
	def void dataTypeTest() {
		val Resource resource = loadModel("model/datatype.uml")
		userInteractor.addNextSelections(1)
		createAndSynchronizeModel("model/" + TARGET_MODEL_NAME, resource.rootElement)
	}
	
}