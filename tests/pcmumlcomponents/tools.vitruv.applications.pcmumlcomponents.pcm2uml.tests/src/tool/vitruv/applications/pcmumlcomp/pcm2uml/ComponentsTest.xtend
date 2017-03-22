package tool.vitruv.applications.pcmumlcomp.pcm2uml

import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static org.junit.Assert.*
import org.eclipse.uml2.uml.Component
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.BasicComponent
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.Interface
import org.palladiosimulator.pcm.repository.RepositoryComponent

class ComponentsTest extends AbstractPcmUmlTest {
	private static val COMPONENT_NAME = "TestComponent";
	
	protected def OperationInterface createInterface(String name) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = name
		rootElement.interfaces__Repository += pcmInterface
		saveAndSynchronizeChanges(rootElement)
		return pcmInterface
	}
	
	protected def BasicComponent createBasicComponent(String name) {
		val pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		pcmComponent.entityName = COMPONENT_NAME;
		rootElement.components__Repository += pcmComponent;
		saveAndSynchronizeChanges(pcmComponent);
		return pcmComponent
	}
	
	protected def Interface getCorrespondingUmlInterface(OperationInterface pcmInterface) {
		val correspondingInterfaceElements = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		correspondingInterfaceElements.get(0) as Interface
	}
	
	protected def Component getCorrespondingUmlComponent(RepositoryComponent pcmComponent) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent]).flatten
		correspondingElements.get(0) as Component
	}
	
	@Test
	public def void testCreateComponent() {
		val pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		pcmComponent.entityName = COMPONENT_NAME;
		rootElement.components__Repository += pcmComponent;
		saveAndSynchronizeChanges(pcmComponent);
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent]).flatten
		assertEquals(1, correspondingElements.size);
		val umlComponent = correspondingElements.get(0);
		assertTrue(umlComponent instanceof Component);
		assertEquals(COMPONENT_NAME, (umlComponent as Component).name);
	}
	
	@Test
	public def void testCreateRequiredRole() {
		val pcmComponent = createBasicComponent("c1")
		val pcmInterface = createInterface("i1")
		val requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole()
		requiredRole.requiredInterface__OperationRequiredRole = pcmInterface
		requiredRole.entityName = "ri1"
		requiredRole.requiringEntity_RequiredRole = pcmComponent
		pcmComponent.requiredRoles_InterfaceRequiringEntity += requiredRole
		saveAndSynchronizeChanges(pcmComponent)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[requiredRole]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof Usage)
		val umlUsage = (correspondingElements.get(0) as Usage)
		assertEquals(requiredRole.entityName, umlUsage.name)
		
		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(1, umlUsage.clients.length)
		assertEquals(umlComponent, umlUsage.clients.get(0))
		assertEquals(1, umlComponent.provideds.length)
		
		val umlInterface = getCorrespondingUmlInterface(pcmInterface)
		assertEquals(1, umlUsage.suppliers.length)
		assertEquals(umlInterface, umlUsage.suppliers.get(0))
	}
	
	@Test
	public def void testCreateProvidedRole() {
		val pcmComponent = createBasicComponent("c2")
		val pcmInterface = createInterface("i2")
		val providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole()
		providedRole.providedInterface__OperationProvidedRole = pcmInterface
		providedRole.entityName = "pr1"
		providedRole.providingEntity_ProvidedRole = pcmComponent
		pcmComponent.providedRoles_InterfaceProvidingEntity += providedRole
		saveAndSynchronizeChanges(pcmComponent)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[providedRole]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof InterfaceRealization)
		val umlInterfaceRealization = (correspondingElements.get(0) as InterfaceRealization)
		assertEquals(providedRole.entityName, umlInterfaceRealization.name)
		
		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(1, umlInterfaceRealization.clients.length)
		assertEquals(umlComponent, umlInterfaceRealization.clients.get(0))
		assertEquals(1, umlComponent.requireds.length)
		
		val umlInterface = getCorrespondingUmlInterface(pcmInterface)
		assertEquals(1, umlInterfaceRealization.suppliers.length)
		assertEquals(umlInterface, umlInterfaceRealization.suppliers.get(0))
	}
}