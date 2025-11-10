package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import java.util.List;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

public class PcmBasicComponentTestModels extends PcmTestModelsBase implements BasicComponentTest.DomainModels {

    public PcmBasicComponentTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyBasicComponentCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
            basicComponent.setEntityName(COMPONENT_NAME);
            pcmRepository.getComponents__Repository().add(basicComponent);

            return List.of(pcmRepository);
        });
    }
}