package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.RepositoryFactory
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*

/**
 *  This test class tests the reactions and routines are supposed to synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
class InterfaceConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_INTERFACE_NAME = "TestInterface"
	static val NEW_TEST_INTERFACE_NAME = "NewTestInterface"

	@Test
	def void testCreateInterfaceConcept_UML() {
		initUMLModel()

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
		initPCMRepository()

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

	@Test
	def void testRenameInterfaceConcept_UML() {
		initUMLModel()

		changeUmlView [
			umlContractsPackage.createOwnedInterface(TEST_INTERFACE_NAME)
			assertNotNull(umlContractsPackage.claimInterface(TEST_INTERFACE_NAME))
		]

		changeUmlView[
			umlContractsPackage.claimInterface(TEST_INTERFACE_NAME).name = NEW_TEST_INTERFACE_NAME
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlContractsPackage.ownedElements)
			assertNotNull(umlContractsPackage.claimInterface(NEW_TEST_INTERFACE_NAME))
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameInterfaceConcept_PCM() {
		initPCMRepository()

		changePcmView [
			var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
			pcmInterface.entityName = TEST_INTERFACE_NAME
			defaultPcmRepository.interfaces__Repository += pcmInterface
			assertEquals(1, defaultPcmRepository.interfaces__Repository.size,
				"There should be exactly one element in list.")
			assertEquals(TEST_INTERFACE_NAME,
				(defaultPcmRepository.interfaces__Repository.get(0) as OperationInterface).entityName)
		]

		changePcmView [
			(defaultPcmRepository.interfaces__Repository.
				get(0) as OperationInterface).entityName = NEW_TEST_INTERFACE_NAME
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertNotNull(umlContractsPackage.ownedElements)
			assertNotNull(pcmPackage.eContents)
			assertEquals(NEW_TEST_INTERFACE_NAME,
				(defaultPcmRepository.interfaces__Repository.get(0) as OperationInterface).entityName)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testDeleteInterfaceConcept_UML() {
		initUMLModel()

		changeUmlView [
			umlContractsPackage.createOwnedInterface(TEST_INTERFACE_NAME)
			assertNotNull(umlContractsPackage.claimInterface(TEST_INTERFACE_NAME))
		]

		changeUmlView[
			umlContractsPackage.claimInterface(TEST_INTERFACE_NAME).destroy
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertTrue(umlContractsPackage.ownedElements.empty)
			assertTrue(pcmPackage.eContents.empty)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testDeleteInterfaceConcept_PCM() {
		initPCMRepository()

		changePcmView [
			var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
			pcmInterface.entityName = TEST_INTERFACE_NAME
			defaultPcmRepository.interfaces__Repository += pcmInterface
			assertEquals(1, defaultPcmRepository.interfaces__Repository.size,
				"There should be exactly one element in list.")
			assertEquals(TEST_INTERFACE_NAME,
				(defaultPcmRepository.interfaces__Repository.get(0) as OperationInterface).entityName)
		]

		changePcmView [
			defaultPcmRepository.interfaces__Repository.remove(0)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			assertTrue(umlContractsPackage.ownedElements.empty)
			assertTrue(pcmPackage.eContents.empty)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}
}
