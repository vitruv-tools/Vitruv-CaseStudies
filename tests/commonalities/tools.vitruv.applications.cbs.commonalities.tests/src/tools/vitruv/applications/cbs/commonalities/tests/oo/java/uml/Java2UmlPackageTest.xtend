package tools.vitruv.applications.cbs.commonalities.tests.oo.java.uml

import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractPackageTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlPackageTestModels

class Java2UmlPackageTest extends AbstractPackageTest {

	override protected getSourceModels() {
		return new JavaPackageTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlPackageTestModels(vitruvApplicationTestAdapter)
	}
}
