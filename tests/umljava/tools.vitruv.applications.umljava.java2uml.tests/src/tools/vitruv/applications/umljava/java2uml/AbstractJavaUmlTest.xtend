package tools.vitruv.applications.umljava.java2uml

import tools.vitruv.applications.umljava.java2uml.JavaToUmlChangePropagationSpecification
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model

class AbstractJavaUmlTest extends VitruviusChangePropagationTest {
	private static val MODEL_FILE_EXTENSION = "uml";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.root as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new JavaToUmlChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
	}
	
	override protected initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel);
	}

}
