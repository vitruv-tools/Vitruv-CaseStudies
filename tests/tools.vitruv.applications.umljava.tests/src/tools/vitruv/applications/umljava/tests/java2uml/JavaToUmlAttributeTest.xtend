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
import static tools.vitruv.applications.util.temporary.uml.UmlTypeUtil.*

import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.types.TypeReference
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

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
			getUniqueJavaClassWithName(className) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = attributeName
					typeReference = type
					makePrivate
				]
			]
		]
	}
	
	private def loadUmlPrimitiveType(String name) {
		getSupportedPredefinedUmlPrimitiveTypes(new ResourceSetImpl()).filter[it.name == name].claimOne
	}

	/**
	 * Tests the creation of an attribute with primitive type.
	 * Checks if a corresponding UML attribute exists afterwards.
	 */
	@Test
	def void testCreatePrimitiveAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				val field = getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
				members += createJavaGetterForAttribute(field, JavaVisibility.PRIVATE)
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
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
			val typeClass = getUniqueJavaClassWithName(TYPE_CLASS_NAME)
			getUniqueJavaClassWithName(CLASS_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(typeClass)
					makePrivate
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlTypeClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(TYPE_CLASS_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
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
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					name = ATTRIBUTE_RENAME
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_RENAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_RENAME)
			assertEquals(ATTRIBUTE_RENAME, umlAttribute.name)
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
			getUniqueJavaClassWithName(CLASS_NAME) => [
				EcoreUtil.delete(getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME))
			]
		]
		createUmlView => [
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
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
			val typeClass = getUniqueJavaClassWithName(TYPE_CLASS_NAME)
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlTypeClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(TYPE_CLASS_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
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
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					static = true
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
			assertTrue(umlAttribute.static)
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
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					final = true
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
			assertTrue(umlAttribute.readOnly)
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
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					makePublic
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PUBLIC_LITERAL)
			assertElementsEqual(umlAttribute, javaField)
		]
		changeView(createJavaClassesView) [
			getUniqueJavaClassWithName(CLASS_NAME) => [
				getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME) => [
					makeProtected
				]
			]
		]
		createUmlAndJavaClassesView => [
			val javaField = getUniqueJavaClassWithName(CLASS_NAME).getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			val umlClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(CLASS_NAME)
			val umlAttribute = umlClass.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PROTECTED_LITERAL)
			assertElementsEqual(umlAttribute, javaField)
		]
	}
}
