package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm.AbstractUmlPcmTest

class ModelConstructionTest extends AbstractUmlPcmTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
	
	protected static val TARGET_MODEL_NAME = "model.uml"
	
	protected def Model getRootElement(Resource resource) {
		return resource.allContents.head as Model
	}
	
	protected def loadModel(String path) {
		val resourceSet = new ResourceSetImpl()
		return resourceSet.getResource(URI.createURI(path), true)
	}
	
}