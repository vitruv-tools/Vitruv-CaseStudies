package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml.constructionsimulation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.jupiter.api.Test

class DatatypeConstructionTest extends ModelConstructionTest {
	
	@Test
	def void dataTypeTest() {
		val Resource resource = loadModel("model/datatype.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
	
}