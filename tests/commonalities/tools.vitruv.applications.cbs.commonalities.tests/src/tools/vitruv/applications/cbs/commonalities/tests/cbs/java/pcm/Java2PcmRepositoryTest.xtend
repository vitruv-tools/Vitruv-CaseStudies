package tools.vitruv.applications.cbs.commonalities.tests.cbs.java.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels

class Java2PcmRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new JavaRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
