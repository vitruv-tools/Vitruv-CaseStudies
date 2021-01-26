package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase
import tools.vitruv.applications.cbs.commonalities.pcm.PcmPrimitiveDataType

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.*

class PcmCompositeDataTypeTestModels extends PcmTestModelsBase implements CompositeDataTypeTest.DomainModels {

	private static def newPcmCompositeDataType() {
		return RepositoryFactory.eINSTANCE.createCompositeDataType => [
			entityName = COMPOSITE_DATATYPE_1_NAME
		]
	}

	private static def newPcmInnerDeclaration() {
		return RepositoryFactory.eINSTANCE.createInnerDeclaration
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty CompositeDataType

	override emptyCompositeDataTypeCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType
			]
			return #[
				pcmRepository
			]
		]
	}

	// Primitive inner elements

	override compositeDataTypeWithBooleanElementCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = BOOLEAN_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.BOOL.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override compositeDataTypeWithIntegerElementCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = INTEGER_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.INT.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override compositeDataTypeWithDoubleElementCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = DOUBLE_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.DOUBLE.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override compositeDataTypeWithStringElementCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = STRING_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.STRING.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = BOOLEAN_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.BOOL.pcmType
					]
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = INTEGER_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.INT.pcmType
					]
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = DOUBLE_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.DOUBLE.pcmType
					]
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = STRING_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.STRING.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	// Multiple CompositeDataTypes

	override multipleCompositeDataTypesWithPrimitiveElementsCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				dataTypes__Repository += newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = BOOLEAN_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.BOOL.pcmType
					]
				]
				dataTypes__Repository += newPcmCompositeDataType => [
					entityName = COMPOSITE_DATATYPE_2_NAME
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = INTEGER_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.INT.pcmType
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}

	override compositeDataTypeWithCompositeElementsCreation() {
		return newModel [
			val pcmRepository = newPcmRepository => [
				val compositeDataType1 = newPcmCompositeDataType => [
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = INTEGER_ELEMENT_NAME
						datatype_InnerDeclaration = PcmPrimitiveDataType.INT.pcmType
					]
				]
				dataTypes__Repository += compositeDataType1
				dataTypes__Repository += newPcmCompositeDataType => [
					entityName = COMPOSITE_DATATYPE_2_NAME
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = COMPOSITE_ELEMENT_1_NAME
						datatype_InnerDeclaration = compositeDataType1
					]
					innerDeclaration_CompositeDataType += newPcmInnerDeclaration => [
						entityName = COMPOSITE_ELEMENT_2_NAME
						datatype_InnerDeclaration = compositeDataType1
					]
				]
			]
			return #[
				pcmRepository
			]
		]
	}
}
