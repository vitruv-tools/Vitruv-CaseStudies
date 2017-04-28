package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.framework.tests.VitruviusApplicationTest
import org.eclipse.uml2.uml.Model
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.umlclassumlcomponents.class2comp.tests.Class2CompTestUtil
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.uml2.uml.Type

abstract class AbstractClass2CompTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml"
	protected static val MODEL_NAME = "model"
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model
	}
	
	/*protected def Model getRootElement(Type umlPackage) {
		return umlPackage.package.name.projectModelPath.firstRootElement as Model
	}*/

	//hack for handling of one singular UML model instead of two
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
	
	//hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
	
	//saveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		Class2CompTestUtil.sendCollectedUserInteractionSelections(this.userInteractor)
		saveAndSynchronizeChanges(object)
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		initializeTestModel()
	}

}

