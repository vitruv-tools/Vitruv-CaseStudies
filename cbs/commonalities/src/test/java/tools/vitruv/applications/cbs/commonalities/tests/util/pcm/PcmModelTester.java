package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class PcmModelTester extends DomainModelTester {

	private final PcmTestHelper pcmTestHelper = new PcmTestHelper(vitruvApplicationTestAdapter);

	public PcmModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public void createAndSynchronizeModel(EObject rootObject) {
		if (rootObject instanceof Repository pcmRepository) {
			pcmTestHelper.createAndSynchronizePcmRepository(pcmRepository);
		} else {
			throw new IllegalStateException("Unhandled PCM root object: " + rootObject);
		}
	}

	@Override
	public void assertModelExists(EObject rootObject) {
		if (rootObject instanceof Repository pcmRepository) {
			pcmTestHelper.assertPcmRepositoryExists(pcmRepository);
		} else {
			throw new IllegalStateException("Unhandled PCM root object: " + rootObject);
		}
	}
}
