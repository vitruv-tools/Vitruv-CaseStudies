package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class TypeReferenceMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

    @Test
    public void testAddImplementsToClassWithCorrespondingComponent() throws Throwable {
        // crete repo
        this.addRepoContractsAndDatatypesPackage();
        // create class
        this.addSecondPackageCorrespondsWithoutCorrespondences();
        this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
        final BasicComponent basicComponent = this.addClassInSecondPackage(BasicComponent.class);
        // create interface
        final OperationInterface opInterface = this.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence("contracts",
                Pcm2JavaTestUtils.INTERFACE_NAME);

        // add the implement relation
        final OperationProvidedRole opr = super.addImplementsCorrespondingToOperationProvidedRoleToClass(
                basicComponent.getEntityName(), opInterface.getEntityName());

        assertEquals("The interface proivieded by the provided role is not the expected interface",
                opr.getProvidedInterface__OperationProvidedRole().getId(), opInterface.getId());
        assertEquals("The component that provides the interface is not the expected component",
                opr.getProvidingEntity_ProvidedRole().getId(), basicComponent.getId());
    }

}
