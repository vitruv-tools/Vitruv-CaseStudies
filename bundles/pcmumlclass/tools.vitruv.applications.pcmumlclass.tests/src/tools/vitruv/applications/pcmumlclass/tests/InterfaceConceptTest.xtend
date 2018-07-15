package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.Assert.*
import org.junit.Ignore

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class InterfaceConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(InterfaceConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	private static val PKG_INSERT_CORR_TO_REPOSITORY = 0 // TODO better solution?
	
	override protected setup() {
	}
	 
	
	def public static checkInterfaceConcept(CorrespondenceModel cm, 
			OperationInterface pcmInterface, 
			Interface umlInterface
	){
		assertNotNull(pcmInterface)
		assertNotNull(umlInterface)
		assertTrue(corresponds(cm, pcmInterface, umlInterface,	TagLiterals.INTERFACE_TO_INTERFACE))
		assertTrue(pcmInterface.entityName == umlInterface.name)
		// should be contained in corresponding repository and contracts package respectively
		assertTrue(corresponds(cm, pcmInterface.repository__Interface, umlInterface.package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE))
		//parent interfaces should correspond
		val umlParentCorrespondences = pcmInterface.parentInterfaces__Interface
				.map[pcmParent | CorrespondenceModelUtil.getCorrespondingEObjectsByType(cm, pcmParent, Interface).head].toList
		assertFalse(umlParentCorrespondences.contains(null))
		assertFalse(
			umlParentCorrespondences
				.map[umlParent | umlInterface.generalizations.exists[gen | EcoreUtil.equals(gen.general, umlParent)]]
				.exists[it == false]
		)
	}
	def protected checkInterfaceConcept(OperationInterface pcmInterface){
		val mUmlInterface = getModifiableCorr(pcmInterface, Interface, TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(correspondenceModel, pcmInterface, mUmlInterface)
	}
	def protected checkInterfaceConcept(Interface umlInterface){
		val mPcmInterface = getModifiableCorr(umlInterface, OperationInterface, TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(correspondenceModel, mPcmInterface, umlInterface)
	}

	def private Repository createRepositoryConcept(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(mPcmRepository) as Repository 
	}
	
	def private Package getContractsPackage(Repository mPcmRepository){
		return getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
	}

	@Test
//	@Ignore
	def void testCreateInterfaceConcept_UML() {
		var mPcmRepository = createRepositoryConcept()
		var mUmlContractsPkg = getContractsPackage(mPcmRepository)
		startRecordingChanges(mUmlContractsPkg)
		
		var mUmlInterface = mUmlContractsPkg.createOwnedInterface("TestInterface")
		saveAndSynchronizeChanges(mUmlContractsPkg)
		
		reloadResourceAndReturnRoot(mUmlContractsPkg)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlContractsPkg = getContractsPackage(mPcmRepository)
		
		mUmlInterface = mUmlContractsPkg.packagedElements.head as Interface
		assertNotNull(mUmlInterface)
		checkInterfaceConcept(mUmlInterface)
	}
	
	@Test
	def void testCreateInterfaceConcept_PCM() {
		var mPcmRepository = createRepositoryConcept()
		var mUmlContractsPkg = getContractsPackage(mPcmRepository)
		startRecordingChanges(mUmlContractsPkg)
		
		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = "TestInterface"
		mPcmRepository.interfaces__Repository += mPcmInterface
		saveAndSynchronizeChanges(mPcmInterface)
		
		reloadResourceAndReturnRoot(mUmlContractsPkg)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlContractsPkg = getContractsPackage(mPcmRepository)
		
		mPcmInterface = mPcmRepository.interfaces__Repository.head as OperationInterface
		assertNotNull(mPcmInterface)
		checkInterfaceConcept(mPcmInterface)
	}
	
	
	
	
	
	
	
	
}
