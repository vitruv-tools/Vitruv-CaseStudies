package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class InterfaceMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {
    /**
     * interface in contracts package --> should automatically be mapped to operation interface
     *
     * @throws Throwable
     */
    @Test
    public void testAddInterfaceInContractsPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();

        final OperationInterface opIf = super.addInterfaceInContractsPackage();

        this.assertOperationInterface(repo, opIf, Pcm2JavaTestUtils.INTERFACE_NAME);
    }

    /**
     * interface in non-repository package --> "user" should be asked and decide to add it
     *
     * @throws Exception
     */
    @Test
    public void testAddArchitecturalInterfaceInNonRepositoryPackage() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

        final OperationInterface opInterface = super.addInterfaceInSecondPackageWithCorrespondence(bc.getEntityName());

        this.assertOperationInterface(repo, opInterface, Pcm2JavaTestUtils.INTERFACE_NAME);
    }

    /**
     * interface in non-repository package --> "user" should be asked and decide to not add it
     *
     * @throws Exception
     */
    @Test
    public void testAddTechnicalInterfaceInNonRepositoryPackage() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

        final EObject eObject = super.addInterfaceInPackageWithoutCorrespondence(bc.getEntityName());

        assertTrue("Corresponding object for interface that is created in non main package is not null: " + eObject,
                null == eObject);
    }

    @Test
    public void testRenameInterfaceWithCorrespondence() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsToBasicComponent();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();

        final OperationInterface newOpInterface = this.renameClassifierWithName(opInterface.getEntityName(),
                Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME, OperationInterface.class);

        this.assertOperationInterface(repo, newOpInterface, Pcm2JavaTestUtils.INTERFACE_NAME
                + Pcm2JavaTestUtils.RENAME);
    }
}
