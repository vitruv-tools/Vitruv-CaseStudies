package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaElementHasTypeRef;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertTypeEquals;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.javaGetterForAttributeExists;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.javaSetterForAttributeExists;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getInnerTypeReferenceOfCollectionTypeReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.createDirectedAssociation;

import com.google.common.collect.Iterables;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This test class contains basic test for associations.
 *
 * @author Fei
 */
public class UmlToJavaAssociationTest extends UmlToJavaTransformationTest {

	private static final String CLASSNAME_1 = "ClassName1";
	private static final String CLASSNAME_2 = "ClassName2";

	private org.eclipse.uml2.uml.Class uClass1;
	private org.eclipse.uml2.uml.Class uClass2;

	@BeforeEach
	public void before() {
		uClass1 = createSimpleUmlClass(getRootElement(), CLASSNAME_1);
		uClass2 = createSimpleUmlClass(getRootElement(), CLASSNAME_2);
		propagate();
	}

	@Test
	public void testCreateAssociation1() {
		this.getUserInteraction().addNextSingleSelection(0);
		createDirectedAssociation(uClass1, uClass2, 0, 1);
		propagate();

		var uAttribute = Iterables.getFirst(uClass1.getOwnedAttributes(), null);
		var jClass1 = getCorrespondingClass(uClass1);
		var jAttribute = getCorrespondingAttribute(uAttribute);
		var jClass2 = getCorrespondingClass(uClass2);
		assertJavaElementHasTypeRef(jAttribute, createNamespaceClassifierReference(jClass2));
		assertElementsEqual(uClass1, jClass1);
		assertElementsEqual(uAttribute, jAttribute);
	}

	@Test
	public void testCreateAssociation2() {
		this.getUserInteraction().addNextSingleSelection(0);
		createDirectedAssociation(uClass1, uClass2, 1, 1);
		propagate();

		var uAttribute = Iterables.getFirst(uClass1.getOwnedAttributes(), null);
		var jClass1 = getCorrespondingClass(uClass1);
		var jAttribute = getCorrespondingAttribute(uAttribute);
		var jClass2 = getCorrespondingClass(uClass2);
		assertJavaElementHasTypeRef(jAttribute, createNamespaceClassifierReference(jClass2));
		assertElementsEqual(uClass1, jClass1);
		assertTrue(javaGetterForAttributeExists(jAttribute));
		assertTrue(javaSetterForAttributeExists(jAttribute));
	}

	@Test
	public void testCreateAssociation3() {
		this.getUserInteraction().addNextSingleSelection(0); // 0 is ArrayList
		createDirectedAssociation(uClass1, uClass2, 0, LiteralUnlimitedNatural.UNLIMITED);
		propagate();

		var uAttribute = Iterables.getFirst(uClass1.getOwnedAttributes(), null);

		var jAttribute = getCorrespondingAttribute(uAttribute);
		var jClass2 = getCorrespondingClass(uClass2);
		var arrayListReference = getClassifierFromTypeReference(jAttribute.getTypeReference());
		assertEquals("ArrayList", arrayListReference.getName());
		var innerTypeRef = getInnerTypeReferenceOfCollectionTypeReference(jAttribute.getTypeReference());
		assertTypeEquals(createNamespaceClassifierReference(jClass2), innerTypeRef);
	}

}
