package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
class RepositoryConceptTest extends TransitiveChangeTest {

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
		val pcmRepository = helper.getModifiableCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(pcmRepository !== null)
		checkPcmRepository(pcmRepository)
		checkJavaRepositoryPackage(umlRepositoryPkg)
	}

	def protected checkPcmRepository(Repository pcmRepository) {
		assertTrue(pcmRepository !== null)
		val umlRepositoryPkg = helper.getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val umlContractsPkg = helper.getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = helper.getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(umlRepositoryPkg !== null)
		assertTrue(umlContractsPkg !== null)
		assertTrue(umlDatatypesPkg !== null)
		checkRepositoryConcept(correspondenceModel, pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		checkJavaRepositoryPackage(umlRepositoryPkg)
	}

	/**
	 * Checks whether the Java package structure fits to the UML package structure (assumes UML is correct)
	 */
	def protected checkJavaRepositoryPackage(Package umlRepositoryPackage) {
		checkJavaPackage(umlRepositoryPackage)
		umlRepositoryPackage.nestedPackages.forEach[checkJavaPackage]
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		var umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = "umlModel"
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE, umlModel)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")

		userInteractor.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		saveAndSynchronizeChanges(umlModel)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)

		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")

		checkUmlRepositoryPackage(umlRepositoryPkg)
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()

		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository

		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()

		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)

		pcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository

		val newName = "pcm2UmlNameChange_2" // should be synchronized to upper case
		pcmRepository.entityName = newName
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository

		assertTrue(pcmRepository.entityName == newName.toFirstUpper)
		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository" // has to be capitalized via round-trip
		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		assertTrue(pcmRepository.entityName == "TestCbsRepository")
		checkPcmRepository(pcmRepository)

		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage

		userInteractor.addNextConfirmationInput(true)
		deleteAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)

		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		// before the following reload, the mUmlModel instance will be out of synch with the vsum 
		assertFalse(umlModel?.packagedElements.empty)
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		assertTrue(umlModel?.packagedElements.empty)

		// There should be not Java packages:
		val allJavaPackages = typeof(org.emftext.language.java.containers.Package).getCorrespondingObjectsOfClass
		assertEquals("Too many Java packages: " + allJavaPackages, umlModel?.packagedElements.size, allJavaPackages.size)
	}
}
