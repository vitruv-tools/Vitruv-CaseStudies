package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation
import tools.vitruv.domains.uml.UmlDomain
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.metamodel.Metamodel

class AbstractComp2ClassTest extends VitruviusChangePropagationTest {
	protected static val MODEL_FILE_EXTENSION = "uml"
	protected static val MODEL_NAME = "model"
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.root as Model
	}

	override protected createMetamodels() {
		//return #[new UmlDomain().metamodel, new UmlDomain().metamodel]
		return #[new UmlDomain().metamodel]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()];
	}
	
	override protected initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
	
	override protected getCorrespondenceModel(){
		//hack for UML
		val Metamodel umlMM = metamodels.iterator().next;
		return this.getVirtualModel().getCorrespondenceModel(umlMM.getURI(), umlMM.getURI()); 
	}

	
}

