package tools.vitruv.applications.simulinkautosar.tests.util

import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARElement
import edu.kit.ipd.sdq.metamodels.autosar.ProvidedPort
import edu.kit.ipd.sdq.metamodels.autosar.RequiredPort
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import edu.kit.ipd.sdq.metamodels.simulink.Block
import edu.kit.ipd.sdq.metamodels.simulink.InPort
import edu.kit.ipd.sdq.metamodels.simulink.OutPort
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkElement
import tools.vitruv.framework.views.View

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

import static extension tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.*
import static extension tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.*
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import edu.kit.ipd.sdq.metamodels.simulink.SingleConnection
import edu.kit.ipd.sdq.metamodels.autosar.DelegationSwConnector
import edu.kit.ipd.sdq.metamodels.autosar.AssemblySwConnector

/**
 * This class provides validations for the equal existence of classifiers of different types to exist
 * in both AutoSAR and SimuLink models.
 */
@FinalFieldsConstructor
class SimuLinkAutoSARClassifierEqualityValidation {
	val String AutoSARModelName
	val ((View)=>void)=>void viewExecutor
	
	
	def assertBlockWithNameInRootModel(String blockName) {
		assertElementWithName(Block, SwComponent, blockName)
	}
	
	def assertSubSystemtWithNameInRootModel(String compositeComponentName){
		assertElementWithName(SubSystem, CompositeSwComponent, compositeComponentName)
	}
	
	def assertPortsInBlockOrComponent(String blockName){
		assertElementWithName(Block, SwComponent, blockName)
	}
	
	def assertNoPortWithNameInComponent(String blockName, String portName){
		assertNoPortWithNameInComponentOrBlock(blockName, portName)
	}
	
	def assertSwComponentWithNameInRootModel(String swComponentName){
		assertElementWithName(Block,SwComponent, swComponentName)
	}
	
	def assertCompositeSwComponentWithNameInRootModel(String compositeComponentName){
		assertElementWithName(SubSystem, CompositeSwComponent, compositeComponentName)
	}
	
	
	
	/*
	 * 
	 * Generic method to assert that no SimuLinkElement or AutoSAR Element exist 
	 * 
	 */
	def assertNoElementWithNameInRootModel(String elememtName){
		viewExecutor.apply [
			assertThat("no Element in AutoSAR model with name " + elememtName + " is expected to exist",
				claimAutoSARModel(AutoSARModelName).swcomponent.filter [
					name == elememtName
				].toSet, is(emptySet))
			assertThat("no SimuLink Element with name " + elememtName + " is expected to exist",
				claimSimuLinkModel(AutoSARModelName).contains.filter [
					name == elememtName
				].toSet, is(emptySet))
		]
	}
	
	private def assertNoPortWithNameInComponentOrBlock(String blockName, String portName){
		viewExecutor.apply [
			assertThat("no Port in AutoSAR Component with name " + portName + " is expected to exist",
				claimAutoSARModel(AutoSARModelName).swcomponent.filter[
					name == blockName
				].head.port.filter[name == portName].toSet , is(emptySet))
			assertThat("no SimuLink Element with name " + portName + " is expected to exist",
				claimSimuLinkModel(AutoSARModelName).contains.filter[
					name == blockName
				].head.ports.filter[name == portName].toSet , is(emptySet))	
				
				   
		]
		
		
	}
	
	
	/*
	 * 
	 * Generic method to assert that a SimuLinkElement and an AutoSAR are equal 
	 * 
	 */
	private def void assertElementWithName(
		Class <? extends SimulinkElement> simulinkElement,
		Class <? extends AutoSARElement> autoSARElement,
		String name) {
		viewExecutor.apply [
			val SimulinkElement = claimSimuLinkElement(simulinkElement, name)
			
			val AutoSARElement = claimAutoSARElement(autoSARElement, name)
			assertElementEquals(SimulinkElement , AutoSARElement )
		]
	}
	
	
	def static dispatch void assertElementEquals(Block block, AtomicSwComponent component){
		assertSimuLinkBlockEqualsAutoSARSwComponent(block,component)
	}
	
	def static dispatch void assertElementEquals(SubSystem subsystem, CompositeSwComponent compositeComponent){
		assertSimuLinkSubsystemEqualsAutoSARCompositeComponent(subsystem,compositeComponent)
	}
	
	
	
	private static def void assertSimuLinkSubsystemEqualsAutoSARCompositeComponent(SubSystem subsystem, CompositeSwComponent compositeComponent){
		assertEquals(subsystem.name, compositeComponent.name)
		assertSameSubBlocksOrComponents(subsystem,compositeComponent)
	}
	
	private static def void assertSameSubBlocksOrComponents(SubSystem subsystem, CompositeSwComponent compositeComponent){
		
		var simuLinkBlocks = subsystem.subBlocks
		var autoSARComponents = compositeComponent.atomicswcomponent
		
		assertEquals(autoSARComponents.size, simuLinkBlocks.size)
		
		for (simuLinkBlock : simuLinkBlocks){
			
			val autoSARComponent = claimAutoSARBlockinComposite(compositeComponent, simuLinkBlock.name)
			assertSimuLinkBlockEqualsAutoSARSwComponent(simuLinkBlock, autoSARComponent)
			
		}
		
	}
	
	private static def void assertSimuLinkBlockEqualsAutoSARSwComponent(Block simulinkElement, SwComponent autoSARElement){
		
		
		assertEquals(simulinkElement.name, autoSARElement.name)
		assertSamePortsInElement(simulinkElement,autoSARElement)
		
	}	
	
	
	private static def void assertSamePortsInElement(Block simulinkElement, SwComponent autoSARElement){
		
		var simulinkPorts = simulinkElement.ports
		var autosarPorts = autoSARElement.port
		
		assertEquals(simulinkPorts.size,autosarPorts.size)
		
		for (simulinkPort : simulinkPorts){
			
			val autosarPort = claimAutoSARPort(autoSARElement, simulinkPort.name) 
			
			
			if(simulinkPort instanceof InPort){
				
				assertTrue(autosarPort instanceof RequiredPort)
				assertSimuLinkPortEqualsAutoSARSwPort(simulinkPort,autosarPort as RequiredPort)
				
			}
			else if (simulinkPort instanceof OutPort){
				
				assertTrue(autosarPort instanceof ProvidedPort)
			}
			
			
			
		}
		
		
	}
	
	
	private static def void assertSimuLinkPortEqualsAutoSARSwPort(InPort inPort, RequiredPort requiredPort){
		
		
		assertEquals(inPort.name, requiredPort.name)
		
	}
		
	
	def void assertDelegationSwConnectorEqualsSingleConnection(String ConnectionName, 
		String compositeComponentName, String addedOutPortBlockName, String atomicComponentName
	){
		viewExecutor.apply [
			val compositeComponent = claimAutoSARElement(CompositeSwComponent,compositeComponentName) as CompositeSwComponent
			val delegationSwConnector = compositeComponent.swconnector.filter[it.name == ConnectionName].head as DelegationSwConnector
			val singleConnection = claimSimuLinkConnection(ConnectionName) as SingleConnection
			val subsystem = claimSimuLinkElement(SubSystem,compositeComponentName) as SubSystem
			val addedOutPortBlock = claimSimuLinkBlockOfSubsystem(subsystem,addedOutPortBlockName)
			
			
			assertEquals(singleConnection.name, delegationSwConnector.name)
			assertEquals(singleConnection.outport.name, delegationSwConnector.innerPort.name)
			assertEquals(singleConnection.inport.name,claimSimuLinkPort(addedOutPortBlock,singleConnection.inport.name).name)
			
		]
	}
	
	def void assertAssemblySwConnectorEqualsSingleConnection(String ConnectionName, 
		String compositeComponentName
	){
		viewExecutor.apply [
			val compositeComponent = claimAutoSARElement(CompositeSwComponent,compositeComponentName) as CompositeSwComponent
			val assemblySwConnector = compositeComponent.swconnector.filter[it.name == ConnectionName].head as AssemblySwConnector
			val singleConnection = claimSimuLinkConnection(ConnectionName) as SingleConnection
			
			assertEquals(singleConnection.name, assemblySwConnector.name)
			assertEquals(singleConnection.outport.name, assemblySwConnector.providedport.head.name)
			assertEquals(singleConnection.inport.name,assemblySwConnector.requiredport.head.name)
			
		]
	}
	
}
