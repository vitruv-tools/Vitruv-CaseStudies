package tools.vitruv.applications.umljava.uml2java.tests

import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlOperationAndParameterUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import org.emftext.language.java.types.TypesFactory
import org.junit.After

class UmlToJavaEnumTest extends Uml2JavaTransformationTest {
	private static val ENUM_NAME = "EnumName"
	private static val ENUM_RENAME = "EnumRenamed"
	private static val STANDARD_ENUM_NAME = "StandardEnumName"
	private static val ENUM_LITERAL_NAMES_1 = #["RED", "BLUE", "GREEN", "YELLOW", "PURPLE"]
	private static val ENUM_LITERAL_NAMES_2 = #["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"]
	private static val LITERAL_NAME = "LITERALNAME"
	private static val OPERATION_NAME = "operationName"
	private static val TYPE_CLASS = "TypeClass"
	private static val ATTRIBUTE_NAME = "attributeName"
	private static var org.eclipse.uml2.uml.Enumeration uEnum
	private static val enumLiterals1 = createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_1)
	private static val enumLiterals2 = createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_2)
    

    
    
	
	@Before
	def void before() {
		uEnum = createUmlEnumAndAddToPackage(rootElement, ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, enumLiterals1)
		saveAndSynchronizeChanges(uEnum)
	}
	
	@After
	def void after() {
		if (uEnum !== null) {
			uEnum.destroy
		}
		saveAndSynchronizeChanges(rootElement)
	}
	
	@Test
	def void testCreateEnum() {
		val enumeration = createUmlEnumAndAddToPackage(rootElement, STANDARD_ENUM_NAME, VisibilityKind.PRIVATE_LITERAL,
			enumLiterals2)
	    saveAndSynchronizeChanges(enumeration)
	    
	    assertJavaFileExists(STANDARD_ENUM_NAME, #[])
	    val jEnum = getCorrespondingEnum(enumeration)
	    assertJavaEnumTraits(jEnum, STANDARD_ENUM_NAME, JavaVisibility.PRIVATE, createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_2))
	    assertEnumEquals(enumeration, jEnum)
	    
	    
	}
	
	@Test
	def void testRenameEnum() {
		uEnum.name = ENUM_RENAME
		saveAndSynchronizeChanges(uEnum)
		
		assertJavaFileExists(ENUM_RENAME, #[])
		assertJavaFileNotExists(ENUM_NAME, #[])
		val jEnum = getCorrespondingEnum(uEnum)
		assertEquals(ENUM_RENAME, uEnum.name)
	    assertEnumEquals(uEnum, jEnum)
	}
	
	@Test
	def void testDeleteEnum() {
		uEnum.destroy
		saveAndSynchronizeChanges(rootElement)
		
		assertJavaFileNotExists(ENUM_NAME, #[])
	}
	
	@Test
	def void testAddEnumLiteral() {
		uEnum.createOwnedLiteral(LITERAL_NAME)
		saveAndSynchronizeChanges(uEnum)
		
		val jEnum = getCorrespondingEnum(uEnum)
		assertJavaEnumHasConstant(jEnum, LITERAL_NAME)
	    assertEnumEquals(uEnum, jEnum)
	}
	
	@Test
	def void testDeleteEnumLiteral() {
		uEnum.ownedLiterals.remove(0)
		saveAndSynchronizeChanges(uEnum)
		
		val jEnum = getCorrespondingEnum(uEnum)
		assertJavaEnumDontHaveConstant(jEnum, ENUM_LITERAL_NAMES_1.head)
	    assertEnumEquals(uEnum, jEnum)
	}
	
	@Test
	def void testAddEnumMethod() {
	    val uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
	    uEnum.ownedOperations += uOperation
	    saveAndSynchronizeChanges(rootElement)
	    
	    val jMethod = getCorrespondingClassMethod(uOperation)
	    val jEnum = getCorrespondingEnum(uEnum)
        assertJavaClassMethodTraits(jMethod, OPERATION_NAME, JavaVisibility.PUBLIC,
            TypesFactory.eINSTANCE.createVoid, false, false, null, jEnum)
        assertClassMethodEquals(uOperation, jMethod)
	}
	
	@Test
    def testAddEnumAttribute() {
        val typeClass = createSimpleUmlClass(rootElement, TYPE_CLASS)
        val attr = uEnum.createOwnedAttribute(ATTRIBUTE_NAME, typeClass);
        saveAndSynchronizeChanges(rootElement);
           
        val jEnum = getCorrespondingEnum(uEnum)
        val jTypeClass = getCorrespondingClass(typeClass)
        val jAttr = getCorrespondingAttribute(attr)
        assertJavaAttributeTraits(jAttr, ATTRIBUTE_NAME, JavaVisibility.PUBLIC, 
            createNamespaceReferenceFromClassifier(jTypeClass), false, false, jEnum)
        assertAttributeEquals(attr, jAttr)
        
    }

}
