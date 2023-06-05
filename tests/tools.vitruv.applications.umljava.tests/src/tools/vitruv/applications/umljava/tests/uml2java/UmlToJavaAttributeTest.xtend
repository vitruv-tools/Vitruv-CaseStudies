package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.mdsd.jamopp.model.java.members.Field
import tools.mdsd.jamopp.model.java.types.TypesFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*
import static tools.vitruv.applications.umljava.tests.util.UmlElementsTestAssertions.assertUmlClassDontHaveOperation
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getJavaVisibilityConstantFromUmlVisibilityKind

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * This test class checks the creating, deleting and modifying of attributes in the UML to Java
 * direction.
 */
class UmlToJavaAttributeTest extends AbstractUmlToJavaTest {
	static val ATTRIBUTE_NAME = "attributName"
	static val ATTRIBUTE_RENAME = "attributeRenamed"
	static val CLASS_NAME = "ClassName"
	static val CLASS_NAME_2 = "ClassName2"
	static val TYPE_CLASS_NAME = "TypeClass"

	@Test
	def void testCreatePrimitiveAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaAttribute = javaClass.claimField(ATTRIBUTE_NAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_NAME, JavaVisibility.PRIVATE,
				TypesFactory.eINSTANCE.createInt, false, false, javaClass)
		]
	}

	@Test
	def void testCreateAttribute() {
		createClassInRootPackage(CLASS_NAME)
		createClassInRootPackage(TYPE_CLASS_NAME)
		changeUmlModel [
			val typeClass = claimClass(TYPE_CLASS_NAME)
			claimClass(CLASS_NAME) => [
				ownedAttributes += UMLFactory.eINSTANCE.createProperty => [
					name = ATTRIBUTE_NAME
					visibility = VisibilityKind.PRIVATE_LITERAL
					type = typeClass
				]
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			val javaAttribute = javaClass.claimField(ATTRIBUTE_NAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_NAME, JavaVisibility.PRIVATE,
				createNamespaceClassifierReference(javaTypeClass), false, false, javaClass)
		]
	}

	@Test
	def void testRenameAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		changeAttribute(CLASS_NAME, ATTRIBUTE_NAME) [ model, attribute |
			attribute.name = ATTRIBUTE_RENAME
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaAttribute = javaClass.claimField(ATTRIBUTE_RENAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_RENAME, JavaVisibility.PRIVATE,
				TypesFactory.eINSTANCE.createInt, false, false, javaClass)
			assertThat("there must be a getter for the attribute " + javaAttribute,
				getJavaGettersOfAttribute(javaAttribute).toSet, is(not(emptySet)))
			assertThat("there must be a setter for the attribute " + javaAttribute,
				getJavaSettersOfAttribute(javaAttribute).toSet, is(not(emptySet)))
			assertJavaMemberContainerDontHaveMember(javaClass, ATTRIBUTE_NAME)
		]
	}

	@Test
	def void testDeleteAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		changeAttribute(CLASS_NAME, ATTRIBUTE_NAME) [ model, attribute |
			attribute.destroy()
		]
		assertSingleClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			assertJavaMemberContainerDontHaveMember(javaClass, ATTRIBUTE_NAME)
		]
	}

	private def void changeAndCheckPropertyOfAttribute(String className, String attributeName,
		(Property)=>void changeUmlProperty, (Field)=>void validateJavaField) {
		changeAttribute(CLASS_NAME, ATTRIBUTE_NAME) [
			changeUmlProperty.apply(it)
		]
		assertSingleClassWithNameInRootPackage(className)
		validateJavaView [
			val javaField = claimJavaClass(className).claimField(attributeName)
			validateJavaField.apply(javaField)
		]
	}

	@Test
	def testStaticAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		changeAndCheckPropertyOfAttribute(CLASS_NAME, ATTRIBUTE_NAME, [isStatic = true], [
			assertJavaModifiableStatic(it, true)
		])
		changeAndCheckPropertyOfAttribute(CLASS_NAME, ATTRIBUTE_NAME, [isStatic = false], [
			assertJavaModifiableStatic(it, false)
		])
	}

	@Test
	def testFinalAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		changeAndCheckPropertyOfAttribute(CLASS_NAME, ATTRIBUTE_NAME, [isReadOnly = true], [
			assertJavaModifiableFinal(it, true)
		])
		changeAndCheckPropertyOfAttribute(CLASS_NAME, ATTRIBUTE_NAME, [isReadOnly = false], [
			assertJavaModifiableFinal(it, false)
		])
	}

	@ParameterizedTest
	@EnumSource(value=VisibilityKind, names=#["PRIVATE_LITERAL"], mode=EnumSource.Mode.EXCLUDE)
	def void testAttributeVisibility(VisibilityKind visibility) {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		changeAndCheckPropertyOfAttribute(CLASS_NAME, ATTRIBUTE_NAME, [it.visibility = visibility], [
			assertJavaModifiableHasVisibility(it, getJavaVisibilityConstantFromUmlVisibilityKind(visibility))
		])
	}

	@Test
	def void testChangeAttributeType() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadIntegerPrimitiveType())
		createClassInRootPackage(TYPE_CLASS_NAME)
		changeAttribute(CLASS_NAME, ATTRIBUTE_NAME) [ model, attribute |
			val typeClass = model.claimClass(TYPE_CLASS_NAME)
			attribute.type = typeClass
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			val javaAttribute = javaClass.claimField(ATTRIBUTE_NAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_NAME, JavaVisibility.PRIVATE,
				createNamespaceClassifierReference(javaTypeClass), false, false, javaClass)
		]
	}

	@Test
	def void testMoveAttribute() {
		createClassWithFieldOfType(CLASS_NAME, ATTRIBUTE_NAME, loadUmlPrimitiveType("Integer"))
		createClassInRootPackage(CLASS_NAME_2)
		changeAttribute(CLASS_NAME, ATTRIBUTE_NAME) [ model, attribute |
			model.claimClass(CLASS_NAME_2) => [
				ownedAttributes += attribute
			]
		]
		assertClassWithNameInRootPackage(CLASS_NAME)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		validateJavaView [
			val javaClass = claimJavaClass(CLASS_NAME)
			val javaClass2 = claimJavaClass(CLASS_NAME_2)
			val javaAttribute = javaClass2.claimField(ATTRIBUTE_NAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_NAME, JavaVisibility.PRIVATE,
				TypesFactory.eINSTANCE.createInt, false, false, javaClass2)
			assertJavaMemberContainerDontHaveMember(javaClass, ATTRIBUTE_NAME)
			assertThat("there must be no getter for removed attribute " + ATTRIBUTE_NAME, javaClass.methods.filter [
				name == buildGetterName(ATTRIBUTE_NAME)
			].toSet, is(emptySet))
			assertThat("there must be not setter for removed attribute " + ATTRIBUTE_NAME, javaClass.methods.filter [
				name == buildSetterName(ATTRIBUTE_NAME)
			].toSet, is(emptySet))
			assertThat("there must be a getter for the attribute " + javaAttribute,
				getJavaGettersOfAttribute(javaAttribute).toSet, is(not(emptySet)))
			assertThat("there must be a setter for the attribute " + javaAttribute,
				getJavaSettersOfAttribute(javaAttribute).toSet, is(not(emptySet)))
		]
	}

	private def PrimitiveType loadIntegerPrimitiveType() {
		loadUmlPrimitiveType("int")
	}

	private def void createClassWithFieldOfType(String className, String attributeName, Type attributeType) {
		createClassInRootPackage(className)
		changeUmlModel [
			claimClass(className) => [
				ownedAttributes += UMLFactory.eINSTANCE.createProperty => [
					name = attributeName
					visibility = VisibilityKind.PRIVATE_LITERAL
					type = attributeType
				]
			]
		]
	}

	private def changeAttribute(String className, String attributeName, (Property)=>void changeFunction) {
		changeAttribute(className, attributeName, [model, property|changeFunction.apply(property)])
	}

	private def changeAttribute(String className, String attributeName, (Model, Property)=>void changeFunction) {
		changeUmlModel [
			val model = it
			claimClass(className) => [
				claimAttribute(attributeName) => [
					changeFunction.apply(model, it)
				]
			]
		]
	}

	static class BidirectionalTest extends UmlToJavaAttributeTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}

		@Test
		override testCreatePrimitiveAttribute() {
			super.testCreatePrimitiveAttribute()
			validateUmlView [
				val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
				assertUmlClassDontHaveOperation(umlClass, buildGetterName(ATTRIBUTE_NAME))
				assertUmlClassDontHaveOperation(umlClass, buildSetterName(ATTRIBUTE_NAME))
			]
		}

		@Test
		override testCreateAttribute() {
			super.testCreateAttribute()
			validateUmlView [
				val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
				assertUmlClassDontHaveOperation(umlClass, buildGetterName(ATTRIBUTE_NAME))
				assertUmlClassDontHaveOperation(umlClass, buildSetterName(ATTRIBUTE_NAME))
			]
		}

		@Test
		override testRenameAttribute() {
			super.testRenameAttribute()
			validateUmlView [
				val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
				assertUmlClassDontHaveOperation(umlClass, buildGetterName(ATTRIBUTE_RENAME))
				assertUmlClassDontHaveOperation(umlClass, buildSetterName(ATTRIBUTE_RENAME))
			]
		}

		@Test
		override testChangeAttributeType() {
			super.testChangeAttributeType()
			validateUmlView [
				val umlClass = defaultUmlModel.claimClass(CLASS_NAME)
				assertUmlClassDontHaveOperation(umlClass, buildGetterName(ATTRIBUTE_NAME))
				assertUmlClassDontHaveOperation(umlClass, buildSetterName(ATTRIBUTE_NAME))
			]
		}

		@Test
		override testMoveAttribute() {
			super.testMoveAttribute()
			validateUmlView [
				val umlClass = defaultUmlModel.claimClass(CLASS_NAME_2)
				assertUmlClassDontHaveOperation(umlClass, buildGetterName(ATTRIBUTE_NAME))
				assertUmlClassDontHaveOperation(umlClass, buildSetterName(ATTRIBUTE_NAME))
			]
		}
	}

}
