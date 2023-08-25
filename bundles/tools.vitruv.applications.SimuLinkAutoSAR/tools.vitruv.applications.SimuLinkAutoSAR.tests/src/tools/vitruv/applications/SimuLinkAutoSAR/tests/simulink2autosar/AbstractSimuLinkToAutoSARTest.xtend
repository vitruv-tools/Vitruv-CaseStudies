package tools.vitruv.applications.SimuLinkAutoSAR.tests.simulink2autosar

import org.junit.jupiter.api.BeforeEach
import simulink.Block
import simulink.SimuLinkFactory
import simulink.SimulinkModel
import tools.vitruv.applications.SimuLinkAutoSAR.tests.SimuLinkAutoSARTransformationTest
import tools.vitruv.applications.SimuLinkAutoSAR.tests.util.SimuLinkAutoSARClassifierEqualityValidation.*
import simulink.SimuLinkPackage

abstract class AbstractSimuLinkToAutoSARTest extends SimuLinkAutoSARTransformationTest {

	@BeforeEach
	def protected void setup() {
		createSimuLinkModel[name = SIMULINK_MODEL_NAME]
	}

	protected def void createSimuLinkModel((SimulinkModel)=>void simuLinkModelInitialization) {
		changeSimuLinkView [
			val simuLinkModel = SimuLinkFactory.eINSTANCE.createSimulinkModel()
			simuLinkModelInitialization.apply(simuLinkModel)
			createAndRegisterRoot(simuLinkModel,SIMULINK_MODEL_NAME.projectModelPath.uri)
		]
	}

	protected def void changeSimuLinkModel((SimulinkModel)=>void modelModification) {
		changeSimuLinkView [
			modelModification.apply(defaultSimuLinkModel)
		]
	}
	
	private def void addBlockToRootModel(Block newBlock, String name) {
		changeSimuLinkModel [
			contains += newBlock => [it.name = name]
		]
	}
	
	
	protected def void createBlockInModel(String blockName) {
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createBlock(), blockName)
		assertBlockWithNameInRootModel(blockName)
	}
	
	protected def void createOutPortBlockinModel(String blockName){
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createOutPortBlock(), blockName)
		assertBlockWithNameInRootModel(blockName)
	}
	
	protected def void createSubsystemInModel(String subSystemName) {
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createSubSystem(), subSystemName)
		assertBlockWithNameInRootModel(subSystemName)
	}
	
	protected def void createSingleConnectionInModel(String name){
		changeSimuLinkModel [
			connection += SimuLinkFactory.eINSTANCE.createSingleConnection => [it.name = name]
		]
	}
	
	
	
}
