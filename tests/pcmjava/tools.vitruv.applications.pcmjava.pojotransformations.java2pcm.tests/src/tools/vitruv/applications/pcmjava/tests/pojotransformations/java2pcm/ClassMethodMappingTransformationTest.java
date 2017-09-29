package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import org.junit.Test;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class ClassMethodMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

    @Test
    public void testAddClassMethodWithCorrespondence() throws Throwable {
        // create repo
        super.addRepoContractsAndDatatypesPackage();
        //this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
        // create component implementing class
        super.addPackageAndImplementingClass(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
        // create interface
        super.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence("contracts", Pcm2JavaTestUtils.INTERFACE_NAME);
        // create interface method
        super.addMethodToInterfaceWithCorrespondence(Pcm2JavaTestUtils.INTERFACE_NAME,
                Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
        // add implements/provided role
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(
                Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Impl", Pcm2JavaTestUtils.INTERFACE_NAME);

        // actual test: add class method to implementing class that overrides the interface
        final ResourceDemandingSEFF correspondingSeff = super.addClassMethodToClassThatOverridesInterfaceMethod(
                Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Impl", Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);

        // assert the correspondingSEFF
        assertCorrespondingSEFF(correspondingSeff,  Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

}
