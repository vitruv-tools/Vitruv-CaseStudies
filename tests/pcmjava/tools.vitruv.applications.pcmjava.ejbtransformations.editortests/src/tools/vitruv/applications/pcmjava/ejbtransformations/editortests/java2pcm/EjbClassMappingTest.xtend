package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals

class EjbClassMappingTest extends EjbJava2PcmTransformationTest {

	@Test
	def testCreateClassAndAddStatelessAnnotation() {
		super.addRepoContractsAndDatatypesPackage()

		val correspondingBasicComponent = createEjbClass(TEST_CLASS_NAME)

		assertEquals(correspondingBasicComponent.entityName, TEST_CLASS_NAME,
			"Created component has different name as added class")
	}

	@Test
	def testCreateMethodThatOverridesInterfaceMethod() {
		super.createPackageEjbClassAndInterface()
		super.addImplementsCorrespondingToOperationProvidedRoleToClass(TEST_CLASS_NAME, TEST_INTERFACE_NAME)
		val correspondingOpSignature = super.addMethodToInterfaceWithCorrespondence(TEST_INTERFACE_NAME)

		val rdSEFF = super.addClassMethodToClassThatOverridesInterfaceMethod(TEST_CLASS_NAME,
			correspondingOpSignature.entityName)

		assertEquals(rdSEFF.describedService__SEFF.id, correspondingOpSignature.id, "RDSEFF describes wrong service")
	}

}
