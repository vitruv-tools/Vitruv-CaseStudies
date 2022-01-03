package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.uml2.uml.UMLFactory

import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat

/**
 * This test class contains basic test cases for package creation, renaming and deletion.
 */
class UmlToJavaPackageTest extends AbstractUmlToJavaTest {
	static val PACKAGE_NAME = "rootpackage"
	static val NESTED_PACKAGE_NAME = "nestedpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	def void createRootPackage(String packageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}

	def void createRootPackageWithNestedPackage(String rootPackageName, String nestedPackageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = rootPackageName
				packagedElements += UMLFactory.eINSTANCE.createPackage => [
					it.name = nestedPackageName
					it.visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]
		]
	}

	@Test
	def void testCreatePackage() {
		createRootPackage(PACKAGE_NAME)
		createUmlAndJavaPackagesView() => [
			val umlPackage = claimUniqueUmlModel.claimPackage(PACKAGE_NAME)
			val javaPackage = claimJavaPackage(PACKAGE_NAME)
			assertEquals(1, javaPackages.size, "exactly one Java package should exist")
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@Test
	def void testCreateUppercasePackage() {
		createRootPackage(PACKAGE_NAME.toFirstUpper)
		createUmlAndJavaPackagesView() => [
			val umlPackage = claimUniqueUmlModel.claimPackage(PACKAGE_NAME.toFirstUpper)
			val javaPackage = claimJavaPackage(PACKAGE_NAME)
			assertEquals(1, javaPackages.size, "exactly one Java package should exist")
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@Test
	def void testCreateNestedPackage() {
		createRootPackageWithNestedPackage(PACKAGE_NAME, NESTED_PACKAGE_NAME)
		createUmlAndJavaPackagesView() => [
			val umlRootPackage = claimUniqueUmlModel.claimPackage(PACKAGE_NAME)
			val umlNestedPackage = umlRootPackage.claimPackage(NESTED_PACKAGE_NAME)
			val javaRootPackage = claimJavaPackage(PACKAGE_NAME)
			val javaNestedPackage = claimJavaPackage(NESTED_PACKAGE_NAME)
			assertEquals(PACKAGE_NAME, javaNestedPackage.namespaces.join("."))
			assertEquals(2, javaPackages.size, "exactly two Java packages should exist")
			assertElementsEqual(umlRootPackage, javaRootPackage)
			assertElementsEqual(umlNestedPackage, javaNestedPackage)
		]
	}

	@Test
	def void testCreateNestedUppercasePackage() {
		createRootPackageWithNestedPackage(PACKAGE_NAME.toFirstUpper, NESTED_PACKAGE_NAME.toFirstUpper)
		createUmlAndJavaPackagesView() => [
			val umlRootPackage = claimUniqueUmlModel.claimPackage(PACKAGE_NAME.toFirstUpper)
			val umlNestedPackage = umlRootPackage.claimPackage(NESTED_PACKAGE_NAME.toFirstUpper)
			val javaRootPackage = claimJavaPackage(PACKAGE_NAME)
			val javaNestedPackage = claimJavaPackage(NESTED_PACKAGE_NAME)
			assertEquals(PACKAGE_NAME, javaNestedPackage.namespaces.join("."))
			assertEquals(2, javaPackages.size, "exactly two Java packages should exist")
			assertElementsEqual(umlRootPackage, javaRootPackage)
			assertElementsEqual(umlNestedPackage, javaNestedPackage)
		]
	}

	@Test
	def void testRenamePackage() {
		createRootPackage(PACKAGE_NAME)
		changeUmlModel [
			claimPackage(PACKAGE_NAME) => [
				name = PACKAGE_RENAMED
			]
		]
		createUmlAndJavaPackagesView() => [
			val umlPackage = claimUniqueUmlModel.claimPackage(PACKAGE_RENAMED)
			val javaPackage = claimJavaPackage(PACKAGE_RENAMED)
			assertEquals(1, javaPackages.size, "exactly one Java package should exist")
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@Test
	def void testMovePackage() {
		createRootPackage(PACKAGE_NAME)
		createRootPackage(NESTED_PACKAGE_NAME)
		changeUmlModel[
			val toBeNestedPackage = claimPackage(NESTED_PACKAGE_NAME)
			claimPackage(PACKAGE_NAME) => [
				packagedElements += toBeNestedPackage
			]
		]
		createUmlAndJavaPackagesView() => [
			val umlRootPackage = claimUniqueUmlModel.claimPackage(PACKAGE_NAME)
			val umlNestedPackage = umlRootPackage.claimPackage(NESTED_PACKAGE_NAME)
			val javaRootPackage = claimJavaPackage(PACKAGE_NAME)
			val javaNestedPackage = claimJavaPackage(NESTED_PACKAGE_NAME)
			assertEquals(PACKAGE_NAME, javaNestedPackage.namespaces.join("."))
			assertEquals(2, javaPackages.size, "exactly two Java packages should exist")
			assertElementsEqual(umlRootPackage, javaRootPackage)
			assertElementsEqual(umlNestedPackage, javaNestedPackage)
		]
	}

	@Test
	def void testDeletePackage() {
		createRootPackage(PACKAGE_NAME)
		changeUmlModel[
			claimPackage(PACKAGE_NAME).destroy
		]
		createUmlAndJavaPackagesView() => [
			assertThat("there should be no element in the UML model", claimUniqueUmlModel.packagedElements,
				is(emptyList))
			assertThat("there should be no Java packages", javaPackages, is(emptyList))
		]
	}

}
