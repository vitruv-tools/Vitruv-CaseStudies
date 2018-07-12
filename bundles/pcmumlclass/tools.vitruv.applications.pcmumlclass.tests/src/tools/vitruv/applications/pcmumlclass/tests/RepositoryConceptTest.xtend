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

class RepositoryConceptTest extends PcmUmlClassApplicationTest {

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	private static val PKG_INSERT_CORR_TO_REPOSITORY = 0 // TODO better solution?
	
	override protected setup() {
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
		
		//TODO cleanup reload
		val modelURI = umlModel.eResource.URI
		umlModel.eResource.unload
		umlModel = resourceSet.getResource(modelURI,true).contents.head as Model
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

}
