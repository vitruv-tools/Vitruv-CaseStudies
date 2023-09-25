package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar

import org.junit.jupiter.api.Test
import static extension tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.*
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkFactory
import edu.kit.ipd.sdq.metamodels.simulink.OutPortBlock
import edu.kit.ipd.sdq.metamodels.simulink.SingleConnection

class SimuLinkToAutoSARPortTest extends AbstractSimuLinkToAutoSARTest {
	
	static val DEFAULT_BLOCK_NAME = "Testblock"
	static val DEFAULT_BLOCK_NAME_TWO = "Testblock2"
	static val DEFAUL_SUBSYSTEM_NAME = "TestSubsystem"
	static val DEFAULT_INPORT_NAME = "TestPort"
	static val DEFAULT_OUTPORT_NAME = "TestOutPort"
	static val DEFAULT_SUBSYSTEM_OUTPORT_NAME = "SubSystemOutport"
	static val DEFAULT_SUBSYSTEM_INPORT_NAME = "SubSystemInport"
	static val DEFAULT_OUTPORT_BLOCK_NAME = "OutPortBlock"
	static val DEFAULT_SINGLECONNECTION_NAME = "SingleConnection"
	
	/*
	 * 
	 * Tests for Ports
	 * 
	 * 
	 */
	
	@Test
	def void testCreateInPortinBlock() {
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createInPort => [
				it.name = DEFAULT_INPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME)
	}
	
	
	
	@Test
	def void testCreateOutPortinBlock() {
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createOutPort => [
				it.name = DEFAULT_OUTPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME)
	}
	
	
	@Test
	def void testCreateInPortinSubSystem() {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createInPort => [
				it.name = DEFAULT_INPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAUL_SUBSYSTEM_NAME)
	}
	
	@Test
	def void testCreateOutPortinSubSystem() {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createOutPort => [
				it.name = DEFAULT_OUTPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAUL_SUBSYSTEM_NAME)
	}
	
	@Test
	def void testDeleteInPortinBlock() {
		
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createInPort => [
				it.name = DEFAULT_INPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME)
				
		changeSimuLinkView [
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			EcoreUtil.remove(claimSimuLinkPort(block, DEFAULT_INPORT_NAME))
		]
		
		assertNoPortWithNameInComponent(DEFAULT_BLOCK_NAME, DEFAULT_INPORT_NAME)
		
		
	}
	
	@Test
	def void testDeleteOutPortinSwComponent() {
		
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			block.ports += SimuLinkFactory.eINSTANCE.createOutPort => [
				it.name = DEFAULT_OUTPORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_BLOCK_NAME)
				
		changeSimuLinkView [
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			EcoreUtil.remove(claimSimuLinkPort(block, DEFAULT_OUTPORT_NAME))
		]
		
		assertNoPortWithNameInComponent(DEFAULT_BLOCK_NAME, DEFAULT_OUTPORT_NAME)
		
		
	}
	
	
	
	/*
	 * 
	 * Test of Connections
	 * 
	 */
	
	@Test
	def void testCreateSingleConnectionFromBlockToOutPortBlock(){
		
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		createBlockInModel(DEFAULT_BLOCK_NAME)
		createOutPortBlockinModel(DEFAULT_OUTPORT_BLOCK_NAME)
		createSingleConnectionInModel(DEFAULT_SINGLECONNECTION_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			val from = SimuLinkFactory.eINSTANCE.createOutPort => [
					it.name = DEFAULT_OUTPORT_NAME
				]
			block.ports += from
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += block
			]
			
			val OutPortBLock = claimSimuLinkBlock(DEFAULT_OUTPORT_BLOCK_NAME) as OutPortBlock => [
				ports += SimuLinkFactory.eINSTANCE.createOutPort => [
					it.name = DEFAULT_SUBSYSTEM_OUTPORT_NAME
				]
			]
			val to = SimuLinkFactory.eINSTANCE.createInPort => [
					it.name = DEFAULT_SUBSYSTEM_INPORT_NAME
				]
			
			
			OutPortBLock.ports += to
			
			
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += OutPortBLock
			]
			
			
			// inport needs to be added first because the reaction 
			// needs this port to know where the corresponding AutoSAR Typ needs to be contained
			claimSimuLinkConnection(DEFAULT_SINGLECONNECTION_NAME) as SingleConnection => [
				
				it.inport = to
			]
			claimSimuLinkConnection(DEFAULT_SINGLECONNECTION_NAME) as SingleConnection => [
				
				it.outport = from
			]
			
			
			
		]
		
		assertDelegationSwConnectorEqualsSingleConnection(DEFAULT_SINGLECONNECTION_NAME,DEFAUL_SUBSYSTEM_NAME,DEFAULT_OUTPORT_BLOCK_NAME,DEFAULT_BLOCK_NAME)
		
	}
	
	
	
	@Test
	def void testCreateSingleConnectionBetweenTwoBLocks(){
		
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		createBlockInModel(DEFAULT_BLOCK_NAME)
		createBlockInModel(DEFAULT_BLOCK_NAME_TWO)
		createSingleConnectionInModel(DEFAULT_SINGLECONNECTION_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			val from = SimuLinkFactory.eINSTANCE.createOutPort => [
					it.name = DEFAULT_OUTPORT_NAME
				]
			block.ports += from
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += block
			]
			
			val block2 = claimSimuLinkBlock(DEFAULT_BLOCK_NAME_TWO)
			val to = SimuLinkFactory.eINSTANCE.createInPort => [
					it.name = DEFAULT_INPORT_NAME
				]
			block2.ports += to
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += block2
			]
			
			
			
			
			// inport needs to be added first because the reaction 
			// needs this port to know where the corresponding AutoSAR Typ needs to be contained
			claimSimuLinkConnection(DEFAULT_SINGLECONNECTION_NAME) as SingleConnection => [
				
				it.inport = to
			]
			claimSimuLinkConnection(DEFAULT_SINGLECONNECTION_NAME) as SingleConnection => [
				
				it.outport = from
			]
			
			
			
		]
		
		assertAssemblySwConnectorEqualsSingleConnection(DEFAULT_SINGLECONNECTION_NAME,DEFAUL_SUBSYSTEM_NAME)
		
		
	}
	
	
}