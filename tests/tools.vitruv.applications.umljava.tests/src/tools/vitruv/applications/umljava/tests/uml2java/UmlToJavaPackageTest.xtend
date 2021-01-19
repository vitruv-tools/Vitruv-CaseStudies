package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This test class contains basic test cases for package creation, renaming and deletion.
 * 
 * @author Fei
 */
class UmlToJavaPackageTest extends UmlToJavaTransformationTest {
	static val PACKAGE_LEVEL_1 = "level1"
	static val PACKAGE_LEVEL_2 = "level2"
	static val PACKAGE_NAME = "packagename"
	static val PACKAGE_RENAMED = "packagerenamed"
	static val CLASS_NAME = "ClassName"

	var Package uPackageLevel1

	@BeforeEach
	def void before() {
		uPackageLevel1 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_1, rootElement)
		createUmlClassAndAddToPackage(uPackageLevel1, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false)
		propagate

	}

	@Test
	def testCreatePackage() {
		val uPackage = createUmlPackageAndAddToSuperPackage(PACKAGE_NAME, rootElement)
		propagate

		val jPackage = getCorrespondingPackage(uPackage)
		assertEquals(PACKAGE_NAME, jPackage.name)
		assertPackageEquals(uPackage, jPackage)

	}

	@Test
	def testCreateNestedPackage() {
		val uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, uPackageLevel1)
		propagate

		val jPackageLevel2 = getCorrespondingPackage(uPackageLevel2)
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.name)
		assertPackageEquals(uPackageLevel2, jPackageLevel2)

	}

	@Test @Disabled
	def testDeletePackage() { // Delete or Refactoring java packages seems to lead to problems
		var jPackage = getCorrespondingPackage(uPackageLevel1)
		assertNotNull(jPackage)

		uPackageLevel1.destroy
		propagate

		jPackage = getCorrespondingPackage(uPackageLevel1)
		assertNull(jPackage)
	}

	@Test @Disabled
	def testRenamePackage() { // Delete or Refactoring java packages seems to lead to problems
		uPackageLevel1.name = PACKAGE_RENAMED
		propagate

		val jPackage = getCorrespondingPackage(uPackageLevel1)
		assertEquals(PACKAGE_RENAMED, jPackage.name)
		assertPackageEquals(uPackageLevel1, jPackage)
	}
}
