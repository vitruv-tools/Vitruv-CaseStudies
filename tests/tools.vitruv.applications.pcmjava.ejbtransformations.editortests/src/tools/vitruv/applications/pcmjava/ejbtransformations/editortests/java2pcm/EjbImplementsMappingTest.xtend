package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class EjbImplementsMappingTest extends EjbJava2PcmTransformationTest {

	@Test
	def testAddImplementsToComponentClass() {
		super.createPackageEjbClassAndInterface()

		val OperationProvidedRole opr = super.
			addImplementsCorrespondingToOperationProvidedRoleToClass(TEST_CLASS_NAME, TEST_INTERFACE_NAME)

		assertEquals(TEST_CLASS_NAME + "_provides_" + TEST_INTERFACE_NAME, opr.entityName,
			"OperationProvidedRole has wrong name")
	}

}
