package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class EjbFieldMappingTest extends EjbJava2PcmTransformationTest {

	@Test
	def void annotateField() {
		val basicComponent = super.createPackageEjbClassAndInterface()

		super.addFieldToClassWithName(TEST_CLASS_NAME, TEST_INTERFACE_NAME, TEST_FIELD_NAME, null)
		val opRequiredRole = super.addAnnotationToField(TEST_FIELD_NAME, EJB_FIELD_ANNOTATION_NAME,
			OperationRequiredRole, TEST_CLASS_NAME)

		assertEquals(basicComponent.id, opRequiredRole.requiringEntity_RequiredRole.id,
			"BasicComponent of required role is wrong")
		assertEquals(TEST_INTERFACE_NAME, opRequiredRole.requiredInterface__OperationRequiredRole.entityName,
			"Required Interface has wrong name")
	}
}
