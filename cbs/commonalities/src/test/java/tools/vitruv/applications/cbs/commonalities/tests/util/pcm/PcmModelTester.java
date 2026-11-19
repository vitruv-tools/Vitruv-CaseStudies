package tools.vitruv.applications.cbs.commonalities.tests.util.pcm;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class PcmModelTester extends DomainModelTester {

    private final PcmTestHelper pcmTestHelper;

    public PcmModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
        this.pcmTestHelper = new PcmTestHelper(vitruvApplicationTestAdapter);
    }

    @Override
    public void createAndSynchronizeModel(EObject rootObject) {
        switch (rootObject) {
            case Repository repository -> pcmTestHelper.createAndSynchronizePcmRepository(repository);
            case null, default -> throw new IllegalStateException("Unhandled PCM root object: " + rootObject);
        }
    }

    @Override
    public void assertModelExists(EObject rootObject) {
        switch (rootObject) {
            case Repository repository -> pcmTestHelper.assertPcmRepositoryExists(repository);
            case null, default -> throw new IllegalStateException("Unhandled PCM root object: " + rootObject);
        }
    }
}
