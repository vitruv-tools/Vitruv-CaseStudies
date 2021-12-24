package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*

/**
 * A Test class to test classes and their traits.
 */
class JavaToUmlClassTest extends AbstractJavaToUmlTest {
	static val CLASS_NAME = "ClassName"
	static val CLASS_RENAMED = "ClassRenamed"
	static val SUPER_CLASS_NAME = "SuperClassName"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_NAME2 = "InterfaceName2"
	static val DEFAULT_UML_MODEL_NAME = "model"
	
	private def assertSingleClassWithName(String name) {
		assertSingleClassWithName(name, name)
	}
	
	private def assertSingleClassWithName(String name, String compilationUnitName) {
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(name)
			val javaCompilationUnit = getUniqueJavaCompilationUnitWithName(compilationUnitName)
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(name)
			assertThat("only one element in UML model is expected to exist", umlModel.packagedElements.toSet, is(#{umlClass}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.toSet, is(#{javaCompilationUnit}))
			assertThat("only one Java class is expected to exist", javaClasses.toSet, is(#{javaClass}))
			assertClassEquals(umlClass, javaClass)
		]
	}
	
	private def assertNoClassExists() {
		createUmlView => [
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			assertThat("no element in UML model is expected to exist", umlModel.packagedElements.toSet, is(emptySet))
			assertThat("no Java class is expected to exist", javaClasses.toSet, is(emptySet))
			assertThat("no Java compilation unit is expected to exist", javaCompilationUnits.toSet, is(emptySet))
		]
	}
	
	/**
	 * Tests if a corresponding Java class is created when a UML class is created.
	 */
	@Test
	def void testCreateClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlClassTraits(umlClass, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, umlModel)
		]
	}

	/**
	 * Tests if renaming a java class also renames the corresponding uml class.
	 */
	@Test
	def void testRenameClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				name = CLASS_RENAMED
			]
		]
		assertSingleClassWithName(CLASS_RENAMED, CLASS_NAME)
		createUmlView => [
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_RENAMED)
			assertUmlClassTraits(umlClass, CLASS_RENAMED, VisibilityKind.PUBLIC_LITERAL, false, false, umlModel)
		]
	}

	/**
	 * Tests if deleting a java class also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	def void testDeleteClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaClassWithName(CLASS_NAME))
		]
		assertNoClassExists()
	}

	/**
	 * Tests if deleting a java compilation unit also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	def void testDeleteCompilationUnit() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaCompilationUnitWithName(CLASS_NAME))
		]
		assertNoClassExists()
	}

	/**
	 * Checks if visibility changes are propagated to the uml class.
	 */
	@Test
	def void testChangeClassVisibility() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				makeProtected
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlNamedElementHasVisibility(umlClass, VisibilityKind.PROTECTED_LITERAL)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				makePrivate
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlNamedElementHasVisibility(umlClass, VisibilityKind.PRIVATE_LITERAL)
		]
	}

	/**
	 * Tests the change of the abstract value in uml.
	 */
	@Test
	def void testChangeAbstractClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				abstract = true
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must be abstract", umlClass.abstract, is(true))
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				abstract = false
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must not be abstract", umlClass.abstract, is(false))
		]
	}

	/**
	 * Checks if the changing the final value in the java class
	 * causes the correct change in the uml class.
	 */
	@Test
	def void testChangeFinalClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				final = true
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must be final", umlClass.finalSpecialization, is(true))
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				final = false
			]
		]
		assertSingleClassWithName(CLASS_NAME)
		createUmlView => [
			val umlClass = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME).getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must not be final", umlClass.finalSpecialization, is(false))
		]
	}

	/**
	 * Tests if add a super class is correctly reflected on the uml side.
	 */
	@Test
	def void testSuperClassChanged() {
		createJavaClassInRootPackage(CLASS_NAME)
		createJavaClassInRootPackage(SUPER_CLASS_NAME)
		changeView(createJavaClassesView) [
			val superClass = getUniqueJavaClassWithName(SUPER_CLASS_NAME) 
			getUniqueJavaClassWithName(CLASS_NAME) => [
				extends = createNamespaceClassifierReference(superClass)
			]
		]
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(CLASS_NAME)
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlSuperClass = umlModel.getUniqueUmlClassWithName(SUPER_CLASS_NAME)
			assertUmlClassifierHasSuperClassifier(umlClass, umlSuperClass)
			assertClassEquals(umlClass, javaClass)
		]
	}

	/**
	 * Tests if removing a super class is reflected on the uml side.
	 */
	@Test
	def void testRemoveSuperClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		createJavaClassInRootPackage(SUPER_CLASS_NAME)
		changeView(createJavaClassesView) [
			val superClass = getUniqueJavaClassWithName(SUPER_CLASS_NAME) 
			getUniqueJavaClassWithName(CLASS_NAME) => [
				extends = createNamespaceClassifierReference(superClass)
			]
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				EcoreUtil.delete(extends)
			]
		]
		createUmlView => [
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlSuperClass = umlModel.getUniqueUmlClassWithName(SUPER_CLASS_NAME)
			assertUmlClassifierDontHaveSuperClassifier(umlClass, umlSuperClass)
		]
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
	def void testAddClassImplement() {
		createJavaClassInRootPackage(CLASS_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			val javaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME) 
			getUniqueJavaClassWithName(CLASS_NAME) => [
				implements += createNamespaceClassifierReference(javaInterface)
			]
		]
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(CLASS_NAME)
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlInterface = umlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			assertUmlClassHasImplement(umlClass, umlInterface)
			assertClassEquals(umlClass, javaClass)
		]
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
	def void testRemoveClassImplement() {
		createJavaClassInRootPackage(CLASS_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME2)
		changeView(createJavaClassesView) [
			val firstJavaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME) 
			val secondJavaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME2)
			getUniqueJavaClassWithName(CLASS_NAME) => [
				implements += createNamespaceClassifierReference(firstJavaInterface)
				implements += createNamespaceClassifierReference(secondJavaInterface)
			]
		]
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(CLASS_NAME)
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlFirstInterface = umlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSecondInterface = umlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME2)
			assertUmlClassHasImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
			assertClassEquals(umlClass, javaClass)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				implements.remove(0)
			]
		]
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(CLASS_NAME)
			val umlModel = getUniqueUmlModelWithName(DEFAULT_UML_MODEL_NAME)
			val umlClass = umlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlFirstInterface = umlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSecondInterface = umlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME2)
			assertUmlClassDontHaveImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
			assertClassEquals(umlClass, javaClass)
		]
	}

}
