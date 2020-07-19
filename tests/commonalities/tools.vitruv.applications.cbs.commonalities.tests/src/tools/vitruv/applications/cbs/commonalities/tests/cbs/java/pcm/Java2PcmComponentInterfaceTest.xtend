package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels

class Java2PcmComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new JavaComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
