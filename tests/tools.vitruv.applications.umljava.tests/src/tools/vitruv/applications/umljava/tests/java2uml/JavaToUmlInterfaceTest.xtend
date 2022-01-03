package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*

/**
 * A Test class for interface tests. Checks their creation, renaming, deleting and the 
 * change of their super interfaces.
 */
class JavaToUmlInterfaceTest extends AbstractJavaToUmlTest {
	static val PACKAGE_NAME = "packagename"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_RENAMED = "InterfaceRename"
	static val SUPER_INTERFACE1_NAME = "SuperInterfaceOne"
	static val SUPER_INTERFACE2_NAME = "SuperInterfaceTwo"

	private def assertSingleInterfaceWithNameInRootPackage(String name) {
		assertSingleInterfaceWithNameInRootPackage(name, name)
	}

	private def assertSingleInterfaceWithNameInRootPackage(String name, String compilationUnitName) {
		createUmlAndJavaClassesView => [
			val javaInterface = getUniqueJavaInterfaceWithName(name)
			val javaCompilationUnit = getUniqueJavaCompilationUnitWithName(compilationUnitName)
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(name)
			assertThat("only one element in UML model is expected to exist",
				uniqueDefaultUmlModel.packagedElements.toSet, is(#{umlInterface}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.toSet,
				is(#{javaCompilationUnit}))
			assertThat("only one Java interface is expected to exist", javaInterfaces.toSet, is(#{javaInterface}))
			assertInterfaceEquals(umlInterface, javaInterface)
		]
	}

	private def assertSingleInterfaceWithNameInPackage(String packageName, String name) {
		createUmlAndJavaClassesView => [
			val javaInterface = getUniqueJavaInterfaceWithName(name)
			val javaCompilationUnit = getUniqueJavaCompilationUnitWithName(name)
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(packageName)
			val umlInterface = umlPackage.getUniqueUmlInterfaceWithName(name)
			assertThat("only one element in UML model is expected to exist", umlPackage.packagedElements.toSet,
				is(#{umlInterface}))
			assertThat("only one Java compilation unit is expected to exist", javaCompilationUnits.toSet,
				is(#{javaCompilationUnit}))
			assertThat("only one Java interface is expected to exist", javaInterfaces.toSet, is(#{javaInterface}))
			assertInterfaceEquals(umlInterface, javaInterface)
		]
	}

	private def assertNoInterfaceExists() {
		createUmlView => [
			assertThat("no element in UML model is expected to exist", uniqueDefaultUmlModel.packagedElements.toSet,
				is(emptySet))
			assertThat("no Java interface is expected to exist", javaInterfaces.toSet, is(emptySet))
			assertThat("no Java compilation unit is expected to exist", javaCompilationUnits.toSet, is(emptySet))
		]
	}

	@Test
	def void testCreateInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		createUmlView => [
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, uniqueDefaultUmlModel)
		]
	}

	@Test
	def void testCreateInterfaceInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInPackage(#[PACKAGE_NAME], INTERFACE_NAME)
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlInterface = umlPackage.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, umlPackage)
		]
	}

	@Test
	def void testRenameInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaInterfaceWithName(INTERFACE_NAME) => [
				name = INTERFACE_RENAMED
			]
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_RENAMED, INTERFACE_NAME)
		createUmlView => [
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_RENAMED)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_RENAMED, VisibilityKind.PUBLIC_LITERAL,
				uniqueDefaultUmlModel)
		]
	}

	@Test
	def void testMoveInterface() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			moveJavaRootElement(getUniqueJavaCompilationUnitWithName(INTERFACE_NAME) => [
				namespaces += PACKAGE_NAME
			])
		]
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlInterface = umlPackage.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, umlPackage)
		]
	}

	@Test
	def void testDeleteInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaInterfaceWithName(INTERFACE_NAME))
		]
		assertNoInterfaceExists()
	}

	@Test
	def void testDeleteCompilationUnit() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaCompilationUnitWithName(INTERFACE_NAME))
		]
		assertNoInterfaceExists()
	}

	@Test
	def void testAddSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		changeView(createJavaClassesView) [
			val superInterface = getUniqueJavaInterfaceWithName(SUPER_INTERFACE1_NAME)
			getUniqueJavaInterfaceWithName(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSuperInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(SUPER_INTERFACE1_NAME)
			val javaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME)
			val javaSuperInterface = getUniqueJavaInterfaceWithName(SUPER_INTERFACE1_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface)
			assertInterfaceEquals(umlInterface, javaInterface)
			assertInterfaceEquals(umlSuperInterface, javaSuperInterface)
		]
	}

	@Test
	def void testRemoveSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE2_NAME)
		changeView(createJavaClassesView) [
			val superInterface1 = getUniqueJavaInterfaceWithName(SUPER_INTERFACE1_NAME)
			val superInterface2 = getUniqueJavaInterfaceWithName(SUPER_INTERFACE2_NAME)
			getUniqueJavaInterfaceWithName(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface1)
				extends += createNamespaceClassifierReference(superInterface2)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSuperInterface1 = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(SUPER_INTERFACE2_NAME)
			val javaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface1)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
			assertInterfaceEquals(umlInterface, javaInterface)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaInterfaceWithName(INTERFACE_NAME) => [
				extends.remove(0)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(INTERFACE_NAME)
			val umlSuperInterface1 = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = uniqueDefaultUmlModel.getUniqueUmlInterfaceWithName(SUPER_INTERFACE2_NAME)
			val javaInterface = getUniqueJavaInterfaceWithName(INTERFACE_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
			assertUmlClassifierDontHaveSuperClassifier(umlInterface, umlSuperInterface1)
			assertInterfaceEquals(umlInterface, javaInterface)
		]
	}

}
