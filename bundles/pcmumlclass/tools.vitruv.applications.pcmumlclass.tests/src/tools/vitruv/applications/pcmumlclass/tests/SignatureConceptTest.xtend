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
import org.palladiosimulator.pcm.repository.OperationSignature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class SignatureConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(SignatureConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	static val TEST_INTERFACE = "TestInterface"
	
	override protected setup() {
	}
	 
	
	def public static void checkSignatureConcept(
			CorrespondenceModel cm, 
			OperationSignature pcmSignature, 
			Operation umlOperation
	){
		val returnParam = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		assertTrue(corresponds(cm, pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION))
		assertTrue(corresponds(cm, pcmSignature, returnParam, TagLiterals.SIGNATURE__RETURN_PARAMETER))
		assertTrue(pcmSignature.entityName == umlOperation.name)
		assertTrue(returnParam.name == DefaultLiterals.RETURN_PARAM_NAME) // the name needs to be set, so that its TUID is distinct and the object is not confused with new instances
		//return types of both model elements should correspond to each other if they are set
//		//TODO update for SIGNATURE__RETURN_PARAM correspondence
//		assertTrue((pcmSignature.returnType__OperationSignature === null && umlOperation.type === null)
//			 || (corresponds(cm, pcmSignature.returnType__OperationSignature, umlOperation.type)))
		// should both be contained in corresponding interfaces
		assertTrue(corresponds(cm, pcmSignature.interface__OperationSignature, umlOperation.interface, TagLiterals.INTERFACE_TO_INTERFACE))
	}
	
	def protected checkSignatureConcept(OperationSignature pcmSignature){
		val mUmlOperation = getModifiableCorr(pcmSignature, Operation, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, mUmlOperation)
	}
	
	def protected checkSignatureConcept(Operation umlOperation){
		val mPcmSignature = getModifiableCorr(umlOperation, OperationSignature, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, mPcmSignature, umlOperation)
	}

	def private Repository createRepositoryWithInterface(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		
		var mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = TEST_INTERFACE
		mPcmRepository.interfaces__Repository += mPcmInterface
		
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(mPcmRepository) as Repository 
	}
	
	def private OperationInterface getPcmTestInterface(Repository mPcmRepository){
		return mPcmRepository.interfaces__Repository.findFirst[it.entityName == TEST_INTERFACE] as OperationInterface
	}
	
	def private Interface getUmlTestInterface(Repository mPcmRepository){
		return getModifiableCorr(getPcmTestInterface(mPcmRepository), Interface, TagLiterals.INTERFACE_TO_INTERFACE)
	}
	

	@Test
	def void testCreateSignatureConcept_UML() {
		var mPcmRepository = createRepositoryWithInterface()
		var mUmlInterface = getUmlTestInterface(mPcmRepository)
		startRecordingChanges(mUmlInterface)
		
		var mUmlOperation = mUmlInterface.createOwnedOperation("testSignature", null, null)
		saveAndSynchronizeChanges(mUmlInterface)
		
		reloadResourceAndReturnRoot(mUmlInterface)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlInterface = getUmlTestInterface(mPcmRepository)
		
		mUmlOperation = mUmlInterface.ownedOperations.head
		assertNotNull(mUmlOperation)
		assertTrue(mUmlOperation.name == "testSignature")
		checkSignatureConcept(mUmlOperation)
	}
	
	@Test
	def void testCreateSignatureConcept_PCM() {
		var mPcmRepository = createRepositoryWithInterface()
		var mPcmInterface = getPcmTestInterface(mPcmRepository)
		
		var mPcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		mPcmSignature.entityName = "testSignature"
		mPcmInterface.signatures__OperationInterface += mPcmSignature
		saveAndSynchronizeChanges(mPcmSignature)
		
//		reloadResourceAndReturnRoot(mUmlInterface)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmInterface = getPcmTestInterface(mPcmRepository)
		
		mPcmSignature = mPcmInterface.signatures__OperationInterface.head
		assertNotNull(mPcmSignature)
		assertTrue(mPcmSignature.entityName == "testSignature")
		checkSignatureConcept(mPcmSignature)
	}
	
	
	
	
	
	
	
	
}
