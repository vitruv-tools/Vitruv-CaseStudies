package tools.vitruv.applications.umljava.tests.java2uml

import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypesFactory
import org.eclipse.uml2.uml.VisibilityKind

import org.junit.jupiter.api.Test
import org.eclipse.emf.ecore.util.EcoreUtil

import tools.vitruv.applications.util.temporary.java.JavaVisibility
import tools.vitruv.applications.util.temporary.java.JavaStandardType

import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.*
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

/**
 * A test class to test the class method reactions.
 */
class JavaToUmlClassMethodTest extends AbstractJavaToUmlTest {
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS_NAME = "TypeName"
	static val OPERATION_NAME = "classMethod"
	static val OPERATION_RENAME = "classMethodRenamed"
	static val PARAMETER_NAME = "parameterName"
	static val PARAMETER_RENAME = "parameterRenamed"

	private def assertSingleClassWithNameInRootPackage(String name) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.Class, name, name)
	}

	private def createJavaClassWithMethod(String className, String methodName) {
		createJavaClassInRootPackage(className)
		changeView(createJavaClassesView) [
			claimJavaClass(className) => [
				members += MembersFactory.eINSTANCE.createClassMethod => [
					name = methodName
					typeReference = TypesFactory.eINSTANCE.createVoid
					makePublic
				]
			]
		]
	}

	private def changeClassMethod(String className, String methodName, (ClassMethod)=>void changeFunction) {
		changeView(createJavaClassesView) [
			claimJavaClass(className) => [
				claimClassMethod(methodName) => [
					changeFunction.apply(it)
				]
			]
		]
	}

	private def createJavaClassWithMethodAndParameter(String className, String methodName, String parameterName) {
		createJavaClassWithMethod(className, methodName)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			parameters += ParametersFactory.eINSTANCE.createOrdinaryParameter => [
				name = PARAMETER_NAME
				typeReference = createJavaPrimitiveType(JavaStandardType.INT)

			]
		]
	}

	/**
	 * Tests if a corresponding UML method is created when a Java method is created.
	 */
	@Test
	def void testCreateMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlOperationTraits(umlOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
				umlClass, null)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Tests if a change of the return type is correctly reflected on the UML method.
	 */
	@Test
	def void testChangeReturnType() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		changeView(createJavaClassesView) [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				claimClassMethod(OPERATION_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertElementsEqual(umlClass, javaClass)
			assertUmlOperationHasReturntype(umlOperation, umlTypeClass)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Tests if renaming the java method also renames the corresponding uml method.
	 */
	@Test
	def void testRenameMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			name = OPERATION_RENAME
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_RENAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_RENAME)
			assertUmlClassHasUniqueOperation(umlClass, OPERATION_RENAME)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Tests if a deletion is reflected on the UML method.
	 */
	@Test
	def void testDeleteMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			EcoreUtil.delete(it)
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			assertUmlClassDontHaveOperation(umlClass, OPERATION_NAME)
		]
	}

	/**
	 * Checks if changing the static modifier also changes the static property of
	 * the corresponding UML method.
	 */
	@Test
	def void testStaticMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			static = true
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlFeatureHasStaticValue(umlOperation, true)
			assertElementsEqual(umlOperation, javaMethod)
		]
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			static = false
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlFeatureHasStaticValue(umlOperation, false)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Tests if changing the abstract modifier also changes the abstract property of
	 * the corresponding UML method.
	 */
	@Test
	def void testAbstractMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			abstract = true
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlOperationHasAbstractValue(umlOperation, true)
			assertElementsEqual(umlOperation, javaMethod)
		]
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			abstract = false
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlOperationHasAbstractValue(umlOperation, false)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Asserts that changing the final modifier also changes the final property of
	 * the corresponding UML method.
	 */
	@Test
	def void testFinalMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			final = true
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlOperationHasFinalValue(umlOperation, true)
			assertElementsEqual(umlOperation, javaMethod)
		]
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			final = false
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaMethod = javaClass.claimClassMethod(OPERATION_NAME)
			assertUmlOperationHasFinalValue(umlOperation, false)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Checks if changing the visibility modifier also changes the visibility of
	 * the corresponding UML method.
	 */
	@Test
	def void testMethodVisibility() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			makeProtected
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			assertUmlNamedElementHasVisibility(umlOperation, VisibilityKind.PROTECTED_LITERAL)
			assertElementsEqual(umlOperation, javaMethod)
		]
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			makePrivate
		]
		createUmlAndJavaClassesView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val javaMethod = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME)
			assertUmlNamedElementHasVisibility(umlOperation, VisibilityKind.PRIVATE_LITERAL)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	/**
	 * Checks if a UML parameter is created after a java parameter is created.
	 */
	@Test
	def void testCreateParameter() {
		createJavaClassWithMethodAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val umlParameter = umlOperation.claimParameter(PARAMETER_NAME)
			val javaParameter = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME).claimParameter(
				PARAMETER_NAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_NAME)
			assertElementsEqual(umlParameter, javaParameter)
		]
	}

	/**
	 * Tests the rename reaction in the context of parameters.
	 */
	@Test
	def void testRenameParameter() {
		createJavaClassWithMethodAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			claimParameter(PARAMETER_NAME) => [
				name = PARAMETER_RENAME
			] 
			
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val umlParameter = umlOperation.claimParameter(PARAMETER_RENAME)
			val javaParameter = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME).claimParameter(
				PARAMETER_RENAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_RENAME)
			assertUmlOperationDontHaveParameter(umlOperation, PARAMETER_NAME)
			assertElementsEqual(umlParameter, javaParameter)
		]
	}

	/**
	 * Tests if the corresponding UML parameter is deleted when the java parameter is deleted.
	 */
	@Test
	def void testDeleteParameter() {
		createJavaClassWithMethodAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			claimParameter(PARAMETER_NAME) => [
				EcoreUtil.delete(it)
			] 
			
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			assertUmlOperationDontHaveParameter(umlOperation, PARAMETER_NAME)
		]
	}

	/**
	 * Tests if a change of the java parameter type is correctly propagated to the UML parameter.
	 */
	@Test
	def void testChangeParameterType() {
		createJavaClassWithMethodAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		changeView(createJavaClassesView) [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				claimClassMethod(OPERATION_NAME) => [
					claimParameter(PARAMETER_NAME) => [
						typeReference = createNamespaceClassifierReference(typeClass)
					]
				]
			]
			
		]
		createUmlAndJavaClassesView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlParameter = umlOperation.claimParameter(PARAMETER_NAME)
			val javaParameter = claimJavaClass(CLASS_NAME).claimClassMethod(OPERATION_NAME).claimParameter(
				PARAMETER_NAME)
			assertUmlParameterTraits(umlParameter, PARAMETER_NAME, umlTypeClass)
			assertElementsEqual(umlParameter, javaParameter)
		]
	}

	/**
	 * Tests if a constructor creates a fitting UML operation.
	 */
	@Test
	def void testCreateConstructor() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				members += MembersFactory.eINSTANCE.createConstructor => [
					name = CLASS_NAME
					javaVisibilityModifier = JavaVisibility.PUBLIC
				] 
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlAndJavaClassesView => [
			val umlConstructor = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(CLASS_NAME)
			val javaConstructor = claimJavaClass(CLASS_NAME).claimConstructor()
			assertElementsEqual(umlConstructor, javaConstructor)
		]
	}

}
