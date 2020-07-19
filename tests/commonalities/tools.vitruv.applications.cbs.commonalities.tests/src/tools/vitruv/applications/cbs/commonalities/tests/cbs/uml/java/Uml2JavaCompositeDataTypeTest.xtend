package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.java

import org.junit.Ignore
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractCompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels

@Ignore // TODO implement uml model
class Uml2JavaCompositeDataTypeTest extends AbstractCompositeDataTypeTest {

	override protected getSourceModels() {
		return new UmlCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaCompositeDataTypeTestModels(vitruvApplicationTestAdapter)
	}
}
