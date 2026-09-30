package tools.vitruv.applications.cbs.commonalities.tests.uml;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.UML;

public final class UmlTestModelHelper {

	private UmlTestModelHelper() {
	}

	public static Model newUmlModel() {
		Model umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.setName(UML.MODEL_NAME);
		return umlModel;
	}
}
