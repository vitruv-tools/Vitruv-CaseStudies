package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import static tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals.*
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest

import org.apache.log4j.Logger

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Path

class RepositoryTest extends PcmUmlClassTest {
	static val logger = Logger.getLogger(RepositoryTest)

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
		val umlRepositoryPkg = helper.getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		val umlContractsPkg = helper.getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
		val umlDatatypesPkg = helper.getCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		assertTrue(umlRepositoryPkg !== null)
		assertTrue(umlContractsPkg !== null)
		assertTrue(umlDatatypesPkg !== null)
		checkRepositoryConcept(pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg)
	}

	@Test
	def void testCreateRepositoryConcept_PCM() {
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository() => [
			entityName = 'TestRepo'
		]
		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		checkPcmRepository(pcmRepository.clearResourcesAndReloadRoot)
	}

	@Test
	def void testCreateRepositoryConcept_UML() {
		val umlModel = UMLFactory.eINSTANCE.createModel() => [
			name = "umlModel"
		]
		resourceAt(Path.of(UML_MODEL_FILE)).startRecordingChanges => [
			contents += umlModel
		]
		propagate
		assertModelExists(UML_MODEL_FILE)

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository")
		propagate
		// should not be created yet, because datatypes and contracts are missing
		assertModelNotExists(PCM_MODEL_FILE)
		umlRepositoryPkg.createNestedPackage("datatypes")
		propagate
		// should not be created yet, because contracts is missing
		assertModelNotExists(PCM_MODEL_FILE)
		umlRepositoryPkg.createNestedPackage("contractsF")
		propagate
		// should not be created yet, because package has wrong name		
		assertModelNotExists(PCM_MODEL_FILE)
		umlRepositoryPkg.createNestedPackage("contracts")
		propagate
		assertModelExists(PCM_MODEL_FILE)

		umlRepositoryPkg = umlModel.clearResourcesAndReloadRoot.nestedPackages.head

		assertEquals("testCbsRepository", umlRepositoryPkg.name)

		checkUmlRepositoryPackage(umlRepositoryPkg)
	}

	@Test
	def void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		val initialRepository = pcmRepository

		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
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

		logger.debug(pcmRepository.entityName)
		assertEquals(newName.toFirstUpper, pcmRepository.entityName)
		checkPcmRepository(pcmRepository)
	}

	@Test
	def void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository() => [
			entityName = "TestCbsRepository"
		]
		val initialRepository = pcmRepository

		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
			contents += initialRepository
		]
		propagate
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository	
		checkPcmRepository(pcmRepository)

		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		var umlModel = umlRepositoryPackage.nestingPackage

		resourceAt(Path.of(PCM_MODEL_FILE)).propagate [
			delete(null)
		]

		assertModelNotExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)
		// before the following reload, the mUmlModel instance will be out of synch with the vsum 
		assertFalse(umlModel?.packagedElements.empty)
		umlModel = umlModel.clearResourcesAndReloadRoot
		assertTrue(umlModel?.packagedElements.empty)
	}

}
