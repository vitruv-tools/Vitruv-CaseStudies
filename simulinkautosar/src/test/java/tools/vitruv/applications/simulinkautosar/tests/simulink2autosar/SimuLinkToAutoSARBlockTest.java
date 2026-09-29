package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar;

import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkBlock;

import edu.kit.ipd.sdq.metamodels.simulink.SubSystem;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

public class SimuLinkToAutoSARBlockTest extends AbstractSimuLinkToAutoSARTest {

	private static final String DEFAULT_BLOCK_NAME = "Testblock";
	private static final String DEFAUL_SUBSYSTEM_NAME = "TestSubsystem";

	/*
	 *
	 * Tests for Blocks and Subsystems
	 *
	 */

	@Test
	public void testCreateBlock() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		equalityValidation.assertBlockWithNameInRootModel(DEFAULT_BLOCK_NAME);
	}

	@Test
	public void testDeleteBlock() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			EcoreUtil.remove(claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_BLOCK_NAME);
	}

	@Test
	public void testCreateSubsystem() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(block);
		});

		equalityValidation.assertSubSystemtWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME);
	}

	@Test
	public void testDeleteSubsystem() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		viewFactory.changeSimuLinkView(view -> {
			EcoreUtil.remove((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME);
	}

	@Test
	public void testSubSystemAndCheckContainedBlocks() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(block);
		});
		viewFactory.changeSimuLinkView(view -> {
			EcoreUtil.remove((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME);
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_BLOCK_NAME);
	}
}
