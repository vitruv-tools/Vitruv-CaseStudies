package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path
import org.emftext.language.java.containers.ContainersPackage
import static extension tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
class RepositoryConceptTest extends PcmUmlJavaTransitiveChangeTest {

	def protected checkRepositoryConcept(
		Repository pcmRepo,
		Package umlRepositoryPkg,
		Package umlContractsPkg,
		Package umlDatatypesPkg
	) {
		// correspondence constraints
		assertTrue(corresponds(pcmRepo, umlRepositoryPkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE))
		assertTrue(corresponds(pcmRepo, umlContractsPkg, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE))
		assertTrue(corresponds(pcmRepo, umlDatatypesPkg, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE))
		// containment constraints
		assertTrue(EcoreUtil.equals(umlContractsPkg.nestingPackage, umlRepositoryPkg))
		assertTrue(EcoreUtil.equals(umlDatatypesPkg.nestingPackage, umlRepositoryPkg))
		// attribute constraints
		assertTrue(umlRepositoryPkg.isPackageFor(pcmRepo))
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
		checkJavaRepositoryPackage(umlRepositoryPkg)
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
		checkRepositoryConcept(pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		checkJavaRepositoryPackage(umlRepositoryPkg)
	}

	/**
	 * Checks whether the Java package structure fits to the UML package structure (assumes UML is correct)
	 */
	def protected checkJavaRepositoryPackage(Package umlRepositoryPackage) {
		umlRepositoryPackage.checkJavaPackage
		umlRepositoryPackage.nestedPackages.forEach[checkJavaPackage]

		val javaRepositoryPackage = getFirstCorrespondingObject(umlRepositoryPackage,
			org.emftext.language.java.containers.Package)
		javaRepositoryPackage.checkUmlPackage
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		val umlModel = UMLFactory.eINSTANCE.createModel() => [
			name = "umlModel"
		]
		resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)).startRecordingChanges => [
			contents += umlModel
		]
		propagate
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")

		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.selection)
		propagate
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)

		umlRepositoryPkg = umlModel.clearResourcesAndReloadRoot.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")

		checkUmlRepositoryPackage(umlRepositoryPkg)
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository()

		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		checkPcmRepository(pcmRepository.clearResourcesAndReloadRoot)
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		val initialRepository = pcmRepository

		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.selection)
		resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += initialRepository
		]
		propagate

		pcmRepository.entityName = "Pcm2UmlNameChange"
		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		val newName = "pcm2UmlNameChange_2" // should be synchronized to upper case
		pcmRepository.entityName = newName
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.selection)
		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		assertTrue(pcmRepository.entityName == newName.toFirstUpper)
		checkPcmRepository(pcmRepository)

		// There should be no Java packages:
		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage
		umlModel.checkNumberOfJavaPackages
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository() => [
			entityName = "testCbsRepository" // has to be capitalized via round-trip
		]
		val initialRepository = pcmRepository
		userInteraction.addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += initialRepository
		]
		propagate
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		assertTrue(pcmRepository.entityName == "TestCbsRepository")
		checkPcmRepository(pcmRepository)

		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage

		userInteraction.addNextConfirmationInput(true)
		resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).propagate [
			delete(null)
		]

		assertModelNotExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		// before the following reload, the mUmlModel instance will be out of synch with the vsum 
		assertFalse(umlModel?.packagedElements.empty)
		umlModel = umlModel.clearResourcesAndReloadRoot
		assertTrue(umlModel?.packagedElements.empty)

		// There should be no Java packages:
		val allJavaPackages = getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE, org.emftext.language.java.containers.Package)
		assertEquals(umlModel?.packagedElements.size, allJavaPackages.size,
			"Too many Java packages: " + allJavaPackages)
	}
}
