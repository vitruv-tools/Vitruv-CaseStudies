package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class ClassMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

    /**
     * Class that in mapped package and same name as component + impl--> should be the new
     * implementing class for the component
     *
     * @throws Exception
     */
    @Test
    public void testAddComponentClassInPackageWithCorrespondingComponent() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        final BasicComponent bc = super.addSecondPackageCorrespondsToBasicComponent();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
        final BasicComponent bcForClass = super.addClassInSecondPackage(BasicComponent.class);

        super.assertRepositoryAndPCMName(repo, bcForClass, bc.getEntityName());
    }

    @Test
    public void testAddCompositeComponentClassInPackageWithCorrespondingCompositeComponent() throws Throwable {
        final Repository repo = super.addRepoContractsAndDatatypesPackage();
        final CompositeComponent cc = super.addSecondPackageCorrespondsToCompositeComponent();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
        final CompositeComponent ccForClass = this.addClassInSecondPackage(CompositeComponent.class);

        super.assertRepositoryAndPCMName(repo, ccForClass, cc.getEntityName());
    }

    @Test
    public void testAddSystemClassInPackageWithCorrespondingSystem() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final System pcmSystem = super.addSecondPackageCorrespondsToSystem();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
        final System systemForClass = super.addClassInSecondPackage(System.class);

        super.assertPCMNamedElement(systemForClass, pcmSystem.getEntityName());
    }

    /**
     * Test ii) class in non corresponding package --> should not be mapped to a Basic Component
     *
     * @throws Throwable
     */
    @Test
    public void testAddClassInPackageWithoutCorrespondingComponent() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsWithoutCorrespondences();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
        try {
            final EObject eObject = super.addClassInPackage(this.secondPackage, EObject.class);
            fail("The class should not have any correspondences, but it has a correspondence to eObject: " + eObject);
        } catch (final RuntimeException re) {
            // ecpected exception
        }
    }

    @Test
    public void testAddBasicComponentClassInPackageWithoutCorrespondence() throws Throwable {
        final Repository repo = this.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsWithoutCorrespondences();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
        final BasicComponent newBc = super.addClassInSecondPackage(BasicComponent.class);

        super.assertRepositoryAndPCMName(repo, newBc, Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME);
    }

    @Test
    public void testAddCompositeComponentClassInPackageWithoutCorrespondence() throws Throwable {
        final Repository repo = this.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsWithoutCorrespondences();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
        final CompositeComponent cc = super.addClassInSecondPackage(CompositeComponent.class);

        super.assertRepositoryAndPCMName(repo, cc, Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME);
    }

    @Test
    public void testAddSystemClassInPackageWithoutCorrespondence() throws Throwable {
        this.addRepoContractsAndDatatypesPackage();
        super.addSecondPackageCorrespondsWithoutCorrespondences();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
        final System pcmSystem = super.addClassInSecondPackage(System.class);

        this.assertPCMNamedElement(pcmSystem, Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME);
    }

    @Test
    public void testAddCompsiteDatatypeClassInDatatypePackage() throws Throwable {
        final Repository repo = this.addRepoContractsAndDatatypesPackage();

        final CompositeDataType cdt = this.addClassThatCorrespondsToCompositeDatatype();

        this.assertRepositoryAndPCMNameForDatatype(repo, cdt, Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME);
    }

    @Test
    public void testAddCollectionDatatypeClassInDatatypePackage() throws Throwable {
        final Repository repo = this.addRepoContractsAndDatatypesPackage();

        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
        final CollectionDataType collection = super.addClassInPackage(this.getDatatypesPackage(),
                CollectionDataType.class);

        this.assertRepositoryAndPCMNameForDatatype(repo, collection, Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME);
    }

    @Test
    public void testAddClassInDatatypePackage() throws Throwable {
        this.addRepoContractsAndDatatypesPackage();
        try {
            this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
            final EObject eObject = super.addClassInPackage(this.getDatatypesPackage(), EObject.class);
            fail("The class should not have any datatype correspondences, but it has a correspondence to eObject: "
                    + eObject);
        } catch (final RuntimeException re) {
            // expected Exception
        }
    }

    @Test
    public void testRenameBasicComponentClass() throws Throwable {
        final Repository repo = this.addRepoContractsAndDatatypesPackage();
        this.addSecondPackageCorrespondsWithoutCorrespondences();
        this.getUserInteractor().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
        final BasicComponent basicComponent = this.addClassInSecondPackage(BasicComponent.class);

        final BasicComponent newBasicComponent = super.renameClassifierWithName(basicComponent.getEntityName(),
                Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME, BasicComponent.class);

        this.assertRepositoryAndPCMName(repo, newBasicComponent,
                Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME);
        this.assertFilesOnlyForEObjects(newBasicComponent);
    }

}
