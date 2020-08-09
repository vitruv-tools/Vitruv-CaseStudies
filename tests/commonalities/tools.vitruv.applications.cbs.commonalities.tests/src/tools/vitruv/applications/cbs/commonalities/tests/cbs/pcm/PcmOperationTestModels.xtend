package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmOperationTestModels extends PcmTestModelsBase implements OperationTest.DomainModels {

	private static def newPcmOperationInterface() {
		return RepositoryFactory.eINSTANCE.createOperationInterface => [
			entityName = INTERFACE_NAME
		]
	}

	private static def newPcmOperationSignature() {
		return RepositoryFactory.eINSTANCE.createOperationSignature => [
			entityName = OPERATION_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyOperationCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
