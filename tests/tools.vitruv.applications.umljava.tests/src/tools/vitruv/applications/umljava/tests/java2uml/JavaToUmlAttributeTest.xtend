package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaStandardType
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.types.TypeReference
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*

/**
 * Test class for testing the attribute reactions.
 */
class JavaToUmlAttributeTest extends AbstractJavaToUmlTest {
	static val ATTRIBUTE_NAME = "attributName"
	static val ATTRIBUTE_RENAME = "attributeRenamed"
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS_NAME = "TypeClass"

	private def void createJavaClassWithFieldOfType(String className, TypeReference type, String attributeName) {
		createJavaClassInRootPackage(className)
		changeView(createJavaClassesView) [
			claimJavaClass(className) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = attributeName
					typeReference = type
					makePrivate
				]
			]
		]
	}

	/**
	 * Tests the creation of an attribute with primitive type.
	 * Checks if a corresponding UML attribute exists afterwards.
	 */
	@Test
	def void testCreatePrimitiveAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				val field = claimField(ATTRIBUTE_NAME)
				members += createJavaGetterForAttribute(field, JavaVisibility.PRIVATE)
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			val umlInteger = loadUmlPrimitiveType("Integer")
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlInteger, false,
				false, umlClass, null, null)
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Tests the creation of an attribute with a type that references a class.
	 * Checks if a corresponding UML attribute exists afterwards.
	 */
	@Test
	def void testCreateAttribute() {
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		createJavaClassInRootPackage(CLASS_NAME)
		changeView(createJavaClassesView) [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(typeClass)
					makePrivate
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlClass, null, null)
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Tests if an attribute rename is correctly synchronized with the UML attribute.
	 */
	@Test
	def void testRenameAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					name = ATTRIBUTE_RENAME
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_RENAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_RENAME)
			assertThat(umlAttribute.name, is(ATTRIBUTE_RENAME))
			assertUmlClassDontHaveProperty(umlClass, ATTRIBUTE_NAME)
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Test if deleting the java attribute also deletes the corresponding UML attribute.
	 */
	@Test
	def void testDeleteAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					EcoreUtil.delete(it)
				]
			]
		]
		createUmlView => [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			assertUmlClassDontHaveProperty(umlClass, ATTRIBUTE_NAME)
		]
	}

	/**
	 * Checks if a type change is correctly reflected on the UML attribute.
	 */
	@Test
	def void testChangeAttributeType() {
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlClass, null, null)
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Tests if a change to static is correctly reflected on the UML attribute.
	 */
	@Test
	def void testStaticAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					static = true
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertThat("UML attribute must be static", umlAttribute.static, is(true))
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Tests if a change to final is correctly reflected on the UML attribute.
	 */
	@Test
	def void testFinalAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					final = true
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertThat("UML attribute must be read only", umlAttribute.readOnly, is(true))
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	/**
	 * Tests if visibility changes are correctly reflected on the UML attribute.
	 */
	@Test
	def void testAttributeVisibility() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					makePublic
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PUBLIC_LITERAL)
			assertElementsEqual(umlAttribute, javaField)
		]
		changeView(createJavaClassesView) [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					makeProtected
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = claimJavaClass(CLASS_NAME).claimField(ATTRIBUTE_NAME)
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PROTECTED_LITERAL)
			assertElementsEqual(umlAttribute, javaField)
		]
	}
}
