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

class SystemConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(SystemConceptTest).simpleName);

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
	def void testCreateSystemConcept_UML() {
		assertTrue(false)
	}
	
	@Test
	def void testCreateSystemConcept_PCM() {
		assertTrue(false)
	}










}
