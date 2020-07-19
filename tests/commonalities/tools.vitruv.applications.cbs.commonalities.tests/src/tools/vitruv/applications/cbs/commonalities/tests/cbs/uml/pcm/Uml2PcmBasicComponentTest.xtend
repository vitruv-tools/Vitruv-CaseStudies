package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels

class Uml2PcmBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new UmlBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
