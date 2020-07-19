package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmComponentInterfaceTestModels extends PcmTestModelsBase implements AbstractComponentInterfaceTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyComponentInterfaceCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += RepositoryFactory.eINSTANCE.createOperationInterface => [
					entityName = INTERFACE_NAME
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
