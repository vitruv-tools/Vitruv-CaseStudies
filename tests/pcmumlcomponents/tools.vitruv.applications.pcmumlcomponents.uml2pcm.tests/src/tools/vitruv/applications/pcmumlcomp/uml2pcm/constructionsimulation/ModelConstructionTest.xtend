package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.VURI

class ModelConstructionTest extends VitruviusEMFCasestudyTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
	
	protected static val TARGET_MODEL = "model"
	protected static val MODEL_FILE_EXTENSION = "uml"
	
	protected var Resource targetResource;
			
	override protected createChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel]
	}
	
	override void beforeTest() {
		super.beforeTest()
		targetResource = resourceSet.createResource(URI.createPlatformResourceURI(TARGET_MODEL.projectModelPath, true))
	}
	
	private def String getProjectModelPath(String modelName) {
		this.currentTestProjectName + "/model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement(Resource resource) {
		return resource.allContents.head as Model
	}
	
	protected def void saveAndSynchronizeChanges(Resource resource) {
		EcoreResourceBridge.saveResource(resource)
		triggerSynchronization(VURI.getInstance(resource))
	}
	
	protected def saveModel(Model model) {
		changeRecorder.beginRecording(VURI.getInstance(targetResource), #[targetResource])
		targetResource.contents += model
		saveAndSynchronizeChanges(targetResource)
	}
	
	protected def loadModel(String path) {
		val resourceSet = new ResourceSetImpl()
		return resourceSet.getResource(URI.createFileURI(path), true)
	}
}