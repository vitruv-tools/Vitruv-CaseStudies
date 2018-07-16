package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class ParameterConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(ParameterConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	static val TEST_INTERFACE = "TestInterface"
	static val TEST_SIGNATURE = "testSignature"
	static val TEST_PARAMETER = "testParameter"
	 
	def private static boolean checkParameterModifiers(ParameterModifier pcmModifier, ParameterDirectionKind umlDirection){
		return switch(pcmModifier){
			case IN: umlDirection == ParameterDirectionKind.IN_LITERAL
			case OUT: umlDirection == ParameterDirectionKind.OUT_LITERAL
//				case INOUT,
//				case NONE,
			default: umlDirection == ParameterDirectionKind.INOUT_LITERAL
		}
	}
	def private static boolean checkParameterModifiers2(ParameterModifier pcmModifier, ParameterDirectionKind umlDirection){
		return umlDirection == PcmUmlClassHelper.getMatchingParameterDirection(pcmModifier)
	}
	
	def public static checkParameterConcept(CorrespondenceModel cm, 
			Parameter pcmParam, 
			org.eclipse.uml2.uml.Parameter umlParam
	){
		assertNotNull(pcmParam)
		assertNotNull(umlParam)
		assertTrue(corresponds(cm, pcmParam, umlParam, TagLiterals.PARAMETER__REGULAR_PARAMETER))
		assertTrue(pcmParam.parameterName == umlParam.name) 
		assertTrue(checkParameterModifiers2(pcmParam.modifier__Parameter, umlParam.direction))
		assertTrue(corresponds(cm, pcmParam.operationSignature__Parameter, umlParam.operation, TagLiterals.SIGNATURE__OPERATION))
	}
	
	def protected checkParameterConcept(Parameter mPcmParam ){
		val mUmlParam = getModifiableCorr(mPcmParam , org.eclipse.uml2.uml.Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, mPcmParam , mUmlParam)
	}
	
	def protected checkParameterConcept(org.eclipse.uml2.uml.Parameter mUmlParam){
		val mPcmParam = getModifiableCorr(mUmlParam, Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, mPcmParam , mUmlParam)
	}

	def private Repository createRepositoryWithSignature(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		val mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		
		val mPcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		mPcmInterface.entityName = TEST_INTERFACE
		mPcmRepository.interfaces__Repository += mPcmInterface
		
		val mPcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		mPcmSignature.entityName = TEST_SIGNATURE
		mPcmInterface.signatures__OperationInterface += mPcmSignature
		
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(mPcmRepository) as Repository 
	}
	
	def private OperationSignature getPcmTestSignature(Repository mPcmRepository){
		val mPcmInterface = mPcmRepository.interfaces__Repository.findFirst[it.entityName == TEST_INTERFACE] as OperationInterface
		val mPcmSignature = mPcmInterface.signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
		return mPcmSignature
	}
	
	def private Operation getUmlTestOperation(Repository mPcmRepository){
		return getModifiableCorr(getPcmTestSignature(mPcmRepository), Operation, TagLiterals.SIGNATURE__OPERATION)
	}
	

	@Test
	def void testCreateParameterConcept_UML() {
		var mPcmRepository = createRepositoryWithSignature
		var mUmlOperation = getUmlTestOperation(mPcmRepository)
		startRecordingChanges(mUmlOperation)
		
		// Careful! If there is any unnamed parameter already inserted and synchronized, 
		// the correspondence model might not be able to differentiate the new from he old parameter and return false correspondences,
		// because the name change for the new Parameter is applied later. Until then, the new Parameter has the same unnamed-TUID as the already existing one.
		var mUmlParameter = mUmlOperation.createOwnedParameter(TEST_PARAMETER, null) 
		mUmlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
		//TODO set type corresponding to some DataType
		saveAndSynchronizeChanges(mUmlParameter)
		
		reloadResourceAndReturnRoot(mUmlParameter)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlOperation = getUmlTestOperation(mPcmRepository)
		
		mUmlParameter = mUmlOperation.ownedParameters.findFirst[it.name == TEST_PARAMETER]
		assertNotNull(mUmlParameter)
		checkParameterConcept(mUmlParameter)
	}
	
	@Test
	def void testCreateParameterConcept_PCM() {
		var mPcmRepository = createRepositoryWithSignature
		var mPcmSignature = getPcmTestSignature(mPcmRepository)
//		startRecordingChanges(mUmlOperation)
		
		var mPcmParameter = RepositoryFactory.eINSTANCE.createParameter
		mPcmParameter.parameterName = TEST_PARAMETER
		mPcmParameter.dataType__Parameter = null // TODO set type corresponding to some DataType
		mPcmSignature.parameters__OperationSignature += mPcmParameter
		saveAndSynchronizeChanges(mPcmParameter)
		
//		reloadResourceAndReturnRoot(mUmlParameter)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmSignature = getPcmTestSignature(mPcmRepository)
		
		mPcmParameter = mPcmSignature.parameters__OperationSignature.findFirst[it.parameterName == TEST_PARAMETER]
		assertNotNull(mPcmParameter)
		checkParameterConcept(mPcmParameter)
	}
	
	
	
	
	
	
	
	
}
