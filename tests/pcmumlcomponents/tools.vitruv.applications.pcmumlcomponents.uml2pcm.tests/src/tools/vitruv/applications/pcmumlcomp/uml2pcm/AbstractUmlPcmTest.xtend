package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusChangePropagationTest

class AbstractUmlPcmTest extends VitruviusChangePropagationTest {
	protected static val MODEL_FILE_EXTENSION = "uml";
	protected static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"repository/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.getProjectModelPath.root as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel];
	}
	
	override protected initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.getProjectModelPath, umlModel);
	}

}
