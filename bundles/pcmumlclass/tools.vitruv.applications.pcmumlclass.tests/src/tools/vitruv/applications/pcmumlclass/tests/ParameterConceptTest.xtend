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
import org.palladiosimulator.pcm.repository.CompositeDataType


/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Parameter 
 * in an pcm::OperationSignature (regular Parameter) with an uml::Parameter in an uml::Operation corresponding to the signature.
 * <br><br>
 * Related files: PcmParameter.reactions, UmlRegularParameter.reactions, UmlReturnAndRegularParameterType.reactions
 */
class ParameterConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(ParameterConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private static val TEST_INTERFACE = "TestInterface"
	private static val TEST_SIGNATURE = "testSignature"
	private static val TEST_PARAMETER = "testParameter"
	private static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	 
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
		val mUmlParam = getModifiableCorr(pcmParam , org.eclipse.uml2.uml.Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, pcmParam , mUmlParam)
	}
	
	def protected checkParameterConcept(org.eclipse.uml2.uml.Parameter umlParam){
		val mPcmParam = getModifiableCorr(umlParam, Parameter, TagLiterals.PARAMETER__REGULAR_PARAMETER)
		checkParameterConcept(correspondenceModel, mPcmParam , umlParam)
	}

	def private Repository createRepositoryWithSignature(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		val pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		
		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmInterface.entityName = TEST_INTERFACE
		pcmRepository.interfaces__Repository += pcmInterface
		
		val pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE
		pcmInterface.signatures__OperationInterface += pcmSignature
		
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private CompositeDataType getPcmCompositeDatatype(Repository pcmRepository){
		return pcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
	}
	
	def private org.eclipse.uml2.uml.Class getUmlCompositeDatatypeClass(Repository pcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype(pcmRepository), org.eclipse.uml2.uml.Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}
	
	def private OperationSignature getPcmTestSignature(Repository pcmRepository){
		val pcmInterface = pcmRepository.interfaces__Repository.findFirst[it.entityName == TEST_INTERFACE] as OperationInterface
		val pcmSignature = pcmInterface.signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
		return pcmSignature
	}
	
	def private Operation getUmlTestOperation(Repository pcmRepository){
		return getModifiableCorr(getPcmTestSignature(pcmRepository), Operation, TagLiterals.SIGNATURE__OPERATION)
	}
	

	@Test
	def void testCreateParameterConcept_UML() {
		var pcmRepository = createRepositoryWithSignature
		var umlOperation = getUmlTestOperation(pcmRepository)
		startRecordingChanges(umlOperation)
		
		// If there is any unnamed parameter already inserted and synchronized, 
		// the correspondence model might not be able to differentiate the new from he old parameter and return false correspondences,
		// because the name change for the new Parameter is applied later. Until then, the new Parameter has the same unnamed-TUID as the already existing one.
		var umlParameter = umlOperation.createOwnedParameter(TEST_PARAMETER, null) 
		umlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
		umlParameter.type = getUmlCompositeDatatypeClass(pcmRepository)
		saveAndSynchronizeChanges(umlParameter)
		
		reloadResourceAndReturnRoot(umlParameter)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlOperation = getUmlTestOperation(pcmRepository)
		
		umlParameter = umlOperation.ownedParameters.findFirst[it.name == TEST_PARAMETER]
		assertNotNull(umlParameter)
		checkParameterConcept(umlParameter)
	}
	
	@Test
	def void testCreateParameterConcept_PCM() {
		var pcmRepository = createRepositoryWithSignature
		var pcmSignature = getPcmTestSignature(pcmRepository)
		
		var pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmParameter.parameterName = TEST_PARAMETER
		pcmParameter.dataType__Parameter = getPcmCompositeDatatype(pcmRepository)
		pcmSignature.parameters__OperationSignature += pcmParameter
		saveAndSynchronizeChanges(pcmParameter)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmSignature = getPcmTestSignature(pcmRepository)
		
		pcmParameter = pcmSignature.parameters__OperationSignature.findFirst[it.parameterName == TEST_PARAMETER]
		assertNotNull(pcmParameter)
		checkParameterConcept(pcmParameter)
	}
	
	
	
	
	
	
	
	
}
