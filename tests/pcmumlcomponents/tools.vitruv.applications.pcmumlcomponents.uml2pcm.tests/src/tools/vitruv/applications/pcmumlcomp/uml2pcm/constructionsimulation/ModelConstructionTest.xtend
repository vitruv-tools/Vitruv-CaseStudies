package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest

class ModelConstructionTest extends VitruviusApplicationTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
	
	protected static val TARGET_MODEL_NAME = "model.uml"
				
	override protected createChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel]
	}
	
	protected def Model getRootElement(Resource resource) {
		return resource.allContents.head as Model
	}
	
	protected def loadModel(String path) {
		val resourceSet = new ResourceSetImpl()
		return resourceSet.getResource(URI.createFileURI(path), true)
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
	}
	
}