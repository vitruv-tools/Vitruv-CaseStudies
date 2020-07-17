package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.pcm

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels

class Uml2PcmRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new UmlRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new PcmRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
