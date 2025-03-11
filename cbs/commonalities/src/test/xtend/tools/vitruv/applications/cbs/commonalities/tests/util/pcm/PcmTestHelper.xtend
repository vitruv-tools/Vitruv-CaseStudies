package tools.vitruv.applications.cbs.commonalities.tests.util.pcm

import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*
import static org.hamcrest.MatcherAssert.assertThat

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmFilePathHelper.*
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertNotNull
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.contains
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoring
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoringUnsetFeatures

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
		assertTrue(pcmRepositoryResource.contents.size == 1, "Expecting resource to contain exactly 1 root object: " + pcmRepositoryResource.URI)
		val pcmRepository = pcmRepositoryResource.contents.head as Repository
		assertNotNull(pcmRepository, "Could not find PCM repository in resource: " + pcmRepositoryResource.URI)
		return pcmRepository
	}

	def assertPcmRepositoryExists(Repository pcmRepository) {
		assertThat(pcmRepository.pcmRepositoryResource, contains(pcmRepository, ignoring('id'), ignoringUnsetFeatures))
	}
}
