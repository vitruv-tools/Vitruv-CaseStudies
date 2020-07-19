package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.uml

import org.junit.Ignore
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels

@Ignore // TODO implement uml model
class Pcm2UmlCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new PcmCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
