package tools.vitruv.applications.umlclassumlcomponents.tests.util

import tools.vitruv.testutils.VitruvApplicationTest
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path

import org.eclipse.uml2.uml.Model
import tools.vitruv.change.propagation.ChangePropagationMode

abstract class AbstractUmlClassCompTest extends VitruvApplicationTest {
	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.projectModelPath)
	}

	@BeforeEach
	protected def void disableTransitiveChangePropagation() {
		virtualModel.changePropagationMode = ChangePropagationMode.SINGLE_STEP
	}
	
	@BeforeEach
	def setup() {
		if (isInitializeTestModel()) {
			initializeTestModel()
		}
	}

	protected def boolean isInitializeTestModel() {
		return true
	}

	private def void initializeTestModel() {
		resourceAt(MODEL_NAME.projectModelPath).propagate [
			contents += UMLFactory.eINSTANCE.createModel() => [
				name = MODEL_NAME
			]
		]
	}

	protected def Path getProjectModelPath(String modelName) {
		Path.of(FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

}
