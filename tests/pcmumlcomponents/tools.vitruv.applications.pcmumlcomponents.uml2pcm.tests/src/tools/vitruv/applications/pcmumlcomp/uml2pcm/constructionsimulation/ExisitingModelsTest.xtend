package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import org.junit.Test

class ExisitingModelsTest extends ModelConstructionTest {
	
	@Test
	public def void mediastoreTest() {
		val resource = loadModel("model/mediastore.uml")
		userInteractor.addNextSelections(1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
	
	@Test
	public def void mediastoreRoundtripTest() {
		val resource = loadModel("model/mediastore_generated.uml")
		userInteractor.addNextSelections(1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
	
	@Test
	public def void smallExampleRoundtripTest() {
		val resource = loadModel("model/small_example.uml")
		userInteractor.addNextSelections(1, 1, 1, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
}