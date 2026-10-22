package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassMethodTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlClassMethodTestModels extends UmlTestModelsBase implements ClassMethodTest.DomainModels {

	private static def newUmlPackage() {
		return UMLFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newUmlClass() {
		return UMLFactory.eINSTANCE.createClass => [
			name = CLASS_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
		]
	}

	private static def newUmlOperation() {
		return UMLFactory.eINSTANCE.createOperation => [
			name = METHOD_NAME
			visibility = VisibilityKind.PUBLIC_LITERAL
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

	override basicClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation
			]))
			return #[
				umlModel
			]
		]
	}

	// Visibility

	override publicClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override protectedClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					visibility = VisibilityKind.PROTECTED_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override packagePrivateClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					visibility = VisibilityKind.PACKAGE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override privateClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					visibility = VisibilityKind.PRIVATE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Modifiers

	override finalClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					isLeaf = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override abstractClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					isAbstract = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override staticClassMethodCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlOperation => [
					isStatic = true
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Return type

	override classMethodWithIntegerReturnCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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

	override classMethodWithStringReturnCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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

	override classMethodWithClassReturnCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
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

	override classMethodWithSelfReturnCreation() {
		return newModel [
			val umlClass = newUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(umlClass => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlReturnParameter => [
						type = umlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Input parameters

	override classMethodWithIntegerInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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

	override classMethodWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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

	override classMethodWithStringInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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

	override classMethodWithClassInputCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
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

	override classMethodWithSelfInputCreation() {
		return newModel [
			val umlClass = newUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(umlClass => [
				ownedOperations += newUmlOperation => [
					ownedParameters += newUmlInputParameter => [
						name = OWN_TYPE_PARAMETER_NAME
						type = umlClass
					]
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override classMethodWithMixedInputsCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
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

	override classMethodWithMixedInputsAndReturnCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
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

	override multipleClassMethodsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
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
