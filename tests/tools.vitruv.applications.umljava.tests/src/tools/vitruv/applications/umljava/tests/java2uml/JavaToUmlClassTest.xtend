package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import java.nio.file.Path

/**
 * A Test class to test classes and their traits.
 * 
 * @author Fei
 */
class JavaToUmlClassTest extends JavaToUmlTransformationTest {
	static val CLASS_NAME = "ClassName"
	static val STANDARD_CLASS_NAME = "StandardClassName"
	static val CLASS_RENAMED = "ClassRenamed"
	static val SUPER_CLASS_NAME = "SuperClassName"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_NAME2 = "InterfaceName2"

	var org.emftext.language.java.classifiers.Class jClass

	@BeforeEach
	def void before() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
	}

	/**
	 * Tests if a corresponding java class is created when an uml class is created.
	 */
	@Test
	def void testCreateClass() {
		val cls = createSimpleJavaClassWithCompilationUnit(STANDARD_CLASS_NAME)

		val uClass = getCorrespondingClass(cls)
		assertUmlClassTraits(uClass, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false,
			registeredUmlModel)
		assertClassEquals(uClass, cls)
	}

	/**
	 * Tests if renaming a java class also renames the corresponding uml class.
	 */
	@Test
	def testRenameClass() {
		jClass.name = CLASS_RENAMED
		propagate

		val uClass = getCorrespondingClass(jClass)
		assertEquals(CLASS_RENAMED, uClass.name)
		assertClassEquals(uClass, jClass)
	}

	/**
	 * Tests if deleting a java class also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	def testDeleteClass() {
		assertNotNull(getCorrespondingClass(jClass))
		jClass.containingCompilationUnit

		EcoreUtil.delete(jClass)
		propagate

		val uClass = getUmlPackagedElementsbyName(Class, CLASS_NAME).head
		assertNull(uClass)
	}

	/**
	 * Tests if deleting a java compilation unit also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	def testDeleteCompilationUnit() {
		val compUnitFilePath = buildJavaFilePath(jClass.containingCompilationUnit)
		assertNotNull(getCorrespondingClass(jClass))
		resourceAt(Path.of(compUnitFilePath)).propagate [
			delete(null)
		]

		assertTrue(getUmlPackagedElementsbyName(Class, CLASS_NAME).nullOrEmpty)
	}

	/**
	 * Checks if visibility changes are propagated to the uml class.
	 */
	@Test
	def testChangeClassVisibility() {
		jClass.makeProtected
		propagate

		var uClass = getCorrespondingClass(jClass)
		assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PROTECTED_LITERAL)
		assertClassEquals(uClass, jClass)

		jClass.makePrivate
		propagate

		uClass = getCorrespondingClass(jClass)
		assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PRIVATE_LITERAL)
		assertClassEquals(uClass, jClass)
	}

	/**
	 * Tests the change of the abstract value in uml.
	 */
	@Test
	def testChangeAbstractClass() {
		jClass.abstract = true
		propagate

		var uClass = getCorrespondingClass(jClass)
		assertTrue(uClass.abstract)
		assertClassEquals(uClass, jClass)

		jClass.abstract = false
		propagate

		uClass = getCorrespondingClass(jClass)
		assertFalse(uClass.abstract)
		assertClassEquals(uClass, jClass)
	}

	/**
	 * Checks if the changing the final value in the java class
	 * causes the correct change in the uml class.
	 */
	@Test
	def testChangeFinalClass() {
		jClass.final = true
		propagate

		var uClass = getCorrespondingClass(jClass)
		assertTrue(uClass.finalSpecialization)
		assertClassEquals(uClass, jClass)

		jClass.final = false
		propagate

		uClass = getCorrespondingClass(jClass)
		assertFalse(uClass.finalSpecialization)
		assertClassEquals(uClass, jClass)
	}

	/**
	 * Tests if add a super class is correctly reflected on the uml side.
	 */
	@Test
	def testSuperClassChanged() {
		val superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME)
		jClass.extends = createNamespaceReferenceFromClassifier(superClass)
		propagate

		val uClass = getCorrespondingClass(jClass)
		val uSuperClass = getCorrespondingClass(superClass)
		assertUmlClassifierHasSuperClassifier(uClass, uSuperClass)
		assertClassEquals(uClass, jClass)

	}

	/**
	 * Tests if removing a super class is reflected on the uml side.
	 */
	@Test
	def testRemoveSuperClass() {
		val superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME)
		jClass.extends = createNamespaceReferenceFromClassifier(superClass)
		propagate

		var uClass = getCorrespondingClass(jClass)
		val uSuperClass = getCorrespondingClass(superClass)
		assertUmlClassifierHasSuperClassifier(uClass, uSuperClass)

		EcoreUtil.delete(jClass.extends)
		propagate
		uClass = getCorrespondingClass(jClass)
		assertUmlClassifierDontHaveSuperClassifier(uClass, uSuperClass)
	}

	/**
	 * Check the creation of an interface implementation on the uml side.
	 * The test does currently NOT work, due to an error in the EMF monitor:
	 * 	 The contract of an InterfaceImplementation is also represented in the supplier feature 
	 *   and automatically added to that feature. So one change description for setting the contract
	 *   and one for adding the supplier are generated by our monitor. When rolling them back, the supplier is
	 *   first removed by the second change description, which results in an also removed
	 *   contract (due to their dependency). When rolling back the second change description
	 *   for the contract, nothing happens. Reversing this procedure results in a missing
	 *   contract, because the change description lost the information as the supplier removal
	 *   removed the contract as well
	 */
	@Test
	def testAddClassImplement() {
		val implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
		jClass.implements += createNamespaceReferenceFromClassifier(implInterface)
		propagate

		val uClass = getCorrespondingClass(jClass)
		val uInterface = getCorrespondingInterface(implInterface)
		assertUmlClassHasImplement(uClass, uInterface)
		assertClassEquals(uClass, jClass)
	}

	/**
	 * Tests if Removing an implementation relation is correctly reflected on the java side.
	 * The test does currently NOT work, due to an error in the EMF monitor:
	 * 	 The contract of an InterfaceImplementation is also represented in the supplier feature 
	 *   and automatically added to that feature. So one change description for setting the contract
	 *   and one for adding the supplier are generated by our monitor. When rolling them back, the supplier is
	 *   first removed by the second change description, which results in an also removed
	 *   contract (due to their dependency). When rolling back the second change description
	 *   for the contract, nothing happens. Reversing this procedure results in a missing
	 *   contract, because the change description lost the information as the supplier removal
	 *   removed the contract as well
	 */
	@Test
	def testRemoveClassImplement() {
		val implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
		val implInterface2 = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME2)
		jClass.implements += createNamespaceReferenceFromClassifier(implInterface)
		jClass.implements += createNamespaceReferenceFromClassifier(implInterface2)
		propagate

		var uClass = getCorrespondingClass(jClass)
		var uInterface = getCorrespondingInterface(implInterface)
		var uInterface2 = getCorrespondingInterface(implInterface2)
		assertUmlClassHasImplement(uClass, uInterface)
		assertUmlClassHasImplement(uClass, uInterface2)

		jClass.implements.remove(0)
		propagate

		uClass = getCorrespondingClass(jClass)
		assertUmlClassDontHaveImplement(uClass, uInterface)
		assertUmlClassHasImplement(uClass, uInterface2)
	}

}
