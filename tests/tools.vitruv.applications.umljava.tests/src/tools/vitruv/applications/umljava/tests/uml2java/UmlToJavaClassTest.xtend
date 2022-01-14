package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getJavaVisibilityConstantFromUmlVisibilityKind
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureBidirectionalExecution

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

	@Test
	def void testCreateClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
	}

	@Test
	def void testCreateClassInPackage() {
		createClassInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		assertSingleClassWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		assertNoClassifierWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testDeleteClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).destroy
		]
		assertNoClassifierWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@ParameterizedTest
	@EnumSource(value=VisibilityKind, names=#["PUBLIC_LITERAL"], mode=EnumSource.Mode.EXCLUDE)
	def void testChangeClassVisibility(VisibilityKind visibility) {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).visibility = visibility
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertJavaModifiableHasVisibility(javaClass, getJavaVisibilityConstantFromUmlVisibilityKind(visibility))
		]
	}

	@Test
	def void testChangeAbstractClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).isAbstract = true
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertJavaModifiableAbstract(javaClass, true)
		]
	}

	@Test
	def void testRenameClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).name = RENAMED_CLASS_NAME
		]
		assertSingleClassWithNameInRootPackage(RENAMED_CLASS_NAME)
		assertNoClassifierWithNameInRootPackage(DEFAULT_CLASS_NAME)
	}

	@Test
	def void testMoveClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createPackageInRootPackage(PACKAGE_NAME)
		changeUmlModel [
			claimPackage(PACKAGE_NAME).packagedElements += claimClass(DEFAULT_CLASS_NAME)
		]
		assertSingleClassWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		assertNoClassifierWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
		changeUmlModel [
			packagedElements += claimPackage(PACKAGE_NAME).claimClass(DEFAULT_CLASS_NAME)
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
	}

	@Test
	def void testChangeFinalClass() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).isFinalSpecialization = true
		]
		assertSingleClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertJavaModifiableFinal(javaClass, true)
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
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertClassWithNameInRootPackage(ADDITIONAL_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			val javaSuperClass = claimJavaClass(ADDITIONAL_CLASS_NAME)
			assertHasSuperClass(javaClass, javaSuperClass)
		]
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME) => [
				generals.clear
			]
		]
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertClassWithNameInRootPackage(ADDITIONAL_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertNull(javaClass.extends)
		]
	}

	@Test
	def void testAddClassImplements() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createInterfaceInRootPackage(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization",
				claimInterface(DEFAULT_INTERFACE_NAME))
		]
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(javaClass.implements.head).name)
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
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertInterfaceWithNameInRootPackage(ADDITIONAL_INTERFACE_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			assertEquals(1, javaClass.implements.size)
			assertEquals(ADDITIONAL_INTERFACE_NAME, getClassifierFromTypeReference(javaClass.implements.head).name)
		]
	}

	@Test
	def void testChangeInterfaceImplementer() {
		createClassInRootPackage(DEFAULT_CLASS_NAME)
		createClassInRootPackage(ADDITIONAL_CLASS_NAME)
		createInterfaceInRootPackage(DEFAULT_INTERFACE_NAME)
		changeUmlModel [
			claimClass(DEFAULT_CLASS_NAME).createInterfaceRealization("InterfaceRealization",
				claimInterface(DEFAULT_INTERFACE_NAME))
		]
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertClassWithNameInRootPackage(ADDITIONAL_CLASS_NAME)
		validateJavaView [
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
		assertClassWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertClassWithNameInRootPackage(ADDITIONAL_CLASS_NAME)
		validateJavaView [
			val firstJavaClass = claimJavaClass(DEFAULT_CLASS_NAME)
			val secondJavaClass = claimJavaClass(ADDITIONAL_CLASS_NAME)
			assertEquals(DEFAULT_INTERFACE_NAME, getClassifierFromTypeReference(secondJavaClass.implements.head).name)
			assertTrue(firstJavaClass.implements.nullOrEmpty)
		]
	}

	@Test
	def void testCreateDataType() {
		createDataTypeInRootPackage(DEFAULT_CLASS_NAME)
		assertSingleDataTypeWithNameInRootPackage(DEFAULT_CLASS_NAME)
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
		assertSingleDataTypeWithNameInPackage(PACKAGE_NAME, DEFAULT_CLASS_NAME)
		assertNoClassifierWithNameInRootPackage(DEFAULT_CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	static class BidirectionalTest extends UmlToJavaClassTest {
		override setupTransformationDirection() {
			configureBidirectionalExecution()
		}
	}

}
