package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
class RepositoryConceptTest extends PcmUmlClassApplicationTest {

	def protected static checkRepositoryConcept(
		CorrespondenceModel cm,
		Repository pcmRepo,
		Package umlRepositoryPkg,
		Package umlContractsPkg,
		Package umlDatatypesPkg
	) {
		// correspondence constraints
		assertTrue(corresponds(cm, pcmRepo, umlRepositoryPkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE))
		assertTrue(corresponds(cm, pcmRepo, umlContractsPkg, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE))
		assertTrue(corresponds(cm, pcmRepo, umlDatatypesPkg, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE))
		// containment constraints
		assertTrue(EcoreUtil.equals(umlContractsPkg.nestingPackage, umlRepositoryPkg))
		assertTrue(EcoreUtil.equals(umlDatatypesPkg.nestingPackage, umlRepositoryPkg))
		// attribute constraints
		assertTrue(umlRepositoryPkg.name == pcmRepo.entityName.toFirstLower)
		assertTrue(umlRepositoryPkg.name.toFirstUpper == pcmRepo.entityName)
		assertTrue(umlContractsPkg.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME)
		assertTrue(umlDatatypesPkg.name == DefaultLiterals.DATATYPES_PACKAGE_NAME)
	}

	def protected checkUmlRepositoryPackage(Package umlRepositoryPkg) {
		assertTrue(umlRepositoryPkg !== null)
		val pcmRepository = helper.getModifiableCorr(umlRepositoryPkg, Repository,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(pcmRepository !== null)
		checkPcmRepository(pcmRepository)
	}

	def protected checkPcmRepository(Repository pcmRepository) {
		assertTrue(pcmRepository !== null)
		val umlRepositoryPkg = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val umlContractsPkg = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(umlRepositoryPkg !== null)
		assertTrue(umlContractsPkg !== null)
		assertTrue(umlDatatypesPkg !== null)
		checkRepositoryConcept(correspondenceModel, pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		val umlModel = UMLFactory.eINSTANCE.createModel() => [
			name = "umlModel"
		]
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)).startRecordingChanges => [
			contents += umlModel
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")

		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)

		umlRepositoryPkg = umlModel.clearResourcesAndReloadRoot.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")

		checkUmlRepositoryPackage(umlRepositoryPkg)
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository()

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		checkPcmRepository(pcmRepository.clearResourcesAndReloadRoot)
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		val initialRepository = pcmRepository

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += initialRepository
		]
		propagate

		pcmRepository.entityName = "Pcm2UmlNameChange"
		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		val newName = "pcm2UmlNameChange_2" // should be synchronized to upper case
		pcmRepository.entityName = newName
		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		assertTrue(pcmRepository.entityName == newName.toFirstUpper)
		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository() => [
			entityName = "testCbsRepository" // has to be capitalized via round-trip
		]
		val initialRepository = pcmRepository

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += initialRepository
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		assertTrue(pcmRepository.entityName == "TestCbsRepository")
		checkPcmRepository(pcmRepository)

		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage

		userInteraction.addNextConfirmationInput(true)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).propagate [
			delete(null)
		]

		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		// before the following reload, the mUmlModel instance will be out of synch with the vsum 
		assertFalse(umlModel?.packagedElements.empty)
		umlModel = umlModel.clearResourcesAndReloadRoot
		assertTrue(umlModel?.packagedElements.empty)
	}
}
