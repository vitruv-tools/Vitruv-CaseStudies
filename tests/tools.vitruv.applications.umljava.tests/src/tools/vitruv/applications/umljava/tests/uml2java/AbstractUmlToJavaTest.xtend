package tools.vitruv.applications.umljava.tests.uml2java

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.BeforeEach

abstract class AbstractUmlToJavaTest extends UmlJavaTransformationTest {

	@BeforeEach
	def protected void setup() {
		createUmlModel[name = UML_MODEL_NAME]
	}

	/**
	 * Changes the UML model in the UML view and commits the performed changes.
	 * 
	 * Precondition: Can only be applied if single UML model exists
	 */
	protected def void changeUmlModel((Model)=>void modelModification) {
		changeView(createUmlView) [
			modelModification.apply(defaultUmlModel)
		]
	}

}
