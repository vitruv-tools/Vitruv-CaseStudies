package tools.vitruv.applications.umljava.tests.java2uml

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.Test

import static tools.vitruv.applications.umljava.tests.util.TestUtil.assertElementsEqual
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import org.emftext.language.java.members.MembersFactory
import org.eclipse.uml2.uml.UMLFactory

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

	private def assertSingleEnumWithNameInRootPackage(String name) {
		assertSingleEnumWithNameInRootPackage(name, name)
	}

	private def assertSingleEnumWithNameInRootPackage(String name, String compilationUnitName) {
		assertSingleClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, name, compilationUnitName)
	}

	private def assertSingleEnumWithNameInPackage(String packageName, String name) {
		assertSingleClassifierWithNameInPackage(org.emftext.language.java.classifiers.Enumeration,
			org.eclipse.uml2.uml.Enumeration, packageName, name)
	}

	@Test
	def void testCreateEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		createUmlView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_NAME)
			assertUmlEnumTraits(umlEnum, ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, uniqueDefaultUmlModel,
				#[])
		]
	}
	
	@Test
	def void testCreateEnumInPackage() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaEnumInPackage(#[PACKAGE_NAME], ENUM_NAME)
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlEnum = umlPackage.getUniqueUmlEnumWithName(ENUM_NAME)
			assertUmlEnumTraits(umlEnum, ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, umlPackage,
				#[])
		]
	}

	@Test
	def void testRenameEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				name = ENUM_RENAME
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_RENAME, ENUM_NAME)
		createUmlView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_RENAME)
			assertEquals(ENUM_RENAME, umlEnum.name)
		]
	}
	
	@Test
	def void testMoveClass() {
		createJavaPackageInRootPackage(PACKAGE_NAME)
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			moveJavaRootElement(getUniqueJavaCompilationUnitWithName(ENUM_NAME) => [
				namespaces += PACKAGE_NAME
			])
		]
		assertSingleEnumWithNameInPackage(PACKAGE_NAME, ENUM_NAME)
		createUmlView => [
			val umlPackage = uniqueDefaultUmlModel.getUniqueUmlPackageWithName(PACKAGE_NAME)
			val umlEnum = umlPackage.getUniqueUmlEnumWithName(ENUM_NAME)
			assertUmlEnumTraits(umlEnum, ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, umlPackage,
				#[])
		]
	}

	@Test
	def void testDeleteEnum() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			EcoreUtil.delete(getUniqueJavaEnumWithName(ENUM_NAME))
		]
		assertNoClassifierExistsInRootPackage()
	}

	@Test
	def void testAddEnumConstant() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME
				]
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		createUmlView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_NAME)
			assertUmlEnumHasLiteral(umlEnum, UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME])
		]
	}

	@Test
	def void testDeleteEnumConstant() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME
				]
				constants += MembersFactory.eINSTANCE.createEnumConstant => [
					name = CONSTANT_NAME_2
				]
			]
		]
		changeView(createJavaClassesView) [
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				EcoreUtil.delete(constants.remove(0))
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		createUmlView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_NAME)
			assertUmlEnumDontHaveLiteral(umlEnum, UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME])
			assertUmlEnumHasLiteral(umlEnum, UMLFactory.eINSTANCE.createEnumerationLiteral => [name = CONSTANT_NAME_2])
		]
	}

	@Test
	def void testAddEnumMethod() {
		createJavaEnumInRootPackage(ENUM_NAME)
		changeView(createJavaClassesView) [
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				members += MembersFactory.eINSTANCE.createClassMethod => [
					name = OPERATION_NAME
					typeReference = TypesFactory.eINSTANCE.createVoid
					makePublic
				]
			]
		]
		assertSingleEnumWithNameInRootPackage(ENUM_NAME)
		createUmlAndJavaClassesView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_NAME)
			val umlOperation = umlEnum.getUniqueUmlOperationWithName(OPERATION_NAME)
			val javaEnum = getUniqueJavaEnumWithName(ENUM_NAME)
			val javaMethod = javaEnum.getUniqueJavaClassOperationWithName(OPERATION_NAME)
			assertUmlOperationTraits(umlOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
				umlEnum, null)
			assertElementsEqual(umlOperation, javaMethod)
		]
	}

	@Test
	def void testAddEnumAttribute() {
		createJavaEnumInRootPackage(ENUM_NAME)
		createJavaClassInRootPackage(TYPE_CLASS_NAME)
		changeView(createJavaClassesView) [
			val typeClass = getUniqueJavaClassWithName(TYPE_CLASS_NAME)
			getUniqueJavaEnumWithName(ENUM_NAME) => [
				members += MembersFactory.eINSTANCE.createField => [
					name = ATTRIBUTE_NAME
					typeReference = createNamespaceClassifierReference(typeClass)
					makePrivate
				]
			]
		]
		createUmlAndJavaClassesView => [
			val umlEnum = uniqueDefaultUmlModel.getUniqueUmlEnumWithName(ENUM_NAME)
			val umlTypeClass = uniqueDefaultUmlModel.getUniqueUmlClassWithName(TYPE_CLASS_NAME)
			val umlAttribute = umlEnum.getUniqueUmlAttributeWithName(ATTRIBUTE_NAME)
			val javaEnum = getUniqueJavaEnumWithName(ENUM_NAME)
			val javaField = javaEnum.getUniqueJavaClassFieldWithName(ATTRIBUTE_NAME)
			assertUmlPropertyTraits(umlAttribute, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlTypeClass, false,
				false, umlEnum, null, null)
			assertElementsEqual(umlAttribute, javaField)
		]
	}
}
