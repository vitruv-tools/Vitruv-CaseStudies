package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import java.util.List;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.ComponentInterfaceTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

public class PcmComponentInterfaceTestModels extends PcmTestModelsBase implements ComponentInterfaceTest.DomainModels {

    public PcmComponentInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyComponentInterfaceCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            OperationInterface operationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
            operationInterface.setEntityName(INTERFACE_NAME);
            pcmRepository.getInterfaces__Repository().add(operationInterface);

            return List.of(pcmRepository);
        });
    }
}