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
	static val DEFAULT_CLASS_NAME = "TestClass"
	static val ADDITIONAL_CLASS_NAME = "AdditionalClass"
	static val DEFAULT_INTERFACE_NAME = "TestInterface"
	static val ADDITIONAL_INTERFACE_NAME = "AdditionalInterface"
	
	private def void assertJavaCompilationUnitCount(int number) {
		createJavaClassesView() => [
			assertEquals(number, rootObjects.size, '''Wrong number of Java compilation units exists: «rootObjects»''')
		]
	}
	
	private def void createRootPackageClass(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createClass => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.getUniqueUmlClassWithName(name)
			val javaClass = getUniqueJavaClassWithName(name)
			assertJavaFileExists(name, #[])
			assertElementsEqual(umlClass, javaClass)	
		]
	}
	
	private def void createRootPackageInterface(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createInterface => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		createUmlAndJavaClassesView() => [
			val umlInterface = uniqueUmlModel.getUniqueUmlInterfaceWithName(name)
			val javaInterface = getUniqueJavaInterfaceWithName(name)
			assertJavaFileExists(name, #[])
			assertElementsEqual(umlInterface, javaInterface)	
		]
	}
	
	private def void createRootPackageDataType(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createDataType => [
				it.name = name
			]
		]
		createUmlAndJavaClassesView() => [
			val umlDataType = uniqueUmlModel.uniqueUmlDataType
			val javaClass = getUniqueJavaClassWithName(umlDataType.name)
			assertJavaFileExists(umlDataType.name, #[])
			assertEquals(name, javaClass.name)	
		]
	}
	
	@Test
	def void testCreateClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		assertJavaCompilationUnitCount(1)
	}
	
	@Test
	def void testDeletedClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.destroy
		]
		createUmlAndJavaClassesView() => [
			assertJavaCompilationUnitCount(0)
			assertTrue(uniqueUmlModel.packagedElements.empty)
			assertTrue(javaClasses.empty)
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])					
		]
	}

	@Test
	def void testChangeClassVisibilityToPrivate() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.visibility = VisibilityKind.PRIVATE_LITERAL
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaModifiableHasVisibility(javaClass, JavaVisibility.PRIVATE)
			assertElementsEqual(umlClass, javaClass)	
		]
	}
	
	@Test
	def void testChangeClassVisibilityToPackage() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.visibility = VisibilityKind.PACKAGE_LITERAL
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaModifiableHasVisibility(javaClass, JavaVisibility.PACKAGE)
			assertElementsEqual(umlClass, javaClass)	
		]
	}

	@Test
	def void testChangeAbstractClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.isAbstract = true
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaModifiableAbstract(javaClass, true)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testRenameClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.name = "RenamedClass"
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			assertJavaFileExists(umlClass.name, #[])
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertElementsEqual(umlClass, javaClass) 
		]
	}
	
	@Test
	def void testMoveClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val umlClass = uniqueUmlClass
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				name = "package"
				packagedElements += umlClass
			]
		]
		createUmlAndJavaClassesView() => [
			val umlPackage = uniqueUmlModel.uniqueUmlPackage
			val umlClass = umlPackage.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaFileExists(umlClass.name, #[umlPackage.name])
			assertJavaFileNotExists(umlClass.name, #[])
			assertElementsEqual(umlClass, javaClass)
			assertEquals(javaClass.containingPackageName.join("."), umlPackage.name)
		]
		changeUmlModel [
			packagedElements += uniqueUmlPackage.uniqueUmlClass
		]
		createUmlAndJavaClassesView() => [
			val umlPackage = uniqueUmlModel.uniqueUmlPackage
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaFileNotExists(umlClass.name, #[umlPackage.name])
			assertJavaFileExists(umlClass.name, #[])
			assertElementsEqual(umlClass, javaClass)
			assertEquals(javaClass.containingPackageName.join("."), "")	
		]
	}

	@Test
	def void testChangeFinalClass() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			uniqueUmlClass.isFinalSpecialization = true
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			assertJavaCompilationUnitCount(1)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertJavaModifiableFinal(javaClass, true)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	@Test
	def void testSuperClassChanged() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val existingClass = uniqueUmlClass
			val superClass = UMLFactory.eINSTANCE.createClass
			packagedElements += superClass => [
				name = ADDITIONAL_CLASS_NAME
			]
			existingClass => [
				generals += superClass
			]
		] 
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.getUniqueUmlClassWithName(DEFAULT_CLASS_NAME)
			val umlSuperClass = uniqueUmlModel.getUniqueUmlClassWithName(ADDITIONAL_CLASS_NAME)
			assertJavaCompilationUnitCount(2)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			val javaSuperClass = getUniqueJavaClassWithName(umlSuperClass.name)
			assertHasSuperClass(javaClass, javaSuperClass)
			assertElementsEqual(umlClass, javaClass)
		]
		changeUmlModel [
			getUniqueUmlClassWithName(DEFAULT_CLASS_NAME) => [
				generals.clear
			]
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.getUniqueUmlClassWithName(DEFAULT_CLASS_NAME)
			assertJavaCompilationUnitCount(2)
			val javaClass = getUniqueJavaClassWithName(umlClass.name)
			assertNull(javaClass.extends)
			assertElementsEqual(umlClass, javaClass)
		]
	}
	
	@Test
	def void testCreateInterface() {
		createRootPackageInterface(DEFAULT_INTERFACE_NAME)
		assertJavaCompilationUnitCount(1)
	}
	
	@Test
	def void testDeletedInterface() {
		createRootPackageInterface(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			uniqueUmlInterface.destroy
		]
		createUmlAndJavaClassesView() => [
			assertJavaCompilationUnitCount(0)
			assertTrue(uniqueUmlModel.packagedElements.empty)
			assertTrue(javaInterfaces.empty)
			assertJavaFileNotExists(DEFAULT_INTERFACE_NAME, #[])					
		]
	}
	
	@Test
	def void testAddClassImplement() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		createRootPackageInterface(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			uniqueUmlClass.createInterfaceRealization("InterfaceRealization", uniqueUmlInterface)
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			val javaClass = getUniqueJavaClassWithName(DEFAULT_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(javaClass.implements.head).name)
			assertElementsEqual(umlClass, javaClass)
		]
	}
	
	@Test
	def void testDeleteClassImplement() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		createRootPackageInterface(DEFAULT_INTERFACE_NAME)
		createRootPackageInterface(ADDITIONAL_INTERFACE_NAME)
		changeUmlModel [
			uniqueUmlClass.createInterfaceRealization("InterfaceRealization", getUniqueUmlInterfaceWithName(DEFAULT_INTERFACE_NAME))
			uniqueUmlClass.createInterfaceRealization("InterfaceRealization2", getUniqueUmlInterfaceWithName(ADDITIONAL_INTERFACE_NAME))
		]
		changeUmlModel [
			uniqueUmlClass.interfaceRealizations.remove(0)
		]
		createUmlAndJavaClassesView() => [
			val umlClass = uniqueUmlModel.uniqueUmlClass
			val javaClass = getUniqueJavaClassWithName(DEFAULT_CLASS_NAME)
			assertEquals(1, javaClass.implements.size)
			assertEquals("AdditionalInterface", getClassifierFromTypeReference(javaClass.implements.head).name)
			assertJavaFileExists(DEFAULT_INTERFACE_NAME, #[])
			assertJavaFileExists("AdditionalInterface", #[])
			assertElementsEqual(umlClass, javaClass)
		]
	}
	
	@Test
	def void testChangeInterfaceImplementer() {
		createRootPackageClass(DEFAULT_CLASS_NAME)
		createRootPackageClass(ADDITIONAL_CLASS_NAME)
		createRootPackageInterface(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			getUniqueUmlClassWithName(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization", uniqueUmlInterface)
		]
		createUmlAndJavaClassesView() => [
			val firstJavaClass = getUniqueJavaClassWithName(DEFAULT_CLASS_NAME)
			val secondJavaClass = getUniqueJavaClassWithName(ADDITIONAL_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(firstJavaClass.implements.head).name)
			assertTrue(secondJavaClass.implements.nullOrEmpty)
		]
		changeUmlModel [
			val secondUmlClass = getUniqueUmlClassWithName(ADDITIONAL_CLASS_NAME)
			val realization = getUniqueUmlClassWithName(DEFAULT_CLASS_NAME).interfaceRealizations.claimOne
			realization.implementingClassifier = secondUmlClass
		]
		createUmlAndJavaClassesView() => [
			val firstJavaClass = getUniqueJavaClassWithName(DEFAULT_CLASS_NAME)
			val secondJavaClass = getUniqueJavaClassWithName(ADDITIONAL_CLASS_NAME)
			val firstUmlClass = uniqueUmlModel.getUniqueUmlClassWithName(DEFAULT_CLASS_NAME)
			val secondUmlClass = uniqueUmlModel.getUniqueUmlClassWithName(ADDITIONAL_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(secondJavaClass.implements.head).name)
			assertTrue(firstJavaClass.implements.nullOrEmpty)
			assertElementsEqual(firstUmlClass, firstJavaClass)
			assertElementsEqual(secondUmlClass, secondJavaClass)
		]
	}

	@Test
	def void testCreateDataType() {
		createRootPackageDataType(DEFAULT_CLASS_NAME)
		assertJavaCompilationUnitCount(1)
	}
	
	@Test
	def void testMoveDataType() {
		createRootPackageDataType(DEFAULT_CLASS_NAME)
		changeUmlModel [
			val umlDataType = uniqueUmlDataType
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				name = "package"
				packagedElements += umlDataType
			]
		]
		createUmlAndJavaClassesView() => [
			val umlPackage = uniqueUmlModel.uniqueUmlPackage
			val javaClass = getUniqueJavaClassWithName(DEFAULT_CLASS_NAME)
			assertJavaFileExists(DEFAULT_CLASS_NAME, #[umlPackage.name])
			assertJavaFileNotExists(DEFAULT_CLASS_NAME, #[])
			assertEquals(DEFAULT_CLASS_NAME, javaClass.name)
			assertEquals(javaClass.containingPackageName.join("."), umlPackage.name)
		]
	}
}
