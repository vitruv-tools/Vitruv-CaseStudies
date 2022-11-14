package tools.vitruv.applications.umljava.tests.uml2java

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import tools.vitruv.change.propagation.ChangePropagationMode

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlPropertyAndAssociationUtil.*

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*

/**
 * This test class contains basic tests for associations.
 */
class UmlToJavaAssociationTest extends AbstractUmlToJavaTest {
	static val CLASS_NAME_1 = "ClassName1"
	static val CLASS_NAME_2 = "ClassName2"

	@ParameterizedTest
	@CsvSource("0,1", "1,1")
	def void testCreateAssociationSingleValued(int lowerBound, int upperBound) {
		createClassInRootPackage(CLASS_NAME_1)
		createClassInRootPackage(CLASS_NAME_2)
		changeUmlModel [
			val class1 = claimClass(CLASS_NAME_1)
			val class2 = claimClass(CLASS_NAME_2)
			createDirectedAssociation(class1, class2, lowerBound, upperBound)
		]
		assertClassWithNameInRootPackage(CLASS_NAME_1)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		validateJavaView [
			val javaAttribute = claimJavaClass(CLASS_NAME_1).claimField(CLASS_NAME_2.toFirstLower)
			val referencedJavaClass = claimJavaClass(CLASS_NAME_2)
			assertJavaElementHasTypeRef(javaAttribute, createNamespaceClassifierReference(referencedJavaClass))
			assertTrue(javaGetterForAttributeExists(javaAttribute))
			assertTrue(javaSetterForAttributeExists(javaAttribute))
		]
	}

	@ParameterizedTest
	@CsvSource("0,2", "0,-1", "1,2", "1,-1", "2,2", "2,-1")
	def void testCreateAssociationMultiValued(int lowerBound, int upperBound) {
		this.userInteraction.onNextMultipleChoiceSingleSelection().respondWithChoiceMatching[it.contains("ArrayList")]
		createClassInRootPackage(CLASS_NAME_1)
		createClassInRootPackage(CLASS_NAME_2)
		changeUmlModel [
			val class1 = claimClass(CLASS_NAME_1)
			val class2 = claimClass(CLASS_NAME_2)
			createDirectedAssociation(class1, class2, lowerBound, upperBound)
		]
		assertClassWithNameInRootPackage(CLASS_NAME_1)
		assertClassWithNameInRootPackage(CLASS_NAME_2)
		validateJavaView [
			val javaAttribute = claimJavaClass(CLASS_NAME_1).claimField(CLASS_NAME_2.toFirstLower)
			val referencedJavaClass = claimJavaClass(CLASS_NAME_2)
			val arrayListReference = getClassifierFromTypeReference(javaAttribute.typeReference)
			assertEquals("ArrayList", arrayListReference.name)
			val innerTypeRef = getInnerTypeReferenceOfCollectionTypeReference(javaAttribute.typeReference)
			assertTypeEquals(createNamespaceClassifierReference(referencedJavaClass), innerTypeRef)
		]
	}

	static class BidirectionalTest extends UmlToJavaAssociationTest {
		override protected getChangePropagationMode() {
			ChangePropagationMode.TRANSITIVE_CYCLIC
		}
	}

}
