package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.Test;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class OperationRequiredRoleMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddOperationRequiredRole() throws Throwable, Throwable {
        final OperationRequiredRole operationRequiredRole = this
                .createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();

        this.assertOperationRequiredRole(operationRequiredRole);
    }

    @Test
    public void testAddOperationRequiredToSystem() throws Throwable {
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME);

        final OperationRequiredRole operationRequiredRole = this
                .createAndSyncOperationRequiredRole(opInterface, system);

        this.assertOperationRequiredRole(operationRequiredRole);
    }

    @Test
    public void testAddOperationRequiredRoleToCompositeComponent() throws Throwable {
        final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME);
        final CompositeComponent compositeComponent = super.createAndSyncCompositeComponent(repo,
                Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);

        // test: add the requried Role
        final OperationRequiredRole operationRequiredRole = this.createAndSyncOperationRequiredRole(opInterface,
                compositeComponent);

        this.assertOperationRequiredRole(operationRequiredRole);

    }

    @Test
    public void testChangeOperationRequiredRole() throws Throwable, Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();
        final Repository repo = opr.getRequiredInterface__OperationRequiredRole().getRepository__Interface();

        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME);
        final BasicComponent newBasicComponent = this.addBasicComponentAndSync(repo,
                Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME);
        opr.setRequiredInterface__OperationRequiredRole(newInterface);
        opr.setRequiringEntity_RequiredRole(newBasicComponent);
        super.saveAndSynchronizeChanges(repo);

       	assertOperationRequiredRole(opr);
    }

    @Test
    public void testChangeNameOfOperationRequiredRole() throws Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();

        opr.setEntityName("operationReqRoleNameChange");
        super.saveAndSynchronizeChanges(opr);

        this.assertOperationRequiredRole(opr);
    }

    @Test
    public void testChangeTypeOfOperationRequiredRole() throws Throwable {
        final OperationRequiredRole opr = this.createAndSyncRepoBasicCompInterfaceAndOperationReqiredRole();
        final Repository repo = opr.getRequiredInterface__OperationRequiredRole().getRepository__Interface();

        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME);
        opr.setRequiredInterface__OperationRequiredRole(newInterface);
        super.saveAndSynchronizeChanges(opr);

        this.assertOperationRequiredRole(opr);
    }

    @Test
    public void testAddOperationRequiredRoleToSystem() throws Throwable {
        final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME);

        final OperationRequiredRole orr = super.createAndSyncOperationRequiredRole(opInterface, system);

        this.assertOperationRequiredRole(orr);
    }

}
