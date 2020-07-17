package tools.vitruv.applications.cbs.commonalities.tests.pcm

import tools.vitruv.applications.cbs.commonalities.tests.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class PcmTestModelsBase extends DomainTestModelsBase {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new PcmModelTester(vitruvApplicationTestAdapter)
	}
}
