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
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.*
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.ParameterizedTest
import tools.vitruv.framework.vsum.views.View

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

	/**
	 * Tests if a corresponding UML method is created when a Java method is created.
	 */
	@Test
	def void testCreateMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			assertUmlOperationTraits(umlOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
				umlClass, null)
		]
	}

	/**
	 * Tests if a change of the return type is correctly reflected on the UML method.
	 */
	@Test
	def void testChangeReturnType() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [ view, method |
			val typeClass = view.claimJavaClass(TYPE_CLASS_NAME)
			method => [
				typeReference = createNamespaceClassifierReference(typeClass)
			]
		]
		changeView(createJavaClassesView)[]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlOperation = umlClass.claimOperation(OPERATION_NAME)
			assertUmlOperationHasReturntype(umlOperation, umlTypeClass)
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
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			assertUmlClassHasUniqueOperation(umlClass, OPERATION_RENAME)
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

	private def void changeAndCheckPropertyOfMethod(String className, String methodName,
		(org.emftext.language.java.members.ClassMethod)=>void changeJavaMethod,
		(org.eclipse.uml2.uml.Operation)=>void validateUmlMethod) {
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			changeJavaMethod.apply(it)
		]
		assertSingleClassWithNameInRootPackage(className)
		createUmlView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			validateUmlMethod.apply(umlOperation)
		]
	}

	/**
	 * Checks if changing the static modifier also changes the static property of
	 * the corresponding UML method.
	 */
	@Test
	def void testStaticMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [static = true], [
			assertUmlFeatureHasStaticValue(it, true)
		])
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [static = false], [
			assertUmlFeatureHasStaticValue(it, false)
		])
	}

	/**
	 * Tests if changing the abstract modifier also changes the abstract property of
	 * the corresponding UML method.
	 */
	@Test
	def void testAbstractMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [abstract = true], [
			assertUmlOperationHasAbstractValue(it, true)
		])
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [abstract = false], [
			assertUmlOperationHasAbstractValue(it, false)
		])
	}

	/**
	 * Asserts that changing the final modifier also changes the final property of
	 * the corresponding UML method.
	 */
	@Test
	def void testFinalMethod() {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [final = true], [
			assertUmlOperationHasFinalValue(it, true)
		])
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [final = false], [
			assertUmlOperationHasFinalValue(it, false)
		])
	}

	/**
	 * Checks if changing the visibility modifier also changes the visibility of
	 * the corresponding UML method.
	 */
	@ParameterizedTest
	@EnumSource(value=JavaVisibility, names=#["PUBLIC"], mode=EnumSource.Mode.EXCLUDE)
	def void testMethodVisibility(JavaVisibility visibility) {
		createJavaClassWithMethod(CLASS_NAME, OPERATION_NAME)
		changeAndCheckPropertyOfMethod(CLASS_NAME, OPERATION_NAME, [javaVisibilityModifier = visibility], [
			assertUmlNamedElementHasVisibility(it, getUmlVisibilityKindFromJavaVisibilityConstant(visibility))
		])
	}

	/**
	 * Checks if a UML parameter is created after a java parameter is created.
	 */
	@Test
	def void testCreateParameter() {
		createJavaClassWithMethodAndParameter(CLASS_NAME, OPERATION_NAME, PARAMETER_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_NAME)
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
		createUmlView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			assertUmlOperationHasUniqueParameter(umlOperation, PARAMETER_RENAME)
			assertUmlOperationDontHaveParameter(umlOperation, PARAMETER_NAME)
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
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [ view, method |
			val typeClass = view.claimJavaClass(TYPE_CLASS_NAME)
			method => [
				claimParameter(PARAMETER_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		createUmlView => [
			val umlOperation = defaultUmlModel.claimClass(CLASS_NAME).claimOperation(OPERATION_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlParameter = umlOperation.claimParameter(PARAMETER_NAME)
			assertUmlParameterTraits(umlParameter, PARAMETER_NAME, umlTypeClass)
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

	private def createJavaClassWithMethodAndParameter(String className, String methodName, String parameterName) {
		createJavaClassWithMethod(className, methodName)
		changeClassMethod(CLASS_NAME, OPERATION_NAME) [
			parameters += ParametersFactory.eINSTANCE.createOrdinaryParameter => [
				name = PARAMETER_NAME
				typeReference = createJavaPrimitiveType(JavaStandardType.INT)

			]
		]
	}

	private def changeClassMethod(String className, String methodName, (ClassMethod)=>void changeFunction) {
		changeClassMethod(className, methodName, [view, method|changeFunction.apply(method)])
	}

	private def changeClassMethod(String className, String methodName, (View, ClassMethod)=>void changeFunction) {
		changeView(createJavaClassesView) [
			val view = it
			claimJavaClass(className) => [
				claimClassMethod(methodName) => [
					changeFunction.apply(view, it)
				]
			]
		]
	}

	private def assertSingleClassWithNameInRootPackage(String name) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.Class, name)
	}

	private def assertClassWithNameInRootPackage(String name) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			name)
	}
}
