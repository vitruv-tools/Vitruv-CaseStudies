package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlEnumDontHaveLiteral;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlEnumHasLiteral;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlEnumTraits;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationTraits;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlPropertyTraits;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaAttribute;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaClassMethod;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaEnumConstant;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaEnumConstantsFromList;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlEnumLiteralsFromList;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlEnumerationLiteral;

import com.google.common.collect.Iterables;
import java.util.List;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * This class contains Tests for creating, deleting and renaming enums.
 * Furthermore there are test to check the adding of methods, attributes and enum constant to enums
 *
 * @author Fei
 */
public class JavaToUmlEnumTest extends JavaToUmlTransformationTest {
	private static final String ENUM_NAME = "EnumName";
	private static final String ENUM_RENAME = "EnumRenamed";
	private static final String STANDARD_ENUM_NAME = "StandardEnumName";
	private static final List<String> ENUM_LITERAL_NAMES_1 = List.of("RED", "BLUE", "GREEN", "YELLOW", "PURPLE");
	private static final List<String> ENUM_LITERAL_NAMES_2 = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");
	private static final String CONSTANT_NAME = "CONSTANTNAME";
	private static final String OPERATION_NAME = "operationName";
	private static final String ATTRIBUTE_NAME = "attributeName";
	private static final String TYPECLASS = "TypeClass";
	private Enumeration jEnum;
	private final List<EnumConstant> enumConstants1 = createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_1);
	private final List<EnumConstant> enumConstants2 = createJavaEnumConstantsFromList(ENUM_LITERAL_NAMES_2);

	@BeforeEach
	public void before() {
		jEnum = createJavaEnumWithCompilationUnit(ENUM_NAME, JavaVisibility.PUBLIC, enumConstants1);
	}

	@Test
	public void testCreateEnum() {
		var enumeration = createJavaEnumWithCompilationUnit(STANDARD_ENUM_NAME, JavaVisibility.PUBLIC, enumConstants2);

		var uEnum = getCorrespondingEnum(enumeration);
		assertUmlEnumTraits(uEnum, STANDARD_ENUM_NAME, VisibilityKind.PUBLIC_LITERAL, false, false, getRegisteredUmlModel(),
			createUmlEnumLiteralsFromList(ENUM_LITERAL_NAMES_2));
		assertElementsEqual(uEnum, enumeration);
	}

	@Test
	public void testRenameEnum() {
		jEnum.setName(ENUM_RENAME);
		propagate();

		var uEnum = getCorrespondingEnum(jEnum);
		assertEquals(ENUM_RENAME, uEnum.getName());
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testDeleteEnum() {
		assertNotNull(jEnum);
		jEnum.getContainingCompilationUnit();

		EcoreUtil.delete(jEnum);
		propagate();
		var uEnum = Iterables.getFirst(getUmlPackagedElementsbyName(org.eclipse.uml2.uml.Enumeration.class, ENUM_NAME), null);
		assertNull(uEnum);
	}

	@Test
	public void testAddEnumConstant() {
		jEnum.getConstants().add(createJavaEnumConstant(CONSTANT_NAME));
		propagate();

		var uEnum = getCorrespondingEnum(jEnum);
		assertUmlEnumHasLiteral(uEnum, createUmlEnumerationLiteral(CONSTANT_NAME));
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testDeleteEnumConstant() {
		EcoreUtil.delete(jEnum.getConstants().remove(0));
		propagate();

		var uEnum = getCorrespondingEnum(jEnum);
		assertUmlEnumDontHaveLiteral(uEnum, createUmlEnumerationLiteral(ENUM_LITERAL_NAMES_1.get(0)));
		assertElementsEqual(uEnum, jEnum);
	}

	@Test
	public void testAddEnumMethod() {
		var jMethod = createJavaClassMethod(OPERATION_NAME, TypesFactory.eINSTANCE.createVoid(), JavaVisibility.PUBLIC,
			false, false, null);
		jEnum.getMembers().add(jMethod);
		propagate();

		var uOperation = getCorrespondingMethod(jMethod);
		var uEnum = getCorrespondingEnum(jEnum);
		assertUmlOperationTraits(uOperation, OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false,
			false, uEnum, null);
		assertElementsEqual(uOperation, jMethod);
	}

	@Test
	public void testAddEnumAttribute() {
		var typeClass = createJavaClassWithCompilationUnit(TYPECLASS, JavaVisibility.PUBLIC, false, false);
		var jAttr = createJavaAttribute(ATTRIBUTE_NAME, createNamespaceClassifierReference(typeClass),
			JavaVisibility.PRIVATE, false, false);
		jEnum.getMembers().add(jAttr);
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		var uTypeClass = getCorrespondingClass(typeClass);
		var uEnum = getCorrespondingEnum(jEnum);
		assertUmlPropertyTraits(uAttr, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass, false, false, uEnum,
			null, null);
		assertElementsEqual(uAttr, jAttr);
	}
}
