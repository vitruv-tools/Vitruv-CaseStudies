package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test

class DatatypeConstructionTest extends ModelConstructionTest {
	
	@Test
	def void dataTypeTest() {
		val Resource resource = loadModel("model/datatype.repository")
		saveModel(resource.rootElement)
	}
	
}