package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmComponentInterfaceTestModels

class Pcm2JavaComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new PcmComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
