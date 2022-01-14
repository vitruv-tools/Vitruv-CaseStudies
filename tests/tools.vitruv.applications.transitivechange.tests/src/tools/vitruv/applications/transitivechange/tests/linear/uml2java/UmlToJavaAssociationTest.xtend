package tools.vitruv.applications.transitivechange.tests.linear.uml2java

import org.eclipse.uml2.uml.LiteralUnlimitedNatural

import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*
import static tools.vitruv.applications.umljava.tests.util.JavaUmlElementEqualityValidation.*

/**
 * This test class contains basic test for associations.
 * 
 * @author Fei
 */
class UmlToJavaAssociationTest extends UmlToJavaTransformationTest {

	static val CLASSNAME_1 = "ClassName1"
	static val CLASSNAME_2 = "ClassName2"

	var org.eclipse.uml2.uml.Class uClass1
	var org.eclipse.uml2.uml.Class uClass2

	@BeforeEach
	def void before() {
		uClass1 = createSimpleUmlClass(rootElement, CLASSNAME_1)
		uClass2 = createSimpleUmlClass(rootElement, CLASSNAME_2)
		propagate
	}

	@Test
	def void testCreateAssociation1() {
		this.userInteraction.addNextSingleSelection(0)
		createDirectedAssociation(uClass1, uClass2, 0, 1)
		propagate

		val uAttribute = uClass1.ownedAttributes.head
		val jClass1 = getCorrespondingClass(uClass1)
		val jAttribute = getCorrespondingAttribute(uAttribute)
		val jClass2 = getCorrespondingClass(uClass2)
		assertJavaElementHasTypeRef(jAttribute, createNamespaceClassifierReference(jClass2))
		assertElementsEqual(uClass1, jClass1)
		assertElementsEqual(uAttribute, jAttribute)
	}

	@Test
	def void testCreateAssociation2() {
		this.userInteraction.addNextSingleSelection(0)
		createDirectedAssociation(uClass1, uClass2, 1, 1)
		propagate

		val uAttribute = uClass1.ownedAttributes.head
		val jClass1 = getCorrespondingClass(uClass1)
		val jAttribute = getCorrespondingAttribute(uAttribute)
		val jClass2 = getCorrespondingClass(uClass2)
		assertJavaElementHasTypeRef(jAttribute, createNamespaceClassifierReference(jClass2))
		assertElementsEqual(uClass1, jClass1)
		assertTrue(javaGetterForAttributeExists(jAttribute))
		assertTrue(javaSetterForAttributeExists(jAttribute))
	}

	@Test
	def void testCreateAssociation3() {
		this.userInteraction.addNextSingleSelection(0) // 0 is ArrayList
		createDirectedAssociation(uClass1, uClass2, 0, LiteralUnlimitedNatural.UNLIMITED)
		propagate

		val uAttribute = uClass1.ownedAttributes.head

		val jAttribute = getCorrespondingAttribute(uAttribute)
		val jClass2 = getCorrespondingClass(uClass2)
		val arrayListReference = getClassifierFromTypeReference(jAttribute.typeReference)
		assertEquals("ArrayList", arrayListReference.name)
		val innerTypeRef = getInnerTypeReferenceOfCollectionTypeReference(jAttribute.typeReference)
		assertTypeEquals(createNamespaceClassifierReference(jClass2), innerTypeRef)
	}

}
