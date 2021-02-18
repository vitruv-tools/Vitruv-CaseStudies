package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.InterfaceMethodTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaInterfaceMethodTestModels extends JavaTestModelsBase implements InterfaceMethodTest.DomainModels {

	private static def newJavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = PACKAGE_NAME
		]
	}

	private static def newJavaInterface() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaInterfaceMethod() {
		return MembersFactory.eINSTANCE.createInterfaceMethod => [
			name = METHOD_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createAbstract
			typeReference = TypesFactory.eINSTANCE.createVoid
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

	override basicInterfaceMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Static

	override staticInterfaceMethodCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					annotationsAndModifiers.removeIf[it instanceof Abstract]
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

	override interfaceMethodWithIntegerReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override interfaceMethodWithStringReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = referenceJamoppType(String)
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override interfaceMethodWithClassReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
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

	override interfaceMethodWithSelfReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaInterface = newJavaInterface
			val javaCompilationUnit = javaPackage.newCompilationUnit(javaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = JavaModificationUtil.createNamespaceClassifierReference(javaInterface)
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	// Input parameters

	override interfaceMethodWithIntegerInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
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

	override interfaceMethodWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
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

	override interfaceMethodWithStringInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = referenceJamoppType(String)
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override interfaceMethodWithClassInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
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

	override interfaceMethodWithSelfInputCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaInterface = newJavaInterface
			val javaCompilationUnit = javaPackage.newCompilationUnit(javaInterface => [
				members += newJavaInterfaceMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = OWN_TYPE_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReference(javaInterface)
					]
				]
			])
			return #[
				javaPackage,
				javaCompilationUnit
			]
		]
	}

	override interfaceMethodWithMixedInputsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = referenceJamoppType(String)
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

	override interfaceMethodWithMixedInputsAndReturnCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val otherJavaClass = newOtherJavaClass
			val javaClassCompilationUnit = javaPackage.newCompilationUnit(otherJavaClass)
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = referenceJamoppType(String)
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

	override multipleInterfaceMethodsCreation() {
		return newModel [
			val javaPackage = newJavaPackage
			val javaCompilationUnit = javaPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = BOOLEAN_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createBoolean
					]
				]
				members += newJavaInterfaceMethod => [
					name = METHOD_2_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = referenceJamoppType(String)
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
