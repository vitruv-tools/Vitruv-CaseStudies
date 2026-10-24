package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar;

import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkBlock;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkConnection;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkPort;

import edu.kit.ipd.sdq.metamodels.simulink.InPort;
import edu.kit.ipd.sdq.metamodels.simulink.OutPort;
import edu.kit.ipd.sdq.metamodels.simulink.OutPortBlock;
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkFactory;
import edu.kit.ipd.sdq.metamodels.simulink.SingleConnection;
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

public class SimuLinkToAutoSARPortTest extends AbstractSimuLinkToAutoSARTest {

	private static final String DEFAULT_BLOCK_NAME = "Testblock";
	private static final String DEFAULT_BLOCK_NAME_TWO = "Testblock2";
	private static final String DEFAUL_SUBSYSTEM_NAME = "TestSubsystem";
	private static final String DEFAULT_INPORT_NAME = "TestPort";
	private static final String DEFAULT_OUTPORT_NAME = "TestOutPort";
	private static final String DEFAULT_SUBSYSTEM_OUTPORT_NAME = "SubSystemOutport";
	private static final String DEFAULT_SUBSYSTEM_INPORT_NAME = "SubSystemInport";
	private static final String DEFAULT_OUTPORT_BLOCK_NAME = "OutPortBlock";
	private static final String DEFAULT_SINGLECONNECTION_NAME = "SingleConnection";

	/*
	 *
	 * Tests for Ports
	 *
	 *
	 */

	@Test
	public void testCreateInPortinBlock() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			InPort inPort = SimuLinkFactory.eINSTANCE.createInPort();
			inPort.setName(DEFAULT_INPORT_NAME);
			block.getPorts().add(inPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME);
	}

	@Test
	public void testCreateOutPortinBlock() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			OutPort outPort = SimuLinkFactory.eINSTANCE.createOutPort();
			outPort.setName(DEFAULT_OUTPORT_NAME);
			block.getPorts().add(outPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME);
	}

	@Test
	public void testCreateInPortinSubSystem() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME);
			InPort inPort = SimuLinkFactory.eINSTANCE.createInPort();
			inPort.setName(DEFAULT_INPORT_NAME);
			block.getPorts().add(inPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAUL_SUBSYSTEM_NAME);
	}

	@Test
	public void testCreateOutPortinSubSystem() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME);
			OutPort outPort = SimuLinkFactory.eINSTANCE.createOutPort();
			outPort.setName(DEFAULT_OUTPORT_NAME);
			block.getPorts().add(outPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAUL_SUBSYSTEM_NAME);
	}

	@Test
	public void testDeleteInPortinBlock() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			InPort inPort = SimuLinkFactory.eINSTANCE.createInPort();
			inPort.setName(DEFAULT_INPORT_NAME);
			block.getPorts().add(inPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME);

		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			EcoreUtil.remove(claimSimuLinkPort(block, DEFAULT_INPORT_NAME));
		});

		equalityValidation.assertNoPortWithNameInComponent(DEFAULT_BLOCK_NAME, DEFAULT_INPORT_NAME);
	}

	@Test
	public void testDeleteOutPortinSwComponent() throws Exception {
		createBlockInModel(DEFAULT_BLOCK_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			OutPort outPort = SimuLinkFactory.eINSTANCE.createOutPort();
			outPort.setName(DEFAULT_OUTPORT_NAME);
			block.getPorts().add(outPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME);

		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			EcoreUtil.remove(claimSimuLinkPort(block, DEFAULT_OUTPORT_NAME));
		});

		equalityValidation.assertNoPortWithNameInComponent(DEFAULT_BLOCK_NAME, DEFAULT_OUTPORT_NAME);
	}

	/*
	 *
	 * Test of Connections
	 *
	 */

	@Test
	public void testCreateSingleConnectionFromBlockToOutPortBlock() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		createBlockInModel(DEFAULT_BLOCK_NAME);
		createOutPortBlockinModel(DEFAULT_OUTPORT_BLOCK_NAME);
		createSingleConnectionInModel(DEFAULT_SINGLECONNECTION_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			OutPort from = SimuLinkFactory.eINSTANCE.createOutPort();
			from.setName(DEFAULT_OUTPORT_NAME);
			block.getPorts().add(from);
			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(block);

			var outPortBlock = (OutPortBlock) claimSimuLinkBlock(view, DEFAULT_OUTPORT_BLOCK_NAME);
			OutPort subSystemOutPort = SimuLinkFactory.eINSTANCE.createOutPort();
			subSystemOutPort.setName(DEFAULT_SUBSYSTEM_OUTPORT_NAME);
			outPortBlock.getPorts().add(subSystemOutPort);

			InPort to = SimuLinkFactory.eINSTANCE.createInPort();
			to.setName(DEFAULT_SUBSYSTEM_INPORT_NAME);

			outPortBlock.getPorts().add(to);

			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(outPortBlock);

			// inport needs to be added first because the reaction
			// needs this port to know where the corresponding AutoSAR Typ needs to be contained
			((SingleConnection) claimSimuLinkConnection(view, DEFAULT_SINGLECONNECTION_NAME)).setInport(to);
			((SingleConnection) claimSimuLinkConnection(view, DEFAULT_SINGLECONNECTION_NAME)).setOutport(from);
		});

		equalityValidation.assertDelegationSwConnectorEqualsSingleConnection(DEFAULT_SINGLECONNECTION_NAME,
				DEFAUL_SUBSYSTEM_NAME, DEFAULT_OUTPORT_BLOCK_NAME, DEFAULT_BLOCK_NAME);
	}

	@Test
	public void testCreateSingleConnectionBetweenTwoBLocks() throws Exception {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME);
		createBlockInModel(DEFAULT_BLOCK_NAME);
		createBlockInModel(DEFAULT_BLOCK_NAME_TWO);
		createSingleConnectionInModel(DEFAULT_SINGLECONNECTION_NAME);
		viewFactory.changeSimuLinkView(view -> {
			var block = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME);
			OutPort from = SimuLinkFactory.eINSTANCE.createOutPort();
			from.setName(DEFAULT_OUTPORT_NAME);
			block.getPorts().add(from);
			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(block);

			var block2 = claimSimuLinkBlock(view, DEFAULT_BLOCK_NAME_TWO);
			InPort to = SimuLinkFactory.eINSTANCE.createInPort();
			to.setName(DEFAULT_INPORT_NAME);
			block2.getPorts().add(to);
			((SubSystem) claimSimuLinkBlock(view, DEFAUL_SUBSYSTEM_NAME)).getSubBlocks().add(block2);

			// inport needs to be added first because the reaction
			// needs this port to know where the corresponding AutoSAR Typ needs to be contained
			((SingleConnection) claimSimuLinkConnection(view, DEFAULT_SINGLECONNECTION_NAME)).setInport(to);
			((SingleConnection) claimSimuLinkConnection(view, DEFAULT_SINGLECONNECTION_NAME)).setOutport(from);
		});

		equalityValidation.assertAssemblySwConnectorEqualsSingleConnection(DEFAULT_SINGLECONNECTION_NAME,
				DEFAUL_SUBSYSTEM_NAME);
	}
}
