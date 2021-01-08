package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.constructionsimulation

import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.EObject
import static extension tools.vitruv.applications.pcmumlcomponents.tests.util.ExistingModelUtil.loadModel

class ExisitingModelsTest extends ModelConstructionTest {
	
	@Test
	def void mediastoreTest() {
		userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        userInteraction.addNextSingleSelection(1)
        val originalResource = loadModel("model/mediastore.uml")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}
	
	@Test
	def void mediastoreRoundtripTest() {
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(3)
		val originalResource = loadModel("model/mediastore_generated.uml")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}
	
	@Test
	def void smallExampleRoundtripTest() {
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		val originalResource = loadModel("model/small_example.uml")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}
}