package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test


import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import static extension tools.vitruv.applications.uml.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureBidirectionalExecution
import static tools.vitruv.applications.umljava.tests.util.UmlElementsTestAssertions.*

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

	@Test
	def void testCreateInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PACKAGE_LITERAL, defaultUmlModel)
		]
	}

	@Test
	def void testCreateInterfaceInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInPackage(#[PACKAGE_NAME], INTERFACE_NAME)
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
		validateUmlView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlInterface = umlPackage.claimInterface(INTERFACE_NAME)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_NAME, VisibilityKind.PACKAGE_LITERAL, umlPackage)
		]
	}

	@Test
	def void testRenameInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeJavaView [
			claimJavaInterface(INTERFACE_NAME) => [
				changeNameWithCompilationUnit(INTERFACE_RENAMED)
			]
			moveJavaRootElement(claimJavaCompilationUnit(INTERFACE_RENAMED))
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_RENAMED)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_RENAMED)
			assertUmlInterfaceTraits(umlInterface, INTERFACE_RENAMED, VisibilityKind.PACKAGE_LITERAL, defaultUmlModel)
		]
	}

	@Test
	def void testMoveInterface() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeJavaView [
			moveJavaRootElement(claimJavaCompilationUnit(INTERFACE_NAME) => [
				namespaces += PACKAGE_NAME
				updateCompilationUnitName(INTERFACE_NAME)
			])
		]
		assertSingleInterfaceWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
		changeJavaView [
			moveJavaRootElement(claimJavaCompilationUnit(PACKAGE_NAME + "." + INTERFACE_NAME) => [
				namespaces.clear
				updateCompilationUnitName(INTERFACE_NAME)
			])
		]
		assertSingleInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, INTERFACE_NAME)
	}

	@Test
	def void testDeleteInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeJavaView [
			EcoreUtil.delete(claimJavaInterface(INTERFACE_NAME))
		]
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testDeleteCompilationUnit() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		changeJavaView [
			EcoreUtil.delete(claimJavaCompilationUnit(INTERFACE_NAME))
		]
		assertNoClassifierWithNameInRootPackage(INTERFACE_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		changeJavaView [
			val superInterface = claimJavaInterface(SUPER_INTERFACE1_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface)
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE1_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface)
		]
	}

	@Test
	def void testRemoveSuperInterface() {
		createJavaInterfaceInRootPackage(INTERFACE_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE1_NAME)
		createJavaInterfaceInRootPackage(SUPER_INTERFACE2_NAME)
		changeJavaView [
			val superInterface1 = claimJavaInterface(SUPER_INTERFACE1_NAME)
			val superInterface2 = claimJavaInterface(SUPER_INTERFACE2_NAME)
			claimJavaInterface(INTERFACE_NAME) => [
				extends += createNamespaceClassifierReference(superInterface1)
				extends += createNamespaceClassifierReference(superInterface2)
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE1_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE2_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface1 = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE2_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface1)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
		]
		changeJavaView [
			claimJavaInterface(INTERFACE_NAME) => [
				extends.remove(0)
			]
		]
		assertInterfaceWithNameInRootPackage(INTERFACE_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE1_NAME)
		assertInterfaceWithNameInRootPackage(SUPER_INTERFACE2_NAME)
		validateUmlView [
			val umlInterface = defaultUmlModel.claimInterface(INTERFACE_NAME)
			val umlSuperInterface1 = defaultUmlModel.claimInterface(SUPER_INTERFACE1_NAME)
			val umlSuperInterface2 = defaultUmlModel.claimInterface(SUPER_INTERFACE2_NAME)
			assertUmlClassifierHasSuperClassifier(umlInterface, umlSuperInterface2)
			assertUmlClassifierDontHaveSuperClassifier(umlInterface, umlSuperInterface1)
		]
	}

	static class BidirectionalTest extends JavaToUmlInterfaceTest {
		override setupTransformationDirection() {
			configureBidirectionalExecution(virtualModel)
		}
	}

}
