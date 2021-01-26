package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

class UmlOperationTestModels extends UmlTestModelsBase implements OperationTest.DomainModels {

	private static def newUmlInterface() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlOperation() {
		return UMLFactory.eINSTANCE.createOperation => [
			name = OPERATION_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
			isAbstract = true
		]
	}

	private static def newUmlInputParameter() {
		return UMLFactory.eINSTANCE.createParameter => [
			direction = ParameterDirectionKind.IN_LITERAL
		]
	}

	private static def newUmlReturnParameter() {
		return UMLFactory.eINSTANCE.createParameter => [
			direction = ParameterDirectionKind.RETURN_LITERAL
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty

	override emptyOperationCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Return type

	override operationWithIntegerReturnCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlReturnParameter => [
							type = UmlPrimitiveType.INTEGER.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override operationWithStringReturnCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlReturnParameter => [
							type = UmlPrimitiveType.STRING.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Input parameters

	override operationWithIntegerInputCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlInputParameter => [
							name = INTEGER_PARAMETER_NAME
							type = UmlPrimitiveType.INTEGER.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override operationWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlInputParameter => [
							name = BOOLEAN_PARAMETER_NAME
							type = UmlPrimitiveType.BOOLEAN.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = INTEGER_PARAMETER_NAME
							type = UmlPrimitiveType.INTEGER.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = DOUBLE_PARAMETER_NAME
							type = UmlPrimitiveType.REAL.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	override operationWithStringInputCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlInputParameter => [
							name = STRING_PARAMETER_NAME
							type = UmlPrimitiveType.STRING.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Mixed input and return types

	override operationWithMixedInputsAndReturnCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlReturnParameter => [
							type = UmlPrimitiveType.INTEGER.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = INTEGER_PARAMETER_NAME
							type = UmlPrimitiveType.INTEGER.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = STRING_PARAMETER_NAME
							type = UmlPrimitiveType.STRING.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}

	// Multiple operations

	override multipleOperationsCreation() {
		return newModel [
			val umlRepositoryModel = new UmlRepositoryModel()

			umlRepositoryModel.contractsPackage => [
				packagedElements += newUmlInterface => [
					ownedOperations += newUmlOperation => [
						ownedParameters += newUmlReturnParameter => [
							type = UmlPrimitiveType.INTEGER.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = BOOLEAN_PARAMETER_NAME
							type = UmlPrimitiveType.BOOLEAN.umlType
						]
					]
					ownedOperations += newUmlOperation => [
						name = OPERATION_2_NAME
						ownedParameters += newUmlReturnParameter => [
							type = UmlPrimitiveType.INTEGER.umlType
						]
						ownedParameters += newUmlInputParameter => [
							name = STRING_PARAMETER_NAME
							type = UmlPrimitiveType.STRING.umlType
						]
					]
				]
			]

			return #[
				umlRepositoryModel.model
			]
		]
	}
}
