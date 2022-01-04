package tools.vitruv.applications.umljava.tests.uml2java

import org.junit.jupiter.api.Test
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is

import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*

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


	private def assertSingleInterfaceWithNameInRootPackage(String interfaceName) {
		assertSingleInterfaceWithNameInRootPackage(interfaceName, interfaceName)
	}

	private def assertSingleInterfaceWithNameInRootPackage(String interfaceName, String compilationUnitName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, interfaceName, compilationUnitName)
	}

	private def assertSingleInterfaceWithNameInPackage(String packageName, String interfaceName) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Interface, org.eclipse.uml2.uml.Interface,
			packageName, interfaceName)
	}

	@Test
	def void testCreateInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertJavaFileExists(INTERFACE_NAME, #[])
		assertJavaCompilationUnitCount(1)
	}
	
	@Test
	def void testCreateInterfaceInPackage() {
		createInterfaceInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertJavaFileExists(INTERFACE_NAME, #[PACKAGE_NAME])
		assertJavaCompilationUnitCount(1)
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
		assertJavaFileExists(INTERFACE_RENAME, #[])
		assertJavaFileNotExists(INTERFACE_NAME, #[])
	}
	
	@Test
	def testMoveInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		createPackageInRootPackage(PACKAGE_NAME)
		changeUmlModel[
			val interf = claimInterface(INTERFACE_NAME)
			claimPackage(PACKAGE_NAME) => [
				packagedElements += interf
			]
		]
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertJavaFileExists(INTERFACE_NAME, #[PACKAGE_NAME])
		assertJavaFileNotExists(INTERFACE_NAME, #[])
	}

	@Test
	def void testDeletedInterface() {
		createInterfaceInRootPackage(INTERFACE_NAME)
		changeUmlModel [
			claimInterface(INTERFACE_NAME).destroy
		]
		assertNoClassifierExistsInRootPackage()
		assertJavaFileNotExists(INTERFACE_NAME, #[])
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
		createUmlAndJavaClassesView => [
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
		assertJavaFileExists(SUPER_INTERFACE_NAME_1, #[])
		createUmlAndJavaClassesView => [
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE_NAME_2)
			assertThat("there must be one super interface", javaInterface.extends.size, is(1)) 
			assertElementsEqual(umlSuperInterface2, getClassifierFromTypeReference(javaInterface.extends.get(0)))
		]
		
	}

}
