package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.OperationInterface
import org.eclipse.uml2.uml.Interface
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationSignature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.ParameterModifier
import static extension edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil.*;
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil

class InterfacesTest extends AbstractPcmUmlTest {
		
	protected def OperationInterface createOperationInterface(String name) {
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = name
		rootElement.interfaces__Repository += pcmInterface
		propagate
		return pcmInterface
	}
	
	protected def OperationSignature createOperationInterfaceOperation(OperationInterface pcmInterface, String operationName, DataType returnType) {
		val operationSignature = RepositoryFactory.eINSTANCE.createOperationSignature()
		operationSignature.entityName = operationName
		operationSignature.returnType__OperationSignature = returnType
		pcmInterface.signatures__OperationInterface += operationSignature
		propagate
		return operationSignature
	}
	
	protected def OperationSignature createOperationInterfaceOperation(OperationInterface pcmInterface, String operationName, PrimitiveTypeEnum returnType) {
		val primitiveType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		primitiveType.type = returnType
		rootElement.dataTypes__Repository += primitiveType
		return createOperationInterfaceOperation(pcmInterface, operationName, primitiveType)
	}
	
	@Test
	def void testInterfaceCreation() {
		val interfaceName = INTERFACE_NAME
		val pcmInterface = createOperationInterface(interfaceName)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof Interface)
		val umlInterface = (correspondingElements.get(0) as Interface)
		assertEquals(interfaceName, umlInterface.name)
	}
	
	@Test
	def void testInterfaceAddOperation() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val returnType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		returnType.type = PrimitiveTypeEnum.BOOL
		rootElement.dataTypes__Repository += returnType
		propagate
		val operationName = OPERATION_NAME
		val pcmOperation = createOperationInterfaceOperation(pcmInterface, operationName, returnType)
		val correspondingInterface = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		assertEquals(1, (correspondingInterface.get(0) as Interface).ownedOperations.length)
		val correspondingOperation = correspondenceModel.getCorrespondingEObjects(#[pcmOperation]).flatten
		assertEquals(1, correspondingOperation.length)
		val umlOperation = (correspondingOperation.get(0) as Operation)
		assertEquals(operationName, umlOperation.name)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(returnType.type), umlOperation.type.name)
	}
	
	@Test
	def void testInterfaceChangeOperation() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val pcmOperation = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME, PrimitiveTypeEnum.BOOL)
		val newReturnType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		newReturnType.type = PrimitiveTypeEnum.CHAR
		rootElement.dataTypes__Repository += newReturnType
		pcmOperation.returnType__OperationSignature = newReturnType
		propagate
		var correspondingOperation = correspondenceModel.getCorrespondingEObjects(#[pcmOperation]).flatten
		var umlOperation = (correspondingOperation.get(0) as Operation)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(newReturnType.type), umlOperation.type.name)
		
		val newOperationName = OPERATION_NAME_2
		pcmOperation.entityName = newOperationName
		propagate
		assertEquals(newOperationName, umlOperation.name)
		
		pcmOperation.returnType__OperationSignature = null
		propagate
		correspondingOperation = correspondenceModel.getCorrespondingEObjects(#[pcmOperation]).flatten
		umlOperation = (correspondingOperation.get(0) as Operation)
		assertEquals(null, umlOperation.type)
	}
	
	@Test
	def void testInterfaceRemoveOperation() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val pcmOperation1 = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME, PrimitiveTypeEnum.INT)
		val pcmOperation2 = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME_2, PrimitiveTypeEnum.DOUBLE)
		val correspondingInterface = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		val umlInterface = (correspondingInterface.get(0) as Interface)
		assertEquals(2, umlInterface.ownedOperations.length)
		pcmInterface.signatures__OperationInterface -= pcmOperation1
		propagate
		assertEquals(1, umlInterface.ownedOperations.length)
		assertEquals(pcmOperation2.entityName, umlInterface.ownedOperations.get(0).name)
	}
	
	@Test
	def void testInterfaceRemove() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmInterface]).flatten
		assertEquals(1, correspondingElements.length)
		val umlModel = (correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten.get(0) as Model)
		val elementCount = umlModel.packagedElements.length
		rootElement.interfaces__Repository -= pcmInterface
		propagate
		assertEquals(elementCount-1, umlModel.packagedElements.length)
	}
	
	protected def Parameter createOperationParameter(OperationSignature pcmSignature, String name, DataType type) {
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter()
		pcmParameter.name = name
		pcmParameter.dataType__Parameter = type
		pcmSignature.parameters__OperationSignature += pcmParameter
		propagate
		return pcmParameter
	}
	
	@Test
	def void testParameterAdd() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val pcmOperation = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME, PrimitiveTypeEnum.BOOL)
		val parameterName = PARAMETER_NAME
		createOperationParameter(pcmOperation, parameterName, pcmOperation.returnType__OperationSignature)
		val parameterType = pcmOperation.returnType__OperationSignature
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmOperation]).flatten
		val umlOperation = (correspondingElements.get(0) as Operation)
		assertEquals(2, umlOperation.ownedParameters.length)
		// first parameter should be the return value
		val umlParameter = umlOperation.ownedParameters.get(1)
		
		assertEquals(parameterName, umlParameter.name)
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName((parameterType as PrimitiveDataType).type),
			umlParameter.type.name)
	}
	
	@Test
	def void testParameterChange() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val pcmOperation = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME, PrimitiveTypeEnum.BOOL)
		val pcmParameter = createOperationParameter(pcmOperation, PARAMETER_NAME, pcmOperation.returnType__OperationSignature)
		
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmParameter]).flatten
		var umlParameter = (correspondingElements.get(0) as org.eclipse.uml2.uml.Parameter)
		
		val newType = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		newType.type = PrimitiveTypeEnum.DOUBLE
		rootElement.dataTypes__Repository += newType
		pcmParameter.dataType__Parameter = newType
		propagate
		
		correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmParameter]).flatten
		umlParameter = (correspondingElements.get(0) as org.eclipse.uml2.uml.Parameter)
				
		assertEquals(PcmToUmlUtil.getUmlPrimitiveTypeName(newType.type),
			umlParameter.type.name)

		val newName = PARAMETER_NAME_2
		pcmParameter.name = newName
		propagate
		assertEquals(newName, umlParameter.name)
		
		pcmParameter.modifier__Parameter = ParameterModifier.OUT
		propagate
		
		correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmParameter]).flatten
		umlParameter = (correspondingElements.get(0) as org.eclipse.uml2.uml.Parameter)
		
		assertEquals(PcmToUmlUtil.getUmlParameterDirection(pcmParameter.modifier__Parameter), umlParameter.direction)
	}
	
	@Test
	def void testParameterDelete() {
		val pcmInterface = createOperationInterface(INTERFACE_NAME)
		val pcmOperation = createOperationInterfaceOperation(pcmInterface, OPERATION_NAME, PrimitiveTypeEnum.BOOL)
		val pcmParameter1 = createOperationParameter(pcmOperation, PARAMETER_NAME, pcmOperation.returnType__OperationSignature)
		val remainingParameterName = PARAMETER_NAME_2
		createOperationParameter(pcmOperation, remainingParameterName, pcmOperation.returnType__OperationSignature)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmOperation]).flatten
		val umlOperation = (correspondingElements.get(0) as Operation)
		assertEquals(3, umlOperation.ownedParameters.length)
		
		val parameterCount = umlOperation.ownedParameters.length
		
		pcmOperation.parameters__OperationSignature -= pcmParameter1
		propagate
		
		assertEquals(parameterCount - 1, umlOperation.ownedParameters.length)
		
		assertEquals(remainingParameterName, umlOperation.ownedParameters.get(1).name)
	}
}