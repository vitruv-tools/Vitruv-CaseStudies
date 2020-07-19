package tools.vitruv.applications.cbs.commonalities.tests.oo.java.uml

import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceTestModels

class Java2UmlInterfaceTest extends AbstractInterfaceTest {

	override protected getSourceModels() {
		return new JavaInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
