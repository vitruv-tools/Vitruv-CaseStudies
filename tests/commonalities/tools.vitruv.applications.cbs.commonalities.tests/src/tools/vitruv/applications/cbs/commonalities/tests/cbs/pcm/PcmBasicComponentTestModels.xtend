package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmBasicComponentTestModels extends PcmTestModelsBase implements BasicComponentTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				components__Repository += RepositoryFactory.eINSTANCE.createBasicComponent => [
					entityName = COMPONENT_NAME
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
