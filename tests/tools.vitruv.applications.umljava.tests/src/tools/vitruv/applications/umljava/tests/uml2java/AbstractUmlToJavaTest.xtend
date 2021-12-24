package tools.vitruv.applications.umljava.tests.uml2java

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.getUniqueUmlModel
import org.eclipse.uml2.uml.Model

abstract class AbstractUmlToJavaTest extends UmlJavaTransformationTest {
	/**
	 * Changes the UML model in the UML view and commits the performed changes.
	 * 
	 * Precondition: Can only be applied if single UML model exists
	 */
	protected def void changeUmlModel((Model)=>void modelModification) {
		changeView(createUmlView) [
			modelModification.apply(uniqueUmlModel)
		]
	}
}