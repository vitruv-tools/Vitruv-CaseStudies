package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.pcmumlclass.tests.UmlQueryUtil.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
class RepositoryConceptTest extends NewPcmUmlClassApplicationTest {

	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	@Test
	def void testCreateRepositoryConcept_UML_System() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_SYSTEM_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmSystemView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmSystem()
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryConcept_UML_Repository() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmRepository
			assertElementsEqual_deep(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = defaultPcmRepository
			assertElementsEqual_deep(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryConcept_UML() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		changeUmlView[
			defaultUmlModel.getPackagedElement(PACKAGE_NAME).setName(PACKAGE_RENAMED)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_RENAMED)
			val pcmPackage = claimPcmRepository(PACKAGE_RENAMED.toFirstUpper)
			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		changePcmView[
			defaultPcmRepository.setEntityName(PACKAGE_RENAMED.toFirstUpper)
		]

		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_RENAMED)
			val pcmPackage = claimPcmRepository(PACKAGE_RENAMED.toFirstUpper)
			assertElementsEqual_deep(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		createPcmRepository(PACKAGE_NAME_FIRST_UPPER)

		validateUmlAndPcmPackagesView [
			assertNotNull(defaultPcmRepository)
			assertNotNull(defaultUmlModel)
		]
		assertModelExists(MODEL_NAME)

		userInteraction.addNextConfirmationInput(true)
		changePcmView[
			EcoreUtil.delete(defaultPcmRepository)
		]

		validateUmlAndPcmPackagesView [
			val umlModel = defaultUmlModel

			assertNull(defaultPcmRepository)
			assertTrue(defaultUmlModel.eContents.empty)
			assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
			assertModelNotExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
			assertTrue(umlModel?.packagedElements.empty)
		]
	}

	@Test
	def void testDeleteRepositoryConcept_UMLModel() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmPackagesView [
			assertNotNull(defaultPcmRepository)
			assertNotNull(defaultUmlModel)
		]

		changeUmlView[
			EcoreUtil.delete(defaultUmlModel)
		]

		validateUmlAndPcmPackagesView [
			assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
			assertModelNotExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		]
	}

	@Test
	def void testDeleteRepositoryConcept_UML() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		createUmlRootPackage(PACKAGE_NAME)

		validateUmlAndPcmPackagesView [
			assertNotNull(defaultPcmRepository)
			assertNotNull(defaultUmlModel)
		]

		changeUmlView[
			EcoreUtil.delete(defaultUmlModel.getNestedPackages().findFirst[it.name == PACKAGE_NAME])
		]

		validateUmlAndPcmPackagesView [
			assertNull(defaultPcmRepository)
			assertTrue(defaultUmlModel.getNestedPackages().empty)
		]
	}
}
