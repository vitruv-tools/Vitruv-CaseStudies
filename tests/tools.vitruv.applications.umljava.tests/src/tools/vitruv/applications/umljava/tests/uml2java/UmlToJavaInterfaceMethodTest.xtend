package tools.vitruv.applications.umljava.tests.uml2java

import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Model
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureBidirectionalExecution
import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*

/**
 * This class provides basic tests for creating, deleting and changing traits of interface methods.
 */
class UmlToJavaInterfaceMethodTest extends AbstractUmlToJavaTest {
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_NAME_2 = "InterfaceName2"
	static val TYPE_CLASS_NAME = "TypeName"
	static val IOPERATION_NAME = "interfaceMethod"
	static val IOPERATION_RENAME = "interfaceMethodRenamed"

	@Test
	def void testCreateInterfaceMethod() {
		createInterfaceWithOperation(INTERFACE_NAME, IOPERATION_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateJavaView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val javaMethod = javaInterface.claimInterfaceMethod(IOPERATION_NAME)
			assertJavaInterfaceMethodTraits(javaMethod, IOPERATION_NAME, TypesFactory.eINSTANCE.createVoid, null,
				javaInterface)
		]
	}

	@Test
	def void testRenameInterfaceMethod() {
		createInterfaceWithOperation(INTERFACE_NAME, IOPERATION_NAME)
		changeMethod(INTERFACE_NAME, IOPERATION_NAME) [
			name = IOPERATION_RENAME
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateJavaView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			assertJavaMemberContainerDontHaveMember(javaInterface, IOPERATION_NAME)
		]
	}

	@Test
	def void testDeleteInterfaceMethod() {
		createInterfaceWithOperation(INTERFACE_NAME, IOPERATION_NAME)
		changeMethod(INTERFACE_NAME, IOPERATION_NAME) [
			destroy()
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateJavaView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			assertJavaMemberContainerDontHaveMember(javaInterface, IOPERATION_NAME)
		]
	}

	@Test
	def void testMoveInterfaceMethod() {
		createInterfaceWithOperation(INTERFACE_NAME, IOPERATION_NAME)
		createInterfaceInRootPackage(INTERFACE_NAME_2)
		changeMethod(INTERFACE_NAME, IOPERATION_NAME) [ model, operation |
			model.claimInterface(INTERFACE_NAME_2).ownedOperations += operation
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME_2)
		validateJavaView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val javaInterface2 = claimJavaInterface(INTERFACE_NAME_2)
			assertJavaMemberContainerDontHaveMember(javaInterface, IOPERATION_NAME)
			assertThat(INTERFACE_NAME_2 + " must have operation " + IOPERATION_NAME,
				javaInterface2.getMembersByName(IOPERATION_NAME).toSet, is(not(emptySet)))
		]
	}

	@Test
	def void testChangeInterfaceMethodReturnType() {
		createInterfaceWithOperation(INTERFACE_NAME, IOPERATION_NAME)
		createClassInRootPackage(TYPE_CLASS_NAME)
		changeMethod(INTERFACE_NAME, IOPERATION_NAME) [ model, operation |
			operation.type = model.claimClass(TYPE_CLASS_NAME)
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateJavaView [
			val javaMethod = claimJavaInterface(INTERFACE_NAME).claimInterfaceMethod(IOPERATION_NAME)
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			assertJavaElementHasTypeRef(javaMethod, createNamespaceClassifierReference(javaTypeClass))
		]
	}

	private def void createInterfaceWithOperation(String interfaceName, String operationName) {
		createInterfaceInRootPackage(interfaceName)
		changeUmlModel [
			claimInterface(interfaceName) => [
				createOwnedOperation(operationName, null, null, null) => [
					isAbstract = true
				]
			]
		]
	}

	private def changeMethod(String interfaceName, String methodName, (Operation)=>void changeFunction) {
		changeMethod(interfaceName, methodName, [model, operation|changeFunction.apply(operation)])
	}

	private def changeMethod(String interfaceName, String methodName, (Model, Operation)=>void changeFunction) {
		changeUmlModel [
			val model = it
			claimInterface(interfaceName) => [
				claimOperation(methodName) => [
					changeFunction.apply(model, it)
				]
			]
		]
	}

	static class BidirectionalTest extends UmlToJavaInterfaceMethodTest {
		override setupTransformationDirection() {
			configureBidirectionalExecution(virtualModel)
		}
	}

}
