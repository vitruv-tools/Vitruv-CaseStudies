package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml.constructionsimulation

import org.palladiosimulator.pcm.repository.Repository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

class ExistingModelsTest extends ModelConstructionTest {
	
	@Test
	def void smallExampleTest() {
		val resource = loadModel("model/small_example.repository")
		val repository = resource.allContents.head as Repository
		constructRepository(repository)
		
	}
	
	@Test
	@Disabled("For ParametricResourceDemand currently not TUID can be calculated")
	def void mediastoreTest() {
		val resource = loadModel("model/mediastore.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	def void mediastoreRoundtripTest() {
		val resource = loadModel("model/mediastore_generated.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	def void smallExampleRoudtripTest() {
		val resource = loadModel("model/small_example_generated.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
}