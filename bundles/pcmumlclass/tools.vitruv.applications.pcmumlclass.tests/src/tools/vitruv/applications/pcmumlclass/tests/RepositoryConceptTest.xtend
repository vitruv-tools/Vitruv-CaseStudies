package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
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

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class RepositoryConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(RepositoryConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	private static val PKG_INSERT_CORR_TO_REPOSITORY = 0 // TODO better solution?
	
	override protected setup() {
	}
	
	def protected EObject reloadResourceAndReturnRoot(EObject modelElement){
		// TODO this is a hack for testing: 
		//	- load tools.vitruv.testutils into workspace
		// 	- change VitruviusApplicationTest.changeRecorder to protected, in order to make it accessible here
		changeRecorder.removeFromRecording(modelElement.eResource) 
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		val rootElement = resourceSet.getResource(resourceURI,true).contents.head
		if(rootElement !== null){
			startRecordingChanges(rootElement) // calls changeRecorder.addToRecording -> calls registerContentsAtUuidResolver
		}
		return rootElement 
	} 
	
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
	
	def protected checkUmlRepositoryPackage(Package mUmlRepositoryPkg){
		assertTrue(mUmlRepositoryPkg !== null)
		val mPcmRepository = getModifiableCorr(mUmlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(mPcmRepository !== null)
		val mUmlContractsPkg = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val mUmlDatatypesPkg = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(mUmlContractsPkg !== null)
		assertTrue(mUmlDatatypesPkg !== null)
		checkRepositoryConcept(correspondenceModel, mPcmRepository, mUmlRepositoryPkg, mUmlContractsPkg, mUmlDatatypesPkg)
	}
	
	def protected checkPcmRepository(Repository mPcmRepository){
		assertTrue(mPcmRepository !== null)
		val mUmlRepositoryPkg = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val mUmlContractsPkg = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val mUmlDatatypesPkg = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(mUmlRepositoryPkg !== null)
		assertTrue(mUmlContractsPkg !== null)
		assertTrue(mUmlDatatypesPkg !== null)
		checkRepositoryConcept(correspondenceModel, mPcmRepository, mUmlRepositoryPkg, mUmlContractsPkg, mUmlDatatypesPkg)
	}

	@Test
	def testCreateRepositoryConcept_UML() {
		userInteractor.addNextSelections(PKG_INSERT_CORR_TO_REPOSITORY)
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		
		var mUmlModel = UMLFactory.eINSTANCE.createModel()
		createAndSynchronizeModel(UML_MODEL_FILE, mUmlModel)
		
		mUmlModel.name = "umlModel"
		var mUmlRepositoryPkg = mUmlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(mUmlModel)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		mUmlModel = reloadResourceAndReturnRoot(mUmlModel) as Model
		mUmlRepositoryPkg = mUmlModel.nestedPackages.head
		assertTrue(mUmlRepositoryPkg.name == "testCbsRepository")
		
		checkUmlRepositoryPackage(mUmlRepositoryPkg)
		return
	}
	
	@Test
	def void testCreateRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		checkPcmRepository(mPcmRepository)
	}

	@Test
	def void testSuppressModelCreationChangesAfterReload_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		logger.debug("Attempting to save reloaded pcmRepository after additional changes.")
		mPcmRepository.entityName = "someOtherRepositoryName"
		saveAndSynchronizeChanges(mPcmRepository)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		checkPcmRepository(mPcmRepository)
	}
	
	// Weirdly, this test succeeds if tested together with the others, but fails, when tested on its own.
	// The testSuppressModelCreationChangesAfterReload_UML test succeeds in both cases.
	@Test
	def void testSuppressModelCreationChangesAfterReload_UML() {
		userInteractor.addNextSelections(PKG_INSERT_CORR_TO_REPOSITORY)
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		
		var mUmlModel = UMLFactory.eINSTANCE.createModel()
		createAndSynchronizeModel(UML_MODEL_FILE, mUmlModel)
		
		mUmlModel.name = "umlrootmodel"
		var mUmlRepositoryPkg = mUmlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(mUmlModel)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		mUmlModel = reloadResourceAndReturnRoot(mUmlModel) as Model
		mUmlRepositoryPkg = mUmlModel.nestedPackages.head
		assertTrue(mUmlRepositoryPkg.name == "testCbsRepository")
		
		logger.debug("Attempting to save reloaded umlModel after additional changes.")
		mUmlModel.name = "someOtherModelName"
		mUmlRepositoryPkg.name = "someOtherRepositoryName"
		saveAndSynchronizeChanges(mUmlModel)

		mUmlModel = reloadResourceAndReturnRoot(mUmlModel) as Model
		mUmlRepositoryPkg = mUmlModel.nestedPackages.head
		assertTrue(mUmlRepositoryPkg.name == "someOtherRepositoryName")
		checkUmlRepositoryPackage(mUmlRepositoryPkg)
	}
	
	@Test
	def void testRenameRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		mPcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(mPcmRepository)
		mPcmRepository.entityName = "pcm2UmlNameChange_2" // still ok
		saveAndSynchronizeChanges(mPcmRepository)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		assertTrue(mPcmRepository.entityName == "Pcm2UmlNameChange_2")
		checkPcmRepository(mPcmRepository)
	}
	
	@Test 
	def void testDeleteRepositoryConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository" //has to be capitalized via round-trip -> makes reload necessary
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository	
		assertTrue(mPcmRepository.entityName == "TestCbsRepository")
		checkPcmRepository(mPcmRepository)
		
		var mUmlRepositoryPackage = getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var mUmlModel = mUmlRepositoryPackage.nestingPackage
		
		userInteractor.addNextSelections(DefaultLiterals.INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES)
		deleteAndSynchronizeModel(PCM_MODEL_FILE)
		
		assertModelNotExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
//		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository// can't reload, because it doesn't exist
		mUmlModel = reloadResourceAndReturnRoot(mUmlModel) as Model
		assertTrue(mUmlModel?.packagedElements.empty)
	}
}
