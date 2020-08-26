package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmRepositoryTestModels extends PcmTestModelsBase implements RepositoryTest.DomainModels {

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
