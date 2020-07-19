package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.uml

import org.junit.Ignore
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels

@Ignore // TODO implement uml model
class Java2UmlCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new JavaCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
