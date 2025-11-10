package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import java.util.List;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

/**
 * Test models for PCM repository creation tests.
 */
public class PcmRepositoryTestModels extends PcmTestModelsBase implements RepositoryTest.DomainModels {

    public PcmRepositoryTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyRepositoryCreation() {
        return newModel(() -> {
            // Create empty repository
            Repository pcmRepository = newPcmRepository();
            return List.of(pcmRepository);
        });
    }
}