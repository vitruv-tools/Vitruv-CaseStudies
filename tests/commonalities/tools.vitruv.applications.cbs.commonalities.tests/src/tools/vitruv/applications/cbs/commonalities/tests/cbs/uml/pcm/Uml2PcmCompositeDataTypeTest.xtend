package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.pcm

import org.junit.Ignore
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels

@Ignore // TODO implement uml model
class Uml2PcmCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new UmlCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
