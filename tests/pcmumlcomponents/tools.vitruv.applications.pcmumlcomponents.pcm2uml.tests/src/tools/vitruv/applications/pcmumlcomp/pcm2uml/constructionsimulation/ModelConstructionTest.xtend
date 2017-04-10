package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.pcm.util.RepositoryModelLoader
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.VURI

abstract class ModelConstructionTest extends VitruviusEMFCasestudyTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
	
	protected var Resource targetResource;
	
	protected val TARGET_MODEL = "model"
	protected val MODEL_FILE_EXTENSION = "repository"
	
	override void beforeTest() {
		super.beforeTest()
		targetResource = resourceSet.createResource(URI.createPlatformResourceURI(TARGET_MODEL.projectModelPath, true))
	}
	
	protected def Resource loadModel(String path) {
		return RepositoryModelLoader.loadPCMResource(path)
	}
	
	private def String getProjectModelPath(String modelName) {
		this.currentTestProjectName + "/model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Repository getRootElement(Resource resource) {
		return resource.allContents.head as Repository
	}
	
	protected def void saveAndSynchronizeChanges(Resource resource) {
		EcoreResourceBridge.saveResource(resource)
		triggerSynchronization(VURI.getInstance(resource))
	}
	
	protected def saveModel(Repository model) {
		changeRecorder.beginRecording(VURI.getInstance(targetResource), #[targetResource])
		targetResource.contents += model
		saveAndSynchronizeChanges(targetResource)
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]
	}
	
	override protected createMetamodels() {
		return #[new PcmDomain().metamodel, new UmlDomain().metamodel]
	}
	
}