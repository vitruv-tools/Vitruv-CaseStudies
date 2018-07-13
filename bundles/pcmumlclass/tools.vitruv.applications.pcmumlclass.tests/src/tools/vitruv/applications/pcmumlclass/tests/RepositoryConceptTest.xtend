package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.jdt.internal.core.ModelUpdater
import tools.vitruv.framework.util.bridges.EcoreBridge
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.RepositoryFactory

class RepositoryConceptTest extends PcmUmlClassApplicationTest {

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	private static val PKG_INSERT_CORR_TO_REPOSITORY = 0 // TODO better solution?
	
	override protected setup() {
	}
	
	def protected EObject reloadResourceAndReturnRoot(EObject modelElement){
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		return resourceSet.getResource(resourceURI,true).contents.head
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
		assertTrue(umlContractsPkg.nestingPackage === umlRepositoryPkg)
		assertTrue(umlDatatypesPkg.nestingPackage === umlRepositoryPkg)
		// attribute constraints
		assertTrue(umlRepositoryPkg.name == pcmRepo.entityName.toFirstLower) 
		assertTrue(umlRepositoryPkg.name.toFirstUpper == pcmRepo.entityName)
		assertTrue(umlContractsPkg.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME)
		assertTrue(umlDatatypesPkg.name == DefaultLiterals.DATATYPES_PACKAGE_NAME)
	}

	@Test
	def testCreateRepositoryConceptFromUmlPackage() {
		userInteractor.addNextSelections(PKG_INSERT_CORR_TO_REPOSITORY)
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		
		var umlModel = UMLFactory.eINSTANCE.createModel()
		createAndSynchronizeModel(UML_MODEL_FILE, umlModel)
		
		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(umlModel)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		assertTrue(umlRepositoryPkg.name == "testCbsRepository")
		
		val pcmRepository = getCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertFalse(pcmRepository === null)
		// for some reason the correspondence model retrieves a different instance with the same content
		// 		-> containment checks obviously fail as a result
		// But as far as I can tell, references between elements retrieved via correspondence model are consistent with each other,
		// and repeated retrieve requests result in the same instance.
		umlRepositoryPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val umlContractsPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		checkRepositoryConcept(correspondenceModel, pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
	}
	
	@Test
	def testCreateRepositoryConceptFromPcmRepository() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val umlRepositoryPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
//		// inverse retrieve here not necessary because pcmRepo is alone on its model side and correspondence resolution still works
//		pcmRepository = getCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE) 
//		assertFalse(pcmRepository === null)
		val umlContractsPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		checkRepositoryConcept(correspondenceModel, pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
	}

	@Test
	def testRenameRepositoryConcept_1() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		pcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository.entityName = "pcm2UmlNameChange_2" // still ok
		saveAndSynchronizeChanges(pcmRepository)
		
		var reloaded_PcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository //necessary for round-trip changes

//		// saveAndSynch() tries to delete the repository, because it interprets the reloaded instance as a distinct/different repository.
//		// But after new creation, the new repository is synchronized... so delete and create do work... yay?
//		userInteractor.addNextSelections(DefaultLiterals.INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES)
//		userInteractor.addNextSelections(UML_MODEL_FILE)
//		saveAndSynchronizeChanges(reloadedPcmRepository) 
		
		var umlRepositoryPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlContractsPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		var umlDatatypesPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		checkRepositoryConcept(correspondenceModel, reloaded_PcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		
		// This demonstrates how the instances differ
		val inverseRetrieved_PcmRepository = getCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(reloaded_PcmRepository !== inverseRetrieved_PcmRepository) //just to confirm my suspicion
		assertTrue(EcoreUtil.equals(reloaded_PcmRepository, inverseRetrieved_PcmRepository))
		
//		saveAndSynchronizeChanges(reloaded_PcmRepository) // still tries to delete the repository
//		saveAndSynchronizeChanges(inverseRetrieved_PcmRepository) // also tries to delete the repository, so this might be a third separate instance?
		
////		umlRepositoryPkg.name = "uml2PcmNameChange" // throws IllegalStateException "Cannot modify resource set without a write transaction"
//		pcmRepository.entityName = "Pcm2UmlNameChange_2" // this seems to be ok
//		inverse_retrievedPcmRepository.entityName = "Pcm2UmlNameChange_2" // throws IllegalStateException "Cannot modify resource set without a write transaction"
//		saveAndSynchronizeChanges(pcmRepository) // somehow it records a deletion change until here		

		assertTrue(false) // just to show in which test cases the problems are documented
		return
	}
	
	@Test
	def testRenameRepositoryConcept_2() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		pcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(pcmRepository)
		
		var reloaded_PcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository //necessary for round-trip changes
		
		var umlRepositoryPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlContractsPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		var umlDatatypesPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		checkRepositoryConcept(correspondenceModel, reloaded_PcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		
		// This demonstrates how the instances differ
		val inverseRetrieved_PcmRepository = getCorr(umlRepositoryPkg, Repository, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		assertTrue(reloaded_PcmRepository !== inverseRetrieved_PcmRepository) //just to confirm my suspicion
		assertTrue(EcoreUtil.equals(reloaded_PcmRepository, inverseRetrieved_PcmRepository))
		
//		umlRepositoryPkg.name = "uml2PcmNameChange" // throws IllegalStateException "Cannot modify resource set without a write transaction"
//		reloaded_PcmRepository.entityName = "Pcm2UmlNameChange_2" // this seems to be ok
//		inverseRetrieved_PcmRepository.entityName = "Pcm2UmlNameChange_2" // throws IllegalStateException "Cannot modify resource set without a write transaction"
//		// Does the framework or correspondence model impose a write-lock?

		assertTrue(false) // just to show in which test cases the problems are documented
		return
	}
	
	@Test 
	def testDeleteRepositoryConcept_FromPcm_WithoutReload() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "pcm2UmlNameChange" //has to be capitalized via round-trip -> makes reload necessary
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		// If I skip the reload, then the information that is visible to the test is out of date, but the deletion is properly propagated.
		var reloaded_PcmRepository = pcmRepository 
//		var reloaded_PcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository //necessary for round-trip changes
		
		var umlRepositoryPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlContractsPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		var umlDatatypesPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
//		checkRepositoryConcept(correspondenceModel, reloaded_PcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		
		val umlRootModel = umlRepositoryPkg.nestingPackage
		
		userInteractor.addNextSelections(DefaultLiterals.INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES)
		deleteAndSynchronizeModel(PCM_MODEL_FILE)
		
		assertFalse(umlRootModel.packagedElements.contains(umlRepositoryPkg))
		
		assertTrue(false) // just to show in which test cases the problems are documented
		return
	}
	
	@Test 
	def testDeleteRepositoryConcept_FromPcm_WithReload() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "pcm2UmlNameChange" //has to be capitalized via round-trip -> makes reload necessary
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		
		var reloaded_PcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository //necessary for round-trip changes
		
		var umlRepositoryPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlContractsPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		var umlDatatypesPkg = getCorr(reloaded_PcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		checkRepositoryConcept(correspondenceModel, reloaded_PcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
		
		val umlRootModel = umlRepositoryPkg.nestingPackage
		
		
		// With reload, the information that is visible to the test is correct and the result seems to be ultimately correct,
		// but the synchronization steps are weird. 
		// On calling deleteAndSynchronize, a new model is created, deleted, created and deleted again.
		// I assume the distinct instances are all interpreted as part of the vsum, 
		// and while one is deleted, the subsequent synchronize results in the creation events. 
		userInteractor.addNextSelections(DefaultLiterals.INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES, 0)
		userInteractor.addNextSelections("", "")
		deleteAndSynchronizeModel(PCM_MODEL_FILE)
		//create, delete, create, delete
		
		assertFalse(umlRootModel.packagedElements.contains(umlRepositoryPkg))
		
		assertTrue(false) // just to show in which test cases the problems are documented
		return
	}
}
