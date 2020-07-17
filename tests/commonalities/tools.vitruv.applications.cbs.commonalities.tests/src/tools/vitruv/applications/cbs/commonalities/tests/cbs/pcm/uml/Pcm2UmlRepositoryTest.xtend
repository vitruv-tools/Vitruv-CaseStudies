package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmRepositoryTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlRepositoryTestModels

class Pcm2UmlRepositoryTest extends AbstractRepositoryTest {

	override protected getSourceModels() {
		return new PcmRepositoryTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlRepositoryTestModels(vitruvApplicationTestAdapter)
	}
}
