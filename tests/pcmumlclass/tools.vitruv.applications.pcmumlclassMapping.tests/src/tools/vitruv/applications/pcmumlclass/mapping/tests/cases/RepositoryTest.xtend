package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

class RepositoryTest extends PcmUmlClassTest {

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
		assertTrue(umlContractsPkg.name == 'contracts')
		assertTrue(umlDatatypesPkg.name == 'datatypes')
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
	def void testCreateRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = 'TestRepo'
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository

		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		var umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = "umlModel"
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE, umlModel)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")
		saveAndSynchronizeChanges(umlModel)
		//should not be created yet, because datatypes and contracts are missing
		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		var umlDatatypesPkg = umlRepositoryPkg.createNestedPackage("datatypes")
		saveAndSynchronizeChanges(umlModel)
		//should not be created yet, because contracts is missing
		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		var wrongNamePkg = umlRepositoryPkg.createNestedPackage("contractsF")				
		saveAndSynchronizeChanges(umlModel)
		//should not be created yet, because package has wrong name		
		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		var umlContractsPkg = umlRepositoryPkg.createNestedPackage("contracts")				
		saveAndSynchronizeChanges(umlModel)	
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)

		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlRepositoryPkg = umlModel.nestedPackages.head
		
		assertEquals("testCbsRepository", umlRepositoryPkg.name)

		checkUmlRepositoryPackage(umlRepositoryPkg)
	}
	
	@Test
	def void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		
		pcmRepository.entityName = "Pcm2UmlNameChange"
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val newName = "pcm2UmlNameChange_2" // should be synchronized to upper case
		pcmRepository.entityName = newName
		saveAndSynchronizeChanges(pcmRepository)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		println(pcmRepository.entityName)
		assertEquals( newName.toFirstUpper,pcmRepository.entityName)
		checkPcmRepository(pcmRepository)
	}

	@Test 
	def void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "TestCbsRepository" 
		
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository	
		checkPcmRepository(pcmRepository)
		
		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage
		
		deleteAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		
		assertModelNotExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		// before the following reload, the mUmlModel instance will be out of synch with the vsum 
		assertFalse(umlModel?.packagedElements.empty)
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		assertTrue(umlModel?.packagedElements.empty)
	}

}
