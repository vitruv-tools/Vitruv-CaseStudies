package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest

import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*
import org.eclipse.emf.ecore.resource.Resource
import java.io.IOException
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.Collections

abstract class AbstractClass2CompTest extends VitruviusApplicationTest {

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
		
	protected def initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
		
	private def String getProjectModelPath(String modelName) {
		FOLDER_NAME + modelName + "." + MODEL_FILE_EXTENSION
	}	
	
	//TODO: The following is a test to traverse containing packages in createAndSynchronizeModel() and add them too:  
//	
//	private def String getPlatformModelPath(String modelPathWithinProject) {
//		return this.getCurrentTestProject().getName() + "/" + modelPathWithinProject
//	}
//	
//	private def VURI getModelVuri(String modelPathWithinProject) {
//		return VURI.getInstance(getPlatformModelPath(modelPathWithinProject));
//	}
//	
//	private def void startRecordingChanges(Resource resource) {
//		val VURI vuri = VURI.getInstance(resource)
//		this.changeRecorder.beginRecording(vuri, Collections.singleton(resource))
//	}
//	
//	override protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) throws IOException {
//		if (modelPathInProject.isEmpty || rootElement === null) {
//			throw new IllegalArgumentException();
//		}
//		val resourceSet = new ResourceSetImpl();
//		val resource = resourceSet.createResource(getModelVuri(modelPathInProject).getEMFUri())
//		this.startRecordingChanges(resource)
//		resource.contents.add(rootElement)
//		for (Package p : (rootElement as Package).packagedElements.filter(Package)) {
//			resource.contents.add(p)
//		}		
//		saveAndSynchronizeChanges(rootElement)
//	}

}

