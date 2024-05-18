package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import tools.mdsd.jamopp.model.java.types.TypesFactory
import org.junit.jupiter.api.Test
import tools.vitruv.applications.util.temporary.java.JavaVisibility

import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * A Test class for creating, renaming and deleting enumerations.
 * Checks the adding of enumerations literals, attributes and methods to enumerations, too.
 */
class UmlToJavaEnumTest extends AbstractUmlToJavaTest {
	static val PACKAGE_NAME = "rootpackage"
	static val ENUM_NAME = "EnumName"
	static val ENUM_RENAME = "EnumRenamed"
	static val LITERAL_NAME = "LITERALNAME"
	static val OPERATION_NAME = "operationName"
	static val TYPE_CLASS_NAME = "TypeClass"
	static val ATTRIBUTE_NAME = "attributeName"

	@Test
	def void testCreateEnum() {
		createEnumInRootPackage(ENUM_NAME)
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateJavaView [
			val javaEnum = claimJavaEnum(ENUM_NAME)
			assertJavaEnumTraits(javaEnum, ENUM_NAME, JavaVisibility.PUBLIC, #[])
		]
	}

	@Test
	def void testCreateEnumInPackage() {
		createEnumInPackage(PACKAGE_NAME, ENUM_NAME)
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
	}

	@Test
	def void testRenameEnum() {
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				name = ENUM_RENAME
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_RENAME)
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
	}

	@Test
	def void testMoveEnum() {
		createEnumInRootPackage(ENUM_NAME)
		createPackageInRootPackage(PACKAGE_NAME)
		changeUmlModel [
			claimPackage(PACKAGE_NAME).packagedElements += claimEnum(ENUM_NAME)
		]
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierExistsInRootPackage()
		changeUmlModel [
			packagedElements += claimPackage(PACKAGE_NAME).claimEnum(ENUM_NAME)
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
	}

	@Test
	def void testDeleteEnum() {
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				destroy()
			]
		]
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddEnumLiteral() {
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				createOwnedLiteral(LITERAL_NAME)
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateJavaView [
			val javaEnum = claimJavaEnum(ENUM_NAME)
			assertJavaEnumHasConstant(javaEnum, LITERAL_NAME)
		]
	}

	@Test
	def void testDeleteEnumLiteral() {
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				createOwnedLiteral(LITERAL_NAME)
			]
		]
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				ownedLiterals.remove(0)
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateJavaView [
			val javaEnum = claimJavaEnum(ENUM_NAME)
			assertJavaEnumDontHaveConstant(javaEnum, LITERAL_NAME)
		]
	}

	@Test
	def void testAddEnumMethod() {
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			claimEnum(ENUM_NAME) => [
				ownedOperations += UMLFactory.eINSTANCE.createOperation => [
					name = OPERATION_NAME
					visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateJavaView [
			val javaEnum = claimJavaEnum(ENUM_NAME)
			val javaMethod = javaEnum.claimClassMethod(OPERATION_NAME)
			assertJavaClassMethodTraits(javaMethod, OPERATION_NAME, JavaVisibility.PUBLIC,
				TypesFactory.eINSTANCE.createVoid, false, false, null, javaEnum)
		]
	}

	@Test
	def void testAddEnumAttribute() {
		createClassInRootPackage(TYPE_CLASS_NAME)
		createEnumInRootPackage(ENUM_NAME)
		changeUmlModel [
			val typeClass = claimClass(TYPE_CLASS_NAME)
			claimEnum(ENUM_NAME) => [
				createOwnedAttribute(ATTRIBUTE_NAME, typeClass)
			]
		]
		assertEnumWithNameInRootPackage(ENUM_NAME)
		validateJavaView [
			val javaTypeClass = claimJavaClass(TYPE_CLASS_NAME)
			val javaEnum = claimJavaEnum(ENUM_NAME)
			val javaAttribute = javaEnum.claimField(ATTRIBUTE_NAME)
			assertJavaAttributeTraits(javaAttribute, ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
				createNamespaceClassifierReference(javaTypeClass), false, false, javaEnum)
		]
	}

	static class BidirectionalTest extends UmlToJavaEnumTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}

}
