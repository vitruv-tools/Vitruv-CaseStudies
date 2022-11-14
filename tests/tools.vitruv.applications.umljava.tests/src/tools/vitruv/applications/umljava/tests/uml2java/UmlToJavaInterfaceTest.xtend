package tools.vitruv.applications.umljava.tests.uml2java

import org.junit.jupiter.api.Test
import tools.vitruv.change.propagation.ChangePropagationMode

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.umljava.tests.util.JavaUmlElementEqualityValidation.assertElementsEqual
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * This class contains tests that deal with changes with interfaces.
 * (creating, deleting, renaming,...)
 */
class UmlToJavaInterfaceTest extends AbstractUmlToJavaTest {
	static val PACKAGE_NAME = "rootpackage"
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_RENAME = "InterfaceRename"
	static val SUPER_INTERFACE_NAME_1 = "SuperInterfaceOne"
	static val SUPER_INTERFACE_NAME_2 = "SuperInterfaceTwo"

	@Test
	def void testCreateInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
	}

	@Test
	def void testCreateInterfaceInPackage() {
		createInterfaceInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
	}

	@Test
	def testRenameInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		changeUmlModel[
			claimInterface(INTERFACE_NAME) => [
				name = INTERFACE_RENAME
			]
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_RENAME)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
	}

	@Test
	def testMoveInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		createPackageInRootPackage(PACKAGE_NAME)
		changeUmlModel[
			claimPackage(PACKAGE_NAME).packagedElements += claimInterface(INTERFACE_NAME)
		]
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
		changeUmlModel [
			packagedElements += claimPackage(PACKAGE_NAME).claimInterface(INTERFACE_NAME)
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)

	}

	@Test
	def void testDeletedInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		changeUmlModel [
			claimInterface(INTERFACE_NAME).destroy
		]
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddSuperInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		createInterfaceInRootPackage(SUPER_INTERFACE_NAME_1)
		createInterfaceInRootPackage(SUPER_INTERFACE_NAME_2)
		changeUmlModel [
			val superInterface1 = claimInterface(SUPER_INTERFACE_NAME_1)
			val superInterface2 = claimInterface(SUPER_INTERFACE_NAME_2)
			claimInterface(INTERFACE_NAME) => [
				generals += superInterface1
				generals += superInterface2
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE_NAME_1)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE_NAME_2)
		validateUmlAndJavaClassesView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val umlSuperInterface1 = defaultUmlModel.claimInterface(SUPER_INTERFACE_NAME_1)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE_NAME_2)
			assertThat("there must be two super interfaces", javaInterface.extends.size, is(2))
			assertElementsEqual(umlSuperInterface1, getClassifierFromTypeReference(javaInterface.extends.get(0)))
			assertElementsEqual(umlSuperInterface2, getClassifierFromTypeReference(javaInterface.extends.get(1)))
		]
	}

	@Test
	def void testRemoveSuperInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		createInterfaceInRootPackage(SUPER_INTERFACE_NAME_1)
		createInterfaceInRootPackage(SUPER_INTERFACE_NAME_2)
		changeUmlModel [
			val superInterface1 = claimInterface(SUPER_INTERFACE_NAME_1)
			val superInterface2 = claimInterface(SUPER_INTERFACE_NAME_2)
			claimInterface(INTERFACE_NAME) => [
				generals += superInterface1
				generals += superInterface2
			]
		]
		changeUmlModel [
			claimInterface(INTERFACE_NAME) => [
				generalizations.remove(0)
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE_NAME_1)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE_NAME_2)
		validateUmlAndJavaClassesView [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE_NAME_2)
			assertThat("there must be one super interface", javaInterface.extends.size, is(1))
			assertElementsEqual(umlSuperInterface2, getClassifierFromTypeReference(javaInterface.extends.get(0)))
		]
	}

	static class BidirectionalTest extends UmlToJavaInterfaceTest {
		override protected getChangePropagationMode() {
			ChangePropagationMode.TRANSITIVE_CYCLIC
		}
	}

}
