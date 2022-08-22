package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.VisibilityKind

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.uml2.uml.UMLFactory

import static extension tools.vitruv.applications.uml.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.params.ParameterizedTest
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureBidirectionalExecution
import static tools.vitruv.applications.umljava.tests.util.JavaUmlElementEqualityValidation.*

/**
 * This test class contains basic test cases for package creation, renaming and deletion.
 */
class UmlToJavaPackageTest extends AbstractUmlToJavaTest {
	static val PACKAGE_NAME = "rootpackage"
	static val PACKAGE_NAME_FIRST_UPPER = "Rootpackage"
	static val NESTED_PACKAGE_NAME = "nestedpackage"
	static val PACKAGE_RENAMED = "rootpackagerenamed"

	@ParameterizedTest
	@ValueSource(strings=#[PACKAGE_NAME, PACKAGE_NAME_FIRST_UPPER])
	def void testCreatePackage(String packageName) {
		createRootPackage(packageName)
		validateUmlAndJavaPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(packageName)
			val javaPackage = claimJavaPackage(packageName)
			assertEquals(1, javaPackages.size, "exactly one Java package should exist")
			assertElementsEqual(umlPackage, javaPackage)
		]
	}

	@ParameterizedTest
	@ValueSource(strings=#[PACKAGE_NAME, PACKAGE_NAME_FIRST_UPPER])
	def void testCreateNestedPackage(String packageName) {
		val nestedPackageName = packageName + "nested"
		createRootPackageWithNestedPackage(packageName, nestedPackageName)
		validateUmlAndJavaPackagesView [
			val umlRootPackage = defaultUmlModel.claimPackage(packageName)
			val umlNestedPackage = umlRootPackage.claimPackage(nestedPackageName)
			val javaRootPackage = claimJavaPackage(packageName)
			val javaNestedPackage = claimJavaPackage(nestedPackageName)
			assertEquals(packageName, javaNestedPackage.namespaces.join("."))
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
		validateUmlAndJavaPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_RENAMED)
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
		validateUmlAndJavaPackagesView [
			val umlRootPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
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
		validateUmlAndJavaPackagesView [
			assertThat("there should be no element in the UML model", defaultUmlModel.packagedElements, is(emptyList))
			assertThat("there should be no Java packages", javaPackages, is(emptyList))
		]
	}

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

	static class BidirectionalTest extends UmlToJavaPackageTest {
		override setupTransformationDirection() {
			configureBidirectionalExecution(virtualModel)
		}
	}

}
