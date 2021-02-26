package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*

/**
 * A Test class to test class methods and its traits.
 * 
 * @author Fei
 */
class UmlToJavaClassMethodTest extends UmlToJavaTransformationTest {
	static val CLASS_NAME = "ClassName"
	static val TYPE_NAME = "TypeName"
	static val OPERATION_NAME = "classMethod"
	static val STANDARD_OPERATION_NAME = "standardMethod"
	static val OPERATION_RENAME = "classMethodRenamed"
	static val PARAMETER_NAME = "parameterName"
	static val DATATYPE_NAME = "DataTypeName"

	var Class uClass
	var Class typeClass
	var Parameter uParam
	var PrimitiveType pType
	var Operation uOperation

	/**
	 * Initializes two uml classes and a primitive type. One uml class contains 
	 * an operation with a parameter.
	 */
	@BeforeEach
	def void before() {
		uClass = createSimpleUmlClass(rootElement, CLASS_NAME)
		typeClass = createSimpleUmlClass(rootElement, TYPE_NAME)
		pType = UmlTypeUtil.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name == "Integer"]
		uParam = createUmlParameter(PARAMETER_NAME, pType)
		uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
		uClass.ownedOperations += uOperation
		rootElement.packagedElements += uClass
		rootElement.packagedElements += typeClass
		propagate
	}

	/**
	 * Tests if creating a uml operation also causes the creating of an corresponding
	 * java method.
	 */
	@Test
	def void testCreateClassMethod() {
		val operation = uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null)
		propagate

		val jMethod = getCorrespondingClassMethod(operation)
		val jClass = getCorrespondingClass(uClass)
		assertNotNull(jMethod)
		assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
		assertClassMethodEquals(operation, jMethod)
	}

	/**
	 * Tests the change of the uml method return type. Checks if
	 * the corresponding java method adapated the corresponding type.
	 */
	@Test
	def void testChangeReturnType() {
		uOperation.type = typeClass
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		val jTypeClass = getCorrespondingClass(typeClass)
		assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests if renaming a method is correctly reflected on the java side.
	 */
	@Test
	def testRenameMethod() {
		uOperation.name = OPERATION_RENAME
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		val jClass = getCorrespondingClass(uClass)
		assertEquals(OPERATION_RENAME, jMethod.name)
		assertClassMethodEquals(uOperation, jMethod)
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
	}

	/**
	 * Tests if deleting a method is correctly reflected on the java side.
	 */
	@Test
	def testDeleteMethod() {
		uOperation.destroy
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
	}
	
	@Test
	def testMoveMethod() {
		val uClass2 = createSimpleUmlClass(rootElement, "ClassName2")
		uClass2.ownedOperations += uOperation
		propagate
		
		val jClass = getCorrespondingClass(uClass)
		val jClass2 = getCorrespondingClass(uClass2)
		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME)
		assertFalse(jClass2.getMembersByName(OPERATION_NAME).nullOrEmpty)
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests if setting a method static correctly reflected on the java side.
	 */
	@Test
	def testStaticMethod() {
		uOperation.isStatic = true
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaModifiableStatic(jMethod, true)
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests if setting a method final correctly reflected on the java side.
	 */
	@Test
	def testFinalMethod() {
		uOperation.isLeaf = true
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaModifiableFinal(jMethod, true)
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests if setting a method abstract is correctly reflected on the java side.
	 */
	@Test
	def testAbstractMethod() {
		uOperation.isAbstract = true
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaModifiableAbstract(jMethod, true)
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests if visibility changes are propagated to the java method.
	 */
	@Test
	def testMethodVisibility() {
		uOperation.visibility = VisibilityKind.PRIVATE_LITERAL
		propagate

		var jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PRIVATE)
		assertClassMethodEquals(uOperation, jMethod)

		uOperation.visibility = VisibilityKind.PROTECTED_LITERAL
		propagate

		jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PROTECTED)
		assertClassMethodEquals(uOperation, jMethod)
	}

	/**
	 * Tests the creation of a method that act as constructor and checks if a 
	 * constructor is created on the java side.
	 */
	@Test
	def testCreateConstructor() {
		val uConstr = createSimpleUmlOperation(uClass.name)
		uClass.ownedOperations += uConstr
		propagate
		val jConstr = getCorrespondingConstructor(uConstr)
		assertNotNull(jConstr)
	}
	
	@Test
	def testMoveConstructor() {
		val uConstr = createSimpleUmlOperation(uClass.name)
		uClass.ownedOperations += uConstr
		propagate
		
		val uClass2 = createSimpleUmlClass(rootElement, "ClassName2")
		uClass2.ownedOperations += uConstr
		uConstr.name = uClass2.name
		propagate
		
		val jClass = getCorrespondingClass(uClass)
		val jClass2 = getCorrespondingClass(uClass2)
		val jConstr = getCorrespondingConstructor(uConstr)
		assertJavaMemberContainerDontHaveMember(jClass, uClass.name)
		assertJavaMemberContainerDontHaveMember(jClass, uClass2.name)
		assertJavaMemberContainerDontHaveMember(jClass2, uClass.name)
		assertFalse(jClass2.getMembersByName(uConstr.name).nullOrEmpty)
		assertNotNull(jConstr)
	}
	
	/**
	 * Same as testMoveConstructor but the order of move and rename is switched
	 */
	@Test
	def testMoveConstructor2() {
		val uConstr = createSimpleUmlOperation(uClass.name)
		uClass.ownedOperations += uConstr
		propagate
		
		val uClass2 = createSimpleUmlClass(rootElement, "ClassName2")
		uConstr.name = uClass2.name
		uClass2.ownedOperations += uConstr
		propagate
		
		val jClass = getCorrespondingClass(uClass)
		val jClass2 = getCorrespondingClass(uClass2)
		val jConstr = getCorrespondingConstructor(uConstr)
		assertJavaMemberContainerDontHaveMember(jClass, uClass.name)
		assertJavaMemberContainerDontHaveMember(jClass, uClass2.name)
		assertJavaMemberContainerDontHaveMember(jClass2, uClass.name)
		assertFalse(jClass2.getMembersByName(uConstr.name).nullOrEmpty)
		assertNotNull(jConstr)
	}

	/**
	 * Checks if method creating in datatypes is reflected in the corresponding java class.
	 */
	@Test
	def void testCreateMethodInDataType() {
		val dataType = createUmlDataType(rootElement, DATATYPE_NAME)
		val operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null)
		propagate

		val jMethod = getCorrespondingClassMethod(operation)
		val jClass = getCorrespondingClass(dataType)
		assertNotNull(jMethod)
		assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createVoid, false, false, null, jClass)
		assertClassMethodEquals(operation, jMethod)
	}

	/**
	 * Tests the deletion of methods in data types and if the deletion is
	 * propagated to the java model.
	 */
	@Test
	def testDeleteMethodInDataType() {
		val dataType = createUmlDataType(rootElement, DATATYPE_NAME)
		val operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null)
		propagate

		var jMethod = getCorrespondingClassMethod(operation)
		assertNotNull(jMethod)

		operation.destroy
		propagate

		val jClass = getCorrespondingClass(dataType)
		assertJavaMemberContainerDontHaveMember(jClass, STANDARD_OPERATION_NAME)
	}
	
	@Test
	def testMoveMethodInDataType() {
		val uDataType = createUmlDataType(rootElement, DATATYPE_NAME)
		val uOperation = uDataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null)
		propagate
		
		val uDataType2 = createUmlDataType(rootElement, "DataTypeName2")
		uDataType2.ownedOperations += uOperation
		propagate
		
		val jDataType = getCorrespondingClass(uDataType)
		val jDataType2 = getCorrespondingClass(uDataType2)
		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaMemberContainerDontHaveMember(jDataType, uOperation.name)
		assertFalse(jDataType2.getMembersByName(uOperation.name).nullOrEmpty)
		assertClassMethodEquals(uOperation, jMethod)
	}
}
