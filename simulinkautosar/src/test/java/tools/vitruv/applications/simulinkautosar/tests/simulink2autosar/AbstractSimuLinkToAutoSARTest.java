package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar;

import edu.kit.ipd.sdq.metamodels.simulink.Block;
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkFactory;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel;
import edu.kit.ipd.sdq.metamodels.simulink.SingleConnection;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.applications.simulinkautosar.tests.SimuLinkAutoSARTransformationTest;

public abstract class AbstractSimuLinkToAutoSARTest extends SimuLinkAutoSARTransformationTest {

	@BeforeEach
	protected void setup() throws Exception {
		createSimuLinkModel(model -> model.setName(SIMULINK_MODEL_NAME));
	}

	protected void createSimuLinkModel(Consumer<SimulinkModel> simuLinkModelInitialization) throws Exception {
		viewFactory.changeSimuLinkView(view -> {
			SimulinkModel simuLinkModel = SimuLinkFactory.eINSTANCE.createSimulinkModel();
			simuLinkModelInitialization.accept(simuLinkModel);
			createAndRegisterRoot(view, simuLinkModel, getUri(getProjectModelPath(SIMULINK_MODEL_NAME)));
		});
	}

	protected void changeSimuLinkModel(Consumer<SimulinkModel> modelModification) throws Exception {
		viewFactory.changeSimuLinkView(view -> modelModification.accept(getDefaultSimuLinkModel(view)));
	}

	private void addBlockToRootModel(Block newBlock, String name) throws Exception {
		changeSimuLinkModel(model -> {
			newBlock.setName(name);
			model.getContains().add(newBlock);
		});
	}

	protected void createBlockInModel(String blockName) throws Exception {
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createBlock(), blockName);
		equalityValidation.assertBlockWithNameInRootModel(blockName);
	}

	protected void createOutPortBlockinModel(String blockName) throws Exception {
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createOutPortBlock(), blockName);
		equalityValidation.assertBlockWithNameInRootModel(blockName);
	}

	protected void createSubsystemInModel(String subSystemName) throws Exception {
		addBlockToRootModel(SimuLinkFactory.eINSTANCE.createSubSystem(), subSystemName);
		equalityValidation.assertBlockWithNameInRootModel(subSystemName);
	}

	protected void createSingleConnectionInModel(String name) throws Exception {
		changeSimuLinkModel(model -> {
			SingleConnection singleConnection = SimuLinkFactory.eINSTANCE.createSingleConnection();
			singleConnection.setName(name);
			model.getConnection().add(singleConnection);
		});
	}
}
