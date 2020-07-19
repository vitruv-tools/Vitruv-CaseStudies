package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels

class Java2PcmBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new JavaBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
