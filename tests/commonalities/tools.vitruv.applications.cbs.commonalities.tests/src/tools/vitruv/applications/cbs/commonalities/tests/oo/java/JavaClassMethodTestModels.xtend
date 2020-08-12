package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassMethodTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaClassMethodTestModels extends JavaTestModelsBase implements ClassMethodTest.DomainModels {

	private static def newJavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newJavaClass() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = CLASS_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	// Creates a method without public modifier:
	private static def newDefaultJavaClassMethod() {
		return MembersFactory.eINSTANCE.createClassMethod => [
			name = METHOD_NAME
			typeReference = TypesFactory.eINSTANCE.createVoid
		]
	}

	private static def newJavaClassMethod() {
		return newDefaultJavaClassMethod => [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newOtherJavaClass() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = OTHER_CLASS_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaOrdinaryParameter() {
		return ParametersFactory.eINSTANCE.createOrdinaryParameter
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Basic

	override basicClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Visibility

	override publicClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override protectedClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createProtected
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override packagePrivateClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaClassMethod
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override privateClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Modifiers

	override finalClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createFinal
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override abstractClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createAbstract
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override staticClassMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createStatic
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Return type

	override classMethodWithIntegerReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithStringReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithClassReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					typeReference = JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)
				]
			])
			return #[
				javaPackage,
				javaClassCompilationUnit,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithSelfReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaClass = newJavaClass
			val javaCompilationUnit = javaPackage.newCompilationUnit(javaClass => [
				members += newJavaClassMethod => [
					typeReference = JavaModificationUtil.createNamespaceClassifierReference(javaClass)
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Input parameters

	override classMethodWithIntegerInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = BOOLEAN_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createBoolean
					]
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = DOUBLE_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createDouble
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithStringInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithClassInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = CLASS_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)
					]
				]
			])
			return #[
				javaPackage,
				javaClassCompilationUnit,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithSelfInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaClass = newJavaClass
			val javaCompilationUnit = javaPackage.newCompilationUnit(javaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = OWN_TYPE_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReference(javaClass)
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override classMethodWithMixedInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
					parameters += newJavaOrdinaryParameter => [
						name = CLASS_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)
					]
				]
			])
			return #[
				javaPackage,
				javaClassCompilationUnit,
				javaCompilationUnit
			]
		]
	}

	// Mixed input and return types

	override classMethodWithMixedInputsAndReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
					parameters += newJavaOrdinaryParameter => [
						name = CLASS_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReference(otherJavaClass)
					]
				]
			])
			return #[
				javaPackage,
				javaClassCompilationUnit,
				javaCompilationUnit
			]
		]
	}

	// Multiple methods

	override multipleClassMethodsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaClassMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = BOOLEAN_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createBoolean
					]
				]
				members += newJavaClassMethod => [
					name = METHOD_2_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}
}
