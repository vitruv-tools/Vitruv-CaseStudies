package tools.vitruv.applications.transitivechange.tests.linear.uml2java

import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*

/**
 * This class provides basic tests for creating, deleting and changing traits of interface methods.
 * 
 * @author Fei
 */
class UmlToJavaInterfaceMethodTest extends UmlToJavaTransformationTest {
	static val INTERFACE_NAME = "InterfaceName"
	static val TYPE_NAME = "TypeName"
	static val IOPERATION_NAME = "interfaceMethod"
	static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod"
	static val IOPERATION_RENAME = "interfaceMethodRenamed"

	var org.eclipse.uml2.uml.Interface uInterface
	var org.eclipse.uml2.uml.Class typeClass
	var org.eclipse.uml2.uml.Operation uOperation

	@BeforeEach
	def void before() {
		uInterface = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
		uOperation = createUmlInterfaceOperation(IOPERATION_NAME, null, null)
		uInterface.ownedOperations += uOperation
		typeClass = createSimpleUmlClass(rootElement, TYPE_NAME)
		rootElement.packagedElements += uInterface
		rootElement.packagedElements += typeClass
		propagate
	}

	@Test
	def void testCreateInterfaceMethod() {
		val interfaceMethod = createUmlInterfaceOperation(STANDARD_IOPERATION_NAME, null, null)
		uInterface.ownedOperations += interfaceMethod
		propagate

		val jMethod = getCorrespondingInterfaceMethod(interfaceMethod)
		val jInterface = getCorrespondingInterface(uInterface)
		assertJavaInterfaceMethodTraits(jMethod, STANDARD_IOPERATION_NAME, TypesFactory.eINSTANCE.createVoid, null,
			jInterface)
		assertInterfaceMethodEquals(interfaceMethod, jMethod)
	}

	@Test
	def testRenameInterfaceMethod() {
		uOperation.name = IOPERATION_RENAME
		propagate

		val jMethod = getCorrespondingInterfaceMethod(uOperation)
		val jInterface = getCorrespondingInterface(uInterface)
		assertEquals(IOPERATION_RENAME, jMethod.name)
		assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
		assertInterfaceMethodEquals(uOperation, jMethod)
	}

	@Test
	def testDeleteInterfaceMethod() {
		assertNotNull(uOperation)
		uOperation.destroy
		propagate

		val jInterface = getCorrespondingInterface(uInterface)
		assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME)
	}
	
	@Test
	def testMoveInterfaceMethod() {
		val uInterface2 = createSimpleUmlInterface(rootElement, "InterfaceName2")
		uInterface2.ownedOperations += uOperation
		propagate
		
		val jInterface = getCorrespondingInterface(uInterface)
		val jInterface2 = getCorrespondingInterface(uInterface2)
		assertJavaMemberContainerDontHaveMember(jInterface, uOperation.name)
		assertFalse(jInterface2.getMembersByName(uOperation.name).nullOrEmpty)
	}

	@Test
	def testChangeInterfaceMethodReturnType() {
		uOperation.type = typeClass
		propagate

		val jMethod = getCorrespondingInterfaceMethod(uOperation)
		val jTypeClass = getCorrespondingClass(typeClass)
		assertJavaElementHasTypeRef(jMethod, createNamespaceReferenceFromClassifier(jTypeClass))
		assertInterfaceMethodEquals(uOperation, jMethod)
	}
}
