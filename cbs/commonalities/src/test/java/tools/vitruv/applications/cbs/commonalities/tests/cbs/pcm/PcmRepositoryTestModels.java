package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

import java.util.List;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;

public class PcmRepositoryTestModels extends PcmTestModelsBase implements RepositoryTest.DomainModels {

    public PcmRepositoryTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyRepositoryCreation() {
        return newModel(() -> List.of(newPcmRepository()));
    }
}
