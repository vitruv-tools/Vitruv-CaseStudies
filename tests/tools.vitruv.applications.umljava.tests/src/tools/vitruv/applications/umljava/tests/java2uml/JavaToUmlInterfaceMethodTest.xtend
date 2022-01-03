package tools.vitruv.applications.umljava.tests.java2uml

import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import org.junit.jupiter.api.Test
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.parameters.ParametersFactory

/**
 * This class contains test cases for the creation, renaming and deleting of interface methods.
 * Plus, it checks the change of parameters and return types of interface methods.
 */
class JavaToUmlInterfaceMethodTest extends AbstractJavaToUmlTest {
	static val INTERFACE_NAME = "InterfaceName"
	static val TYPE_NAME = "TypeName"
	static val IOPERATION_NAME = "interfaceMethod"
	static val IOPERATION_RENAME = "interfaceMethodRenamed"
	static val PARAMETER_NAME = "parameterName"

	private def void createDefaultInterfaceWithMethod(String methodName) {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			containedJavaInterface(INTERFACE_NAME) => [
				members += MembersFactory.eINSTANCE.createInterfaceMethod => [
					name = methodName
					typeReference = TypesFactory.eINSTANCE.createVoid
				]
			]
		]
	}

	@Test
	def void testCreateInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		createUmlAndJavaClassesView => [
			val umlInterface = containedDefaultUmlModel.containedInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.containedOperation(IOPERATION_NAME)
			val javaInterfaceMethod = containedJavaInterface(INTERFACE_NAME).containedInterfaceMethod(IOPERATION_NAME)
			assertUmlOperationTraits(umlOperation, IOPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
				umlInterface, null)
			assertElementsEqual(umlOperation, javaInterfaceMethod)
		]
	}

	@Test
	def void testRenameInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		changeView(createJavaClassesView) [
			containedJavaInterface(INTERFACE_NAME) => [
				containedInterfaceMethod(IOPERATION_NAME) => [
					name = IOPERATION_RENAME
				]
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = containedDefaultUmlModel.containedInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.containedOperation(IOPERATION_RENAME)
			val javaInterfaceMethod = containedJavaInterface(INTERFACE_NAME).containedInterfaceMethod(IOPERATION_RENAME)
			assertEquals(IOPERATION_RENAME, umlOperation.name)
			assertUmlInterfaceDontHaveOperation(umlInterface, IOPERATION_NAME)
			assertUmlOperationTraits(umlOperation, IOPERATION_RENAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
				umlInterface, null)
			assertElementsEqual(umlOperation, javaInterfaceMethod)
		]

	}

	@Test
	def void testDeleteInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		changeView(createJavaClassesView) [
			containedJavaInterface(INTERFACE_NAME) => [
				EcoreUtil.delete(containedInterfaceMethod(IOPERATION_NAME))
			]
		]
		createUmlView => [
			val umlInterface = containedDefaultUmlModel.containedInterface(INTERFACE_NAME)
			assertUmlInterfaceDontHaveOperation(umlInterface, IOPERATION_NAME)
		]
	}

	@Test
	def void testChangeInterfaceMethodReturnType() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		createJavaClassInRootPackage(TYPE_NAME)
		changeView(createJavaClassesView) [
			val typeClass = containedJavaClass(TYPE_NAME)
			containedJavaInterface(INTERFACE_NAME) => [
				containedInterfaceMethod(IOPERATION_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = containedDefaultUmlModel.containedInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.containedOperation(IOPERATION_NAME)
			val umlTypeClass = containedDefaultUmlModel.containedClass(TYPE_NAME)
			val javaInterfaceMethod = containedJavaInterface(INTERFACE_NAME).containedInterfaceMethod(IOPERATION_NAME)
			assertUmlOperationHasReturntype(umlOperation, umlTypeClass)
			assertElementsEqual(umlOperation, javaInterfaceMethod)
		]
	}

	@Test
	def void testCreateInterfaceParameter() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		createJavaClassInRootPackage(TYPE_NAME)
		changeView(createJavaClassesView) [
			val typeClass = containedJavaClass(TYPE_NAME)
			containedJavaInterface(INTERFACE_NAME) => [
				containedInterfaceMethod(IOPERATION_NAME) => [
					parameters += ParametersFactory.eINSTANCE.createOrdinaryParameter => [
						name = PARAMETER_NAME
						typeReference = createNamespaceClassifierReference(typeClass)
					]
				]
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = containedDefaultUmlModel.containedInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.containedOperation(IOPERATION_NAME)
			val javaInterfaceMethod = containedJavaInterface(INTERFACE_NAME).containedInterfaceMethod(IOPERATION_NAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_NAME)
			assertElementsEqual(umlOperation, javaInterfaceMethod)
		]
	}
}
