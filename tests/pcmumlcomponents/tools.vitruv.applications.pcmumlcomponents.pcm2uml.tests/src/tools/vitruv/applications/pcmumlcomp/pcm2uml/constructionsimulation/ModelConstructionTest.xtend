package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlcomp.pcm2uml.AbstractPcmUmlTest
import tools.vitruv.domains.pcm.util.RepositoryModelLoader

abstract class ModelConstructionTest extends AbstractPcmUmlTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
		
	protected val TARGET_MODEL_NAME = "model/model.repository"
	
	protected def Resource loadModel(String path) {
		return RepositoryModelLoader.loadPcmResource(path)
	}
	
	protected def Repository getRootElement(Resource resource) {
		return resource.allContents.head as Repository
	}
	
	override protected def initializeTestModel() {
		
	}
	
}