package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getUmlVisibilityKindFromJavaVisibilityConstant

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

	/**
	 * Tests if a corresponding Java class is created when a UML class is created.
	 */
	@Test
	def void testCreateClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			assertUmlClassTraits(umlClass, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, defaultUmlModel)
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
		assertNoClassifierWithNameInRootPackage(CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
		createUmlView => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlClass = umlPackage.claimClass(CLASS_NAME)
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
			claimJavaClass(CLASS_NAME) => [
				changeNameWithCompilationUnit(CLASS_RENAMED)
			]
			moveJavaRootElement(claimJavaCompilationUnit(CLASS_RENAMED))
		]
		assertSingleClassWithNameInRootPackage(CLASS_RENAMED)
		assertNoClassifierWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_RENAMED)
			assertUmlClassTraits(umlClass, CLASS_RENAMED, VisibilityKind.PUBLIC_LITERAL, false, false, defaultUmlModel)
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
			moveJavaRootElement(claimJavaCompilationUnit(CLASS_NAME) => [
				namespaces += PACKAGE_NAME
				updateCompilationUnitName(CLASS_NAME)
			])
		]
		assertSingleClassWithNameInPackage(PACKAGE_NAME, CLASS_NAME)
		assertNoClassifierWithNameInRootPackage(CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
		changeView(createJavaClassesView) [
			moveJavaRootElement(claimJavaCompilationUnit(PACKAGE_NAME + "." + CLASS_NAME) => [
				namespaces.clear
				updateCompilationUnitName(CLASS_NAME)
			])
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, CLASS_NAME)
	}

	/**
	 * Tests if deleting a Java class also cause the deleting of the corresponding
	 * UML class.
	 */
	@Test
	def void testDeleteClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(claimJavaClass(CLASS_NAME))
		]
		assertNoClassifierWithNameInRootPackage(CLASS_NAME)
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
			EcoreUtil.delete(claimJavaCompilationUnit(CLASS_NAME))
		]
		assertNoClassifierWithNameInRootPackage(CLASS_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	private def void changeAndCheckPropertyOfClass(String className,
		(org.emftext.language.java.classifiers.Class)=>void changeJavaClass,
		(org.eclipse.uml2.uml.Class)=>void validateUmlClass) {
		changeView(createJavaClassesView) [
			claimJavaClass(className) => [
				changeJavaClass.apply(it)
			]
		]
		assertSingleClassWithNameInRootPackage(className)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(className)
			validateUmlClass.apply(umlClass)
		]
	}

	/**
	 * Checks if visibility changes are propagated to the UML class.
	 */
	@ParameterizedTest
	@EnumSource(value=JavaVisibility, names=#["PUBLIC"], mode=EnumSource.Mode.EXCLUDE)
	def void testChangeClassVisibility(JavaVisibility visibility) {
		createJavaClassInRootPackage(CLASS_NAME)
		changeAndCheckPropertyOfClass(CLASS_NAME, [javaVisibilityModifier = visibility], [
			assertUmlNamedElementHasVisibility(it, getUmlVisibilityKindFromJavaVisibilityConstant(visibility))
		])
	}

	/**
	 * Tests the change of the abstract value in UML.
	 */
	@Test
	def void testChangeAbstractClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeAndCheckPropertyOfClass(CLASS_NAME, [abstract = true], [
			assertThat("UML class must be abstract", it.abstract, is(true))
		])
		changeAndCheckPropertyOfClass(CLASS_NAME, [abstract = false], [
			assertThat("UML class must not be abstract", it.abstract, is(false))
		])
	}

	/**
	 * Checks if the changing the final value in the Java class
	 * causes the correct change in the Uml class.
	 */
	@Test
	def void testChangeFinalClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeAndCheckPropertyOfClass(CLASS_NAME, [final = true], [
			assertThat("UML class must be final", it.finalSpecialization, is(true))
		])
		changeAndCheckPropertyOfClass(CLASS_NAME, [final = false], [
			assertThat("UML class must not be final", it.finalSpecialization, is(false))
		])
	}

	/**
	 * Tests if add a super class is correctly reflected on the UML side.
	 */
	@Test
	def void testSuperClassChanged() {
		createJavaClassInRootPackage(CLASS_NAME)
		createJavaClassInRootPackage(SUPER_CLASS_NAME)
		changeView(createJavaClassesView) [
			val superClass = claimJavaClass(SUPER_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				extends = createNamespaceClassifierReference(superClass)
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		assertClassWithNameInRootPackage(SUPER_CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlSuperClass = defaultUmlModel.claimClass(SUPER_CLASS_NAME)
			assertUmlClassifierHasSuperClassifier(umlClass, umlSuperClass)
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
			val superClass = claimJavaClass(SUPER_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				extends = createNamespaceClassifierReference(superClass)
			]
		]
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				EcoreUtil.delete(extends)
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlSuperClass = defaultUmlModel.claimClass(SUPER_CLASS_NAME)
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
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			claimJavaClass(CLASS_NAME) => [
				implements += createNamespaceClassifierReference(javaInterface)
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			assertUmlClassHasImplement(umlClass, umlInterface)
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
			val firstJavaInterface = claimJavaInterface(INTERFACE_NAME)
			val secondJavaInterface = claimJavaInterface(INTERFACE_NAME2)
			claimJavaClass(CLASS_NAME) => [
				implements += createNamespaceClassifierReference(firstJavaInterface)
				implements += createNamespaceClassifierReference(secondJavaInterface)
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlFirstInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSecondInterface = defaultUmlModel.claimInterface(INTERFACE_NAME2)
			assertUmlClassHasImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
		]
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				implements.remove(0)
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlFirstInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSecondInterface = defaultUmlModel.claimInterface(INTERFACE_NAME2)
			assertUmlClassDontHaveImplement(umlClass, umlFirstInterface)
			assertUmlClassHasImplement(umlClass, umlSecondInterface)
		]
	}

}
