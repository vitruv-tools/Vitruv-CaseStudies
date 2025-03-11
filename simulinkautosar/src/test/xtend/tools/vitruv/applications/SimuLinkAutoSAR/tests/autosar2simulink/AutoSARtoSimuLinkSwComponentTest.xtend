package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink

import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.*
import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent

class AutoSARtoSimuLinkSwComponentTest extends AbstractAutoSARToSimuLinkTest {
	
	static val DEFAULT_Component_NAME = "TestComponent"
	static val DEFAULT_COMPOSITE_COMPONENT_NAME = "TestCompositeComponent"
	
	/*
	 * 
	 * Tests for AtomicSwComponents
	 * 
	 * 
	 */
	
	@Test
	def void testCreateComponent() {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		assertSwComponentWithNameInRootModel(DEFAULT_Component_NAME)
	}
	
	
	@Test
	def void testDeleteComponent(){
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView [
			EcoreUtil.delete(claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME))
		]
		assertNoElementWithNameInRootModel(DEFAULT_Component_NAME)
	}
	
	
	
	/*
	 * 
	 * Tests for CompositeSwComponents
	 * 
	 * 
	 */
	
	
	@Test
	def void testCreateCompositeComponent() {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME) => [
				atomicswcomponent += atomicComponent
			]
		]
		
		assertCompositeSwComponentWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
	}
	
	@Test
	def void testDeleteCompositeComponent(){
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		changeAutoSARView [
			EcoreUtil.delete(claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME))
		]
		assertNoElementWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
	}
	
	
	@Test
	def void testDeleteCompositeComponentAndCheckContainedAtomicComponent() {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME) => [
				atomicswcomponent += atomicComponent
			]
		]
		changeAutoSARView [
			EcoreUtil.delete(claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME))
		]
		assertNoElementWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		assertNoElementWithNameInRootModel(DEFAULT_Component_NAME)
		
	}
		
	
}