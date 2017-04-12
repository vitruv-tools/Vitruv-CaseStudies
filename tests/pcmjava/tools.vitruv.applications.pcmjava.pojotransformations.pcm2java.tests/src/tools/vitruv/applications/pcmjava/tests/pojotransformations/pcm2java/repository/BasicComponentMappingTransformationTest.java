package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class BasicComponentMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testRenameBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        basicComponent.setEntityName(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME);
        this.saveAndSynchronizeChanges(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testDeleteBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        EcoreUtil.delete(basicComponent);
        super.saveAndSynchronizeChanges(repo);

        this.assertEmptyCorrespondence(basicComponent);
        this.assertCompilationUnitForBasicComponentDeleted(basicComponent);
    }

    @Test
    public void testAddTwoBasicComponentAndDeleteOne() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);
        final BasicComponent basicComponent2 = this.addBasicComponentAndSync(repo, "SecondBasicComponent");

        EcoreUtil.delete(basicComponent);
        super.saveAndSynchronizeChanges(repo);

        this.assertEmptyCorrespondence(basicComponent);
        this.assertBasicComponentCorrespondences(basicComponent2);
    }

    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(basicComponent, 3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }

}
