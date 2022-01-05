package tools.vitruv.applications.umljava.tests.uml2java

import org.eclipse.uml2.uml.LiteralUnlimitedNatural

import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.domains.java.util.JavaModificationUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * This test class contains basic tests for associations.
 */
class UmlToJavaAssociationTest extends AbstractUmlToJavaTest {
	static val CLASS_NAME_1 = "ClassName1"
	static val CLASS_NAME_2 = "ClassName2"

	private def assertClassWithNameInRootPackage(String name) {
		assertClassifierWithNameInRootPackage(org.emftext.language.java.classifiers.Class, org.eclipse.uml2.uml.Class,
			name)
	}

	@Test
	def void testCreateAssociationZeroToOne() {
		createClassInRootPackage(CLASS_NAME_1)
		createClassInRootPackage(CLASS_NAME_2)
		changeUmlModel [
			val class1 = claimClass(CLASS_NAME_1)
			val class2 = claimClass(CLASS_NAME_2)
			createDirectedAssociation(class1, class2, 0, 1)
		]
		assertClassWithNameInRootPackage(CLASS_NAME_1)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaAttribute = claimJavaClass(CLASS_NAME_1).claimField(CLASS_NAME_2.toFirstLower)
			val referencedJavaClass = claimJavaClass(CLASS_NAME_2)
			assertJavaElementHasTypeRef(javaAttribute, createNamespaceClassifierReference(referencedJavaClass))
			assertTrue(javaGetterForAttributeExists(javaAttribute))
			assertTrue(javaSetterForAttributeExists(javaAttribute))
		]
	}

	@Test
	def void testCreateAssociationOne() {
		createClassInRootPackage(CLASS_NAME_1)
		createClassInRootPackage(CLASS_NAME_2)
		changeUmlModel [
			val class1 = claimClass(CLASS_NAME_1)
			val class2 = claimClass(CLASS_NAME_2)
			createDirectedAssociation(class1, class2, 1, 1)
		]
		assertClassWithNameInRootPackage(CLASS_NAME_1)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaAttribute = claimJavaClass(CLASS_NAME_1).claimField(CLASS_NAME_2.toFirstLower)
			val referencedJavaClass = claimJavaClass(CLASS_NAME_2)
			assertJavaElementHasTypeRef(javaAttribute, createNamespaceClassifierReference(referencedJavaClass))
			assertTrue(javaGetterForAttributeExists(javaAttribute))
			assertTrue(javaSetterForAttributeExists(javaAttribute))
		]
	}

	@Test
	def void testCreateAssociationUnlimited() {
		this.userInteraction.onNextMultipleChoiceSingleSelection().respondWithChoiceMatching[it.contains("ArrayList")]
		createClassInRootPackage(CLASS_NAME_1)
		createClassInRootPackage(CLASS_NAME_2)
		changeUmlModel [
			val class1 = claimClass(CLASS_NAME_1)
			val class2 = claimClass(CLASS_NAME_2)
			createDirectedAssociation(class1, class2, 0, LiteralUnlimitedNatural.UNLIMITED)
		]
		assertClassWithNameInRootPackage(CLASS_NAME_1)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		createJavaClassesView => [
			val javaAttribute = claimJavaClass(CLASS_NAME_1).claimField(CLASS_NAME_2.toFirstLower)
			val referencedJavaClass = claimJavaClass(CLASS_NAME_2)
			val arrayListReference = getClassifierFromTypeReference(javaAttribute.typeReference)
			assertEquals("ArrayList", arrayListReference.name)
			val innerTypeRef = getInnerTypeReferenceOfCollectionTypeReference(javaAttribute.typeReference)
			assertTypeEquals(createNamespaceClassifierReference(referencedJavaClass), innerTypeRef)
		]
	}

}
