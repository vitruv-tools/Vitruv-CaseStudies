package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels

class Java2UmlRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new JavaRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
