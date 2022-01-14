package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.emftext.language.java.members.MembersFactory
import org.eclipse.uml2.uml.UMLFactory
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.*
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureBidirectionalExecution

/**
 * This class contains Tests for creating, deleting and renaming enumerations.
 * Furthermore, there are test to check the adding of methods, attributes and enumeration constant to enumerations.
 */
class JavaToUmlEnumTest extends AbstractJavaToUmlTest {
	static val PACKAGE_NAME = "packagename"
	static val ENUM_NAME = "EnumName"
	static val ENUM_RENAME = "EnumRenamed"
	static val CONSTANT_NAME = "CONSTANTNAME"
	static val CONSTANT_NAME_2 = "CONSTANTNAME2"
	static val OPERATION_NAME = "operationName"
	static val ATTRIBUTE_NAME = "attributeName"
	static val TYPE_CLASS_NAME = "TypeClass"

	@Test
	def void testCreateEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateUmlView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_NAME)
			assertUmlEnumTraits(umlEnum, ENUM_NAME, VisibilityKind.PACKAGE_LITERAL, false, false, defaultUmlModel, #[])
		]
	}

	@Test
	def void testCreateEnumInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaEnumInPackage(#[PACKAGE_NAME], ENUM_NAME)
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierExistsInRootPackage()
		validateUmlView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val umlEnum = umlPackage.claimEnum(ENUM_NAME)
			assertUmlEnumTraits(umlEnum, ENUM_NAME, VisibilityKind.PACKAGE_LITERAL, false, false, umlPackage, #[])
		]
	}

	@Test
	def void testRenameEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			claimJavaEnum(ENUM_NAME) => [
				changeNameWithCompilationUnit(ENUM_RENAME)
			]
			moveJavaRootElement(claimJavaCompilationUnit(ENUM_RENAME))
		]
		assertSingleEnumWithNameInRootPackage(ENUM_RENAME)
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		validateUmlView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_RENAME)
			assertThat(umlEnum.name, is(ENUM_RENAME))
		]
	}

	@Test
	def void testMoveClass() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			moveJavaRootElement(claimJavaCompilationUnit(ENUM_NAME) => [
				namespaces += PACKAGE_NAME
				updateCompilationUnitName(ENUM_NAME)
			])
		]
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierExistsInRootPackage()
		changeJavaView [
			moveJavaRootElement(claimJavaCompilationUnit(PACKAGE_NAME + "." + ENUM_NAME) => [
				namespaces.clear
				updateCompilationUnitName(ENUM_NAME)
			])
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
	}

	@Test
	def void testDeleteEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			EcoreUtil.delete(claimJavaEnum(ENUM_NAME))
		]
		assertNoClassifierWithNameInRootPackage(ENUM_NAME)
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddEnumConstant() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			claimJavaEnum(ENUM_NAME) => [
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME
				]
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateUmlView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_NAME)
			assertUmlEnumHasLiteral(umlEnum, UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME])
		]
	}

	@Test
	def void testDeleteEnumConstant() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			claimJavaEnum(ENUM_NAME) => [
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME
				]
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME_2
				]
			]
		]
		changeJavaView [
			claimJavaEnum(ENUM_NAME) => [
				EcoreUtil.delete(constants.remove(0))
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateUmlView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_NAME)
			assertUmlEnumDontHaveLiteral(umlEnum,
				UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME])
			assertUmlEnumHasLiteral(umlEnum, UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME_2])
		]
	}

	@Test
	def void testAddEnumMethod() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeJavaView [
			claimJavaEnum(ENUM_NAME) => [
				members += MembersFactory.eINSTANCE.createClassMethod => [
					name = OPERATION_NAME
					typeReference = TypesFactory.eINSTANCE.createVoid
					makePublic
				]
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		validateUmlAndJavaClassesView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_NAME)
			val umlOperation = umlEnum.claimOperation(OPERATION_NAME)
			val javaMethod = claimJavaEnum(ENUM_NAME).claimClassMethod(OPERATION_NAME)
			assertUmlOperationTraits(umlOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
				umlEnum, null)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	@Test
	def void testAddEnumAttribute() {
		createJavaEnumInRootPackage(ENUM_NAME)
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		changeJavaView [
			val typeClass = claimJavaClass(TYPE_CLASS_NAME)
			claimJavaEnum(ENUM_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(typeClass)
					makePrivate
				]
			]
		]
		validateUmlAndJavaClassesView [
			val umlEnum = defaultUmlModel.claimEnum(ENUM_NAME)
			val umlTypeClass = defaultUmlModel.claimClass(TYPE_CLASS_NAME)
			val umlAttribute = umlEnum.claimAttribute(ATTRIBUTE_NAME)
			val javaField = claimJavaEnum(ENUM_NAME).claimField(ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlEnum, null, null)
			assertElementsEqual(umlAttribute, javaField)
		]
	}

	static class BidirectionalTest extends JavaToUmlEnumTest {
		override setupTransformationDirection() {
			configureBidirectionalExecution()
		}
	}

}
