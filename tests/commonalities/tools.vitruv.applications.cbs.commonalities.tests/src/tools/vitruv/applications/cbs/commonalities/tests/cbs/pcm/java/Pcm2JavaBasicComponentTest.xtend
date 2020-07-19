package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels

class Pcm2JavaBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new PcmBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
