package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This class contains Tests for creating, deleting and renaming enums.
 * Furthermore there are test to check the adding of methods, attributes and enum constant to enums
 * 
 * @author Fei
 */
class JavaToUmlEnumTest extends JavaToUmlTransformationTest {
	static val ENUM_NAME = "EnumName"
	static val ENUM_RENAME = "EnumRenamed"
	static val STANDARD_ENUM_NAME = "StandardEnumName"
	static val ENUM_LITERAL_NAMES_1 = #["RED", "BLUE", "GREEN", "YELLOW", "PURPLE"]
	static val ENUM_LITERAL_NAMES_2 = #["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"]
	static val CONSTANT_NAME = "CONSTANTNAME"
	static val OPERATION_NAME = "operationName"
	static val ATTRIBUTE_NAME = "attributeName"
	static val TYPECLASS = "TypeClass"
	var Enumeration jEnum
	val enumConstants1 = createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_1)
	val enumConstants2 = createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_2)

	@BeforeEach
	def void before() {
		jEnum = createJavaEnumWithCompilationUnit(ENUM_NAME, JavaVisibility.PUBLIC, enumConstants1)
	}

	@Test
	def void testCreateEnum() {
		val enumeration = createJavaEnumWithCompilationUnit(STANDARD_ENUM_NAME, JavaVisibility.PUBLIC, enumConstants2)

		val uEnum = getCorrespondingEnum(enumeration)
		assertUmlEnumTraits(uEnum, STANDARD_ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, registeredUmlModel,
			createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_2))
		assertEnumEquals(uEnum, enumeration)
	}

	@Test
	def void testRenameEnum() {
		jEnum.name = ENUM_RENAME
		propagate

		val uEnum = getCorrespondingEnum(jEnum)
		assertEquals(ENUM_RENAME, uEnum.name)
		assertEnumEquals(uEnum, jEnum)
	}

	@Test
	def void testDeleteEnum() {
		assertNotNull(jEnum)
		jEnum.containingCompilationUnit

		EcoreUtil.delete(jEnum)
		propagate
		val uEnum = getUmlPackagedElementsbyName(org.eclipse.uml2.uml.Enumeration, ENUM_NAME).head
		assertNull(uEnum)
	}

	@Test
	def void testAddEnumConstant() {
		jEnum.constants += createJavaEnumConstant(CONSTANT_NAME)
		propagate

		val uEnum = getCorrespondingEnum(jEnum)
		assertUmlEnumHasLiteral(uEnum, createUmlEnumerationLiteral(CONSTANT_NAME))
		assertEnumEquals(uEnum, jEnum)
	}

	@Test
	def void testDeleteEnumConstant() {
		EcoreUtil.delete(jEnum.constants.remove(0))
		propagate

		val uEnum = getCorrespondingEnum(jEnum)
		assertUmlEnumDontHaveLiteral(uEnum, createUmlEnumerationLiteral(ENUM_LITERAL_NAMES_1.head))
		assertEnumEquals(uEnum, jEnum)
	}

	@Test
	def void testAddEnumMethod() {
		val jMethod = createJavaClassMethod(OPERATION_NAME, TypesFactory.eINSTANCE.createVoid, JavaVisibility.PUBLIC,
			false, false, null)
		jEnum.members += jMethod
		propagate

		val uOperation = getCorrespondingMethod(jMethod)
		val uEnum = getCorrespondingEnum(jEnum)
		assertUmlOperationTraits(uOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false,
			false, uEnum, null)
		assertClassMethodEquals(uOperation, jMethod)
	}

	@Test
	def void testAddEnumAttribute() {
		val typeClass = createJavaClassWithCompilationUnit(TYPECLASS, JavaVisibility.PUBLIC, false, false)
		val jAttr = createJavaAttribute(ATTRIBUTE_NAME, createNamespaceReferenceFromClassifier(typeClass),
			JavaVisibility.PRIVATE, false, false)
		jEnum.members += jAttr
		propagate

		val uAttr = getCorrespondingAttribute(jAttr)
		val uTypeClass = getCorrespondingClass(typeClass)
		val uEnum = getCorrespondingEnum(jEnum)
		assertUmlPropertyTraits(uAttr, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass, false, false, uEnum,
			null, null)
		assertAttributeEquals(uAttr, jAttr)
	}
}
