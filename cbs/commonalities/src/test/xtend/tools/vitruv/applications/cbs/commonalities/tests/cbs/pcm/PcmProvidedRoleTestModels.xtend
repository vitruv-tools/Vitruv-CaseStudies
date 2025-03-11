package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmProvidedRoleTestModels extends PcmTestModelsBase implements ProvidedRoleTest.DomainModels {

	private static def newPcmBasicComponent() {
		return RepositoryFactory.eINSTANCE.createBasicComponent => [
			entityName = COMPONENT_NAME
		]
	}

	private static def newPcmOperationProvidedRole() {
		return RepositoryFactory.eINSTANCE.createOperationProvidedRole
	}

	private static def newPcmOperationInterface1() {
		return RepositoryFactory.eINSTANCE.createOperationInterface => [
			entityName = INTERFACE_1_NAME
		]
	}

	private static def newPcmOperationInterface2() {
		return newPcmOperationInterface1 => [
			entityName = INTERFACE_2_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override componentWithProvidedRoleCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				val interface1 = newPcmOperationInterface1
				interfaces__Repository += interface1
				components__Repository += newPcmBasicComponent => [
					providedRoles_InterfaceProvidingEntity += newPcmOperationProvidedRole => [
						providedInterface__OperationProvidedRole = interface1
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override componentWithMultipleProvidedRolesCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				val interface1 = newPcmOperationInterface1
				val interface2 = newPcmOperationInterface2
				interfaces__Repository += interface1
				interfaces__Repository += interface2
				components__Repository += newPcmBasicComponent => [
					providedRoles_InterfaceProvidingEntity += newPcmOperationProvidedRole => [
						providedInterface__OperationProvidedRole = interface1
					]
					providedRoles_InterfaceProvidingEntity += newPcmOperationProvidedRole => [
						providedInterface__OperationProvidedRole = interface2
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
