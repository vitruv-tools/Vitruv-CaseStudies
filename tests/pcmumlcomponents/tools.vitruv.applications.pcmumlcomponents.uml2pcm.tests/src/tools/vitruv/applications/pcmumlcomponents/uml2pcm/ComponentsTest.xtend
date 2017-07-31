package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.junit.Test

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Interface

import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.OperationRequiredRole

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ComponentsTest extends AbstractUmlPcmTest {
	protected static val USAGE_NAME = "testUsage"
	protected static val INTERFACE_REALIZATION_NAME = "testInterfaceRealization"

	@Test
	def void testCreateBasicComponent() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof BasicComponent, is(true))
		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
	}

	@Test
	def void testAddProvidedInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface = createUmlInterface(INTERFACE_NAME)
		val interfaceRealization = createInterfaceRealization
		interfaceRealization.name = INTERFACE_REALIZATION_NAME
		interfaceRealization.suppliers += umlInterface
		umlComponent.interfaceRealizations += interfaceRealization
		saveAndSynchronizeChanges(umlComponent)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(1, equalTo(pcmComponent.providedRoles_InterfaceProvidingEntity.length))
		val pcmRole = (pcmComponent.providedRoles_InterfaceProvidingEntity.get(0) as OperationProvidedRole)
		assertThat(interfaceRealization.name, equalTo(pcmRole.entityName))
		val pcmInterface = getCorrespondingInterface(umlInterface)
		assertThat(pcmInterface, equalTo(pcmRole.providedInterface__OperationProvidedRole))
	}

	@Test
	def void testChangeProvidedInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface1 = createUmlInterface(INTERFACE_NAME)
		val interfaceRealization = createInterfaceRealization
		interfaceRealization.name = INTERFACE_REALIZATION_NAME
		interfaceRealization.suppliers += umlInterface1
		umlComponent.interfaceRealizations += interfaceRealization
		saveAndSynchronizeChanges(umlComponent)

		val umlInterface2 = createUmlInterface(INTERFACE_NAME + "2")
		interfaceRealization.suppliers.clear
		interfaceRealization.suppliers += umlInterface2
		saveAndSynchronizeChanges(interfaceRealization)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(1, equalTo(pcmComponent.providedRoles_InterfaceProvidingEntity.length))
		val pcmRole = (pcmComponent.providedRoles_InterfaceProvidingEntity.get(0) as OperationProvidedRole)
		assertThat(interfaceRealization.name, equalTo(pcmRole.entityName))
		val pcmInterface = getCorrespondingInterface(umlInterface2)
		assertThat(pcmInterface, equalTo(pcmRole.providedInterface__OperationProvidedRole))
	}

	@Test
	def void testRemoveProvidedInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface = createUmlInterface(INTERFACE_NAME)
		val interfaceRealization = createInterfaceRealization
		interfaceRealization.name = INTERFACE_REALIZATION_NAME
		interfaceRealization.suppliers += umlInterface
		umlComponent.interfaceRealizations += interfaceRealization
		saveAndSynchronizeChanges(umlComponent)

		umlComponent.interfaceRealizations -= interfaceRealization
		saveAndSynchronizeChanges(umlComponent)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(0, equalTo(pcmComponent.providedRoles_InterfaceProvidingEntity.length))
	}

	@Test
	def void testAddRequiredInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface = createUmlInterface(INTERFACE_NAME)
		val usage = createUsage
		usage.name = USAGE_NAME
		usage.suppliers += umlInterface
		umlComponent.packagedElements += usage
		saveAndSynchronizeChanges(umlComponent)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(1, equalTo(pcmComponent.requiredRoles_InterfaceRequiringEntity.length))
		val pcmRole = (pcmComponent.requiredRoles_InterfaceRequiringEntity.get(0) as OperationRequiredRole)
		assertThat(usage.name, equalTo(pcmRole.entityName))
		val pcmInterface = getCorrespondingInterface(umlInterface)
		assertThat(pcmInterface, equalTo(pcmRole.requiredInterface__OperationRequiredRole))
	}

	@Test
	def void testChangeRequiredInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface1 = createUmlInterface(INTERFACE_NAME)
		val usage = createUsage
		usage.name = USAGE_NAME
		usage.suppliers += umlInterface1
		umlComponent.packagedElements += usage
		saveAndSynchronizeChanges(umlComponent)

		val umlInterface2 = createUmlInterface(INTERFACE_NAME + "2")
		// usage.suppliers.clear
		// usage.suppliers += #[umlInterface2]
		usage.suppliers.set(0, umlInterface2)
		saveAndSynchronizeChanges(usage)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(1, equalTo(pcmComponent.requiredRoles_InterfaceRequiringEntity.length))
		val pcmRole = (pcmComponent.requiredRoles_InterfaceRequiringEntity.get(0) as OperationRequiredRole)
		assertThat(usage.name, equalTo(pcmRole.entityName))
		val pcmInterface = getCorrespondingInterface(umlInterface2)
		assertThat(pcmInterface, equalTo(pcmRole.requiredInterface__OperationRequiredRole))
	}

	@Test
	def void testRemoveRequiredInterface() {
		val umlComponent = createUmlComponent(COMPONENT_NAME)
		val umlInterface = createUmlInterface(INTERFACE_NAME)
		val usage = createUsage
		usage.name = USAGE_NAME
		usage.suppliers += umlInterface
		umlComponent.packagedElements += usage
		saveAndSynchronizeChanges(umlComponent)

		umlComponent.packagedElements -= usage
		saveAndSynchronizeChanges(umlComponent)

		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(0, equalTo(pcmComponent.requiredRoles_InterfaceRequiringEntity.length))
	}

	@Test
	def void testCreateCompositeComponent() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, true)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof CompositeComponent, is(true))
		val pcmComponent = getCorrespondingCompositeComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
	}

	@Test
	def void testCreateCompositeComponentAndChangeName() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, true)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof CompositeComponent, is(true))
		val pcmComponent = getCorrespondingCompositeComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
		val newName = "newName"
		umlComponent.name = newName
		val propagatedChanges = saveAndSynchronizeChanges(umlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val pcmComponent2 = getCorrespondingCompositeComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent2.entityName))
	}

	@Test
	def void testCreateBasicComponentAndChangeName() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, false)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof BasicComponent, is(true))
		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
		val newName = "newName"
		umlComponent.name = newName
		val propagatedChanges = saveAndSynchronizeChanges(umlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val pcmComponent2 = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent2.entityName))
	}

	protected def Component createUmlComponent(String name, Boolean isComposable) {
		val umlComponent = createComponent
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		val componentMode = if(isComposable) 0 else 1
		userInteractor.addNextSelections(componentMode)
		saveAndSynchronizeChanges(rootElement)
		return umlComponent
	}

	protected def Component createUmlComponent(String name) {
		createUmlComponent(name, false)
	}

	protected def Interface createUmlInterface(String name) {
		val umlInterface = createInterface
		umlInterface.name = name
		rootElement.packagedElements += umlInterface
		saveAndSynchronizeChanges(rootElement)
		return umlInterface
	}

	protected def CompositeComponent getCorrespondingCompositeComponent(Component umlComponent) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		return (correspondingElements.get(0) as CompositeComponent)
	}

	protected def BasicComponent getCorrespondingBasicComponent(Component umlComponent) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		return (correspondingElements.get(0) as BasicComponent)
	}

	protected def OperationInterface getCorrespondingInterface(Interface umlInterface) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlInterface]).flatten
		return (correspondingElements.get(0) as OperationInterface)
	}
}
