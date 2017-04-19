package tool.vitruv.applications.pcmumlcomp.pcm2uml

import tools.vitruv.aplications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.domains.pcm.PcmDomainProvider

class AbstractPcmUmlTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "repository";
	protected static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Repository getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Repository;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain, new PcmDomainProvider().domain];
	}
	
	override protected setup() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.entityName = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, pcmRepository);
	}

	override protected cleanup() {
		// Do nothing
	}
	
}
