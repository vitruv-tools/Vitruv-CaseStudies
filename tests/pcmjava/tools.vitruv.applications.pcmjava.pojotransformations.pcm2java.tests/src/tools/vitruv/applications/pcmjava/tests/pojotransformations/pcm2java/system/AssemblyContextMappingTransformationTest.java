package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system;

import org.junit.Test;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class AssemblyContextMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testCreateAssemblyContext() throws Throwable {
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        final Repository repo = super.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = super.addBasicComponentAndSync(repo);

        final AssemblyContext assemblyContext = this.createAndSyncAssemblyContext(system, basicComponent);

        this.assertAssemblyContext(assemblyContext);
    }

}
