package tools.vitruv.applications.cbs.commonalities.tests.util.java

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class JavaTestModelsBase extends DomainTestModelsBase {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new JavaModelTester(vitruvApplicationTestAdapter)
	}
}
