package tools.vitruv.applications.pcmumlcomponents.uml2pcm.constructionsimulation

import org.junit.Test

class ExisitingModelsTest extends ModelConstructionTest {

	@Test
	def void mediastoreTest() {
		val resource = loadModel("model/mediastore.uml")
		userInteractor.addNextSelections(1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}

	@Test
	def void mediastoreRoundtripTest() {
		val resource = loadModel("model/mediastore_generated.uml")
		userInteractor.addNextSelections(1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}

	@Test
	def void smallExampleRoundtripTest() {
		val resource = loadModel("model/small_example.uml")
		userInteractor.addNextSelections(1, 1, 1, 1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
}
