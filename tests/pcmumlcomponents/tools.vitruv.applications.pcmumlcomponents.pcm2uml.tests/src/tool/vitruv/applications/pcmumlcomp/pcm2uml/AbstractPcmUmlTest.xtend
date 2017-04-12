package tool.vitruv.applications.pcmumlcomp.pcm2uml

import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import tools.vitruv.aplications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Repository

class AbstractPcmUmlTest extends VitruviusChangePropagationTest {
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
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel];
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
