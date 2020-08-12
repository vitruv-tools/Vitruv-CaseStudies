package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaOperationTestModels extends JavaTestModelsBase implements OperationTest.DomainModels {

	private static def newJavaInterface() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaInterfaceMethod() {
		return MembersFactory.eINSTANCE.createInterfaceMethod => [
			name = OPERATION_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createAbstract
			typeReference = TypesFactory.eINSTANCE.createVoid
		]
	}

	private static def newJavaOrdinaryParameter() {
		return ParametersFactory.eINSTANCE.createOrdinaryParameter
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	// Empty

	override emptyOperationCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Return type

	override operationWithIntegerReturnCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override operationWithStringReturnCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Input parameters

	override operationWithIntegerInputCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override operationWithMultiplePrimitiveInputsCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
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

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	override operationWithStringInputCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Mixed input and return types

	override operationWithMixedInputsAndReturnCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = INTEGER_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createInt
					]
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}

	// Multiple operations

	override multipleOperationsCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod => [
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = BOOLEAN_PARAMETER_NAME
						typeReference = TypesFactory.eINSTANCE.createBoolean
					]
				]
				members += newJavaInterfaceMethod => [
					name = OPERATION_2_NAME
					typeReference = TypesFactory.eINSTANCE.createInt
					parameters += newJavaOrdinaryParameter => [
						name = STRING_PARAMETER_NAME
						typeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
					]
				]
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}
}
