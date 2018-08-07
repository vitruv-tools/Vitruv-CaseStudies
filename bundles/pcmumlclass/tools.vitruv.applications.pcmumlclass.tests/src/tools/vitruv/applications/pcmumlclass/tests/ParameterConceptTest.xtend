package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.junit.Test
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Parameter 
 * in an pcm::OperationSignature (regular Parameter) with an uml::Parameter in an uml::Operation corresponding to the signature.
 * <br><br>
 * Related files: PcmParameter.reactions, UmlRegularParameter.reactions, UmlReturnAndRegularParameterType.reactions
 */
class ParameterConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(ParameterConceptTest).simpleName);

	private static val TEST_PARAMETER_NAME = "testParameter"
	 
	def private static boolean checkParameterModifiers(ParameterModifier pcmModifier, ParameterDirectionKind umlDirection){
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
		assertTrue(checkParameterModifiers(pcmParam.modifier__Parameter, umlParam.direction))
		assertTrue(isCorrect_DataType_Parameter_Correspondence(cm, pcmParam.dataType__Parameter, umlParam))
		assertTrue(corresponds(cm, pcmParam.operationSignature__Parameter, umlParam.operation, TagLiterals.SIGNATURE__OPERATION))
	}
	
	def protected checkParameterConcept(Parameter pcmParam ){
		val mUmlParam = helper.getModifiableCorr(pcmParam , org.eclipse.uml2.uml.Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, pcmParam , mUmlParam)
	}
	
	def protected checkParameterConcept(org.eclipse.uml2.uml.Parameter umlParam){
		val mPcmParam = helper.getModifiableCorr(umlParam, Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, mPcmParam , umlParam)
	}

	def private Repository createRepositoryWithSignature(){
		val pcmRepository = helper.createRepository()
		helper.createCompositeDataType(pcmRepository)
		val pcmInterface = helper.createOperationInterface(pcmRepository)
		helper.createOperationSignature(pcmInterface)
		
		userInteractor.addNextSelections(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}	

	@Test
	def void testCreateParameterConcept_UML() {
		var pcmRepository = createRepositoryWithSignature
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		var umlOperation = helper.getUmlOperation(pcmInterface)
		startRecordingChanges(umlOperation)
		
		// If there is any unnamed parameter already inserted and synchronized, 
		// the correspondence model might not be able to differentiate the new from he old parameter and return false correspondences,
		// because the name change for the new Parameter is applied later. Until then, the new Parameter has the same unnamed-TUID as the already existing one.
		var umlParameter = umlOperation.createOwnedParameter(tools.vitruv.applications.pcmumlclass.tests.ParameterConceptTest.TEST_PARAMETER_NAME, null) 
		umlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
		umlParameter.type = helper.getUmlCompositeDataTypeClass(pcmRepository)
		saveAndSynchronizeChanges(umlParameter)
		
		reloadResourceAndReturnRoot(umlParameter)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		umlOperation = helper.getUmlOperation(pcmInterface)
		
		umlParameter = umlOperation.ownedParameters.findFirst[it.name == tools.vitruv.applications.pcmumlclass.tests.ParameterConceptTest.TEST_PARAMETER_NAME]
		assertNotNull(umlParameter)
		checkParameterConcept(umlParameter)
	}
	
	@Test
	def void testCreateParameterConcept_PCM() {
		var pcmRepository = createRepositoryWithSignature
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		var pcmSignature = helper.getPcmOperationSignature(pcmInterface)
		
		var pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmParameter.parameterName = tools.vitruv.applications.pcmumlclass.tests.ParameterConceptTest.TEST_PARAMETER_NAME
		pcmParameter.dataType__Parameter = helper.getPcmCompositeDataType(pcmRepository)
		pcmSignature.parameters__OperationSignature += pcmParameter
		saveAndSynchronizeChanges(pcmParameter)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		pcmSignature = helper.getPcmOperationSignature(pcmInterface)
		
		pcmParameter = pcmSignature.parameters__OperationSignature.findFirst[it.parameterName == tools.vitruv.applications.pcmumlclass.tests.ParameterConceptTest.TEST_PARAMETER_NAME]
		assertNotNull(pcmParameter)
		checkParameterConcept(pcmParameter)
	}
	
	
	
	
	
	
	
	
}
