package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.eclipse.uml2.uml.UMLFactory

/**
 * This class provides tests for basic class tests in the UML to Java direction
 */
class UmlToJavaClassTest extends AbstractUmlToJavaTest {
	static val PACKAGE_NAME = "rootpackage"
	static val DEFAULT_CLASS_NAME = "TestClass"
	static val RENAMED_CLASS_NAME = "RenamedTestClass"
	static val ADDITIONAL_CLASS_NAME = "AdditionalClass"
	static val DEFAULT_INTERFACE_NAME = "TestInterface"
	static val ADDITIONAL_INTERFACE_NAME = "AdditionalInterface"
	
	private def assertSingleClassWithNameInRootPackage(String name) {
		assertSingleClassWithNameInRootPackage(name, name)
	}

	private def assertSingleClassWithNameInRootPackage(String name, String compilationUnitName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class,
			org.eclipse.uml2.uml.Class, name, compilationUnitName)
	}

	private def assertSingleClassWithNameInPackage(String packageName, String name) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			packageName, name)
	}

	@Test
	def void testCreateClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		assertJavaCompilationUnitCount(1)
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
	}
	
	@Test
	def void testCreateClassInPackage() {
		createClassInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		assertJavaCompilationUnitCount(1)
		assertSingleClassWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
	}

	@Test
	def void testDeletedClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).destroy
		]
		assertNoClassifierExistsInRootPackage()
		createUmlAndJavaClassesView() => [
			assertJavaCompilationUnitCount(0)
			assertTrue(defaultUmlModel.packagedElements.empty)
			assertTrue(javaClasses.empty)
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])
		]
	}

	@Test
	def void testChangeClassVisibilityToPrivate() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).visibility = VisibilityKind.PRIVATE_LITERAL
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaModifiableHasVisibility(javaClass, JavaVisibility.PRIVATE)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testChangeClassVisibilityToPackage() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).visibility = VisibilityKind.PACKAGE_LITERAL
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaModifiableHasVisibility(javaClass, JavaVisibility.PACKAGE)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testChangeAbstractClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).isAbstract = true
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaModifiableAbstract(javaClass, true)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testRenameClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).name = RENAMED_CLASS_NAME
		]
		assertSingleClassWithNameInRootPackage(RENAMED_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(RENAMED_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			assertJavaFileExists(RENAMED_CLASS_NAME, #[])
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])
			val javaClass = claimJavaClass(umlClass.name)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testMoveClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val umlClass = claimClass(DEFAULT_CLASS_NAME)
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				name = PACKAGE_NAME
				packagedElements += umlClass
			]
		]
		assertSingleClassWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlClass = umlPackage.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaFileExists(umlClass.name, #[umlPackage.name])
			assertJavaFileNotExists(umlClass.name, #[])
			assertElementsEqual(umlClass, javaClass)
			assertEquals(javaClass.containingPackageName.join("."), umlPackage.name)
		]
		changeUmlModel [
			packagedElements += claimPackage(PACKAGE_NAME).claimClass(DEFAULT_CLASS_NAME)
		]
		createUmlAndJavaClassesView() => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaFileNotExists(umlClass.name, #[umlPackage.name])
			assertJavaFileExists(umlClass.name, #[])
			assertElementsEqual(umlClass, javaClass)
			assertEquals(javaClass.containingPackageName.join("."), "")
		]
	}

	@Test
	def void testChangeFinalClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).isFinalSpecialization = true
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(1)
			val javaClass = claimJavaClass(umlClass.name)
			assertJavaModifiableFinal(javaClass, true)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testSuperClassChanged() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val existingClass = claimClass(DEFAULT_CLASS_NAME)
			val superClass = UMLFactory.eINSTANCE.createClass
			packagedElements += superClass => [
				name = ADDITIONAL_CLASS_NAME
			]
			existingClass => [
				generals += superClass
			]
		]
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			val umlSuperClass = defaultUmlModel.claimClass(ADDITIONAL_CLASS_NAME)
			assertJavaCompilationUnitCount(2)
			val javaClass = claimJavaClass(umlClass.name)
			val javaSuperClass = claimJavaClass(umlSuperClass.name)
			assertHasSuperClass(javaClass, javaSuperClass)
			assertElementsEqual(umlClass, javaClass)
		]
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME) => [
				generals.clear
			]
		]
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(2)
			val javaClass = claimJavaClass(umlClass.name)
			assertNull(javaClass.extends)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testAddClassImplements() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createInterfaceInRootPackage(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization", claimInterface(DEFAULT_INTERFACE_NAME))
		]
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(javaClass.implements.head).name)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testDeleteClassImplements() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createInterfaceInRootPackage(DEFAULT_INTERFACE_NAME)
		createInterfaceInRootPackage(ADDITIONAL_INTERFACE_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization",
				claimInterface(DEFAULT_INTERFACE_NAME))
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization2",
				claimInterface(ADDITIONAL_INTERFACE_NAME))
		]
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).interfaceRealizations.remove(0)
		]
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertEquals(1, javaClass.implements.size)
			assertEquals("AdditionalInterface", getClassifierFromTypeReference(javaClass.implements.head).name)
			assertJavaFileExists(DEFAULT_INTERFACE_NAME, #[])
			assertJavaFileExists("AdditionalInterface", #[])
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testChangeInterfaceImplementer() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createClassInRootPackage(ADDITIONAL_CLASS_NAME)
		createInterfaceInRootPackage(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization", claimInterface(DEFAULT_INTERFACE_NAME))
		]
		createUmlAndJavaClassesView() => [
			val firstJavaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			val secondJavaClass = claimJavaClass(ADDITIONAL_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(firstJavaClass.implements.head).name)
			assertTrue(secondJavaClass.implements.nullOrEmpty)
		]
		changeUmlModel [
			val secondUmlClass = claimClass(ADDITIONAL_CLASS_NAME)
			val realization = claimClass(DEFAULT_CLASS_NAME).interfaceRealizations.claimOne
			realization.implementingClassifier = secondUmlClass
		]
		createUmlAndJavaClassesView() => [
			val firstJavaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			val secondJavaClass = claimJavaClass(ADDITIONAL_CLASS_NAME)
			val firstUmlClass = defaultUmlModel.claimClass(DEFAULT_CLASS_NAME)
			val secondUmlClass = defaultUmlModel.claimClass(ADDITIONAL_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(secondJavaClass.implements.head).name)
			assertTrue(firstJavaClass.implements.nullOrEmpty)
			assertElementsEqual(firstUmlClass, firstJavaClass)
			assertElementsEqual(secondUmlClass, secondJavaClass)
		]
	}

	@Test
	def void testCreateDataType() {
		createDataTypeInRootPackage(DEFAULT_CLASS_NAME)
		assertJavaCompilationUnitCount(1)
	}

	@Test
	def void testMoveDataType() {
		createDataTypeInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val umlDataType = claimDataType(DEFAULT_CLASS_NAME)
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				name = PACKAGE_NAME
				packagedElements += umlDataType
			]
		]
		createUmlAndJavaClassesView() => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertJavaFileExists(DEFAULT_CLASS_NAME, #[umlPackage.name])
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])
			assertEquals(DEFAULT_CLASS_NAME, javaClass.name)
			assertEquals(javaClass.containingPackageName.join("."), umlPackage.name)
		]
	}
}
