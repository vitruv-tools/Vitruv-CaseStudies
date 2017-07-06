package tools.vitruv.applications.pcmumlcomponents.pcm2uml.constructionsimulation

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

class ExistingModelsTest extends ModelConstructionTest {
	
	@Test
	public def void smallExampleTest() {
		val resource = loadModel("model/small_example.repository")
		//loadPrimitiveTypes(resource.resourceSet)
		val repository = resource.allContents.head as Repository
		constructRepository(repository)
		
	}
	
	@Test
	public def void mediastoreTest() {
		val resource = loadModel("model/mediastore.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	public def void mediastoreRoundtripTest() {
		val resource = loadModel("model/mediastore_current.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	public def void smallExampleRoudtripTest() {
		val resource = loadModel("model/small_example_generated.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
}