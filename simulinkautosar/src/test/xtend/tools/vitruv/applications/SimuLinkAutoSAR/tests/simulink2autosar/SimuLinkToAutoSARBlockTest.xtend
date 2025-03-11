package tools.vitruv.applications.simulinkautosar.tests.simulink2autosar

import org.junit.jupiter.api.Test
import static extension tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.*
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem

class SimuLinkToAutoSARBlockTest extends AbstractSimuLinkToAutoSARTest {
	
	static val DEFAULT_BLOCK_NAME = "Testblock"
	static val DEFAUL_SUBSYSTEM_NAME = "TestSubsystem"
	
	
	/*
	 * 
	 * Tests for Blocks and Subsystems
	 * 
	 */
	
	@Test
	def void testCreateBlock() {
		createBlockInModel(DEFAULT_BLOCK_NAME)
		assertBlockWithNameInRootModel(DEFAULT_BLOCK_NAME)
	}
	
	@Test
	def void testDeleteBlock(){
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView [
			EcoreUtil.remove(claimSimuLinkBlock(DEFAULT_BLOCK_NAME))
		]
		assertNoElementWithNameInRootModel(DEFAULT_BLOCK_NAME)
	}
	
	@Test
	def void testCreateSubsystem() {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += block
			]
		]
		
		assertSubSystemtWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME)
	}
	
	@Test
	def void testDeleteSubsystem(){
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		changeSimuLinkView [
			EcoreUtil.remove(claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem)
		]
		assertNoElementWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME)
	}
	
	
	@Test
	def void testSubSystemAndCheckContainedBlocks() {
		createSubsystemInModel(DEFAUL_SUBSYSTEM_NAME)
		createBlockInModel(DEFAULT_BLOCK_NAME)
		changeSimuLinkView[
			val block = claimSimuLinkBlock(DEFAULT_BLOCK_NAME)
			claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem => [
				subBlocks += block
			]
		]
		changeSimuLinkView [
			EcoreUtil.remove(claimSimuLinkBlock(DEFAUL_SUBSYSTEM_NAME) as SubSystem)
		]
		assertNoElementWithNameInRootModel(DEFAUL_SUBSYSTEM_NAME)
		assertNoElementWithNameInRootModel(DEFAULT_BLOCK_NAME)
		
	}
	
	
}