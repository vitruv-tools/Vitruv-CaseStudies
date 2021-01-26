package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This class provides tests for basic class tests in the uml to java direction
 * @author Fei
 */
class UmlToJavaClassTest extends UmlToJavaTransformationTest {
	static val CLASS_NAME = "ClassName"
	static val DATATYPE_NAME = "DataTypeName"
	static val STANDARD_CLASS_NAME = "StandardClassName"
	static val CLASS_RENAME = "ClassRenamed"
	static val SUPER_CLASS_NAME = "SuperClassName"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_NAME2 = "InterfaceName2"

	var Class uClass

	@BeforeEach
	def void before() {
		uClass = createUmlClassAndAddToPackage(rootElement, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
		propagate
	}

	@Test
	def testCreateClass() {
		val c = createUmlClassAndAddToPackage(rootElement, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false,
			false)
		propagate

		val jClass = getCorrespondingClass(c)
		assertEquals(STANDARD_CLASS_NAME, jClass.name)
		assertJavaFileExists(STANDARD_CLASS_NAME, #[])
	}

	@Test
	def testDeletedClass() {
		uClass.destroy
		propagate

		assertJavaFileNotExists(CLASS_NAME, #[])
	}

	@Test
	def testChangeClassVisibility() {
		uClass.visibility = VisibilityKind.PRIVATE_LITERAL
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaModifiableHasVisibility(jClass, JavaVisibility.PRIVATE)
		assertClassEquals(uClass, jClass)

	}

	@Test
	def testChangeClassVisibility2() {
		uClass.visibility = VisibilityKind.PACKAGE_LITERAL
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaModifiableHasVisibility(jClass, JavaVisibility.PACKAGE)
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testChangeAbstractClass() {
		uClass.isAbstract = true
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaModifiableAbstract(jClass, true)
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testRenameClass() {
		uClass.name = CLASS_RENAME
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertEquals(CLASS_RENAME, jClass.name)
		assertJavaFileExists(CLASS_RENAME, #[])
		assertJavaFileNotExists(CLASS_NAME, #[])
		assertClassEquals(uClass, jClass)
	}
	
	@Test
	def testMoveClass() {
		val uPackage = createUmlPackageAndAddToSuperPackage("package", rootElement)
		uPackage.packagedElements += uClass
		propagate
		
		val jClass = getCorrespondingClass(uClass)
		assertJavaFileExists(CLASS_NAME, #[uPackage.name])
		assertJavaFileNotExists(CLASS_NAME, #[])
		assertClassEquals(uClass, jClass)
		assertEquals(jClass.containingPackageName.join("."), uPackage.name)
	}

	@Test
	def testChangeFinalClass() {
		uClass.isFinalSpecialization = true
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaModifiableFinal(jClass, true)
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testSuperClassChanged() {
		val superClass = createSimpleUmlClass(rootElement, SUPER_CLASS_NAME)
		uClass.generals += superClass
		propagate
		val jClass = getCorrespondingClass(uClass)
		val jSuperClass = getCorrespondingClass(superClass)
		assertHasSuperClass(jClass, jSuperClass)
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testDeleteClassImplement() {
		val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
		val uI2 = createSimpleUmlInterface(rootElement, INTERFACE_NAME2)
		uClass.createInterfaceRealization("InterfacRealization", uI)
		uClass.createInterfaceRealization("InterfacRealization2", uI2)
		propagate

		uClass.interfaceRealizations.remove(0)
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertTrue(jClass.implements.size == 1, jClass.implements.size.toString)
		assertEquals(INTERFACE_NAME2, getClassifierFromTypeReference(jClass.implements.get(0)).name)
		assertJavaFileExists(INTERFACE_NAME, #[])
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testAddClassImplement() {
		val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
		uClass.createInterfaceRealization("InterfacRealization", uI)
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(jClass.implements.head).name)
		assertClassEquals(uClass, jClass)
	}

	@Test
	def testChangeInterfaceImplementer() {
		val uClass2 = createSimpleUmlClass(rootElement, STANDARD_CLASS_NAME)
		val uI = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
		val realization = uClass.createInterfaceRealization("InterfacRealization", uI)
		propagate

		var jClass = getCorrespondingClass(uClass)
		var jClass2 = getCorrespondingClass(uClass2)
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(jClass.implements.head).name)
		assertTrue(jClass2.implements.nullOrEmpty)

		realization.implementingClassifier = uClass2
		propagate

		jClass = getCorrespondingClass(uClass)
		jClass2 = getCorrespondingClass(uClass2)
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(jClass2.implements.head).name)
		assertTrue(jClass.implements.nullOrEmpty)

		assertClassEquals(uClass, jClass)
		assertClassEquals(uClass2, jClass2)

	}

	@Test
	def testCreateDataType() {
		val dataType = createUmlDataType(rootElement, DATATYPE_NAME)
		propagate

		val jClass = getCorrespondingClass(dataType)
		assertEquals(DATATYPE_NAME, jClass.name)
		assertJavaFileExists(DATATYPE_NAME, #[])
	}
	
	@Test
	def testMoveDataType() {
		val uDataType = createUmlDataType(rootElement, DATATYPE_NAME)
		propagate
		
		val uPackage = createUmlPackageAndAddToSuperPackage("package", rootElement)
		uPackage.packagedElements += uDataType
		propagate
		
		val jDataType = getCorrespondingClass(uDataType)
		assertJavaFileExists(DATATYPE_NAME, #[uPackage.name])
		assertJavaFileNotExists(DATATYPE_NAME, #[])
		assertEquals(DATATYPE_NAME, jDataType.name)
		assertEquals(jDataType.containingPackageName.join("."), uPackage.name)
	}
}
