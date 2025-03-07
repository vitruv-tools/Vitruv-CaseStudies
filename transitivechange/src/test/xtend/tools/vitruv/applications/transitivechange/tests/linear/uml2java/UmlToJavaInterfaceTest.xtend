package tools.vitruv.applications.transitivechange.tests.linear.uml2java

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Interface

import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This class contains tests that deal with changes with interfaces.
 * (creating, deleting, renaming,...)
 * @author Fei
 */
class UmlToJavaInterfaceTest extends UmlToJavaTransformationTest {
	static val INTERFACE_NAME = "InterfaceName"
	static val INTERFACE_RENAME = "InterfaceRename"
	static val SUPERINTERFACENAME_1 = "SuperInterfaceOne"
	static val SUPERINTERFACENAME_2 = "SuperInterfaceTwo"
	static val STANDARD_INTERFACE_NAME = "StandardInterfaceName"
	var Interface uInterface

	@BeforeEach
	def void before() {
		uInterface = createSimpleUmlInterface(rootElement, INTERFACE_NAME)
		propagate

	}

	@Test
	def testCreateInterface() {
		val interface = createSimpleUmlInterface(rootElement, STANDARD_INTERFACE_NAME)
		propagate

		assertJavaFileExists(STANDARD_INTERFACE_NAME, #[])
		val jInterface = getCorrespondingInterface(interface)
		assertEquals(STANDARD_INTERFACE_NAME, jInterface.name)
	}

	@Test
	def testRenameInterface() {
		uInterface.name = INTERFACE_RENAME
		propagate

		assertJavaFileExists(INTERFACE_RENAME, #[])
		val jInterface = getCorrespondingInterface(uInterface)
		assertEquals(INTERFACE_RENAME, jInterface.name)
		assertJavaFileNotExists(INTERFACE_NAME, #[])
	}

	@Test
	def testDeleteInterface() {
		uInterface.destroy
		propagate

		assertJavaFileNotExists(INTERFACE_NAME, #[])
	}

	@Test
	def testAddSuperInterface() {
		val interface = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1,
			SUPERINTERFACENAME_2)
		propagate
		val jI = getCorrespondingInterface(interface)
		assertEquals(SUPERINTERFACENAME_1, getClassifierFromTypeReference(jI.extends.get(0)).name)
		assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.extends.get(1)).name)
	}

	@Test
	def testRemoveSuperInterface() {
		val uInterface = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1,
			SUPERINTERFACENAME_2)
		propagate
		uInterface.generalizations.remove(0)
		propagate
		val jI = getCorrespondingInterface(uInterface)
		assertTrue(jI.extends.size == 1, jI.extends.size.toString)
		assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.extends.get(0)).name)
		assertJavaFileExists(SUPERINTERFACENAME_1, #[])
	}

	/**
	 * @return an interface named iName which inherits from two other interfaces named superName1 an superName2
	 */
	private def Interface createInterfaceWithTwoSuperInterfaces(String iName, String superName1, String superName2) {
		val super1 = createSimpleUmlInterface(rootElement, superName1)
		val super2 = createSimpleUmlInterface(rootElement, superName2)
		val EList<Interface> supers = new BasicEList<Interface>
		supers += super1
		supers += super2
		return createUmlInterfaceAndAddToPackage(rootElement, iName, supers)
	}

}
