package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmRepositoryTestModels extends PcmTestModelsBase implements AbstractRepositoryTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyRepositoryCreation() {
		return newModel [
			val pcmRepository = newPcmRepository
			return #[
				pcmRepository
			]
		]
	}
}
