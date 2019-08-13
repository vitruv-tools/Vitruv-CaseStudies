package tools.vitruv.applications.pcmumlclassjava.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.junit.Test
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.uml2.uml.Interface
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationSignature with its
 * corresponding uml::Operation and the return type of the signature with an uml::Parameter (return parameter) in the uml::Operation.
 * <br><br>
 * Related files: PcmSignature.reactions, UmlSignatureOperation.reactions, UmlReturnAndRegularParameterType.reactions
 */
class SignatureConceptTest extends TransitiveChangeTest {

	private static val TEST_SIGNATURE_NAME = "testSignature"
	
	def public static void checkSignatureConcept(
			CorrespondenceModel cm, 
			OperationSignature pcmSignature, 
			Operation umlOperation
	) {
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
	
	def protected checkSignatureConcept(OperationSignature pcmSignature) {
		val umlOperation = helper.getModifiableCorr(pcmSignature, Operation, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
		checkJavaSignatureConcept(umlOperation, pcmSignature)
	}
	
	def protected checkSignatureConcept(Operation umlOperation) {
		val pcmSignature = helper.getModifiableCorr(umlOperation, OperationSignature, TagLiterals.SIGNATURE__OPERATION)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
		checkJavaSignatureConcept(umlOperation, pcmSignature)
	}
	
	def protected checkJavaSignatureConcept(Operation umlOperation, OperationSignature pcmSignature) {
		val pcmRepository = pcmSignature.interface__OperationSignature.repository__Interface
		checkJavaType(umlOperation.eContainer as Interface)
		checkJavaMethod(umlOperation)
		// Created before test cases, should be still there:
		val umlPackage = helper.getUmlRepositoryPackage(pcmRepository)
		checkJavaPackage(umlPackage)
		umlPackage.nestedPackages.forEach[checkJavaPackage]
		helper.getUmlCompositeDataTypeClass(pcmRepository).checkJavaType
		helper.getUmlCompositeDataTypeClass_2(pcmRepository).checkJavaType
	}

	def private Repository createRepositoryWithInterface() {
		var pcmRepository = helper.createRepository()
		helper.createOperationInterface(pcmRepository)
		helper.createCompositeDataType(pcmRepository)
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository)
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2)
		
		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		
		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	private def _testCreateSignatureConcept_UML() {
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
	
	private def _testReturnTypePropagation_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository
		var umlInterface = helper.getUmlInterface(pcmRepository)
		var umlOperation = umlInterface.ownedOperations.head
		var umlReturnParameter = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		
		umlReturnParameter.type = umlType
		umlReturnParameter.lower = lower
		umlReturnParameter.upper = upper
		
		userInteractor.addNextSingleSelection(ARRAY_LIST_SELECTION)
		saveAndSynchronizeChanges(umlInterface)
		reloadResourceAndReturnRoot(umlInterface)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlInterface = helper.getUmlInterface(pcmRepository)
		umlOperation = umlInterface.ownedOperations.head
		
		checkSignatureConcept(umlOperation)
		var reloadedUmlType = helper.getModifiableInstance(umlType)
		assertNotNull("The DataType should not be null after reload", reloadedUmlType)
		assertTrue(EcoreUtil.equals(umlOperation.type, reloadedUmlType))
		assertEquals(lower, umlOperation.lower)
		assertEquals(upper, umlOperation.upper)
		assertEquals("The Operation should have only a return parameter, since no other parameters were supposed to be added by this test.",
			1, umlOperation.ownedParameters.size
		)
	}
	
	@Test 
	def void testCreateSignatureConcept_UML_primitiveReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML
		assertNotNull("Initialization of PrimitiveTypes seems to have failed", helper.UML_STRING)
		_testReturnTypePropagation_UML(pcmRepository, helper.UML_STRING, 1, 1)
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
	
	private def _testCreateSignatureConcept_PCM_withReturnType(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		
		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE_NAME
		pcmSignature.returnType__OperationSignature = pcmType
		pcmInterface.signatures__OperationInterface += pcmSignature
		userInteractor.addNextSingleSelection(ARRAY_LIST_SELECTION) // Mock user input
		
		saveAndSynchronizeChanges(pcmSignature)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmInterface = helper.getPcmOperationInterface(pcmRepository)
		
		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		checkSignatureConcept(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE_NAME)
		val reloadedPcmType = helper.getModifiableInstance(pcmType)
		assertNotNull("The DataType should not be null after reload", reloadedPcmType)
		assertTrue(EcoreUtil.equals(pcmSignature.returnType__OperationSignature, reloadedPcmType))
		
		assertEquals("The Signature should have no parameter, since none were supposed to be added by this test.",
			0, pcmSignature.parameters__OperationSignature.size
		)
	}
	
	@Test
	def void testCreateSignatureConcept_PCM_primitiveReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		assertNotNull("Initialization of PrimitiveTypes seems to have failed", helper.PCM_STRING)
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.PCM_STRING)
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
}
