package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels

class Java2UmlComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new JavaComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
