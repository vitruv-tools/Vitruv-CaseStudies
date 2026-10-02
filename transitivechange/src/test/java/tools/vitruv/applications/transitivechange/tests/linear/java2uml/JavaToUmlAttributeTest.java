package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassDontHaveOperation;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlNamedElementHasVisibility;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlPropertyTraits;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaAttribute;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaGetterForAttribute;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setFinal;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setStatic;
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.createJavaPrimitiveType;
import static tools.vitruv.applications.util.temporary.uml.UmlTypeUtil.getUmlPrimitiveTypes;

import java.util.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.members.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaStandardType;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * Test class for testing the attribute reactions.
 *
 * @author Fei
 */
public class JavaToUmlAttributeTest extends JavaToUmlTransformationTest {
	private static final String ATTRIBUTE_NAME = "attributName";
	private static final String ATTRIBUTE_RENAME = "attributeRenamed";
	private static final String STANDARD_ATTRIBUTE_NAME = "standardAttributName";
	private static final String CLASS_NAME = "ClassName";
	private static final String TYPE_CLASS = "TypeClass";

	private Field jAttr;
	private Class jClass;
	private Class typeClass;

	/**
	 * Initializes two java classes. One class contains an attribute.
	 */
	@BeforeEach
	public void testSetup() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
		typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_CLASS);
		jAttr = createJavaAttribute(ATTRIBUTE_NAME, createJavaPrimitiveType(JavaStandardType.INT),
			JavaVisibility.PRIVATE, false, false);
		jClass.getMembers().add(jAttr);
		propagate();
	}

	/**
	 * Tests the creation of an attribute with primitive type.
	 * Checks if a corresponding uml attribute exists afterwards.
	 */
	@Test
	public void testCreatePrimitiveAttribute() {
		var attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createJavaPrimitiveType(JavaStandardType.INT),
			JavaVisibility.PRIVATE, false, false);
		jClass.getMembers().add(attr);
		var getter = createJavaGetterForAttribute(attr, JavaVisibility.PRIVATE);
		jClass.getMembers().add(getter);
		propagate();

		var uAttr = getCorrespondingAttribute(attr);
		var uClass = getCorrespondingClass(jClass);
		var umlInteger = getUmlPrimitiveTypes(resourceRetriever).stream()
			.filter(it -> Objects.equals(it.getName(), "int"))
			.findFirst().orElse(null);
		assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, umlInteger,
			false, false, uClass, null, null);
		assertElementsEqual(uAttr, attr);
	}

	/**
	 * Tests the creation of an attribute with a type that references a class.
	 * Checks if a corresponding uml attribute exists afterwards.
	 */
	@Test
	public void testCreateAttribute() {
		var attr = createJavaAttribute(STANDARD_ATTRIBUTE_NAME, createNamespaceClassifierReference(typeClass),
			JavaVisibility.PRIVATE, false, false);
		jClass.getMembers().add(attr);
		propagate();

		var uAttr = getCorrespondingAttribute(attr);
		var uClass = getCorrespondingClass(jClass);
		var uTypeClass = getCorrespondingClass(typeClass);
		assertUmlPropertyTraits(uAttr, STANDARD_ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass,
			false, false, uClass, null, null);
		assertElementsEqual(uAttr, attr);
	}

	/**
	 * Tests if an attribute rename is correctly synchronized with the uml attribute.
	 */
	@Test
	public void testRenameAttribute() {
		jAttr.setName(ATTRIBUTE_RENAME);
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		var uClass = getCorrespondingClass(jClass);
		assertEquals(ATTRIBUTE_RENAME, uAttr.getName());
		assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME);
		assertElementsEqual(uAttr, jAttr);
	}

	/**
	 * Test if deleting the java attribute also deletes the corresponding uml attribute.
	 */
	@Test
	public void testDeleteAttribute() {
		assertNotNull(getCorrespondingAttribute(jAttr));

		EcoreUtil.delete(jAttr);
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertUmlClassDontHaveOperation(uClass, ATTRIBUTE_NAME);
	}

	/**
	 * Checks if a type change is correctly reflected on the uml attribute.
	 */
	@Test
	public void testChangeAttributeType() {
		jAttr.setTypeReference(createNamespaceClassifierReference(typeClass));
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		var uClass = getCorrespondingClass(jClass);
		var uTypeClass = getCorrespondingClass(typeClass);
		assertUmlPropertyTraits(uAttr, ATTRIBUTE_NAME, VisibilityKind.PRIVATE_LITERAL, uTypeClass, false, false, uClass,
			null, null);
		assertElementsEqual(uAttr, jAttr);
	}

	/**
	 * Tests if a change to static is correctly reflected on the uml attribute.
	 */
	@Test
	public void testStaticAttribute() {
		setStatic(jAttr, true);
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		assertTrue(uAttr.isStatic());
		assertElementsEqual(uAttr, jAttr);
	}

	/**
	 * Tests if a change to final is correctly reflected on the uml attribute.
	 */
	@Test
	public void testFinalAttribute() {
		setFinal(jAttr, true);
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		assertTrue(uAttr.isReadOnly());
		assertElementsEqual(uAttr, jAttr);
	}

	/**
	 * Tests if visibility changes are correctly reflected on the uml attribute.
	 */
	@Test
	public void testAttributeVisibility() {
		jAttr.makePublic();
		propagate();

		var uAttr = getCorrespondingAttribute(jAttr);
		assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PUBLIC_LITERAL);
		assertElementsEqual(uAttr, jAttr);

		jAttr.makeProtected();
		propagate();

		uAttr = getCorrespondingAttribute(jAttr);
		assertUmlNamedElementHasVisibility(uAttr, VisibilityKind.PROTECTED_LITERAL);
		assertElementsEqual(uAttr, jAttr);
	}
}
