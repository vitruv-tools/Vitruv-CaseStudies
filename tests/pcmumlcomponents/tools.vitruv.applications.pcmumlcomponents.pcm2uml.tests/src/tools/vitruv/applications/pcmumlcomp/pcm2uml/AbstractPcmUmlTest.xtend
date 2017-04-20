package tools.vitruv.applications.pcmumlcomp.pcm2uml

import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest

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
	
	protected def initializeTestModel() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.entityName = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, pcmRepository);
	}
	
	protected def Model getUmlModel() {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		return (correspondingElements.get(0) as Model)
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		initializeTestModel()
	}

}
