package tools.vitruv.applications.cbs.commonalities.tests.oo.uml.java

import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractInterfaceTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaInterfaceTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlInterfaceTestModels

class Uml2JavaInterfaceTest extends AbstractInterfaceTest {

	override protected getSourceModels() {
		return new UmlInterfaceTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaInterfaceTestModels(vitruvApplicationTestAdapter)
	}
}
