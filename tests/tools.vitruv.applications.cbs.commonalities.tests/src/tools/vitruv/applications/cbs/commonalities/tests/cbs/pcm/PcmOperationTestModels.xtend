package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.pcm.PcmPrimitiveDataType

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmOperationTestModels extends PcmTestModelsBase implements OperationTest.DomainModels {

	private static def newPcmOperationInterface() {
		return RepositoryFactory.eINSTANCE.createOperationInterface => [
			entityName = INTERFACE_NAME
		]
	}

	private static def newPcmOperationSignature() {
		return RepositoryFactory.eINSTANCE.createOperationSignature => [
			entityName = OPERATION_NAME
		]
	}

	private static def newPcmParameter() {
		return RepositoryFactory.eINSTANCE.createParameter
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty

	override emptyOperationCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	// Return type

	override operationWithIntegerReturnCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						returnType__OperationSignature = PcmPrimitiveDataType.INT.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override operationWithStringReturnCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						returnType__OperationSignature = PcmPrimitiveDataType.STRING.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	// Input parameters

	override operationWithIntegerInputCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						parameters__OperationSignature += newPcmParameter => [
							parameterName = INTEGER_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.INT.pcmType
						]
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override operationWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						parameters__OperationSignature += newPcmParameter => [
							parameterName = BOOLEAN_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.BOOL.pcmType
						]
						parameters__OperationSignature += newPcmParameter => [
							parameterName = INTEGER_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.INT.pcmType
						]
						parameters__OperationSignature += newPcmParameter => [
							parameterName = DOUBLE_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.DOUBLE.pcmType
						]
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override operationWithStringInputCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						parameters__OperationSignature += newPcmParameter => [
							parameterName = STRING_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.STRING.pcmType
						]
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	// Mixed input and return types

	override operationWithMixedInputsAndReturnCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						returnType__OperationSignature = PcmPrimitiveDataType.INT.pcmType
						parameters__OperationSignature += newPcmParameter => [
							parameterName = INTEGER_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.INT.pcmType
						]
						parameters__OperationSignature += newPcmParameter => [
							parameterName = STRING_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.STRING.pcmType
						]
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	// Multiple operations

	override multipleOperationsCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				interfaces__Repository += newPcmOperationInterface => [
					signatures__OperationInterface += newPcmOperationSignature => [
						returnType__OperationSignature = PcmPrimitiveDataType.INT.pcmType
						parameters__OperationSignature += newPcmParameter => [
							parameterName = BOOLEAN_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.BOOL.pcmType
						]
					]
					signatures__OperationInterface += newPcmOperationSignature => [
						entityName = OPERATION_2_NAME
						returnType__OperationSignature = PcmPrimitiveDataType.INT.pcmType
						parameters__OperationSignature += newPcmParameter => [
							parameterName = STRING_PARAMETER_NAME
							dataType__Parameter = PcmPrimitiveDataType.STRING.pcmType
						]
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
