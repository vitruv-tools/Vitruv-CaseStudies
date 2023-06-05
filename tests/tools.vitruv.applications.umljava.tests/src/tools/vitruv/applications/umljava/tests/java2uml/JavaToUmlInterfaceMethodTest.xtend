package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import tools.mdsd.jamopp.model.java.members.MembersFactory
import tools.mdsd.jamopp.model.java.parameters.ParametersFactory
import tools.mdsd.jamopp.model.java.types.TypesFactory
import org.junit.jupiter.api.Test

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.umljava.tests.util.UmlElementsTestAssertions.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

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

	@Test
	def void testCreateInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.claimOperation(IOPERATION_NAME)
			assertUmlOperationTraits(umlOperation, IOPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
				umlInterface, null)
		]
	}

	@Test
	def void testRenameInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		changeJavaView [
			claimJavaInterface(INTERFACE_NAME) => [
				claimInterfaceMethod(IOPERATION_NAME) => [
					name = IOPERATION_RENAME
				]
			]
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlOperation = umlInterface.claimOperation(IOPERATION_RENAME)
			assertThat(umlOperation.name, is(IOPERATION_RENAME))
			assertUmlInterfaceDontHaveOperation(umlInterface, IOPERATION_NAME)
			assertUmlOperationTraits(umlOperation, IOPERATION_RENAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
				umlInterface, null)
		]

	}

	@Test
	def void testDeleteInterfaceMethod() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		changeJavaView [
			claimJavaInterface(INTERFACE_NAME) => [
				EcoreUtil.delete(claimInterfaceMethod(IOPERATION_NAME))
			]
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceDontHaveOperation(umlInterface, IOPERATION_NAME)
		]
	}

	@Test
	def void testChangeInterfaceMethodReturnType() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		createJavaClassInRootPackage(TYPE_NAME)
		changeJavaView [
			val typeClass = claimJavaClass(TYPE_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				claimInterfaceMethod(IOPERATION_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlOperation = defaultUmlModel.claimInterface(INTERFACE_NAME).claimOperation(IOPERATION_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_NAME)
			assertUmlOperationHasReturntype(umlOperation, umlTypeClass)
		]
	}

	@Test
	def void testCreateInterfaceParameter() {
		createDefaultInterfaceWithMethod(IOPERATION_NAME)
		createJavaClassInRootPackage(TYPE_NAME)
		changeJavaView [
			val typeClass = claimJavaClass(TYPE_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				claimInterfaceMethod(IOPERATION_NAME) => [
					parameters += ParametersFactory.eINSTANCE.createOrdinaryParameter => [
						name = PARAMETER_NAME
						typeReference = createNamespaceClassifierReference(typeClass)
					]
				]
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlOperation = defaultUmlModel.claimInterface(INTERFACE_NAME).claimOperation(IOPERATION_NAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_NAME)
		]
	}

	private def void createDefaultInterfaceWithMethod(String methodName) {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeJavaView [
			claimJavaInterface(INTERFACE_NAME) => [
				members += MembersFactory.eINSTANCE.createInterfaceMethod => [
					name = methodName
					typeReference = TypesFactory.eINSTANCE.createVoid
				]
			]
		]
	}

	static class BidirectionalTest extends JavaToUmlInterfaceMethodTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}

}
