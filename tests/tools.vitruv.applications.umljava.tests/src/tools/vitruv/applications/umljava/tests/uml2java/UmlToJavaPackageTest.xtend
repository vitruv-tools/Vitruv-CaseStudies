package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.buildJavaFilePath
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path
import org.emftext.language.java.containers.ContainersFactory

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
	def testCreateUppercasePackage() {
		val uPackage = createUmlPackageAndAddToSuperPackage(PACKAGE_NAME.toFirstUpper, rootElement)
		propagate

		val jPackage = getCorrespondingPackage(uPackage)
		assertEquals(PACKAGE_NAME, jPackage.name)
		assertPackageEquals(uPackage, jPackage)
	}

	@Test
	def testCreateNestedUppercasePackage() {
		uPackageLevel1.name = uPackageLevel1.name.toFirstUpper
		propagate
		
		val uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2.toFirstUpper, uPackageLevel1)
		propagate

		val jPackageLevel1 = getCorrespondingPackage(uPackageLevel1)
		val jPackageLevel2 = getCorrespondingPackage(uPackageLevel2)
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.name)
		assertEquals(#[jPackageLevel1.name], jPackageLevel2.namespaces)
		assertPackageEquals(uPackageLevel2, jPackageLevel2)
	}
	
	@Test
	def testCreateNestedPackage() {
		val uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, uPackageLevel1)
		propagate

		val jPackageLevel1 = getCorrespondingPackage(uPackageLevel1)
		val jPackageLevel2 = getCorrespondingPackage(uPackageLevel2)
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.name)
		assertEquals(#[jPackageLevel1.name], jPackageLevel2.namespaces)
		assertPackageEquals(uPackageLevel2, jPackageLevel2)
	}
	
	@Test
	def testMovePackage() {
		val uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, rootElement)
		propagate
		
		uPackageLevel1.packagedElements += uPackageLevel2
		propagate
		
		val jPackageLevel1 = getCorrespondingPackage(uPackageLevel1)
		val jPackageLevel2 = getCorrespondingPackage(uPackageLevel2)
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.name)
		assertEquals(#[jPackageLevel1.name], jPackageLevel2.namespaces)
		assertPackageEquals(uPackageLevel2, jPackageLevel2)
	}

	@Test
	def void testDeletePackage() {
		val expectedPackagePath = buildJavaFilePath(ContainersFactory.eINSTANCE.createPackage => [name = PACKAGE_LEVEL_1])
		assertNotNull(getCorrespondingPackage(uPackageLevel1), "Corresponding Java package does not exist")
		assertFalse(resourceAt(Path.of(expectedPackagePath)).contents.empty, "Java package does not exist")
		
		uPackageLevel1.destroy
		propagate
		
		renewResourceCache
		assertTrue(resourceAt(Path.of(expectedPackagePath)).contents.empty, "Java package still exists")
	}

	@Test
	def testRenamePackage() {
		uPackageLevel1.name = PACKAGE_RENAMED
		propagate

		val jPackage = getCorrespondingPackage(uPackageLevel1)
		assertEquals(PACKAGE_RENAMED, jPackage.name)
		assertPackageEquals(uPackageLevel1, jPackage)
	}
}
