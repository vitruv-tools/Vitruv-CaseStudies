package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels

class Pcm2UmlBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new PcmBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
