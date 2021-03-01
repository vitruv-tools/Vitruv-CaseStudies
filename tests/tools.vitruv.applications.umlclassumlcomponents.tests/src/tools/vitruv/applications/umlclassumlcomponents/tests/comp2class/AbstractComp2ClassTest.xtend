package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation

import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path

import tools.vitruv.testutils.VitruvApplicationTest

abstract class AbstractComp2ClassTest extends VitruvApplicationTest {

	override protected getChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
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

	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.projectModelPath)
	}
	
	protected def Path getProjectModelPath(String modelName) {
		Path.of(FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

}
