package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

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
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, name, compilationUnitName)
	}

	private def assertSingleInterfaceWithNameInPackage(String packageName, String name) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Interface,
			org.eclipse.uml2.uml.Interface, packageName, name)
	}

	@Test
	def void testCreateInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		createUmlView => [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, defaultUmlModel)
		]
	}

	@Test
	def void testCreateInterfaceInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInPackage(#[PACKAGE_NAME], INTERFACE_NAME)
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		createUmlView => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlInterface = umlPackage.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, umlPackage)
		]
	}

	@Test
	def void testRenameInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			claimJavaInterface(INTERFACE_NAME) => [
				name = INTERFACE_RENAMED
			]
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_RENAMED, INTERFACE_NAME)
		createUmlView => [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_RENAMED)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_RENAMED, VisibilityKind.PUBLIC_LITERAL, defaultUmlModel)
		]
	}

	@Test
	def void testMoveInterface() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			moveJavaRootElement(claimJavaCompilationUnit(INTERFACE_NAME) => [
				namespaces += PACKAGE_NAME
				name = PACKAGE_NAME + "." + name
			])
		]
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		createUmlView => [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlInterface = umlPackage.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, umlPackage)
		]
	}

	@Test
	def void testDeleteInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(claimJavaInterface(INTERFACE_NAME))
		]
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testDeleteCompilationUnit() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(claimJavaCompilationUnit(INTERFACE_NAME))
		]
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		changeView(createJavaClassesView) [
			val superInterface = claimJavaInterface(SUPER_INTERFACE1_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			val javaSuperInterface = claimJavaInterface(SUPER_INTERFACE1_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface)
			assertElementsEqual(umlInterface, javaInterface)
			assertElementsEqual(umlSuperInterface, javaSuperInterface)
		]
	}

	@Test
	def void testRemoveSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE2_NAME)
		changeView(createJavaClassesView) [
			val superInterface1 = claimJavaInterface(SUPER_INTERFACE1_NAME)
			val superInterface2 = claimJavaInterface(SUPER_INTERFACE2_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface1)
				extends += createNamespaceClassifierReference(superInterface2)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface1 = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE2_NAME)
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface1)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
			assertElementsEqual(umlInterface, javaInterface)
		]
		changeView(createJavaClassesView) [
			claimJavaInterface(INTERFACE_NAME) => [
				extends.remove(0)
			]
		]
		createUmlAndJavaClassesView => [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface1 = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE2_NAME)
			val javaInterface = claimJavaInterface(INTERFACE_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
			assertUmlClassifierDontHaveSuperClassifier(umlInterface, umlSuperInterface1)
			assertElementsEqual(umlInterface, javaInterface)
		]
	}

}
