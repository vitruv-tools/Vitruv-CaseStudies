package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.pcm.util.RepositoryModelLoader
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest

abstract class ModelConstructionTest extends VitruviusApplicationTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
		
	protected val TARGET_MODEL_NAME = "model.repository"
	
	protected def Resource loadModel(String path) {
		return RepositoryModelLoader.loadPcmResource(path)
	}
	
	protected def Repository getRootElement(Resource resource) {
		return resource.allContents.head as Repository
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]
	}
	
	override protected getVitruvDomains() {
		return #[new PcmDomainProvider().domain, new UmlDomainProvider().domain]
	}
	
	override protected cleanup() {
		
	}
	
	override protected setup() {
	}
	
}