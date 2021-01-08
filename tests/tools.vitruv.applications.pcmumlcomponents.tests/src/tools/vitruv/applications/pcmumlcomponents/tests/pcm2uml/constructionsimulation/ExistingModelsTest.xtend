package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml.constructionsimulation

import org.palladiosimulator.pcm.repository.Repository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import org.eclipse.emf.ecore.EObject
import static extension tools.vitruv.applications.pcmumlcomponents.tests.util.ExistingModelUtil.loadModel

class ExistingModelsTest extends ModelConstructionTest {

	@Test
	def void smallExampleTest() {
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += Repository.from(loadModel("model/small_example.repository"))
		]
		propagate
	}

	@Test
	@Disabled("For ParametricResourceDemand currently not TUID can be calculated")
	def void mediastoreTest() {
		val originalResource = loadModel("model/mediastore.repository")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}

	@Test
	def void mediastoreRoundtripTest() {
		val originalResource = loadModel("model/mediastore_generated.repository")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}

	@Test
	def void smallExampleRoudtripTest() {
		val originalResource = loadModel("model/small_example_generated.repository")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}

}
