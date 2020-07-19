package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels

class Pcm2UmlComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new PcmComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
