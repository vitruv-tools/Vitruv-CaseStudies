package tools.vitruv.applications.cbs.commonalities.tests.oo.uml.java

import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractPackageTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaPackageTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlPackageTestModels

class Uml2JavaPackageTest extends AbstractPackageTest {

	override protected getSourceModels() {
		return new UmlPackageTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaPackageTestModels(vitruvApplicationTestAdapter)
	}
}
