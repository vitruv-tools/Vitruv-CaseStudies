package tools.vitruv.applications.pcmjava.pojotransformations.editortests.java2pcm;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeReferenceMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

	@Test
	public void testAddImplementsToClassWithCorrespondingComponent() throws Throwable {
		// create repo
		this.addRepoContractsAndDatatypesPackage();
		// create class
		this.addSecondPackageCorrespondsWithoutCorrespondences();
		this.getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		final BasicComponent basicComponent = this.addClassInSecondPackage(BasicComponent.class);
		// create interface
		final OperationInterface opInterface = this.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence(
				"contracts", Pcm2JavaTestUtils.INTERFACE_NAME);

		// add the implement relation
		final OperationProvidedRole opr = super.addImplementsCorrespondingToOperationProvidedRoleToClass(
				basicComponent.getEntityName(), opInterface.getEntityName());

		assertEquals(opr.getProvidedInterface__OperationProvidedRole().getId(), opInterface.getId(),
				"The interface proivieded by the provided role is not the expected interface");
		assertEquals(opr.getProvidingEntity_ProvidedRole().getId(), basicComponent.getId(),
				"The component that provides the interface is not the expected component");
	}

}
