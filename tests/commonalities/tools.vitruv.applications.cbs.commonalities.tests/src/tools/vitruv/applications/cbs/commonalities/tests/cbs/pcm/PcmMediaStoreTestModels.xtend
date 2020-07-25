package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.commonalities.tests.cbs.MediaStoreTest
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static org.junit.Assert.*

class PcmMediaStoreTestModels extends PcmTestModelsBase implements MediaStoreTest.DomainModels {

	// private static val PCM_MEDIA_STORE_REPOSITORY_PATH = 'resources/model/pcm/MediaStore.repository'
	static val PCM_MEDIA_STORE_REPOSITORY_PATH = 'resources/model/pcm/MediaStore_NoSEFF.repository'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override mediaStoreCreation() {
		return newModel [
			val pcmMediaStoreResource = getTestResource(PCM_MEDIA_STORE_REPOSITORY_PATH)
			val pcmMediaStoreRepository = pcmMediaStoreResource.contents.head as Repository
			assertNotNull("Could not find PCM MediaStore repository!", pcmMediaStoreRepository)
			return #[
				pcmMediaStoreRepository
			]
		]
	}
}
