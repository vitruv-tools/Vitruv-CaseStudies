package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
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
class RepositoryConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(RepositoryConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION 
	
	def protected static checkRepositoryConcept(
			CorrespondenceModel cm, 
			Repository pcmRepo,
			Package umlRepositoryPkg, 
			Package umlContractsPkg,
			Package umlDatatypesPkg
	){
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
	
	def protected checkUmlRepositoryPackage(Package umlRepositoryPkg){
		assertTrue(umlRepositoryPkg !== null)
		val pcmRepository = getModifiableCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(pcmRepository !== null)
		checkPcmRepository(pcmRepository)
	}
	
	def protected checkPcmRepository(Repository pcmRepository){
		assertTrue(pcmRepository !== null)
		val umlRepositoryPkg = getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val umlContractsPkg = getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(umlRepositoryPkg !== null)
		assertTrue(umlContractsPkg !== null)
		assertTrue(umlDatatypesPkg !== null)
		checkRepositoryConcept(correspondenceModel, pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		userInteractor.addNextSelections(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		
		var umlModel = UMLFactory.eINSTANCE.createModel()
		createAndSynchronizeModel(UML_MODEL_FILE, umlModel)
		
		umlModel.name = "umlModel"
		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(umlModel)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")
		
		checkUmlRepositoryPackage(umlRepositoryPkg)
	}
	
	@Test
	def void testCreateRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testSuppressModelCreationChangesAfterReload_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		logger.debug("Attempting to save reloaded pcmRepository after additional changes.")
		pcmRepository.entityName = "someOtherRepositoryName"
		saveAndSynchronizeChanges(pcmRepository)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		checkPcmRepository(pcmRepository)
	}
	
	// Weirdly, this test succeeds if tested together with the others, but fails, when tested on its own.
	// The testSuppressModelCreationChangesAfterReload_UML test succeeds in both cases.
	@Test
	def void testSuppressModelCreationChangesAfterReload_UML() {
		userInteractor.addNextSelections(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		
		var umlModel = UMLFactory.eINSTANCE.createModel()
		createAndSynchronizeModel(UML_MODEL_FILE, umlModel)
		
		umlModel.name = "umlrootmodel"
		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(umlModel)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")
		
		logger.debug("Attempting to save reloaded umlModel after additional changes.")
		umlModel.name = "someOtherModelName"
		umlRepositoryPkg.name = "someOtherRepositoryName"
		saveAndSynchronizeChanges(umlModel)

		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "someOtherRepositoryName")
		checkUmlRepositoryPackage(umlRepositoryPkg)
	}
	
	@Test
	def void testRenameRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		pcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository.entityName = "pcm2UmlNameChange_2" // still ok
		saveAndSynchronizeChanges(pcmRepository)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		assertTrue(pcmRepository.entityName == "Pcm2UmlNameChange_2")
		checkPcmRepository(pcmRepository)
	}
	
	@Test 
	def void testDeleteRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository" //has to be capitalized via round-trip -> makes reload necessary
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository	
		assertTrue(pcmRepository.entityName == "TestCbsRepository")
		checkPcmRepository(pcmRepository)
		
		var umlRepositoryPackage = getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage
		
		userInteractor.addNextSelections(DefaultLiterals.INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES)
		deleteAndSynchronizeModel(PCM_MODEL_FILE)
		
		assertModelNotExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		assertFalse(umlModel?.packagedElements.empty) //before the reload, the mUmlModel instance will be out of synch with the vsum 
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		assertTrue(umlModel?.packagedElements.empty)
	}
}
