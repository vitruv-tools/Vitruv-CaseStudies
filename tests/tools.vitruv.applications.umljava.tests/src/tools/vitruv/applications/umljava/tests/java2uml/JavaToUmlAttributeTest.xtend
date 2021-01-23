package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.Field
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaStandardType
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlTypeUtil.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * Test class for testing the attribute reactions.
 * 
 * @author Fei
 */
class JavaToUmlAttributeTest extends JavaToUmlTransformationTest {
	static val ATTRIBUTE_NAME = "attributName"
	static val ATTRIBUTE_RENAME = "attributeRenamed"
	static val STANDARD_ATTRIBUTE_NAME = "standardAttributName"
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS = "TypeClass"

	var Field jAttr
	var Class jClass
	var Class typeClass

	/**
	 * Initializes two java classes. One class contains an attribute.
	 */
	@BeforeEach
	def void testSetup() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME)
		typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_CLASS)
		jAttr = createJavaAttribute(ATTRIBUTE_NAME, createJavaPrimitiveType(JavaStandardType.INT),
			JavaVisibility.PRIVATE, false, false)
		jClass.members += jAttr
		propagate
	}

	/**
	 * Tests the creation of an attribute with primitive type.
	 * Checks if a corresponding uml attribute exists afterwards.
	 */
	@Test
	def testCreatePrimitiveAttribute() {
		val attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createJavaPrimitiveType(JavaStandardType.INT),
			JavaVisibility.PRIVATE, false, false)
		jClass.members += attr
		val getter = createJavaGetterForAttribute(attr, JavaVisibility.PRIVATE)
		jClass.members += getter
		propagate

		val uAttr = getCorrespondingAttribute(attr)
		val uClass = getCorrespondingClass(jClass)
		val umlInteger = getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name == "Integer"]
		assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlInteger,
			false, false, uClass, null, null)
		assertAttributeEquals(uAttr, attr)
	}

	/**
	 * Tests the creation of an attribute with a type that references a class.
	 * Checks if a corresponding uml attribute exists afterwards.
	 */
	@Test
	def testCreateAttribute() {
		val attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createNamespaceReferenceFromClassifier(typeClass),
			JavaVisibility.PRIVATE, false, false)
		jClass.members += attr
		propagate

		val uAttr = getCorrespondingAttribute(attr)
		val uClass = getCorrespondingClass(jClass)
		val uTypeClass = getCorrespondingClass(typeClass)
		assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass,
			false, false, uClass, null, null)
		assertAttributeEquals(uAttr, attr)
	}

	/**
	 * Tests if an attribute rename is correctly synchronized with the uml attribute.
	 */
	@Test
	def void testRenameAttribute() {
		jAttr.name = ATTRIBUTE_RENAME
		propagate

		val uAttr = getCorrespondingAttribute(jAttr)
		val uClass = getCorrespondingClass(jClass)
		assertEquals(ATTRIBUTE_RENAME, uAttr.name)
		assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME)
		assertAttributeEquals(uAttr, jAttr)
	}

	/**
	 * Test if deleting the java attribute also deletes the corresponding uml attribute.
	 */
	@Test
	def testDeleteAttribute() {
		assertNotNull(getCorrespondingAttribute(jAttr))

		EcoreUtil.delete(jAttr)
		propagate

		val uClass = getCorrespondingClass(jClass)
		assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME)
	}

	/**
	 * Checks if a type change is correctly reflected on the uml attribute.
	 */
	@Test
	def testChangeAttributeType() {
		jAttr.typeReference = createNamespaceReferenceFromClassifier(typeClass)
		propagate

		val uAttr = getCorrespondingAttribute(jAttr)
		val uClass = getCorrespondingClass(jClass)
		val uTypeClass = getCorrespondingClass(typeClass)
		assertUmlPropertyTraits(uAttr, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass, false, false, uClass,
			null, null)
		assertAttributeEquals(uAttr, jAttr)
	}

	/**
	 * Tests if a change to static is correctly reflected on the uml attribute.
	 */
	@Test
	def testStaticAttribute() {
		jAttr.static = true
		propagate

		val uAttr = getCorrespondingAttribute(jAttr)
		assertTrue(uAttr.static)
		assertAttributeEquals(uAttr, jAttr)
	}

	/**
	 * Tests if a change to final is correctly reflected on the uml attribute.
	 */
	@Test
	def testFinalAttribute() {
		jAttr.final = true
		propagate

		val uAttr = getCorrespondingAttribute(jAttr)
		assertTrue(uAttr.readOnly)
		assertAttributeEquals(uAttr, jAttr)
	}

	/**
	 * Tests if visibility changes are correctly reflected on the uml attribute.
	 */
	@Test
	def testAttributeVisibility() {
		jAttr.makePublic
		propagate

		var uAttr = getCorrespondingAttribute(jAttr)
		assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PUBLIC_LITERAL)
		assertAttributeEquals(uAttr, jAttr)

		jAttr.makeProtected
		propagate

		uAttr = getCorrespondingAttribute(jAttr)
		assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PROTECTED_LITERAL)
		assertAttributeEquals(uAttr, jAttr)
	}
}
