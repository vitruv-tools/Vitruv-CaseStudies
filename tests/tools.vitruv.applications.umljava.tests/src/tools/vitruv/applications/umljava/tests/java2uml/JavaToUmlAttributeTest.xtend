package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import tools.mdsd.jamopp.model.java.JavaClasspath
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier
import tools.mdsd.jamopp.model.java.members.MembersFactory
import tools.mdsd.jamopp.model.java.types.TypeReference
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaStandardType
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.testutility.uml.UmlQueryUtil.loadUmlPrimitiveType
import static tools.vitruv.applications.umljava.tests.util.UmlElementsTestAssertions.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*

/**
 * Test class for testing the attribute reactions.
 */
class JavaToUmlAttributeTest extends AbstractJavaToUmlTest {
	static val ATTRIBUTE_NAME = "attributName"
	static val ATTRIBUTE_RENAME = "attributeRenamed"
	static val CLASS_NAME = "ClassName"
	static val TYPE_CLASS_NAME = "TypeClass"

	/**
	 * Tests the creation of an attribute with primitive type.
	 * Checks if a corresponding UML attribute exists afterwards.
	 */
	@Test
	def void testCreatePrimitiveAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				val field = claimField(ATTRIBUTE_NAME)
				members += createJavaGetterForAttribute(field, JavaVisibility.PRIVATE)
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			val umlInteger = loadUmlPrimitiveType("int")
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlInteger, false,
				false, umlClass, null, null)
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
		changeJavaView [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(typeClass)
					makePrivate
				]
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlClass, null, null)
		]
	}

	/**
	 * Tests the creation of an attribute with a type that references a standard library class.
	 * Checks if a corresponding UML attribute exists afterwards.
	 */
	@Test
	def void testCreateAttributeOfStandardLibraryClass() {
		createJavaClassInRootPackage(CLASS_NAME)
		changeJavaView [
			val standardLibraryClass = JavaClasspath.get().getClassifier("java.lang.Iterable") as ConcreteClassifier
			claimJavaClass(CLASS_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(standardLibraryClass)
					makePrivate
				]
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlStandardLibraryClass = defaultUmlModel.claimPackage("java").claimPackage("lang").
				claimInterface("Iterable")
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL,
				umlStandardLibraryClass, false, false, umlClass, null, null)
		]
	}

	/**
	 * Tests if an attribute rename is correctly synchronized with the UML attribute.
	 */
	@Test
	def void testRenameAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					name = ATTRIBUTE_RENAME
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_RENAME)
			assertThat(umlAttribute.name, is(ATTRIBUTE_RENAME))
			assertUmlClassDontHaveProperty(umlClass, ATTRIBUTE_NAME)
		]
	}

	/**
	 * Test if deleting the java attribute also deletes the corresponding UML attribute.
	 */
	@Test
	def void testDeleteAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					EcoreUtil.delete(it)
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
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
		changeJavaView [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					typeReference = createNamespaceClassifierReference(typeClass)
				]
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
			val umlAttribute = umlClass.claimAttribute(ATTRIBUTE_NAME)
			assertUmlClassHasUniqueProperty(umlClass, ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlClass, null, null)
		]
	}

	/**
	 * Tests if a change to static is correctly reflected on the UML attribute.
	 */
	@Test
	def void testStaticAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					static = true
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertThat("UML attribute must be static", umlAttribute.static, is(true))
		]
	}

	/**
	 * Tests if a change to final is correctly reflected on the UML attribute.
	 */
	@Test
	def void testFinalAttribute() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					final = true
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertThat("UML attribute must be read only", umlAttribute.readOnly, is(true))
		]
	}

	/**
	 * Tests if visibility changes are correctly reflected on the UML attribute.
	 */
	@Test
	def void testAttributeVisibility() {
		createJavaClassWithFieldOfType(CLASS_NAME, createJavaPrimitiveType(JavaStandardType.INT), ATTRIBUTE_NAME)
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					makePublic
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PUBLIC_LITERAL)
		]
		changeJavaView [
			claimJavaClass(CLASS_NAME) => [
				claimField(ATTRIBUTE_NAME) => [
					makeProtected
				]
			]
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateUmlView [
			val umlAttribute = defaultUmlModel.claimClass(CLASS_NAME).claimAttribute(ATTRIBUTE_NAME)
			assertUmlNamedElementHasVisibility(umlAttribute, VisibilityKind.PROTECTED_LITERAL)
		]
	}

	private def void createJavaClassWithFieldOfType(String className, TypeReference type, String attributeName) {
		createJavaClassInRootPackage(className)
		changeJavaView [
			claimJavaClass(className) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = attributeName
					typeReference = type
					makePrivate
				]
			]
		]
	}

	static class BidirectionalTest extends JavaToUmlAttributeTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}

}
