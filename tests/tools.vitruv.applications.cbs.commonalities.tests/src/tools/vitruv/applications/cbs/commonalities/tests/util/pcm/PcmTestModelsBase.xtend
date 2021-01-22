package tools.vitruv.applications.cbs.commonalities.tests.util.pcm

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class PcmTestModelsBase extends DomainTestModelsBase {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new PcmModelTester(vitruvApplicationTestAdapter)
	}
}
