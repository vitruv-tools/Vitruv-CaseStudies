package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTest
import tools.vitruv.applications.pcmumlclass.DefaultLiterals

class PcmToUmlRepositoryTest extends PcmUmlClassApplicationTest {

	// Only literals used to create the pcm-model side can be prescribed here, because the UML values are
	// supposed to be generated from the transformations, which use the literals defined in <...>.pcm2uml.DefaultLiterals. 
	private static final String PCM_MODEL_FILE = "model/Repository.repository";
	private static final String UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION;
	
	private static final String CUSTOM_PCM_NAME = "CustomRepositoryName"

	override protected setup() {
	}
	
	def private static checkRepoAndPackageNamesCorrespond(Repository pcmRepo, Package umlRepositoryPkg){
		return pcmRepo.entityName.toFirstLower == umlRepositoryPkg.name 
				&& pcmRepo.entityName == umlRepositoryPkg.name.toFirstUpper
	}
	def private static assertEqualNames(Repository pcmRepo, Package umlRepositoryPkg){
		assertTrue(checkRepoAndPackageNamesCorrespond(pcmRepo, umlRepositoryPkg))
	}

	def private static getCorrespondingUmlRepositoryPackage(CorrespondenceModel correspondenceModel, Repository pcmRepo){
		val packages = correspondenceModel.getCorrespondingEObjectsByType(pcmRepo, Package).filter[pkg | checkRepoAndPackageNamesCorrespond(pcmRepo, pkg)]
		assertEquals("There should be exactly one uml::Package as base package for a pcm::Repository, yet there were " +packages.size +"!", 1, packages.size)
		return packages.head
	}
	
	def private static assertCorrespondingUmlRepositoryPackageExists(CorrespondenceModel correspondenceModel, Repository pcmRepo){
		getCorrespondingUmlRepositoryPackage(correspondenceModel, pcmRepo) // already does an assertion check, alias for comprehension
		return;
	}

	@Test
	def testCreatePcmRepositoryWithDefaultName() {
		val repo = RepositoryFactory.eINSTANCE.createRepository();
		createAndSynchronizeModel(PCM_MODEL_FILE, repo);

		assertModelExists(PCM_MODEL_FILE);
		assertModelExists(UML_MODEL_FILE);
		assertCorrespondingUmlRepositoryPackageExists(correspondenceModel, repo)
		
		val umlRepositoryPkg = getCorrespondingUmlRepositoryPackage(correspondenceModel, repo)
		val umlDatatypePkg = umlRepositoryPkg.packagedElements.filter(Package).findFirst[pkg | pkg.name == DefaultLiterals.DATATYPES_PACKAGE_NAME]
		assertEquals("The number of generated uml::PrimitiveTypes does not match the number of pcm::PrimitiveDataTypes",
//			PrimitiveTypeEnum.values.length, umlDatatypePkg.packagedElements.filter(PrimitiveType).size); 
//			actually only 6 types, PrimitiveTypeEnum.LONG is not modeled in 'PCM_MODELS/PrimitiveTypes.repository'
			6, umlDatatypePkg.packagedElements.filter(PrimitiveType).size);
	}
	
	@Test
	def testCreatePcmRepositoryInPreExistingUmlModel() {
		val umlRootModel = UMLFactory.eINSTANCE.createModel()
		val presetName = "preexistingumlmodel"
		umlRootModel.name = presetName
		createAndSynchronizeModel(UML_MODEL_FILE, umlRootModel);
		assertModelExists(UML_MODEL_FILE);
		
		val repo = RepositoryFactory.eINSTANCE.createRepository();
		createAndSynchronizeModel(PCM_MODEL_FILE, repo);

		assertModelExists(PCM_MODEL_FILE);
		assertEquals(presetName, umlRootModel.name)
		val umlRepositoryPkg = getCorrespondingUmlRepositoryPackage(correspondenceModel, repo)
		assertFalse(umlRootModel.ownedElements.contains(umlRepositoryPkg))
	}
	
	@Test
	def testCreatePcmRepositoryWithCustomName() {
		val repo = RepositoryFactory.eINSTANCE.createRepository();
		repo.entityName = CUSTOM_PCM_NAME
		createAndSynchronizeModel(PCM_MODEL_FILE, repo);

		assertCorrespondingUmlRepositoryPackageExists(correspondenceModel, repo)
	}
	
	@Test
	def testChangePcmRepositoryName() {
		val repo = RepositoryFactory.eINSTANCE.createRepository();
		createAndSynchronizeModel(PCM_MODEL_FILE, repo);
		saveAndSynchronizeChanges(repo)

		val umlRepositoryPkg = getCorrespondingUmlRepositoryPackage(correspondenceModel, repo)
		
		repo.entityName = CUSTOM_PCM_NAME
		saveAndSynchronizeChanges(repo)
		
		assertEqualNames(repo, umlRepositoryPkg)
	}
	
	@Test
	def testDeletePcmRepository() {
		val repo = RepositoryFactory.eINSTANCE.createRepository();
		createAndSynchronizeModel(PCM_MODEL_FILE, repo);

		assertModelExists(UML_MODEL_FILE);
		val umlRepositoryPkg = getCorrespondingUmlRepositoryPackage(correspondenceModel, repo)
		val umlRootModel = umlRepositoryPkg.nestingPackage
			
		deleteAndSynchronizeModel(PCM_MODEL_FILE)
		assertModelNotExists(PCM_MODEL_FILE);
		
		assertFalse(umlRootModel.ownedElements.contains(umlRepositoryPkg))
	}

}
