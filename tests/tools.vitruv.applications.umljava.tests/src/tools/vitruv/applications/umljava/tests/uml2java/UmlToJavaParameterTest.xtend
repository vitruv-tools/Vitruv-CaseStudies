package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test
import tools.vitruv.change.propagation.ChangePropagationMode

import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * This class tests the change of parameter traits.
 */
class UmlToJavaParameterTest extends AbstractUmlToJavaTest {
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS_NAME = "TypeName"
	static val OPERATION_NAME = "classMethod"
	static val PARAMETER_NAME = "parameterName"
	static val PARAMETER_RENAME = "parameterRenamed"

	@Test
	def void testCreateParameter() {
		createClassWithOperationAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaParameter = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME).claimParameter(
				PARAMETER_NAME)
			assertJavaParameterTraits(javaParameter, PARAMETER_NAME, TypesFactory.eINSTANCE.createInt)
		]
	}

	@Test
	def void testRenameParameter() {
		createClassWithOperationAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		changeParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME) [
			name = PARAMETER_RENAME
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			assertJavaMethodHasUniqueParameter(javaMethod, PARAMETER_RENAME, TypesFactory.eINSTANCE.createInt)
			assertJavaMethodDontHaveParameter(javaMethod, PARAMETER_NAME)
		]

	}

	@Test
	def void testDeleteParameter() {
		createClassWithOperationAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		changeParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME) [
			destroy()
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			assertJavaMethodDontHaveParameter(javaMethod, PARAMETER_NAME)
		]
	}

	@Test
	def void testChangeParameterType() {
		createClassWithOperationAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		createClassInRootPackage(TYPE_CLASS_NAME)
		changeParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME) [ model, parameter |
			parameter.type = model.claimClass(TYPE_CLASS_NAME)
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaParameter = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME).claimParameter(
				PARAMETER_NAME)
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			assertJavaParameterTraits(javaParameter, PARAMETER_NAME, createNamespaceClassifierReference(javaTypeClass))
		]
	}

	@Test
	def void testChangeParameterDirectionToReturn() {
		createClassWithOperationAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		changeParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME) [
			direction = ParameterDirectionKind.RETURN_LITERAL
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			assertJavaElementHasTypeRef(javaMethod, TypesFactory.eINSTANCE.createInt)
		]
	}

	private def void createClassWithOperationAndParameter(String className, String operationName,
		String parameterName) {
		createClassInRootPackage(className)
		changeUmlModel [
			claimClass(className) => [
				createOwnedOperation(operationName, null, null, null) => [
					createOwnedParameter(parameterName, loadUmlPrimitiveType("int"))
				]
			]
		]
	}

	private def changeParameter(String className, String methodName, String parameterName,
		(Parameter)=>void changeFunction) {
		changeParameter(className, methodName, parameterName, [model, parameter|changeFunction.apply(parameter)])
	}

	private def changeParameter(String className, String methodName, String parameterName,
		(Model, Parameter)=>void changeFunction) {
		changeUmlModel [
			val model = it
			claimClass(className) => [
				claimOperation(methodName) => [
					claimParameter(parameterName) => [
						changeFunction.apply(model, it)
					]
				]
			]
		]
	}

	static class BidirectionalTest extends UmlToJavaParameterTest {
		override protected getChangePropagationMode() {
			ChangePropagationMode.TRANSITIVE_CYCLIC
		}
	}

}
