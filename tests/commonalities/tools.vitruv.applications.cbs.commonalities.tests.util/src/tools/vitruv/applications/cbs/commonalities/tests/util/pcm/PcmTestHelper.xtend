package tools.vitruv.applications.cbs.commonalities.tests.util.pcm

import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.*
import static tools.vitruv.testutils.matchers.ModelMatchers.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmFilePathHelper.*

class PcmTestHelper {

	val extension VitruvApplicationTestAdapter vitruvApplicationTestAdapter

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null")
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter
	}

	def Repository createAndSynchronizePcmRepository(Repository pcmRepository) {
		createAndSynchronizeModel(pcmRepository.pcmRepositoryFilePath, pcmRepository)
		return pcmRepository
	}

	def pcmRepositoryResource(Repository pcmRepository) {
		return getResourceAt(pcmRepository.pcmRepositoryFilePath)
	}

	def getPcmRepository(Resource pcmRepositoryResource) {
		assertTrue("Expecting resource to contain exactly 1 root object: " + pcmRepositoryResource.URI,
			pcmRepositoryResource.contents.size == 1)
		val pcmRepository = pcmRepositoryResource.contents.head as Repository
		assertNotNull("Could not find PCM repository in resource: " + pcmRepositoryResource.URI, pcmRepository)
		return pcmRepository
	}

	def assertPcmRepositoryeExists(Repository pcmRepository) {
		assertThat(pcmRepository.pcmRepositoryResource, contains(pcmRepository, ignoring('id'), ignoringUnsetFeatures))
	}
}
