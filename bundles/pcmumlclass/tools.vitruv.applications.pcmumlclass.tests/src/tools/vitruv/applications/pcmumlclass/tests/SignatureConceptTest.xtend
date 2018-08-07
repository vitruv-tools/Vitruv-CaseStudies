package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.junit.Ignore
import org.junit.Test
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.core.entity.Entity

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationSignature with its
 * corresponding uml::Operation and the return type of the signature with an uml::Parameter (return parameter) in the uml::Operation.
 * <br><br>
 * Related files: PcmSignature.reactions, UmlSignatureOperation.reactions, UmlReturnAndRegularParameterType.reactions
 */
class SignatureConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(SignatureConceptTest).simpleName);

	private static val TEST_SIGNATURE_NAME = "testSignature"
	
	def public static void checkSignatureConcept(
			CorrespondenceModel cm, 
			OperationSignature pcmSignature, 
			Operation umlOperation
	){
		val returnParam = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		assertNotNull(pcmSignature)
		assertNotNull(umlOperation)
		assertNotNull(returnParam)
		assertTrue(corresponds(cm, pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION))
		assertTrue(corresponds(cm, pcmSignature, returnParam, TagLiterals.SIGNATURE__RETURN_PARAMETER))
		assertTrue(pcmSignature.entityName == umlOperation.name)
		// the name needs to be set, so that its TUID is distinct and the object is not confused with new instances
		assertTrue(returnParam.name == DefaultLiterals.RETURN_PARAM_NAME) 
		// return types of both model elements should correspond to each other if they are set
		assertTrue(isCorrect_DataType_Parameter_Correspondence(cm, pcmSignature.returnType__OperationSignature, returnParam))
		// should both be contained in corresponding interfaces
		assertTrue(corresponds(cm, pcmSignature.interface__OperationSignature, umlOperation.interface, TagLiterals.INTERFACE_TO_INTERFACE))
	}
	
	def protected checkSignatureConcept(OperationSignature pcmSignature){
		val umlOperation = helper.getModifiableCorr(pcmSignature, Operation, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}
	
	def protected checkSignatureConcept(Operation umlOperation){
		val pcmSignature = helper.getModifiableCorr(umlOperation, OperationSignature, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}

	def private Repository createRepositoryWithInterface(){
		var pcmRepository = helper.createRepository()
		helper.createOperationInterface(pcmRepository)
		helper.createCompositeDataType(pcmRepository)
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository)
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2)
		
		userInteractor.addNextSelections(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		
		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	private def _testCreateSignatureConcept_UML(){
		var pcmRepository = createRepositoryWithInterface()
		var umlInterface = helper.getUmlInterface(pcmRepository)
		startRecordingChanges(umlInterface)
		
		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE_NAME, null, null)
		
		saveAndSynchronizeChanges(umlInterface)
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlInterface = helper.getUmlInterface(pcmRepository)
		
		umlOperation = umlInterface.ownedOperations.head
		assertNotNull(umlOperation)
		assertTrue(umlOperation.name == TEST_SIGNATURE_NAME)
		checkSignatureConcept(umlOperation)
		return pcmRepository
	}
	
	private def _testReturnTypePropagation_UML(Repository inPcmRepository, Type umlType, int lower, int upper){
		var pcmRepository = inPcmRepository
		var umlInterface = helper.getUmlInterface(pcmRepository)
		var umlOperation = umlInterface.ownedOperations.head
		var umlReturnParameter = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		
		umlReturnParameter.type = umlType
		umlReturnParameter.lower = lower
		umlReturnParameter.upper = upper
		
		saveAndSynchronizeChanges(umlInterface)
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlInterface = helper.getUmlInterface(pcmRepository)
		umlOperation = umlInterface.ownedOperations.head
		
		checkSignatureConcept(umlOperation)
		assertTrue(EcoreUtil.equals(umlOperation.type, helper.getModifiableInstance(umlType)))
		assertEquals(lower, umlOperation.lower)
		assertEquals(upper, umlOperation.upper)
	}
	
	@Test
	def void testCreateSignatureConcept_UML() {
		_testCreateSignatureConcept_UML
	}
	@Test @Ignore
	def void testCreateSignatureConcept_UML_primitiveReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML
		_testReturnTypePropagation_UML(pcmRepository, helper.UML_INT, 1, 1)
	}
	@Test
	def void testCreateSignatureConcept_UML_compositeReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML
		_testReturnTypePropagation_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1)
	}
	@Test
	def void testCreateSignatureConcept_UML_collectionReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML
		_testReturnTypePropagation_UML(pcmRepository, helper.getUmlCompositeDataTypeClass_2(pcmRepository), 0, LiteralUnlimitedNatural.UNLIMITED)
	}
	
//	@Test
//	@Ignore
//	def void testCreateSignatureConcept_UML_withPrimitiveType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var umlInterface = helper.getUmlInterface(pcmRepository)
//		startRecordingChanges(umlInterface)
//		
//		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE_NAME, null, null)
//		saveAndSynchronizeChanges(umlInterface)
//		
//		reloadResourceAndReturnRoot(umlInterface)
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		umlInterface = helper.getUmlInterface(pcmRepository)
//		
//		umlOperation = umlInterface.ownedOperations.head
//		assertNotNull(umlOperation)
//		var umlParam = umlOperation.createOwnedParameter(TEST_PARAMETER_NAME, helper.UML_INT)
//		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
//		
//		assertTrue(umlOperation.name == TEST_SIGNATURE_NAME)
//		checkSignatureConcept(umlOperation)
//		
//		//Type propagation check
//		val pcmParameter = helper.getPcmOperationInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE_NAME]
//				.parameters__OperationSignature.findFirst[it.parameterName == TEST_PARAMETER_NAME]
//		assertNotNull(pcmParameter)
//		assertTrue(pcmParameter.dataType__Parameter == helper.PCM_INT)
//	}
	
	
	private def _testCreateSignatureConcept_PCM_withReturnType(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		
		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE_NAME
		pcmSignature.returnType__OperationSignature = pcmType
		pcmInterface.signatures__OperationInterface += pcmSignature
		saveAndSynchronizeChanges(pcmSignature)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		
		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		checkSignatureConcept(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE_NAME)
//		assertTrue(EcoreUtil.equals(pcmSignature.returnType__OperationSignature, pcmType))// always fails, perhaps because of reload
		if(pcmType !== null){
			if(pcmType instanceof PrimitiveDataType){
				assertEquals(
					(pcmType as PrimitiveDataType).type,
					(pcmSignature.returnType__OperationSignature as PrimitiveDataType).type
				)
			} else {
				assertEquals(
					(pcmType as Entity).id, 
					(pcmSignature.returnType__OperationSignature as Entity).id
				)
			}	
		}
	}
	
	@Test
	@Ignore
	def void testCreateSignatureConcept_PCM_primitiveReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.PCM_INT)
	}
	
	@Test
	def void testCreateSignatureConcept_PCM_compositeReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCompositeDataType(pcmRepository))
	}
	
	@Test
	def void testCreateSignatureConcept_PCM_collectionReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCollectionDataType(pcmRepository))
	}
	
//	@Test
//	@Ignore
//	def void testCreateSignatureConcept_PCM_withPrimitiveType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)
//		
//		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
//		pcmSignature.entityName = TEST_SIGNATURE_NAME
//		pcmSignature.returnType__OperationSignature = helper.PCM_INT
//		pcmInterface.signatures__OperationInterface += pcmSignature
//		saveAndSynchronizeChanges(pcmSignature)
//		
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		pcmInterface = helper.getPcmOperationInterface(pcmRepository)
//		
//		pcmSignature = pcmInterface.signatures__OperationInterface.head
//		assertNotNull(pcmSignature)
//		checkSignatureConcept(pcmSignature)
//		assertTrue(pcmSignature.entityName == TEST_SIGNATURE_NAME)
//		assertTrue(pcmSignature.returnType__OperationSignature == helper.PCM_INT)
//	}
//	
//	@Test
//	def void testCreateSignatureConcept_UML_withCompositeType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var umlInterface = getUmlTestInterface(pcmRepository)
//		startRecordingChanges(umlInterface)
//		
//		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE, null, null)
//		var umlParam = umlOperation.createOwnedParameter("someParameter", getUmlCompositeDatatypeClass(pcmRepository))
//		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
//		saveAndSynchronizeChanges(umlInterface)
//		
//		reloadResourceAndReturnRoot(umlInterface)
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		umlInterface = getUmlTestInterface(pcmRepository)
//		
//		umlOperation = umlInterface.ownedOperations.head
//		assertNotNull(umlOperation)
//		assertTrue(umlOperation.name == TEST_SIGNATURE)
//		checkSignatureConcept(umlOperation)
//		
//		val pcmParameter = getPcmTestInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
//				.parameters__OperationSignature.findFirst[it.parameterName == "someParameter"]
//		assertNotNull(pcmParameter)
//		assertTrue(pcmParameter.dataType__Parameter == getPcmCompositeDatatype(pcmRepository))
//	}
//	
//	@Test
//	def void testCreateSignatureConcept_PCM_withCompositeType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var pcmInterface = getPcmTestInterface(pcmRepository)
//		
//		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
//		pcmSignature.entityName = TEST_SIGNATURE
//		pcmSignature.returnType__OperationSignature = getPcmCompositeDatatype(pcmRepository)
//		pcmInterface.signatures__OperationInterface += pcmSignature
//		saveAndSynchronizeChanges(pcmSignature)
//		
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		pcmInterface = getPcmTestInterface(pcmRepository)
//		
//		pcmSignature = pcmInterface.signatures__OperationInterface.head
//		assertNotNull(pcmSignature)
//		assertTrue(pcmSignature.entityName == TEST_SIGNATURE)
//		checkSignatureConcept(pcmSignature)
//
//		val umlTestOperation = getUmlTestInterface(pcmRepository).operations.findFirst[it.name == TEST_SIGNATURE]
//		assertNotNull(umlTestOperation)
//		assertTrue(umlTestOperation.type == getUmlCompositeDatatypeClass(pcmRepository))
//	}
//	
//	@Test
//	def void testCreateSignatureConcept_UML_withCollectionType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var umlInterface = getUmlTestInterface(pcmRepository)
//		startRecordingChanges(umlInterface) 
//		// TODO fails here with "IllegalState: InterfaceImpl has no UUID" if the Test-class runs alone,
//		// but works in testCreateSignatureConcept_UML_withCompositeType, or when run together with other test classes
//		
//		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE, null, null)
//		var umlParam = umlOperation.createOwnedParameter("someParameter", null)
//		umlParam.direction = ParameterDirectionKind.INOUT_LITERAL
//		//set collection type
//		umlParam.lower = 0
//		umlParam.upper = LiteralUnlimitedNatural.UNLIMITED
//		umlParam.type = getUmlCompositeDatatypeClass_2(pcmRepository)
//		saveAndSynchronizeChanges(umlInterface)
//		
//		reloadResourceAndReturnRoot(umlInterface)
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		umlInterface = getUmlTestInterface(pcmRepository)
//		
//		umlOperation = umlInterface.ownedOperations.head
//		assertNotNull(umlOperation)
//		assertTrue(umlOperation.name == TEST_SIGNATURE)
//		checkSignatureConcept(umlOperation)
//		
//		val pcmParameter = getPcmTestInterface(pcmRepository).signatures__OperationInterface.findFirst[it.entityName == TEST_SIGNATURE]
//				.parameters__OperationSignature.findFirst[it.parameterName == "someParameter"]
//		assertNotNull(pcmParameter)
//		assertTrue(pcmParameter.dataType__Parameter == getPcmCollectionDatatype(pcmRepository))
//	}
//	
//	@Test
//	def void testCreateSignatureConcept_PCM_withCollectionType() {
//		var pcmRepository = createRepositoryWithInterface()
//		var pcmInterface = getPcmTestInterface(pcmRepository)
//		
//		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
//		pcmSignature.entityName = TEST_SIGNATURE
//		pcmSignature.returnType__OperationSignature = getPcmCollectionDatatype(pcmRepository)
//		pcmInterface.signatures__OperationInterface += pcmSignature
//		saveAndSynchronizeChanges(pcmSignature)
//		
//		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
//		pcmInterface = getPcmTestInterface(pcmRepository)
//		
//		pcmSignature = pcmInterface.signatures__OperationInterface.head
//		assertNotNull(pcmSignature)
//		assertTrue(pcmSignature.entityName == TEST_SIGNATURE)
//		checkSignatureConcept(pcmSignature)
//
//		val umlTestOperation = getUmlTestInterface(pcmRepository).operations.findFirst[it.name == TEST_SIGNATURE]
//		assertNotNull(umlTestOperation)
//		assertTrue(EcoreUtil.equals(umlTestOperation.type, getUmlCompositeDatatypeClass_2(pcmRepository)))
//	}
//	
	
	
	
	
	
}
