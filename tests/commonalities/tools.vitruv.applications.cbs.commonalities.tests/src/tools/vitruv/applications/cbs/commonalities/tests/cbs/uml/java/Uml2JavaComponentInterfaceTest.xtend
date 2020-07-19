package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractComponentInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaComponentInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlComponentInterfaceTestModels

class Uml2JavaComponentInterfaceTest extends AbstractComponentInterfaceTest {

	override protected getSourceModels() {
		return new UmlComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaComponentInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
