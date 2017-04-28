package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation 
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.framework.tests.VitruviusApplicationTest
import org.eclipse.uml2.uml.Model
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractComp2ClassTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml"
	protected static val MODEL_NAME = "model" 
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model
	}

	//hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
	}
	
	protected def initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
	
	//hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		initializeTestModel()
	}

	
}

