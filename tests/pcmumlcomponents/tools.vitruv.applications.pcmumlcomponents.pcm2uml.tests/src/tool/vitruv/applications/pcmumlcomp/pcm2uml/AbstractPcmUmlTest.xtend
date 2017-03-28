package tool.vitruv.applications.pcmumlcomp.pcm2uml

import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model

class AbstractPcmUmlTest extends VitruviusChangePropagationTest {
	protected static val MODEL_FILE_EXTENSION = "repository";
	protected static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Repository getRootElement() {
		return MODEL_NAME.projectModelPath.root as Repository;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel];
	}
	
	override protected initializeTestModel() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.entityName = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, pcmRepository);
	}
	
	protected def Model getUmlModel() {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		return (correspondingElements.get(0) as Model)
	}

}
