package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest

import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

abstract class AbstractClass2CompTest extends VitruviusApplicationTest {
		
	private def String getProjectModelPath(String modelName) {
		FOLDER_NAME + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model
	}
	
	//Hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}
	
	protected def initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
	
	//Hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
	
	//SaveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		sendCollectedUserInteractionSelections(this.userInteractor)
		saveAndSynchronizeChanges(object)
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		initializeTestModel()
	}

}

