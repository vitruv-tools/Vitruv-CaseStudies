package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels

class Uml2PcmComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new UmlComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
