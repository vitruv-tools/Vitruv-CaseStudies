package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractMediaStoreTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmMediaStoreTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlMediaStoreTestModels_1_Packages

class Pcm2UmlMediaStoreTest_1_Packages extends AbstractMediaStoreTest {

	override protected getSourceModels() {
		return new PcmMediaStoreTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlMediaStoreTestModels_1_Packages(vitruvApplicationTestAdapter)
	}
}
