package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Interface
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.RepositoryFactory
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*

/**
 *  This test class tests the reactions and routines are supposed to synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
class InterfaceConceptTest extends NewPcmUmlClassApplicationTest {

	static val TEST_INTERFACE_NAME = "TestInterface"
	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = PACKAGE_NAME.toFirstUpper

	def checkInterfaceConcept(
		OperationInterface pcmInterface,
		Interface umlInterface
	) {
		assertNotNull(pcmInterface)
		assertNotNull(umlInterface)
		assertTrue(pcmInterface.entityName == umlInterface.name)
	// should be contained in corresponding repository and contracts package respectively
	// parent interfaces should correspond
	}

	@Test
	def void testCreateInterfaceConcept_UML() {
		init_UML()

		changeUmlView [
			umlContractsPackage.createOwnedInterface(TEST_INTERFACE_NAME)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlContractsPackage.ownedElements)
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateInterfaceConcept_PCM() {
		init_PCM()

		changePcmView [
			var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
			mPcmInterface.entityName = TEST_INTERFACE_NAME
			defaultPcmRepository.interfaces__Repository += mPcmInterface
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlContractsPackage.ownedElements)
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

}
