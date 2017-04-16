package tools.vitruv.applications.umljava.uml2java.tests

import org.junit.Before
import org.junit.Test
import static tools.vitruv.applications.umljava.util.UmlUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.eclipse.uml2.uml.VisibilityKind

class UmlToJavaEnumTest extends AbstractUmlJavaTest {
	private static val ENUM_NAME = "EnumName"
	private static val ENUM_RENAME = "EnumRenamed"
	private static val STANDARD_ENUM_NAME = "StandardEnumName"
	private static val ENUM_LITERAL_NAMES_1 = #["RED", "BLUE", "GREEN", "YELLOW", "PURPLE"]
	private static val ENUM_LITERAL_NAMES_2 = #["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"]
	private static val LITERAL_NAME = "LITERALNAME"
	
	private static var org.eclipse.uml2.uml.Enumeration uEnum
	
	@Before
	def void before() {
		uEnum = createUmlEnumAndAddToPackage(rootElement, ENUM_NAME, VisibilityKind.PUBLIC_LITERAL,
			createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_1))
		saveAndSynchronizeChanges(uEnum)
	}
	
	def void after() {
		if (uEnum !== null) {
			uEnum.destroy
		}
	}
	
	@Test
	def void testCreateEnum() {
		val enumeration = createUmlEnumAndAddToPackage(rootElement, STANDARD_ENUM_NAME, VisibilityKind.PRIVATE_LITERAL,
			createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_2))
	    saveAndSynchronizeChanges(enumeration)
	    assertJavaFileExists(STANDARD_ENUM_NAME)
	    val jEnum = getCorrespondingObject(enumeration, org.emftext.language.java.classifiers.Enumeration)
	    assertEnumEquals(enumeration, jEnum)
	    
	}
	
	@Test
	def void testRenameEnum() {
		uEnum.name = ENUM_RENAME
		saveAndSynchronizeChanges(uEnum)
		assertJavaFileExists(ENUM_RENAME)
		assertJavaFileNotExists(ENUM_NAME)
		val jEnum = getCorrespondingObject(uEnum, org.emftext.language.java.classifiers.Enumeration)
	    assertEnumEquals(uEnum, jEnum)
	}
	
	@Test
	def void testDeleteEnum() {
		uEnum.destroy
		saveAndSynchronizeChanges(rootElement)
		assertJavaFileNotExists(ENUM_NAME)
	}
	
	@Test
	def void testAddEnumLiteral() {
		uEnum.createOwnedLiteral(LITERAL_NAME)
		saveAndSynchronizeChanges(uEnum)
		val jEnum = getCorrespondingObject(uEnum, org.emftext.language.java.classifiers.Enumeration)
	    assertEnumEquals(uEnum, jEnum)
	}
	
	@Test
	def void testDeleteEnumLiteral() {
		uEnum.ownedLiterals.remove(0)
		saveAndSynchronizeChanges(uEnum)
		val jEnum = getCorrespondingObject(uEnum, org.emftext.language.java.classifiers.Enumeration)
	    assertEnumEquals(uEnum, jEnum)
	}
	
}