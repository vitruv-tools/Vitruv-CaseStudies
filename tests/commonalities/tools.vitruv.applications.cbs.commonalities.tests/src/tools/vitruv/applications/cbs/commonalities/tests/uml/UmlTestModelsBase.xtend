package tools.vitruv.applications.cbs.commonalities.tests.uml

import tools.vitruv.applications.cbs.commonalities.tests.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestHelper

class UmlTestModelsBase extends DomainTestModelsBase {

	protected val extension UmlTestHelper umlTestHelper = new UmlTestHelper(vitruvApplicationTestAdapter)

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new UmlModelTester(vitruvApplicationTestAdapter)
	}
}
