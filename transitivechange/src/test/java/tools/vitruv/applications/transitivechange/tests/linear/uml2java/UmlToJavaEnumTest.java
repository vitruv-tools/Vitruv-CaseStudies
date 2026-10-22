package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaAttributeTraits;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaClassMethodTraits;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaEnumDontHaveConstant;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaEnumHasConstant;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaEnumTraits;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaEnumConstantsFromList;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlEnumAndAddToPackage;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlEnumLiteralsFromList;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlOperation;

import java.util.List;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * A Test class for creating, renaming and deleting enums.
 * Checks the adding of enum literals, attributes and methods to enums, too.
 *
 * @author Fei
 */
public class UmlToJavaEnumTest extends UmlToJavaTransformationTest {
	private static final String ENUM_NAME = "EnumName";
	private static final String ENUM_RENAME = "EnumRenamed";
	private static final String STANDARD_ENUM_NAME = "StandardEnumName";
	private static final List<String> ENUM_LITERAL_NAMES_1 = List.of("RED", "BLUE", "GREEN", "YELLOW", "PURPLE");
	private static final List<String> ENUM_LITERAL_NAMES_2 = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");
	private static final String LITERAL_NAME = "LITERALNAME";
	private static final String OPERATION_NAME = "operationName";
	private static final String TYPE_CLASS = "TypeClass";
	private static final String ATTRIBUTE_NAME = "attributeName";
	private Enumeration uEnum;
	private final List<EnumerationLiteral> enumLiterals1 = createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_1);
	private final List<EnumerationLiteral> enumLiterals2 = createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_2);

	@BeforeEach
	public void before() {
		uEnum = createUmlEnumAndAddToPackage(getRootElement(), ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, enumLiterals1);
		propagate();
	}

	@Test
	public void testCreateEnum() {
		var enumeration = createUmlEnumAndAddToPackage(getRootElement(), STANDARD_ENUM_NAME, VisibilityKind.PRIVATE_LITERAL,
			enumLiterals2);
		propagate();

		assertJavaFileExists(STANDARD_ENUM_NAME, new String[] {});
		var jEnum = getCorrespondingEnum(enumeration);
		assertJavaEnumTraits(jEnum, STANDARD_ENUM_NAME, JavaVisibility.PRIVATE,
			createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_2));
		assertElementsEqual(enumeration, jEnum);
	}

	@Test
	public void testRenameEnum() {
		uEnum.setName(ENUM_RENAME);
		propagate();

		assertJavaFileExists(ENUM_RENAME, new String[] {});
		assertJavaFileNotExists(ENUM_NAME, new String[] {});
		var jEnum = getCorrespondingEnum(uEnum);
		assertEquals(ENUM_RENAME, uEnum.getName());
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testDeleteEnum() {
		uEnum.destroy();
		propagate();

		assertJavaFileNotExists(ENUM_NAME, new String[] {});
	}

	@Test
	public void testAddEnumLiteral() {
		uEnum.createOwnedLiteral(LITERAL_NAME);
		propagate();

		var jEnum = getCorrespondingEnum(uEnum);
		assertJavaEnumHasConstant(jEnum, LITERAL_NAME);
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testDeleteEnumLiteral() {
		uEnum.getOwnedLiterals().remove(0);
		propagate();

		var jEnum = getCorrespondingEnum(uEnum);
		assertJavaEnumDontHaveConstant(jEnum, ENUM_LITERAL_NAMES_1.get(0));
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testAddEnumMethod() {
		var uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, null);
		uEnum.getOwnedOperations().add(uOperation);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		var jEnum = getCorrespondingEnum(uEnum);
		assertJavaClassMethodTraits(jMethod, OPERATION_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createVoid(), false, false, null, jEnum);
		assertElementsEqual(uOperation, jMethod);
	}

	@Test
	public void testAddEnumAttribute() {
		var typeClass = createSimpleUmlClass(getRootElement(), TYPE_CLASS);
		var attr = uEnum.createOwnedAttribute(ATTRIBUTE_NAME, typeClass);
		propagate();

		var jEnum = getCorrespondingEnum(uEnum);
		var jTypeClass = getCorrespondingClass(typeClass);
		var jAttr = getCorrespondingAttribute(attr);
		assertJavaAttributeTraits(jAttr, ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
			createNamespaceClassifierReference(jTypeClass), false, false, jEnum);
		assertElementsEqual(attr, jAttr);

	}

}
