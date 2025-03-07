package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceMethodTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlInterfaceMethodTestModels extends UmlTestModelsBase implements InterfaceMethodTest.DomainModels {

	private static def newUmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newUmlInterface() {
		return UMLFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlOperation() {
		return UMLFactory.eINSTANCE.createOperation => [
			name = METHOD_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
			isAbstract = true
		]
	}

	private static def newOtherUmlClass() {
		return UMLFactory.eINSTANCE.createClass => [
			name = OTHER_CLASS_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
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

	// Basic

	override basicInterfaceMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation
			]))
			return #[
				umlModel
			]
		]
	}

	// Static

	override staticInterfaceMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation => [
					isAbstract = false
					isStatic = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Return type

	override interfaceMethodWithIntegerReturnCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlReturnParameter => [
						type = UmlPrimitiveType.INTEGER.umlType
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithStringReturnCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlReturnParameter => [
						type = UmlPrimitiveType.STRING.umlType
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithClassReturnCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlReturnParameter => [
						type = otherUmlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithSelfReturnCreation() {
		return newModel [
			val umlInterface = newUmlInterface
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(umlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlReturnParameter => [
						type = umlInterface
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Input parameters

	override interfaceMethodWithIntegerInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = INTEGER_PARAMETER_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
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
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithStringInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = STRING_PARAMETER_NAME
						type = UmlPrimitiveType.STRING.umlType
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithClassInputCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = CLASS_PARAMETER_NAME
						type = otherUmlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithSelfInputCreation() {
		return newModel [
			val umlInterface = newUmlInterface
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(umlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = OWN_TYPE_PARAMETER_NAME
						type = umlInterface
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override interfaceMethodWithMixedInputsCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlInterface => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = INTEGER_PARAMETER_NAME
						type = UmlPrimitiveType.INTEGER.umlType
					]
					ownedParameters += newUmlInputParameter => [
						name = STRING_PARAMETER_NAME
						type = UmlPrimitiveType.STRING.umlType
					]
					ownedParameters += newUmlInputParameter => [
						name = CLASS_PARAMETER_NAME
						type = otherUmlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Mixed input and return types

	override interfaceMethodWithMixedInputsAndReturnCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlInterface => [
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
					ownedParameters += newUmlInputParameter => [
						name = CLASS_PARAMETER_NAME
						type = otherUmlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Multiple methods

	override multipleInterfaceMethodsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlInterface => [
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
					name = METHOD_2_NAME
					ownedParameters += newUmlReturnParameter => [
						type = UmlPrimitiveType.INTEGER.umlType
					]
					ownedParameters += newUmlInputParameter => [
						name = STRING_PARAMETER_NAME
						type = UmlPrimitiveType.STRING.umlType
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}
}
