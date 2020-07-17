package tools.vitruv.applications.cbs.commonalities.tests.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.UML

@Utility
class UmlTestModelHelper {

	static def newUmlModel() {
		return UMLFactory.eINSTANCE.createModel => [
			name = UML.MODEL_NAME
		]
	}
}
