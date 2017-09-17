package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class RequiredDelegationConnectorMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Ignore
    @Test
    public void testAddRequireDelegationConnector() throws Throwable {
        final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComp = super.addBasicComponentAndSync(repo, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
        final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
                Pcm2JavaTestUtils.INTERFACE_NAME);
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        final AssemblyContext assemblyContext = super.createAndSyncAssemblyContext(system, basicComp);
        final OperationRequiredRole basicComponentRequiredRole = super.createAndSyncOperationRequiredRole(opInterface,
                basicComp);
        final OperationRequiredRole systemRequiredRole = super.createAndSyncOperationRequiredRole(opInterface, system);

        final RequiredDelegationConnector requiredDelegationConnector = CompositionFactory.eINSTANCE
                .createRequiredDelegationConnector();
        requiredDelegationConnector.setAssemblyContext_RequiredDelegationConnector(assemblyContext);
        requiredDelegationConnector.setInnerRequiredRole_RequiredDelegationConnector(basicComponentRequiredRole);
        requiredDelegationConnector.setOuterRequiredRole_RequiredDelegationConnector(systemRequiredRole);
        requiredDelegationConnector.setParentStructure__Connector(system);
        super.saveAndSynchronizeChanges(system);

        this.assertRequiredDelegationConnector(requiredDelegationConnector);
    }

    private void assertRequiredDelegationConnector(final RequiredDelegationConnector requiredDelegationConnector) {
        fail("no assert yet");
    }

}
