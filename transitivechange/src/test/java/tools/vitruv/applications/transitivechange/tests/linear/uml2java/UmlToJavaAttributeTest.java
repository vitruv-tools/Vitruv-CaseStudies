package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaAttributeTraits;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaMemberContainerDontHaveMember;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableFinal;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableHasVisibility;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableStatic;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.buildGetterName;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.buildSetterName;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.javaGetterForAttributeExists;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.javaSetterForAttributeExists;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.createUmlAttribute;

import java.util.Objects;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil;

/**
 * This Test class checks the creating, deleting and modifying of attributes in den uml to java
 * direction.
 *
 * @author Fei
 */
public class UmlToJavaAttributeTest extends UmlToJavaTransformationTest {
	private static final String ATTRIBUTE_NAME = "attributName";
	private static final String ATTRIBUTE_RENAME = "attributeRenamed";
	private static final String STANDARD_ATTRIBUTE_NAME = "standardAttributName";
	private static final String CLASS_NAME = "ClassName";
	private static final String TYPE_CLASS = "TypeClass";

	private Property uAttr;
	private Class uClass;
	private Class typeClass;
	private PrimitiveType pType;

	@BeforeEach
	public void before() {
		uClass = createSimpleUmlClass(getRootElement(), CLASS_NAME);
		typeClass = createSimpleUmlClass(getRootElement(), TYPE_CLASS);
		uAttr = createUmlAttribute(ATTRIBUTE_NAME, typeClass, VisibilityKind.PUBLIC_LITERAL, false, false);
		uClass.getOwnedAttributes().add(uAttr);
		pType = UmlTypeUtil.getUmlPrimitiveTypes(resourceRetriever).stream()
			.filter(it -> Objects.equals(it.getName(), "int"))
			.findFirst().orElse(null);
		propagate();
	}

	@Test
	public void testCreatePrimitiveAttribute() {
		var attr = createUmlAttribute(STANDARD_ATTRIBUTE_NAME, pType, VisibilityKind.PUBLIC_LITERAL, false, false);
		uClass.getOwnedAttributes().add(attr);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jAttr = getCorrespondingAttribute(attr);
		assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createInt(), false, false, jClass);
		assertElementsEqual(attr, jAttr);
	}

	@Test
	public void testCreateAttribute() {
		var attr = uClass.createOwnedAttribute(STANDARD_ATTRIBUTE_NAME, typeClass);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jtypeClass = getCorrespondingClass(typeClass);
		var jAttr = getCorrespondingAttribute(attr);
		assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC,
			createNamespaceClassifierReference(jtypeClass), false, false, jClass);
		assertElementsEqual(attr, jAttr);

	}

	@Test
	public void testRenameAttribute() {
		uAttr.setName(ATTRIBUTE_RENAME);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jAttr = getCorrespondingAttribute(uAttr);
		assertEquals(ATTRIBUTE_RENAME, uAttr.getName());
		assertElementsEqual(uAttr, jAttr);
		assertTrue(javaGetterForAttributeExists(jAttr));
		assertTrue(javaSetterForAttributeExists(jAttr));
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME);
	}

	@Test
	public void testDeleteAttribute() {
		uAttr.destroy();
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME);
	}

	@Test
	public void testStaticAttribute() {
		uAttr.setIsStatic(true);
		propagate();

		var jAttr = getCorrespondingAttribute(uAttr);
		assertJavaModifiableStatic(jAttr, true);
		assertElementsEqual(uAttr, jAttr);
	}

	@Test
	public void testFinalAttribute() {
		uAttr.setIsReadOnly(true);
		propagate();

		var jAttr = getCorrespondingAttribute(uAttr);
		assertJavaModifiableFinal(jAttr, true);
		assertElementsEqual(uAttr, jAttr);
	}

	@Test
	public void testAttributeVisibility() {
		uAttr.setVisibility(VisibilityKind.PRIVATE_LITERAL);
		propagate();

		var jAttr = getCorrespondingAttribute(uAttr);
		assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PRIVATE);
		assertElementsEqual(uAttr, jAttr);

		uAttr.setVisibility(VisibilityKind.PACKAGE_LITERAL);
		propagate();

		jAttr = getCorrespondingAttribute(uAttr);
		assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PACKAGE);
		assertElementsEqual(uAttr, jAttr);
	}

	@Test
	public void testChangeAttributeType() {
		uAttr.setType(pType);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jAttr = getCorrespondingAttribute(uAttr);
		assertJavaAttributeTraits(jAttr, ATTRIBUTE_NAME, JavaVisibility.PUBLIC, TypesFactory.eINSTANCE.createInt(), false,
			false, jClass);
		assertElementsEqual(uAttr, jAttr);
	}

	@Test
	public void testMoveAttribute() {
		var uClass2 = createSimpleUmlClass(getRootElement(), "ClassName2");
		uClass2.getOwnedAttributes().add(uAttr);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jClass2 = getCorrespondingClass(uClass2);
		var jAttr = getCorrespondingAttribute(uAttr);
		assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME);
		assertTrue(jClass.getMethods().stream().noneMatch(it -> Objects.equals(it.getName(), buildGetterName(ATTRIBUTE_NAME))));
		assertTrue(jClass.getMethods().stream().noneMatch(it -> Objects.equals(it.getName(), buildSetterName(ATTRIBUTE_NAME))));
		assertFalse(isNullOrEmpty(jClass2.getMembersByName(ATTRIBUTE_NAME)));
		assertElementsEqual(uAttr, jAttr);
		assertTrue(javaGetterForAttributeExists(jAttr));
		assertTrue(javaSetterForAttributeExists(jAttr));
	}
}
