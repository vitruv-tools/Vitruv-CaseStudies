package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels

class Uml2JavaRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new UmlRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
