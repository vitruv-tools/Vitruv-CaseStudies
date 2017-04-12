package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class SystemMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testCreateSystem() throws Throwable {
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);

        this.assertSystem(system);
    }

    @Test
    public void testRenameSystem() throws Throwable {
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);

        system.setEntityName(Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME);
        super.saveAndSynchronizeChanges(system);

        this.assertSystem(system);
    }

    @Test
    public void testRemoveSystem() throws Throwable {
        final System system = super.createAndSyncSystem(Pcm2JavaTestUtils.SYSTEM_NAME);
        this.assertSystem(system);
        
        deleteAndSynchronizeModel(createSystemPathInProject(system.getEntityName()));
        
        assertEmptyCorrespondence(system);
        assertCompilationUnitForSystemDeleted(system);
        // TODO There should may be more deletions here than only the system
    }

    /**
     * a system should correspond to a Package with its name and a class with its name "Impl"
     *
     * @param system
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertSystem(final System system) throws Throwable {
        final String expectedName = system.getEntityName();
        this.assertCorrespondnecesAndCompareNames(system, 3,
                new Class[] { Package.class, CompilationUnit.class, org.emftext.language.java.classifiers.Class.class },
                new String[] { expectedName, expectedName + "Impl", expectedName + "Impl" });
    }
}
