package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.domains.uml.UmlDomain
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.metamodel.Metamodel
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.umlclassumlcomponents.class2comp.tests.Class2CompTestUtil
import org.eclipse.uml2.uml.Type

abstract class AbstractClass2CompTest extends VitruviusChangePropagationTest {
	protected static val MODEL_FILE_EXTENSION = "uml"
	protected static val MODEL_NAME = "model"
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.root as Model
	}
	
	/*protected def Model getRootElement(Type umlPackage) {
		return umlPackage.package.name.projectModelPath.root as Model
	}*/

	override protected createMetamodels() {
		//return #[new UmlDomain().metamodel, new UmlDomain().metamodel]
		return #[new UmlDomain().metamodel]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}
	
	override protected initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
	
	//hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val Metamodel umlMM = metamodels.iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlMM.getURI(), umlMM.getURI()) 
	}
	
	//saveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		Class2CompTestUtil.sendCollectedUserInteractionSelections(this.testUserInteractor)
		saveAndSynchronizeChanges(object)
	}

}

