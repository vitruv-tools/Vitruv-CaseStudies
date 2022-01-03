package tools.vitruv.applications.umljava.tests.java2uml

import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual

import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * This class contains basis tests for Java packages.
 */
class JavaToUmlPackageTest extends AbstractJavaToUmlTest {
	static val PACKAGE_NAME = "packagename"
	static val NESTED_PACKAGE_NAME = "packagenamenested"
	static val PACKAGE_RENAMED = "packagerenamed"

	private def assertSinglePackageWithName(String name) {
		createUmlAndJavaPackagesView => [
			val javaPackage = containedJavaPackage(name)
			val umlPackage = containedDefaultUmlModel.containedPackage(name)
			assertThat("only one element in UML model is expected to exist",
				containedDefaultUmlModel.packagedElements.toSet, is(#{umlPackage}))
			assertThat("only one Java package is expected to exist", containedJavaPackages.toSet, is(#{javaPackage}))
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@Test
	def void testCreatePackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		assertSinglePackageWithName(PACKAGE_NAME)
		createUmlAndJavaPackagesView => [
			val umlPackage = containedDefaultUmlModel.containedPackage(PACKAGE_NAME)
			val javaPackage = containedJavaPackage(PACKAGE_NAME)
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@Test
	def void testRenamePackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		changeView(createJavaPackagesView) [
			containedJavaPackage(PACKAGE_NAME) => [
				name = PACKAGE_RENAMED
			]
		]
		assertSinglePackageWithName(PACKAGE_RENAMED)
	}

	@Test
	def void testDeletePackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		changeView(createJavaPackagesView) [
			EcoreUtil.delete(containedJavaPackage(PACKAGE_NAME))
		]
		createUmlAndJavaPackagesView => [
			assertThat("no element in UML model is expected to exist", containedDefaultUmlModel.packagedElements.toSet,
				is(emptySet))
			assertThat("no Java package is expected to exist", containedJavaPackages.toSet, is(emptySet))
		]
	}

	@Test
	def void testCreateNestedPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaPackage[
			name = NESTED_PACKAGE_NAME
			namespaces += PACKAGE_NAME
		]
		createUmlAndJavaPackagesView => [
			val javaPackage = containedJavaPackage(PACKAGE_NAME)
			val nestedJavaPackage = containedJavaPackage(NESTED_PACKAGE_NAME)
			val umlPackage = containedDefaultUmlModel.containedPackage(PACKAGE_NAME)
			val nestedUmlPackage = umlPackage.containedPackage(NESTED_PACKAGE_NAME)
			assertThat("only one package in UML model are expected to exist",
				containedDefaultUmlModel.packagedElements.toSet, is(#{umlPackage}))
			assertThat("only one nested package in UML package are expected to exist",
				umlPackage.packagedElements.toSet, is(#{nestedUmlPackage}))
			assertThat("two Java packages are expected to exist", containedJavaPackages.toSet,
				is(#{javaPackage, nestedJavaPackage}))
			assertElementsEqual(umlPackage, javaPackage)
			assertElementsEqual(nestedUmlPackage, nestedJavaPackage)
		]
	}

	@Test
	def void testMovePackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaPackageInRootPackage(NESTED_PACKAGE_NAME)
		changeView(createJavaPackagesView) [
			moveJavaRootElement(containedJavaPackage(NESTED_PACKAGE_NAME) => [
				namespaces += PACKAGE_NAME
			])
		]
		createUmlAndJavaPackagesView => [
			val javaPackage = containedJavaPackage(PACKAGE_NAME)
			val nestedJavaPackage = containedJavaPackage(NESTED_PACKAGE_NAME)
			val umlPackage = containedDefaultUmlModel.containedPackage(PACKAGE_NAME)
			val nestedUmlPackage = umlPackage.containedPackage(NESTED_PACKAGE_NAME)
			assertThat("only one package in UML model are expected to exist",
				containedDefaultUmlModel.packagedElements.toSet, is(#{umlPackage}))
			assertThat("only one nested package in UML package are expected to exist",
				umlPackage.packagedElements.toSet, is(#{nestedUmlPackage}))
			assertThat("two Java packages are expected to exist", containedJavaPackages.toSet,
				is(#{javaPackage, nestedJavaPackage}))
			assertElementsEqual(umlPackage, javaPackage)
			assertElementsEqual(nestedUmlPackage, nestedJavaPackage)
		]
	}

}
