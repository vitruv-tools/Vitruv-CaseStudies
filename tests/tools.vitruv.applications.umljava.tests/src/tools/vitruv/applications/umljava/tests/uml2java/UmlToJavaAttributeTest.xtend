package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.*

/**
 * This Test class checks the creating, deleting and modifying of attributes in den uml to java
 * direction.
 * 
 * @author Fei
 */
class UmlToJavaAttributeTest extends UmlToJavaTransformationTest {
	static val ATTRIBUTE_NAME = "attributName"
	static val ATTRIBUTE_RENAME = "attributeRenamed"
	static val STANDARD_ATTRIBUTE_NAME = "standardAttributName"
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS = "TypeClass"

	var Property uAttr
	var Class uClass
	var Class typeClass
	var PrimitiveType pType

	@BeforeEach
	def void before() {
		uClass = createSimpleUmlClass(rootElement, CLASS_NAME)
		typeClass = createSimpleUmlClass(rootElement, TYPE_CLASS)
		uAttr = createUmlAttribute(ATTRIBUTE_NAME, typeClass, VisibilityKind.PUBLIC_LITERAL, false, false)
		uClass.ownedAttributes += uAttr
		pType = UmlTypeUtil.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name == "Integer"]
		propagate
	}

	@Test
	def testCreatePrimitiveAttribute() {
		val attr = createUmlAttribute(STANDARD_ATTRIBUTE_NAME, pType, VisibilityKind.PUBLIC_LITERAL, false, false)
		uClass.ownedAttributes += attr
		propagate

		val jClass = getCorrespondingClass(uClass)
		val jAttr = getCorrespondingAttribute(attr)
		assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createInt, false, false, jClass)
		assertAttributeEquals(attr, jAttr)
	}

	@Test
	def testCreateAttribute() {
		val attr = uClass.createOwnedAttribute(STANDARD_ATTRIBUTE_NAME, typeClass)
		propagate

		val jClass = getCorrespondingClass(uClass)
		val jtypeClass = getCorrespondingClass(typeClass)
		val jAttr = getCorrespondingAttribute(attr)
		assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
			createNamespaceReferenceFromClassifier(jtypeClass), false, false, jClass)
		assertAttributeEquals(attr, jAttr)

	}

	@Test
	def testRenameAttribute() {
		uAttr.name = ATTRIBUTE_RENAME
		propagate

		val jClass = getCorrespondingClass(uClass)
		val jAttr = getCorrespondingAttribute(uAttr)
		assertEquals(ATTRIBUTE_RENAME, uAttr.name)
		assertAttributeEquals(uAttr, jAttr)
		assertTrue(javaGetterForAttributeExists(jAttr))
		assertTrue(javaSetterForAttributeExists(jAttr))
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME)
	}

	@Test
	def testDeleteAttribute() {
		uAttr.destroy
		propagate

		val jClass = getCorrespondingClass(uClass)
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME)
	}

	@Test
	def testStaticAttribute() {
		uAttr.isStatic = true
		propagate

		val jAttr = getCorrespondingAttribute(uAttr)
		assertJavaModifiableStatic(jAttr, true)
		assertAttributeEquals(uAttr, jAttr)
	}

	@Test
	def testFinalAttribute() {
		uAttr.isReadOnly = true
		propagate

		val jAttr = getCorrespondingAttribute(uAttr)
		assertJavaModifiableFinal(jAttr, true)
		assertAttributeEquals(uAttr, jAttr)
	}

	@Test
	def testAttributeVisibility() {
		uAttr.visibility = VisibilityKind.PRIVATE_LITERAL
		propagate

		var jAttr = getCorrespondingAttribute(uAttr)
		assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PRIVATE)
		assertAttributeEquals(uAttr, jAttr)

		uAttr.visibility = VisibilityKind.PACKAGE_LITERAL
		propagate

		jAttr = getCorrespondingAttribute(uAttr)
		assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PACKAGE)
		assertAttributeEquals(uAttr, jAttr)
	}

	@Test
	def testChangeAttributeType() {
		uAttr.type = pType
		propagate

		val jClass = getCorrespondingClass(uClass)
		val jAttr = getCorrespondingAttribute(uAttr)
		assertJavaAttributeTraits(jAttr, ATTRIBUTE_NAME, JavaVisibility.PUBLIC, TypesFactory.eINSTANCE.createInt, false,
			false, jClass)
		assertAttributeEquals(uAttr, jAttr)
	}
	
	@Test
	def testMoveAttribute() {
		val uClass2 = createSimpleUmlClass(rootElement, "ClassName2")
		uClass2.ownedAttributes += uAttr
		propagate
		
		val jClass = getCorrespondingClass(uClass)
		val jClass2 = getCorrespondingClass(uClass2)
		val jAttr = getCorrespondingAttribute(uAttr)
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME)
		assertTrue(jClass.methods.filter [name == buildGetterName(ATTRIBUTE_NAME)].nullOrEmpty)
		assertTrue(jClass.methods.filter [name == buildSetterName(ATTRIBUTE_NAME)].nullOrEmpty)
		assertFalse(jClass2.getMembersByName(ATTRIBUTE_NAME).nullOrEmpty)
		assertAttributeEquals(uAttr, jAttr)
		assertTrue(javaGetterForAttributeExists(jAttr))
		assertTrue(javaSetterForAttributeExists(jAttr))
	}
}
