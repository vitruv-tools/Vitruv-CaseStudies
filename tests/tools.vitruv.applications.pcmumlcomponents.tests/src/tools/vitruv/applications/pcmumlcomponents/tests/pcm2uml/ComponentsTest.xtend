package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.palladiosimulator.pcm.repository.RepositoryFactory

import org.eclipse.uml2.uml.Component
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.BasicComponent
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.Interface
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

class ComponentsTest extends AbstractPcmUmlTest {
	static val COMPONENT_NAME = "TestComponent"
	static val INTERFACE_NAME = "TestInterface1"
	static val INTERFACE_NAME2 = "TestInterface2"
	protected static val PROVIDED_ROLE_NAME = "TestProvided"
	protected static val REQUIRED_ROLE_NAME = "TestRequired"

	protected def OperationInterface createInterface(String name) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = name
		rootElement.interfaces__Repository += pcmInterface
		propagate
		return pcmInterface
	}

	protected def BasicComponent createBasicComponent(String name) {
		val pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent()
		pcmComponent.entityName = COMPONENT_NAME
		rootElement.components__Repository += pcmComponent
		propagate
		return pcmComponent
	}

	protected def Interface getCorrespondingUmlInterface(OperationInterface pcmInterface) {
		return getCorrespondingEObjects(pcmInterface, Interface).claimOne
	}

	protected def Component getCorrespondingUmlComponent(RepositoryComponent pcmComponent) {
		return getCorrespondingEObjects(pcmComponent, Component).claimOne
	}

	@Test
	def void testCreateComponent() {
		val pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent()
		pcmComponent.entityName = COMPONENT_NAME
		rootElement.components__Repository += pcmComponent
		propagate
		val correspondingElements = pcmComponent.correspondingElements
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTrue(umlComponent instanceof Component)
		assertEquals(COMPONENT_NAME, (umlComponent as Component).name)
	}

	@Test
	def void testCreateRequiredRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface = createInterface(INTERFACE_NAME)
		val requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole()
		requiredRole.requiredInterface__OperationRequiredRole = pcmInterface
		requiredRole.entityName = REQUIRED_ROLE_NAME
		requiredRole.requiringEntity_RequiredRole = pcmComponent
		pcmComponent.requiredRoles_InterfaceRequiringEntity += requiredRole
		propagate

		val correspondingElements = requiredRole.correspondingElements
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof Usage)
		val umlUsage = (correspondingElements.get(0) as Usage)
		assertEquals(requiredRole.entityName, umlUsage.name)

		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(1, umlUsage.clients.length)
		assertEquals(umlComponent, umlUsage.clients.get(0))
		assertEquals(1, umlComponent.requireds.length)

		val umlInterface = getCorrespondingUmlInterface(pcmInterface)
		assertEquals(1, umlUsage.suppliers.length)
		assertEquals(umlInterface, umlUsage.suppliers.get(0))
	}

	@Test
	def void testChangeRequiredRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface1 = createInterface(INTERFACE_NAME)
		val requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole()
		requiredRole.requiredInterface__OperationRequiredRole = pcmInterface1
		requiredRole.entityName = REQUIRED_ROLE_NAME
		pcmComponent.requiredRoles_InterfaceRequiringEntity += requiredRole
		propagate

		val pcmInterface2 = createInterface(INTERFACE_NAME + "2")
		requiredRole.requiredInterface__OperationRequiredRole = pcmInterface2
		propagate

		val correspondingElements = requiredRole.correspondingElements
		val umlUsage = (correspondingElements.get(0) as Usage)
		val umlInterface = getCorrespondingUmlInterface(pcmInterface2)
		assertEquals(umlInterface, umlUsage.suppliers.get(0))
	}

	@Test
	def void testRemoveRequiredRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface = createInterface(INTERFACE_NAME)
		val requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole()
		requiredRole.requiredInterface__OperationRequiredRole = pcmInterface
		requiredRole.entityName = REQUIRED_ROLE_NAME
		pcmComponent.requiredRoles_InterfaceRequiringEntity += requiredRole
		propagate

		pcmComponent.requiredRoles_InterfaceRequiringEntity -= requiredRole
		propagate

		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(0, umlComponent.packagedElements.length)
	}

	@Test
	def void testCreateProvidedRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface = createInterface(INTERFACE_NAME)
		val providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole()
		providedRole.providedInterface__OperationProvidedRole = pcmInterface
		providedRole.entityName = PROVIDED_ROLE_NAME
		providedRole.providingEntity_ProvidedRole = pcmComponent
		pcmComponent.providedRoles_InterfaceProvidingEntity += providedRole
		propagate

		val correspondingElements = providedRole.correspondingElements
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof InterfaceRealization)
		val umlInterfaceRealization = (correspondingElements.get(0) as InterfaceRealization)
		assertEquals(providedRole.entityName, umlInterfaceRealization.name)

		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(1, umlInterfaceRealization.clients.length)
		assertEquals(umlComponent, umlInterfaceRealization.clients.get(0))
		assertEquals(1, umlComponent.provideds.length)

		val umlInterface = getCorrespondingUmlInterface(pcmInterface)
		assertEquals(1, umlInterfaceRealization.suppliers.length)
		assertEquals(umlInterface, umlInterfaceRealization.suppliers.get(0))
	}

	@Test
	def void testChangeRovidedRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface1 = createInterface(INTERFACE_NAME)
		val providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole()
		providedRole.providedInterface__OperationProvidedRole = pcmInterface1
		providedRole.entityName = PROVIDED_ROLE_NAME
		providedRole.providingEntity_ProvidedRole = pcmComponent
		pcmComponent.providedRoles_InterfaceProvidingEntity += providedRole
		propagate

		val pcmInterface2 = createInterface(INTERFACE_NAME2)
		providedRole.providedInterface__OperationProvidedRole = pcmInterface2
		propagate

		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		val umlInterface = getCorrespondingUmlInterface(pcmInterface2)
		assertEquals(umlInterface, umlComponent.provideds.get(0))
	}

	@Test
	def void testRemoveRovidedRole() {
		val pcmComponent = createBasicComponent(COMPONENT_NAME)
		val pcmInterface = createInterface(INTERFACE_NAME)
		val providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole()
		providedRole.providedInterface__OperationProvidedRole = pcmInterface
		providedRole.entityName = PROVIDED_ROLE_NAME
		providedRole.providingEntity_ProvidedRole = pcmComponent
		pcmComponent.providedRoles_InterfaceProvidingEntity += providedRole
		propagate

		pcmComponent.providedRoles_InterfaceProvidingEntity -= providedRole
		propagate

		val umlComponent = getCorrespondingUmlComponent(pcmComponent)
		assertEquals(0, umlComponent.packagedElements.length)
	}
}
