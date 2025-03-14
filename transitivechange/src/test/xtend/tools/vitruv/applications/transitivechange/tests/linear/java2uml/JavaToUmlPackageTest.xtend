package tools.vitruv.applications.transitivechange.tests.linear.java2uml

import org.eclipse.uml2.uml.Package
import org.emftext.language.java.classifiers.Class
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import java.nio.file.Path
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import org.junit.jupiter.api.Disabled
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.*
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.*

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
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY)
		userInteraction.addNextTextInput("model/model.repository")
		jPackageLevel1 = createJavaPackageAsModel(PACKAGE_LEVEL_1, null)
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
		jPackageLevel1.compilationUnits += getContainingCompilationUnit(jClass)
		propagate
	}

	@Test
	def void testCreatePackage() {
		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__NOTHING)
		val jPackage = createJavaPackageAsModel(PACKAGE_NAME, null)

		val uPackage = getCorrespondingPackage(jPackage)
		assertNotNull(uPackage)
		assertEquals(PACKAGE_NAME, uPackage.name)
		assertElementsEqual(uPackage, jPackage)
	}

	@Test
	def void testRenamePackage() {
		jPackageLevel1.name = PACKAGE_RENAMED
		propagate

		val uPackage = getCorrespondingPackage(jPackageLevel1)
		assertEquals(PACKAGE_RENAMED, uPackage.name)
		assertElementsEqual(uPackage, jPackageLevel1)
	}

	@Test
	def void testDeletePackage() {
		resourceAt(Path.of(buildJavaFilePath(jPackageLevel1))).propagate [
			delete(null)
		]
		assertTrue(getUmlPackagedElementsbyName(Package, PACKAGE_LEVEL_1).nullOrEmpty)
	}

	@Test
	@Disabled("Java in-memory model is not correctly updated")
	//https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/issues/130
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
