package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaBasicComponentTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlBasicComponentTestModels

class Uml2JavaBasicComponentTest extends AbstractBasicComponentTest {

	override protected getSourceModels() {
		return new UmlBasicComponentTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaBasicComponentTestModels(vitruvApplicationTestAdapter)
	}
}
