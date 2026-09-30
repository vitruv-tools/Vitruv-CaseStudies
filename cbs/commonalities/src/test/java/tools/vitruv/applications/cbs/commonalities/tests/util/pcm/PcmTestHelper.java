package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.contains;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoring;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoringUnsetFeatures;
import static tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmFilePathHelper.pcmRepositoryFilePath;

import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class PcmTestHelper {

	private final VitruvApplicationTestAdapter vitruvApplicationTestAdapter;

	public PcmTestHelper(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null");
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter;
	}

	public Repository createAndSynchronizePcmRepository(Repository pcmRepository) {
		vitruvApplicationTestAdapter.createAndSynchronizeModel(pcmRepositoryFilePath(pcmRepository), pcmRepository);
		return pcmRepository;
	}

	public Resource pcmRepositoryResource(Repository pcmRepository) {
		return vitruvApplicationTestAdapter.getResourceAt(pcmRepositoryFilePath(pcmRepository));
	}

	public Repository getPcmRepository(Resource pcmRepositoryResource) {
		assertTrue(pcmRepositoryResource.getContents().size() == 1,
				"Expecting resource to contain exactly 1 root object: " + pcmRepositoryResource.getURI());
		Repository pcmRepository = (Repository) (pcmRepositoryResource.getContents().isEmpty() ? null
				: pcmRepositoryResource.getContents().get(0));
		assertNotNull(pcmRepository, "Could not find PCM repository in resource: " + pcmRepositoryResource.getURI());
		return pcmRepository;
	}

	public void assertPcmRepositoryExists(Repository pcmRepository) {
		assertThat(pcmRepositoryResource(pcmRepository),
				contains(pcmRepository, ignoring("id"), ignoringUnsetFeatures()));
	}
}
