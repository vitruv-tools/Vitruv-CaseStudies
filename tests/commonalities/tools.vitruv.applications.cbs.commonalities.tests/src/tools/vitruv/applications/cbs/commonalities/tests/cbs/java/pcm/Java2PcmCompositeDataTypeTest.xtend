package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.pcm

import org.junit.Ignore
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels

@Ignore // TODO This does not work yet.
class Java2PcmCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new JavaCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
