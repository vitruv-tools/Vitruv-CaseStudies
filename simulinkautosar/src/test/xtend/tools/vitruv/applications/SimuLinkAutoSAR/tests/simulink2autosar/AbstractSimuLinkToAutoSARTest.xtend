package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar

import org.junit.jupiter.api.BeforeEach
import edu.kit.ipd.sdq.metamodels.simulink.Block
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkFactory
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel
import tools.vitruv.applications.simulinkautosar.tests.SimuLinkAutoSARTransformationTest
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
