package tools.vitruv.applications.cbs.commonalities.tests.java

import tools.vitruv.applications.cbs.commonalities.tests.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class JavaTestModelsBase extends DomainTestModelsBase {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new JavaModelTester(vitruvApplicationTestAdapter)
	}
}
