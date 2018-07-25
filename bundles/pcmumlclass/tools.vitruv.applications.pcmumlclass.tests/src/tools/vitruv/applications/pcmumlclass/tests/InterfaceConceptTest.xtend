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

/**
 * The following reactions and routines synchronize a pcm::OperationInterface
 * with its corresponding uml::Interface (in the contracts uml::Package corresponding to a pcm::Repository).
 * <br><br>
 * Related files: PcmInterface.reactions, UmlInterface.reactions, UmlInterfaceGeneralization.reactions
 */
class InterfaceConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(InterfaceConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION 
	
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
		val umlInterface = getModifiableCorr(pcmInterface, Interface, TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(correspondenceModel, pcmInterface, umlInterface)
	}
	def protected checkInterfaceConcept(Interface umlInterface){
		val pcmInterface = getModifiableCorr(umlInterface, OperationInterface, TagLiterals.INTERFACE_TO_INTERFACE)
		checkInterfaceConcept(correspondenceModel, pcmInterface, umlInterface)
	}

	def private Repository createRepositoryConcept(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private Package getContractsPackage(Repository pcmRepository){
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
	}

	@Test
	def void testCreateInterfaceConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlContractsPkg = getContractsPackage(pcmRepository)
		startRecordingChanges(umlContractsPkg)
		
		var mUmlInterface = umlContractsPkg.createOwnedInterface("TestInterface")
		saveAndSynchronizeChanges(umlContractsPkg)
		
		reloadResourceAndReturnRoot(umlContractsPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlContractsPkg = getContractsPackage(pcmRepository)
		
		mUmlInterface = umlContractsPkg.packagedElements.head as Interface
		assertNotNull(mUmlInterface)
		checkInterfaceConcept(mUmlInterface)
	}
	
	@Test
	def void testCreateInterfaceConcept_PCM() {
		var pcmRepository = createRepositoryConcept()
		var umlContractsPkg = getContractsPackage(pcmRepository)
		startRecordingChanges(umlContractsPkg)
		
		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = "TestInterface"
		pcmRepository.interfaces__Repository += mPcmInterface
		saveAndSynchronizeChanges(mPcmInterface)
		
		reloadResourceAndReturnRoot(umlContractsPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlContractsPkg = getContractsPackage(pcmRepository)
		
		mPcmInterface = pcmRepository.interfaces__Repository.head as OperationInterface
		assertNotNull(mPcmInterface)
		checkInterfaceConcept(mPcmInterface)
	}
	
	
	
	
	
	
	
	
}
