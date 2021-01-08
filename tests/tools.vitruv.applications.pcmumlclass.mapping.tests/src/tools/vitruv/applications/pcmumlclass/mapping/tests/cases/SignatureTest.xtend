package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import static tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals.*
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel

import org.apache.log4j.Logger

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import java.nio.file.Path

class SignatureTest extends PcmUmlClassTest {
	static val logger = Logger.getLogger(SignatureTest)

	static val TEST_SIGNATURE_NAME = "testSignature"

	def static void checkSignatureConcept(
		CorrespondenceModel cm,
		OperationSignature pcmSignature,
		Operation umlOperation
	) {
		val returnParam = umlOperation.ownedParameters.findFirst [ param |
			param.direction === ParameterDirectionKind.RETURN_LITERAL
		]
		assertNotNull(pcmSignature)
		assertNotNull(umlOperation)
		assertNotNull(returnParam)
		assertTrue(corresponds(cm, pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION))
		assertTrue(corresponds(cm, pcmSignature, returnParam, TagLiterals.SIGNATURE__RETURN_PARAMETER))
		assertEquals(umlOperation.name, pcmSignature.entityName)
		// the name needs to be set, so that its TUID is distinct and the object is not confused with new instances
		assertEquals(DefaultLiterals.RETURN_PARAM_NAME, returnParam.name)
		// return types of both model elements should correspond to each other if they are set
		logger.debug('''pcm «pcmSignature.returnType__OperationSignature»  uml «returnParam»''')
		assertTrue(
			isCorrect_DataType_Parameter_Correspondence(cm, pcmSignature.returnType__OperationSignature, returnParam))
		// should both be contained in corresponding interfaces
		assertTrue(
			corresponds(cm, pcmSignature.interface__OperationSignature, umlOperation.interface,
				TagLiterals.INTERFACE_TO_INTERFACE))
	}

	def protected checkSignatureConcept(OperationSignature pcmSignature) {
		val umlOperation = helper.getModifiableCorr(pcmSignature, Operation, TagLiterals.SIGNATURE__OPERATION)
		assertNotNull(umlOperation)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}

	def protected checkSignatureConcept(Operation umlOperation) {
		val pcmSignature = helper.getModifiableCorr(umlOperation, OperationSignature, TagLiterals.SIGNATURE__OPERATION)
		assertNotNull(pcmSignature)
		checkSignatureConcept(correspondenceModel, pcmSignature, umlOperation)
	}

	def private Repository createRepositoryWithInterface() {
		val pcmRepository = helper.createRepository()
		helper.createOperationInterface(pcmRepository)
		helper.createCompositeDataType(pcmRepository)
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository)
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2)

		userInteraction.addNextTextInput(UML_MODEL_FILE)
		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate

		return pcmRepository.clearResourcesAndReloadRoot
	}

	private def _testCreateSignatureConcept_UML(Repository pcmRepository, Type type) {
		var umlInterface = helper.getUmlInterface(pcmRepository)
		startRecordingChanges(umlInterface)
		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE_NAME, null, null)
		umlOperation.createOwnedParameter(DefaultLiterals.RETURN_PARAM_NAME, type)
		propagate

		umlInterface.clearResourcesAndReloadRoot
		val reloadedPcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlInterface = helper.getUmlInterface(reloadedPcmRepository)

		umlOperation = umlInterface.ownedOperations.head
		assertNotNull(umlOperation)
		assertTrue(umlOperation.name == TEST_SIGNATURE_NAME)
		checkSignatureConcept(umlOperation)
		return reloadedPcmRepository
	}

	private def _testReturnTypePropagation_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository
		var umlInterface = helper.getUmlInterface(pcmRepository)
		var umlOperation = umlInterface.ownedOperations.head
		var umlReturnParameter = umlOperation.ownedParameters.findFirst [ param |
			param.direction === ParameterDirectionKind.RETURN_LITERAL
		]

		umlReturnParameter.type = umlType
		umlReturnParameter.lower = lower
		umlReturnParameter.upper = upper

		propagate
		umlInterface.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		umlInterface = helper.getUmlInterface(pcmRepository)
		umlOperation = umlInterface.ownedOperations.head

		checkSignatureConcept(umlOperation)
		var reloadedUmlType = helper.getModifiableInstance(umlType)
		assertNotNull(reloadedUmlType, "The DataType should not be null after reload")
		assertTrue(EcoreUtil.equals(umlOperation.type, reloadedUmlType))
		assertEquals(lower, umlOperation.lower)
		assertEquals(upper, umlOperation.upper)
		assertEquals(
			1,
			umlOperation.ownedParameters.size,
			"The Operation should have only a return parameter, since no other parameters were supposed to be added by this test."
		)
	}

	@Disabled("Not working properly yet")
	@Test
	def void testCreateSignatureConcept_UML_primitiveReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		pcmRepository = _testCreateSignatureConcept_UML(pcmRepository, helper.UML_STRING)
		assertNotNull(helper.UML_STRING, "Initialization of PrimitiveTypes seems to have failed")
		_testReturnTypePropagation_UML(pcmRepository, helper.UML_STRING, 1, 1)
	}

	@Disabled("Not working properly yet")
	@Test
	def void testCreateSignatureConcept_UML_compositeReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		val type = helper.getUmlCompositeDataTypeClass(pcmRepository)
		pcmRepository = _testCreateSignatureConcept_UML(pcmRepository, type)
		_testReturnTypePropagation_UML(pcmRepository, type, 1, 1)
	}

	@Disabled("Not working properly yet")
	@Test
	def void testCreateSignatureConcept_UML_collectionReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		val type = helper.getUmlCompositeDataTypeClass_2(pcmRepository)
		pcmRepository = _testCreateSignatureConcept_UML(pcmRepository, type)
		_testReturnTypePropagation_UML(pcmRepository, type, 0, LiteralUnlimitedNatural.UNLIMITED)
	}

	private def _testCreateSignatureConcept_PCM_withReturnType(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository)

		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmSignature.entityName = TEST_SIGNATURE_NAME
		pcmSignature.returnType__OperationSignature = pcmType
		var pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmParameter.parameterName = DefaultLiterals.RETURN_PARAM_NAME
		pcmParameter.dataType__Parameter = pcmType
		pcmSignature.parameters__OperationSignature += pcmParameter
		pcmInterface.signatures__OperationInterface += pcmSignature
		propagate

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		pcmInterface = helper.getPcmOperationInterface(pcmRepository)

		pcmSignature = pcmInterface.signatures__OperationInterface.head
		assertNotNull(pcmSignature)
		checkSignatureConcept(pcmSignature)
		assertTrue(pcmSignature.entityName == TEST_SIGNATURE_NAME)
		val reloadedPcmType = helper.getModifiableInstance(pcmType)
		assertNotNull(reloadedPcmType, "The DataType should not be null after reload")
		assertTrue(EcoreUtil.equals(pcmSignature.returnType__OperationSignature, reloadedPcmType))

//		assertEquals(
//			"The Signature should have no parameter, since none were supposed to be added by this test.",
//			0,
//			pcmSignature.parameters__OperationSignature.size
//		)
	}

	@Disabled("Not working properly yet")
	@Test
	def void testCreateSignatureConcept_PCM_primitiveReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		assertNotNull(helper.PCM_STRING, "Initialization of PrimitiveTypes seems to have failed")
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.PCM_STRING)
	}

	@Test
	def void testCreateSignatureConcept_PCM_compositeReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCompositeDataType(pcmRepository))
	}

	@Disabled("Not working properly yet")
	@Test
	def void testCreateSignatureConcept_PCM_collectionReturnType() {
		var pcmRepository = createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCollectionDataType(pcmRepository))
	}

}
