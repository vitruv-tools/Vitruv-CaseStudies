package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.ConstructorTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaConstructorTestModels extends JavaTestModelsBase implements ConstructorTest.DomainModels {

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

	// Creates a constructor without any visibility modifier:
	private static def newDefaultJavaConstructor() {
		return MembersFactory.eINSTANCE.createConstructor => [
			name = CLASS_NAME
		]
	}

	private static def newJavaConstructor() {
		return newDefaultJavaConstructor => [
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

	override basicConstructorCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Visibility

	override publicConstructorCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaConstructor => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override protectedConstructorCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaConstructor => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createProtected
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override packagePrivateConstructorCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaConstructor
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override privateConstructorCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newDefaultJavaConstructor => [
					annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Input parameters

	override constructorWithIntegerInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
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

	override constructorWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
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

	override constructorWithStringInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
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

	override constructorWithClassInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
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

	override constructorWithSelfInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaClass = newJavaClass
			val javaCompilationUnit = javaPackage.newCompilationUnit(javaClass => [
				members += newJavaConstructor => [
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

	override constructorWithMixedInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
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

	// Multiple constructors

	override multipleConstructorsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaClass => [
				members += newJavaConstructor => [
					parameters += newJavaOrdinaryParameter => [
						name = BOOLEAN_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createBoolean
					]
				]
				members += newJavaConstructor => [
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
