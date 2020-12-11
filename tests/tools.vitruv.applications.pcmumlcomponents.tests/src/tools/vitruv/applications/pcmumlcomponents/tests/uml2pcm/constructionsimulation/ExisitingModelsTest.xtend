package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.constructionsimulation

import org.junit.jupiter.api.Test

class ExisitingModelsTest extends ModelConstructionTest {
	
	@Test
	def void mediastoreTest() {
		val resource = loadModel("model/mediastore.uml")
		userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
        userInteractor.addNextSingleSelection(1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
	
	@Test
	def void mediastoreRoundtripTest() {
		val resource = loadModel("model/mediastore_generated.uml")
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(3)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
	
	@Test
	def void smallExampleRoundtripTest() {
		val resource = loadModel("model/small_example.uml")
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		userInteractor.addNextSingleSelection(1)
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
	}
}