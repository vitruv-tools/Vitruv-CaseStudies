package tools.vitruv.applications.transitivechange.tests.linear.java2uml

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.ClassMethod
import org.junit.jupiter.api.Test

import static extension tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.parameters.OrdinaryParameter
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * A test class to test the class method reactions.
 * @author Fei
 */
class JavaToUmlClassMethodTest extends JavaToUmlTransformationTest {
	static val CLASS_NAME = "ClassName"
	static val TYPE_NAME = "TypeName"
	static val TYPE_NAME2 = "TypeName2"
	static val OPERATION_NAME = "classMethod"
	static val OPERATION_NAME2 = "classMethod2"
	static val STANDARD_OPERATION_NAME = "standardMethod"
	static val OPERATION_RENAME = "classMethodRenamed"
	static val PARAMETER_NAME = "parameterName"
	static val PARAMETER_NAME2 = "parameterName2"
	static val PARAMETER_RENAME = "parameterRenamed"

	var Class jClass
	var Class typeClass
	var Class typeClass2
	var ClassMethod jMeth
	var ClassMethod jParamMeth
	var OrdinaryParameter jParam

	/**
	 * Initializes and synchronizes three classes. One class has two methods.
	 * One of the methods owns a parameter.
	 */
	@BeforeEach
	def void before() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
		typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME)
		typeClass2 = createSimpleJavaClassWithCompilationUnit(TYPE_NAME2)
		jMeth = createSimpleJavaOperation(OPERATION_NAME)
		jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass2))
		jParamMeth = createJavaClassMethod(OPERATION_NAME2, TypesFactory.eINSTANCE.createBoolean, JavaVisibility.PUBLIC,
			false, false, #[jParam])
		jClass.members += jMeth
		jClass.members += jParamMeth
		propagate
	}

	/**
	 * Tests if a corresponding uml method is created when a java method is created.
	 */
	@Test
	def void testCreateMethod() {
		val meth = createSimpleJavaOperation(STANDARD_OPERATION_NAME)
		jClass.members += meth
		propagate
		val uOperation = getCorrespondingMethod(meth)
		val uClass = getCorrespondingClass(jClass)
		assertUmlOperationTraits(uOperation, STANDARD_OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
			uClass, null)
		assertClassMethodEquals(uOperation, meth)
	}

	/**
	 * Tests if a change of the return type is correctly reflected on the uml method.
	 */
	@Test
	def void testChangeReturnType() {
		jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
		propagate

		val uOperation = getCorrespondingMethod(jMeth)
		val uTypeClass = getCorrespondingClass(typeClass)
		assertUmlOperationHasReturntype(uOperation, uTypeClass)
		assertClassMethodEquals(uOperation, jMeth)
	}

	/**
	 * Tests if renaming the java method also renames the corresponding uml method.
	 */
	@Test
	def void testRenameMethod() {
		jMeth.name = OPERATION_RENAME
		propagate

		val uOperation = getCorrespondingMethod(jMeth)
		val uClass = getCorrespondingClass(jClass)
		assertEquals(OPERATION_RENAME, uOperation.name)
		assertUmlClassHasUniqueOperation(uClass, OPERATION_RENAME)
		assertClassMethodEquals(uOperation, jMeth)
	}

	/**
	 * Tests if a deletion is reflected on the uml method.
	 */
	@Test
	def void testDeleteMethod() {
		assertNotNull(getCorrespondingMethod(jMeth))

		EcoreUtil.delete(jMeth)
		propagate

		val uClass = getCorrespondingClass(jClass)
		assertUmlClassDontHaveOperation(uClass, OPERATION_RENAME)
	}

	/**
	 * Checks if changing the static modifier also changes the static property of
	 * the corresponding uml method.
	 */
	@Test
	def testStaticMethod() {
		jMeth.static = true
		propagate

		var uOperation = getCorrespondingMethod(jMeth)
		assertUmlFeatureHasStaticValue(uOperation, true)
		assertClassMethodEquals(uOperation, jMeth)

		jMeth.static = false
		propagate

		uOperation = getCorrespondingMethod(jMeth)
		assertUmlFeatureHasStaticValue(uOperation, false)
		assertClassMethodEquals(uOperation, jMeth)

	}

	/**
	 * Tests if changing the abstract modifier also changes the abstract property of
	 * the corresponding uml method.
	 */
	@Test
	def testAbstractMethod() {
		jMeth.abstract = true
		propagate

		var uOperation = getCorrespondingMethod(jMeth)
		assertUmlOperationHasAbstractValue(uOperation, true)
		assertClassMethodEquals(uOperation, jMeth)

		jMeth.abstract = false
		propagate

		uOperation = getCorrespondingMethod(jMeth)
		assertUmlOperationHasAbstractValue(uOperation, false)
		assertClassMethodEquals(uOperation, jMeth)
	}

	/**
	 * Asserts that changing the final modifier also changes the final property of
	 * the corresponding uml method.
	 */
	@Test
	def testFinalMethod() {
		jMeth.final = true
		propagate

		var uOperation = getCorrespondingMethod(jMeth)
		assertUmlOperationHasFinalValue(uOperation, true)
		assertClassMethodEquals(uOperation, jMeth)

		jMeth.final = false
		propagate

		uOperation = getCorrespondingMethod(jMeth)
		assertUmlOperationHasFinalValue(uOperation, false)
		assertClassMethodEquals(uOperation, jMeth)
	}

	/**
	 * Checks if changing the visibility modifier also changes the visibility of
	 * the corresponding uml method.
	 */
	@Test
	def testMethodVisibility() {
		jMeth.makeProtected
		propagate

		var uOperation = getCorrespondingMethod(jMeth)
		assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PROTECTED_LITERAL)
		assertClassMethodEquals(uOperation, jMeth)

		jMeth.makePrivate
		propagate

		uOperation = getCorrespondingMethod(jMeth)
		assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PRIVATE_LITERAL)
		assertClassMethodEquals(uOperation, jMeth)
	}

	/**
	 * Checks if a uml parameter is created after a java parameter is created.
	 */
	@Test
	def testCreateParameter() {
		val param = createJavaParameter(PARAMETER_NAME2, createNamespaceReferenceFromClassifier(typeClass))
		jMeth.parameters += param
		propagate

		val uOperation = getCorrespondingMethod(jMeth)
		val uParam = getCorrespondingParameter(param)
		assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME2)
		assertParameterEquals(uParam, param)
	}

	/**
	 * Tests the rename reaction in the context of parameters.
	 */
	@Test
	def testRenameParameter() {
		jParam.name = PARAMETER_RENAME
		propagate

		val uOperation = getCorrespondingMethod(jParamMeth)
		val uParam = getCorrespondingParameter(jParam)
		assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_RENAME)
		assertParameterEquals(uParam, jParam)
	}

	/**
	 * Tests if the corresponding uml parameter is deleted when the java parameter is deleted.
	 */
	@Test
	def testDeleteParameter() {
		assertNotNull(jParam)
		EcoreUtil.delete(jParam)
		propagate

		val uOperation = getCorrespondingMethod(jMeth)
		assertUmlOperationDontHaveParameter(uOperation, PARAMETER_NAME)
	}

	/**
	 * Tests if a change of the java parameter type is correctly propagated to the uml parameter.
	 */
	@Test
	def testChangeParameterType() {
		jParam.typeReference = createNamespaceReferenceFromClassifier(typeClass)
		propagate

		val uParam = getCorrespondingParameter(jParam)
		val uTypeClass = getCorrespondingClass(typeClass)
		assertUmlParameterTraits(uParam, PARAMETER_NAME, uTypeClass)
		assertParameterEquals(uParam, jParam)
	}

	/**
	 * Tests if a constructor creates a fitting uml operation.
	 */
	@Test
	def testCreateConstructor() {
		val jConstr = createJavaConstructorAndAddToClass(jClass, JavaVisibility.PUBLIC)
		propagate

		val uConstr = getCorrespondingMethod(jConstr)
		assertNotNull(uConstr)
		assertEquals(jConstr.name, uConstr.name)
		assertUmlNamedElementHasVisibility(uConstr, VisibilityKind.PUBLIC_LITERAL)

	}

}
