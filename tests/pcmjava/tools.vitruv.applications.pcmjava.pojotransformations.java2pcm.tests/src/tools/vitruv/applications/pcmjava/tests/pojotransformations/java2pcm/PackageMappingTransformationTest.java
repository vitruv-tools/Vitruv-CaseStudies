package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.Package;
import org.junit.Assert;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;
import static java.util.stream.Collectors.toSet;

public class PackageMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

    /**
     * first package is created --> should be mapped to a repository
     *
     * @throws Throwable
     */
    @Test
    public void testAddFirstPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        assertEquals("Name of the repository is not the same as the name of the package",
                Pcm2JavaTestUtils.REPOSITORY_NAME_EXPECTED, repo.getEntityName());
        this.assertResourceAndFileForEObjects(repo);
        this.assertFilesOnlyForEObjects(repo);
    }

    @Test
    public void testAddFirstPackageWithoutFile() throws Throwable {
        super.createPackage(new String[] { Pcm2JavaTestUtils.REPOSITORY_NAME });
        final CorrespondenceModel ci = this.getCorrespondenceModel();
        assertTrue("CorrespondenceModel == null", null != ci);
        assertTrue("No repository found in correspondence instance.",
                0 < ci.getAllEObjectsOfTypeInCorrespondences(Repository.class).size());
    }

    /**
     * second packages is added --> should be mapped to a basic component
     *
     * @throws Throwable
     */
    @Test
    public void testAddSecondPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

        this.assertRepositoryAndPCMName(repo, bc, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
        this.assertFilesOnlyForEObjects(bc);
    }

    @Test
    public void testCreateCompositeComponent() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        final CompositeComponent cc = super.addSecondPackageCorrespondsToCompositeComponent();

        this.assertRepositoryAndPCMName(repo, cc, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
        this.assertFilesOnlyForEObjects(cc);
    }

    @Test
    public void testCreateSystem() throws Throwable {
        final Repository repository = super.addRepoContractsAndDatatypesPackage();

        final System system = super.addSecondPackageCorrespondsToSystem();

        this.assertPCMNamedElement(system, Pcm2JavaTestUtils.SYSTEM_NAME);
        this.assertFilesOnlyForEObjects(repository, system);
    }

    @Test
    public void testCreateNoCorrespondingObject() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        super.addSecondPackageCorrespondsWithoutCorrespondences();
        Assert.assertNotNull(repo);
        // TODO:what to check?
    }

    @Test
    public void testRenamePackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsToBasicComponent();

        final String packageName = Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME;

        final Package renamedPackage = super.renamePackage(this.secondPackage, packageName);

        // EcoreResourceBridge.saveEObjectAsOnlyContent(secondPackage, );
        //waitForSynchronization();

        final Set<EObject> correspondingEObjects = CorrespondenceModelUtil
                .getCorrespondingEObjects(this.getCorrespondenceModel(), renamedPackage)
                .stream().filter(it -> it != null).collect(toSet()); // filter out null from non-EObject correspondence
        final EObject correspondingEObject = claimOne(correspondingEObjects);
        assertTrue("The corresponding EObject for the package has to be a BasicComponent",
                correspondingEObject instanceof BasicComponent);
        final BasicComponent bc = (BasicComponent) correspondingEObject;
        // repository of basic component has to be the repository
        assertEquals("Repository of basic compoennt is not the repository: " + repo, repo.getId(),
                bc.getRepository__RepositoryComponent().getId());
        // name should be changed since there is no implementing class (yet) fot the component
        assertTrue(
                "The name of the basic component, which is '" + bc.getEntityName()
                        + "', is not contained in the name of the package: " + packageName + " ",
                packageName.toLowerCase().contains(bc.getEntityName().toLowerCase()));
        this.assertResourceAndFileForEObjects(bc);
    }
}
