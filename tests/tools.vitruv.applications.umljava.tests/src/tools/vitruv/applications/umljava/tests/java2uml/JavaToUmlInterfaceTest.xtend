package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Interface
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * A Test class for interface tests. Checks their creation, renaming, deleting and the 
 * change of their super interfaces.
 * 
 * @author Fei
 */
class JavaToUmlInterfaceTest extends JavaToUmlTransformationTest {
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_RENAME = "InterfaceRename"
	static val STANDARD_INTERFACE_NAME = "StandardInterfaceName"
	static val SUPERINTERFACENAME_1 = "SuperInterfaceOne"
	static val SUPERINTERFACENAME_2 = "SuperInterfaceTwo"

	var Interface jInterface

	@BeforeEach
	def void before() {
		jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
	}

	@Test
	def void testCreateInterface() {
		val jInterface = createSimpleJavaInterfaceWithCompilationUnit(STANDARD_INTERFACE_NAME)

		val uInterface = getCorrespondingInterface(jInterface)
		assertUmlInterfaceTraits(uInterface, STANDARD_INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, registeredUmlModel)
		assertInterfaceEquals(uInterface, jInterface)
	}

	@Test
	def void testRenameInterface() {
		jInterface.name = INTERFACE_RENAME
		propagate

		val uInterface = getCorrespondingInterface(jInterface)
		assertEquals(INTERFACE_RENAME, uInterface.name)
		assertInterfaceEquals(uInterface, jInterface)
	}

	@Test
	def void testDeleteInterface() {
		jInterface.containingCompilationUnit
		EcoreUtil.delete(jInterface)
		propagate
		assertTrue(getUmlPackagedElementsbyName(org.eclipse.uml2.uml.Interface, INTERFACE_NAME).nullOrEmpty)
	}

	@Test
	def testAddSuperInterface() {
		val superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1)
		jInterface.extends += createNamespaceReferenceFromClassifier(superInterface)
		propagate

		val uInterface = getCorrespondingInterface(jInterface)
		val uSuperInterface = getCorrespondingInterface(superInterface)
		assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface)
		assertInterfaceEquals(uInterface, jInterface)
	}

	@Test
	def testRemoveSuperInterface() {
		val superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1)
		val superInterface2 = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_2)
		jInterface.extends += createNamespaceReferenceFromClassifier(superInterface)
		jInterface.extends += createNamespaceReferenceFromClassifier(superInterface2)
		propagate

		jInterface.extends.remove(0)
		propagate

		val uInterface = getCorrespondingInterface(jInterface)
		val uSuperInterface = getCorrespondingInterface(superInterface)
		val uSuperInterface2 = getCorrespondingInterface(superInterface2)
		assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface2)
		assertUmlClassifierDontHaveSuperClassifier(uInterface, uSuperInterface)
		assertInterfaceEquals(uInterface, jInterface)
	}

}
