package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels

class Pcm2JavaRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new PcmRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
