package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.uml2.uml.Package
import org.emftext.language.java.classifiers.Class
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import java.nio.file.Path

/**
 * This class contains basis tests for java packages.
 * 
 * @author Fei
 */
class JavaToUmlPackageTest extends JavaToUmlTransformationTest {

	static val PACKAGE_LEVEL_1 = "level1"
	static val PACKAGE_NAME = "packagename"
	static val PACKAGE_RENAMED = "packagerenamed"
	static val CLASS_NAME = "ClassName"
	static val CLASS_NAME2 = "ClassName2"

	var org.emftext.language.java.containers.Package jPackageLevel1
	var Class jClass

	@BeforeEach
	def void testSetup() {
		jPackageLevel1 = createJavaPackageAsModel(PACKAGE_LEVEL_1, null)
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
		jPackageLevel1.compilationUnits += getContainingCompilationUnit(jClass)
		propagate
	}

	@Test
	def void testCreatePackage() {
		val jPackage = createJavaPackageAsModel(PACKAGE_NAME, null)

		val uPackage = getCorrespondingPackage(jPackage)
		assertNotNull(uPackage)
		assertEquals(PACKAGE_NAME, uPackage.name)
		assertPackageEquals(uPackage, jPackage)
	}

	@Test
	def void testRenamePackage() {
		jPackageLevel1.name = PACKAGE_RENAMED
		propagate

		val uPackage = getCorrespondingPackage(jPackageLevel1)
		assertEquals(PACKAGE_RENAMED, uPackage.name)
		assertPackageEquals(uPackage, jPackageLevel1)
	}

	@Test
	def void testDeletePackage() {
		resourceAt(Path.of(buildJavaFilePath(jPackageLevel1))).propagate [
			delete(null)
		]
		assertTrue(getUmlPackagedElementsbyName(Package, PACKAGE_LEVEL_1).nullOrEmpty)

	}

	@Test
	def void testAddClassToPackage() {
		val javaClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME2)
		jPackageLevel1.compilationUnits += getContainingCompilationUnit(javaClass)
		propagate

		val uPackage = getCorrespondingPackage(jPackageLevel1)
		val uClass = getCorrespondingClass(javaClass)
		assertNotNull(uClass, "UML class")
		assertNotNull(uPackage, "UML package")
		assertUmlPackageableElementIsInPackage(uClass, uPackage)
	}

}
