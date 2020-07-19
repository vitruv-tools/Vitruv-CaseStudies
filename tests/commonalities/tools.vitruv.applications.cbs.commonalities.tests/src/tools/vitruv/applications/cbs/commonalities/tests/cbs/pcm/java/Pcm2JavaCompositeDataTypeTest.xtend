package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels

class Pcm2JavaCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new PcmCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
