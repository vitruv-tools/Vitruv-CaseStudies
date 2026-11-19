package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

import java.util.List;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;

public class PcmProvidedRoleTestModels extends PcmTestModelsBase implements ProvidedRoleTest.DomainModels {

    private static BasicComponent newPcmBasicComponent(OperationInterface... providedInterfaces) {
        BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
        basicComponent.setEntityName(COMPONENT_NAME);
        for (OperationInterface providedInterface : providedInterfaces) {
            basicComponent.getProvidedRoles_InterfaceProvidingEntity()
                    .add(newPcmOperationProvidedRole(providedInterface));
        }
        return basicComponent;
    }

    private static OperationProvidedRole newPcmOperationProvidedRole(OperationInterface providedInterface) {
        OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        providedRole.setProvidedInterface__OperationProvidedRole(providedInterface);
        return providedRole;
    }

    private static OperationInterface newPcmOperationInterface1() {
        OperationInterface operationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        operationInterface.setEntityName(INTERFACE_1_NAME);
        return operationInterface;
    }

    private static OperationInterface newPcmOperationInterface2() {
        OperationInterface operationInterface = newPcmOperationInterface1();
        operationInterface.setEntityName(INTERFACE_2_NAME);
        return operationInterface;
    }

    public PcmProvidedRoleTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel componentWithProvidedRoleCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();
            OperationInterface interface1 = newPcmOperationInterface1();
            pcmRepository.getInterfaces__Repository().add(interface1);
            pcmRepository.getComponents__Repository().add(newPcmBasicComponent(interface1));
            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel componentWithMultipleProvidedRolesCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();
            OperationInterface interface1 = newPcmOperationInterface1();
            OperationInterface interface2 = newPcmOperationInterface2();
            pcmRepository.getInterfaces__Repository().add(interface1);
            pcmRepository.getInterfaces__Repository().add(interface2);
            pcmRepository.getComponents__Repository().add(newPcmBasicComponent(interface1, interface2));
            return List.of(pcmRepository);
        });
    }
}
