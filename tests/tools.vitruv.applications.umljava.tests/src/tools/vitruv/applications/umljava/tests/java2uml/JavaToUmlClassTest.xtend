package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
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
	static val PACKAGE_NAME = "packagename"
	static val CLASS_NAME = "ClassName"
	static val CLASS_RENAMED = "ClassRenamed"
	static val SUPER_CLASS_NAME = "SuperClassName"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_NAME2 = "InterfaceName2"

	private def assertSingleClassWithNameInRootPackage(String name) {
		assertSingleClassWithNameInRootPackage(name, name)
	}

	private def assertSingleClassWithNameInRootPackage(String name, String compilationUnitName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class, name, compilationUnitName)
	}

	private def assertSingleClassWithNameInPackage(String packageName, String name) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class, packageName, name)
	}

	/**
	 * Tests if a corresponding Java class is created when a UML class is created.
	 */
	@Test
	def void testCreateClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlClassTraits(umlClass, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false,
				uniqueDefaultUmlModel)
		]
	}

	/**
	 * Tests if a corresponding Java class is created when a UML class is created within a package.
	 */
	@Test
	def void testCreateClassInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaClassInPackage(#[PACKAGE_NAME], CLASS_NAME)
		assertSingleClassWithNameInPackage(PACKAGE_NAME, CLASS_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlClass = umlPackage.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlClassTraits(umlClass, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, umlPackage)
		]
	}

	/**
	 * Tests if renaming a java class also renames the corresponding UML class.
	 */
	@Test
	def void testRenameClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				name = CLASS_RENAMED
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_RENAMED, CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_RENAMED)
			assertUmlClassTraits(umlClass, CLASS_RENAMED, VisibilityKind.PUBLIC_LITERAL, false, false,
				uniqueDefaultUmlModel)
		]
	}

	/**
	 * Tests if moving a Java class leads to the movement of the corresponding UML class.
	 */
	@Test
	def void testMoveClass() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			moveJavaRootElement(getUniqueJavaCompilationUnitWithName(CLASS_NAME) => [
				namespaces += PACKAGE_NAME
			])
		]
		assertSingleClassWithNameInPackage(PACKAGE_NAME, CLASS_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlClass = umlPackage.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlClassTraits(umlClass, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, umlPackage)
		]
	}

	/**
	 * Tests if deleting a Java class also cause the deleting of the corresponding
	 * UML class.
	 */
	@Test
	def void testDeleteClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaClassWithName(CLASS_NAME))
		]
		assertNoClassifierExistsInRootPackage()
	}

	/**
	 * Tests if deleting a Java compilation unit also cause the deleting of the corresponding
	 * UML class.
	 */
	@Test
	def void testDeleteCompilationUnit() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaCompilationUnitWithName(CLASS_NAME))
		]
		assertNoClassifierExistsInRootPackage()
	}

	/**
	 * Checks if visibility changes are propagated to the UML class.
	 */
	@Test
	def void testChangeClassVisibility() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				makeProtected
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlNamedElementHasVisibility(umlClass, VisibilityKind.PROTECTED_LITERAL)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				makePrivate
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertUmlNamedElementHasVisibility(umlClass, VisibilityKind.PRIVATE_LITERAL)
		]
	}

	/**
	 * Tests the change of the abstract value in UML.
	 */
	@Test
	def void testChangeAbstractClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				abstract = true
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must be abstract", umlClass.abstract, is(true))
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				abstract = false
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must not be abstract", umlClass.abstract, is(false))
		]
	}

	/**
	 * Checks if the changing the final value in the Java class
	 * causes the correct change in the Uml class.
	 */
	@Test
	def void testChangeFinalClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				final = true
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must be final", umlClass.finalSpecialization, is(true))
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				final = false
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			assertThat("UML class must not be final", umlClass.finalSpecialization, is(false))
		]
	}

	/**
	 * Tests if add a super class is correctly reflected on the UML side.
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
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlSuperClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(SUPER_CLASS_NAME)
			assertUmlClassifierHasSuperClassifier(umlClass, umlSuperClass)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	/**
	 * Tests if removing a super class is reflected on the UML side.
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
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlSuperClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(SUPER_CLASS_NAME)
			assertUmlClassifierDontHaveSuperClassifier(umlClass, umlSuperClass)
		]
	}

	/**
	 * Check the creation of an interface implementation on the UML side.
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
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			assertUmlClassHasImplement(umlClass, umlInterface)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	/**
	 * Tests if removing an implementation relation is correctly reflected on the UML side.
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
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlFirstInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSecondInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME2)
			assertUmlClassHasImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
			assertElementsEqual(umlClass, javaClass)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				implements.remove(0)
			]
		]
		createUmlAndJavaClassesView => [
			val javaClass = getUniqueJavaClassWithName(CLASS_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlFirstInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSecondInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME2)
			assertUmlClassDontHaveImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
			assertElementsEqual(umlClass, javaClass)
		]
	}

}
