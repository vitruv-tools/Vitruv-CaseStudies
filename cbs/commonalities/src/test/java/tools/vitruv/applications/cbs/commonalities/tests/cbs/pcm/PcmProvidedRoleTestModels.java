package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

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
import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

public class PcmProvidedRoleTestModels extends PcmTestModelsBase implements ProvidedRoleTest.DomainModels {

    private static BasicComponent newPcmBasicComponent() {
        BasicComponent component = RepositoryFactory.eINSTANCE.createBasicComponent();
        component.setEntityName(COMPONENT_NAME);
        return component;
    }

    private static OperationProvidedRole newPcmOperationProvidedRole() {
        return RepositoryFactory.eINSTANCE.createOperationProvidedRole();
    }

    private static OperationInterface newPcmOperationInterface1() {
        OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setEntityName(INTERFACE_1_NAME);
        return opInterface;
    }

    private static OperationInterface newPcmOperationInterface2() {
        OperationInterface opInterface = newPcmOperationInterface1();
        opInterface.setEntityName(INTERFACE_2_NAME);
        return opInterface;
    }

    public PcmProvidedRoleTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel componentWithProvidedRoleCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = newPcmRepository();

            // Create and add interface
            OperationInterface interface1 = newPcmOperationInterface1();
            pcmRepository.getInterfaces__Repository().add(interface1);

            // Create component with provided role
            BasicComponent component = newPcmBasicComponent();
            OperationProvidedRole providedRole = newPcmOperationProvidedRole();
            providedRole.setProvidedInterface__OperationProvidedRole(interface1);
            component.getProvidedRoles_InterfaceProvidingEntity().add(providedRole);

            // Add component to repository
            pcmRepository.getComponents__Repository().add(component);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel componentWithMultipleProvidedRolesCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = newPcmRepository();

            // Create and add interfaces
            OperationInterface interface1 = newPcmOperationInterface1();
            OperationInterface interface2 = newPcmOperationInterface2();
            pcmRepository.getInterfaces__Repository().add(interface1);
            pcmRepository.getInterfaces__Repository().add(interface2);

            // Create component with provided roles
            BasicComponent component = newPcmBasicComponent();

            // Add first provided role
            OperationProvidedRole providedRole1 = newPcmOperationProvidedRole();
            providedRole1.setProvidedInterface__OperationProvidedRole(interface1);
            component.getProvidedRoles_InterfaceProvidingEntity().add(providedRole1);

            // Add second provided role
            OperationProvidedRole providedRole2 = newPcmOperationProvidedRole();
            providedRole2.setProvidedInterface__OperationProvidedRole(interface2);
            component.getProvidedRoles_InterfaceProvidingEntity().add(providedRole2);

            // Add component to repository
            pcmRepository.getComponents__Repository().add(component);

            return List.of(pcmRepository);
        });
    }
}