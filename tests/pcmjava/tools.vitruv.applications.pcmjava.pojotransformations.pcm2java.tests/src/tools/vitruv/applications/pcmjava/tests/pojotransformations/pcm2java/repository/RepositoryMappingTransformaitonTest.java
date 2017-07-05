package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;

public class RepositoryMappingTransformaitonTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddRepository() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

        this.assertRepositoryCorrespondences(repo);
    }

    @Test
    public void testRepositoryNameChange() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

        // Test
        repo.setEntityName(Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.saveAndSynchronizeChanges(repo);

        // check
        this.assertRepositoryCorrespondences(repo);
    }

    @Test
    public void testRepositoryNameChangeWithComponents() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);
        
        // Test
        repo.setEntityName(Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.saveAndSynchronizeChanges(repo);

        // check
        this.assertRepositoryCorrespondences(repo);
        this.assertBasicComponentCorrespondences(repo, basicComponent);
    }
    
    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final Repository repository, final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(basicComponent, 3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { repository.getEntityName() + "." + basicComponent.getEntityName() + "." + basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }
    
    private void assertRepositoryCorrespondences(final Repository repo) throws Throwable {
        if (null == this.getCorrespondenceModel()) {
            fail("correspondence instance is still null - no transformation was executed.");
            return;
        }
        final Set<Package> jaMoPPPackages = CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceModel(), repo, Package.class);
        boolean foundRepositoryPackage = false;
        boolean foundDatatypesPackage = false;
        boolean foundContractsPackage = false;
        for (final Package pack : jaMoPPPackages) {
            if (pack.getName().equals(repo.getEntityName())) {
                foundRepositoryPackage = true;
            }
            if (pack.getName().equals("contracts")) {
                foundContractsPackage = true;
            }
            if (pack.getName().equals("datatypes")) {
                foundDatatypesPackage = true;
            }
        }
        assertTrue("No correspondeing repository package found", foundRepositoryPackage);
        assertTrue("No correspondeing datatype package found", foundDatatypesPackage);
        assertTrue("No correspondeing contracts package found", foundContractsPackage);
    }
}
