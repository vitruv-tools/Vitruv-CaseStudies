package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.constructionsimulation

import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.EObject
import static extension tools.vitruv.applications.pcmumlcomponents.tests.util.ExistingModelUtil.loadModel

class DatatypeConstructionTest extends ModelConstructionTest {

	@Test
	def void dataTypeTest() {
		userInteraction.addNextSingleSelection(1)
		val originalResource = loadModel("model/datatype.uml")
		resourceAt(TARGET_MODEL_PATH).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}

}
