package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink

import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.*
import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARFactory

class AutoSARtoSimuLinkPortTest extends AbstractAutoSARToSimuLinkTest {
	
	static val DEFAULT_Component_NAME = "TestComponent"
	static val DEFAULT_COMPOSITE_COMPONENT_NAME = "TestCompositeComponent"
	static val DEFAULT_REQUIRED_PORT_NAME = "TestRequiredPort"
	static val DEFAULT_PROVIDED_PORT_NAME = "TestProvidedPort"
	static val DEFAULT_DELAGATIONSWCONNECTOR_NAME = "DelegationConnection"
	static val DEFAULT_REQUIREDPORT_COMPOSITE_NAME = "TestName"
	static val DEFAULT_OUTPORTBLOCK_NAME = "OutPortBlock"
	
	/*
	 * 
	 * 
	 * Tests for Ports
	 * 
	 * 
	 */
	 
	@Test
	def void testCreateRequiredPortinSwComponent() {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			atomicComponent.port += AutoSARFactory.eINSTANCE.createRequiredPort => [
				it.name = DEFAULT_REQUIRED_PORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_Component_NAME)
	}
	
	
	
	@Test
	def void testCreateProvidedPortinSwComponent() {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			atomicComponent.port += AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_PROVIDED_PORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_Component_NAME)
	}
	
	@Test
	def void testCreateRequiredPortinCompositeSwComponent() {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		changeAutoSARView[
			val compositeComponent = claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME) as CompositeSwComponent
			compositeComponent.port += AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_REQUIRED_PORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_COMPOSITE_COMPONENT_NAME)
	}
	
	
	
	@Test
	def void testCreateProvidedPortinCompositeSwComponent() {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		changeAutoSARView[
			val compositeComponent = claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME) as CompositeSwComponent
			compositeComponent.port += AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_PROVIDED_PORT_NAME
			]
		]
		assertPortsInBlockOrComponent(DEFAULT_COMPOSITE_COMPONENT_NAME)
	}
	
	@Test
	def void testDeleteRequiredPortinSwComponent() {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			atomicComponent.port += AutoSARFactory.eINSTANCE.createRequiredPort => [
				it.name = DEFAULT_REQUIRED_PORT_NAME
			]
		]
		
		assertPortsInBlockOrComponent(DEFAULT_Component_NAME)
				
		changeAutoSARView [
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME)
			EcoreUtil.delete(claimAutoSARPort(atomicComponent, DEFAULT_REQUIRED_PORT_NAME))
		]
		
		assertNoPortWithNameInComponent(DEFAULT_Component_NAME, DEFAULT_REQUIRED_PORT_NAME)
		
		
	}
	
	@Test
	def void testDeleteProvidedPortinSwComponent() {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		changeAutoSARView[
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			atomicComponent.port += AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_PROVIDED_PORT_NAME
			]
		]
		
		assertPortsInBlockOrComponent(DEFAULT_Component_NAME)
				
		changeAutoSARView [
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME)
			EcoreUtil.delete(claimAutoSARPort(atomicComponent, DEFAULT_PROVIDED_PORT_NAME))
		]
		
		assertNoPortWithNameInComponent(DEFAULT_Component_NAME, DEFAULT_PROVIDED_PORT_NAME)
		
		
	}
	
	/*
	 * 
	 * Test of SwConnectors
	 * 
	 */
	
	@Test
	def void testCreateDelegationSwConnector(){
		
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME)
		createAtomicSWComponentInModel(DEFAULT_Component_NAME)
		
		changeAutoSARView[
			
			val atomicComponent = claimAutoSARElement(AtomicSwComponent, DEFAULT_Component_NAME) as AtomicSwComponent
			val atomicOutPort = AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_PROVIDED_PORT_NAME
			]
			
			atomicComponent.port += atomicOutPort
			
			val compositeComponent = claimAutoSARCompositeSwComponent(CompositeSwComponent, DEFAULT_COMPOSITE_COMPONENT_NAME) as CompositeSwComponent
			
			
			val compositeOutPort = AutoSARFactory.eINSTANCE.createProvidedPort => [
				it.name = DEFAULT_REQUIREDPORT_COMPOSITE_NAME 
			]
			
			compositeComponent.port += compositeOutPort
			compositeComponent.atomicswcomponent += atomicComponent
			
			//assertPortsInBlockOrComponent(DEFAULT_Component_NAME)
			
			compositeComponent.swconnector += AutoSARFactory.eINSTANCE.createDelegationSwConnector => [
				
				it.name = DEFAULT_DELAGATIONSWCONNECTOR_NAME
				it.innerPort = atomicOutPort
				it.outerPort = compositeOutPort
				
			]
			
		
			
		]
		assertDelegationSwConnectorEqualsSingleConnection(DEFAULT_DELAGATIONSWCONNECTOR_NAME,DEFAULT_COMPOSITE_COMPONENT_NAME,DEFAULT_OUTPORTBLOCK_NAME,DEFAULT_Component_NAME)
		
		
		
	}
	
	
	
	
	
	
}