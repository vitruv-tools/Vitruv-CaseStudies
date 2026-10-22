package tools.vitruv.applications.cbs.commonalities.tests.oo.uml

import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.ConstructorTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.uml.UmlPrimitiveType

import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlModelHelper.*

class UmlConstructorTestModels extends UmlTestModelsBase implements ConstructorTest.DomainModels {

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

	private static def newUmlConstructor() {
		return UMLFactory.eINSTANCE.createOperation => [
			// Constructor: Operation with the same name as the containing class and no return parameter.
			name = CLASS_NAME
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

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Basic

	override basicConstructorCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor
			]))
			return #[
				umlModel
			]
		]
	}

	// Visibility

	override publicConstructorCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
					visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override protectedConstructorCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
					visibility = VisibilityKind.PROTECTED_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override packagePrivateConstructorCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
					visibility = VisibilityKind.PACKAGE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	override privateConstructorCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
					visibility = VisibilityKind.PRIVATE_LITERAL
				]
			]))
			return #[
				umlModel
			]
		]
	}

	// Input parameters

	override constructorWithIntegerInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
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

	override constructorWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
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

	override constructorWithStringInputCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
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

	override constructorWithClassInputCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
				ownedOperations += newUmlConstructor => [
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

	override constructorWithSelfInputCreation() {
		return newModel [
			val umlClass = newUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(umlClass => [
				ownedOperations += newUmlConstructor => [
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

	override constructorWithMixedInputsCreation() {
		return newModel [
			val otherUmlClass = newOtherUmlClass
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(otherUmlClass, newUmlClass => [
				ownedOperations += newUmlConstructor => [
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

	override multipleConstructorsCreation() {
		return newModel [
			val umlModel = newUmlModel.withElements(newUmlPackage.withElements(newUmlClass => [
				ownedOperations += newUmlConstructor => [
					ownedParameters += newUmlInputParameter => [
						name = BOOLEAN_PARAMETER_NAME
						type = UmlPrimitiveType.BOOLEAN.umlType
					]
				]
				ownedOperations += newUmlConstructor => [
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
