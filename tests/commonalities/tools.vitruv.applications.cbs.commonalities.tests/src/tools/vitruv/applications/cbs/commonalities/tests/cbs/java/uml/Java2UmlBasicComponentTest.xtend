package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels

class Java2UmlBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new JavaBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
