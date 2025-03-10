package tools.vitruv.applications.testutility.integration

import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Feature
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.TypedElement
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.ValueSpecification
import org.eclipse.uml2.uml.EnumerationLiteral
import java.util.List
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.LiteralInteger
import org.eclipse.uml2.uml.LiteralUnlimitedNatural

import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * Class for assertions that only involves UML elements.
 */
@Utility
class UmlElementsTestAssertions {

	/**
	 * Asserts that the given class has the given traits. Does not check operations/attributes of the class.
	 */
	def static void assertUmlClassTraits(Class uClass, String name, VisibilityKind visibility, boolean isAbstract,
		boolean isFinal, Package uPackage) {
		assertEquals(name, uClass.name, "Class name is not as expected")
		assertUmlNamedElementHasVisibility(uClass, visibility)
		assertUmlClassHasFinalValue(uClass, isFinal)
		assertUmlClassHasAbstractValue(uClass, isAbstract)
		assertUmlPackageableElementIsInPackage(uClass, uPackage)
	}

	/**
	 * Asserts that the given interface has the given traits. Does not check operations/attributes of the interface.
	 */
	def static void assertUmlInterfaceTraits(Interface uInterface, String name, VisibilityKind visibility,
		Package uPackage) {
		assertEquals(name, uInterface.name, "Interface name is not as expected")
		assertUmlNamedElementHasVisibility(uInterface, visibility)
		assertUmlPackageableElementIsInPackage(uInterface, uPackage)
	}

	/**
	 * Asserts that the given enum has the given traits. Does not check operations/attributes of the enum.
	 * But it checks the enum literals if they are pairwise corresponding  to the given literals list (by name)
	 */
	def static void assertUmlEnumTraits(Enumeration uEnum, String name, VisibilityKind visibility, boolean isAbstract,
		boolean isFinal, Package uPackage, List<EnumerationLiteral> enumLiteralList) {
		assertEquals(name, uEnum.name, "Enumeration name is not as expected")
		assertUmlNamedElementHasVisibility(uEnum, visibility)
		assertUmlPackageableElementIsInPackage(uEnum, uPackage)
		assertUmlEnumLiteralListEquals(enumLiteralList, uEnum.ownedLiterals)
	}

	/**
	 * Asserts that the given property has the given traits.
	 */
	def static void assertUmlPropertyTraits(Property uProperty, String name, VisibilityKind visibility, Type type,
		boolean isStatic, boolean isFinal, Classifier containingClassifier, ValueSpecification lowerMultiplicity,
		ValueSpecification upperMultiplicity) {
		assertEquals(name, uProperty.name, "Property name is not as expected")
		assertUmlNamedElementHasVisibility(uProperty, visibility)
		assertUmlTypedElementHasType(uProperty, type)
		assertUmlFeatureHasStaticValue(uProperty, isStatic)
		assertUmlPropertyHasFinalValue(uProperty, isFinal)
		assertEquals(containingClassifier.name, (uProperty.eContainer as Classifier).name,
			"Containing classifier is not as expected")
		assertUmlValueSpecificationEquals(lowerMultiplicity, uProperty.lowerValue)
		assertUmlValueSpecificationEquals(upperMultiplicity, uProperty.upperValue)
	}

	/**
	 * Asserts that the given operation has the given traits.
	 */
	def static void assertUmlOperationTraits(Operation uOperation, String name, VisibilityKind visibility,
		Type returntype, boolean isStatic, boolean isAbstract, Classifier containingClassifier,
		List<Parameter> paramList) {
		assertEquals(name, uOperation.name, "Operation name is not as expected")
		assertUmlNamedElementHasVisibility(uOperation, visibility)
		assertUmlOperationHasReturntype(uOperation, returntype)
		assertUmlFeatureHasStaticValue(uOperation, isStatic)
		assertUmlOperationHasAbstractValue(uOperation, isAbstract)
		assertEquals(containingClassifier.name, (uOperation.eContainer as Classifier).name,
			"Containing classifier is not as expected")
		assertUmlParameterListEquals(paramList, uOperation.ownedParameters)
	}

	/**
	 * Asserts that the given parameter has the given traits.
	 */
	def static void assertUmlParameterTraits(Parameter uParam, String name, Type type) {
		assertEquals(name, uParam.name, "Parameter name is not as expected")
		assertUmlTypedElementHasType(uParam, type)
	}

	/**
	 * Asserts that the two given lists contain enum literals that correspond pairwise 
	 * by comparing their name
	 */
	def static void assertUmlEnumLiteralListEquals(List<EnumerationLiteral> expectedList,
		List<EnumerationLiteral> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty, [
				String.format("Enumeration literal list should be empty but is %s", actualList)
			])
		} else {
			assertEquals(expectedList.size, actualList.size, "Enumeration literal lists have different sizes")
			for (literal : expectedList) {
				val correspondingLiterals = actualList.filter[name == literal.name]
				assertEquals(1, correspondingLiterals.size, [
					String.format("There is not exactly one corresponding enumeration literal with name %s",
						literal.name)
				])
			}
		}

	}

	/**
	 * Asserts that the two given lists contain parameters that correspond pairwise 
	 * by comparing their name
	 */
	def static void assertUmlParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (expectedList.nullOrEmpty) {
			if (!actualList.nullOrEmpty) {
				assertTrue(actualList.filter[direction != ParameterDirectionKind.RETURN_LITERAL].nullOrEmpty, [
					String.format("Parameter list should be empty but has entries %s", actualList)
				])
			}
		} else {
			assertEquals(expectedList.size, expectedList.size, "Parameter lists have different sizes")
			for (param : expectedList) {
				val correspondingParams = actualList.filter[name == param.name]
				assertEquals(1, correspondingParams.size, [
					String.format("There is not exactly one corresponding parameter with name %s", param.name)
				])
				assertUmlParameterTraits(correspondingParams.head, param.name, param.type)
			}
		}
	}

	def static dispatch void assertUmlValueSpecificationEquals(ValueSpecification value1, ValueSpecification value2) {
		fail("We currently only support LiteralInteger and LiteralUnlimitedNatural ValueSpecifications")
	}

	def static dispatch void assertUmlValueSpecificationEquals(LiteralInteger value1, LiteralInteger value2) {
		assertEquals(value1.integerValue, value2.integerValue, "Literal integers are expected to be equal")
	}

	def static dispatch void assertUmlValueSpecificationEquals(LiteralUnlimitedNatural value1,
		LiteralUnlimitedNatural value2) {
		// Do nothing -> Assertion passed
	}

	def static dispatch void assertUmlValueSpecificationEquals(Void value1, Void value2) {
		// Do nothing -> Assertion passed
	}

	def static void assertUmlPackageableElementIsInPackage(PackageableElement packageable, Package uPackage) {
		assertThat("Packageable element " + uPackage + " must be in expected package",
			getUmlNamespaceAsStringList(uPackage), is(getUmlNamespaceAsStringList(packageable.namespace)))
	}

	def static void assertUmlPackageableElementIsNotInPackage(PackageableElement packageable, Package uPackage) {
		for (elem : uPackage.packagedElements) {
			assertFalse(elem.name.equals(packageable.name) && elem.class.equals(packageable.class), [
				String.format("Package %s contains element %s although it should not", uPackage, packageable)
			])
		}
	}

	def static void assertUmlNamedElementHasVisibility(NamedElement uElement, VisibilityKind visibility) {
		assertEquals(visibility, uElement.visibility, [
			String.format("Visibility of element %s not as expected", uElement)
		])
	}

	def static void assertUmlFeatureHasStaticValue(Feature uFeature, boolean isStatic) {
		assertEquals(isStatic, uFeature.static, [
			String.format("Static property of feature %s not as expected", uFeature)
		])
	}

	def static void assertUmlClassHasFinalValue(Class uClass, boolean isFinal) {
		assertEquals(isFinal, uClass.finalSpecialization, [
			String.format("Final property of class %s not as expected", uClass)
		])
	}

	def static void assertUmlPropertyHasFinalValue(Property uAttribute, boolean isFinal) {
		assertEquals(isFinal, uAttribute.readOnly, [
			String.format("Final property of attribute %s not as expected", uAttribute)
		])
	}

	def static void assertUmlClassHasAbstractValue(Class uClass, boolean isAbstract) {
		assertEquals(isAbstract, uClass.abstract, [
			String.format("Abstract property of class %s not as expected", uClass)
		])
	}

	// UmlClass and UmlOperation dont have a common superClass for abstract
	def static void assertUmlOperationHasAbstractValue(Operation uOperation, boolean isAbstract) {
		assertEquals(isAbstract, uOperation.abstract, [
			String.format("Abstract property of operation %s not as expected", uOperation)
		])
	}

	def static void assertUmlOperationHasFinalValue(Operation uOperation, boolean isFinal) {
		assertEquals(isFinal, uOperation.isLeaf, [
			String.format("Final property of operation %s not as expected", uOperation)
		])
	}

	/**
	 * Uml-TypedElement includes Property and Parameter, but NOT Operation 
	 */
	def static void assertUmlTypedElementHasType(TypedElement typedElem, Type type) {
		assertUmlTypeEquals(type, typedElem.type)
	}

	def static dispatch void assertUmlTypeEquals(Classifier classifier1, Classifier classifier2) {
		assertEquals(classifier1.name, classifier2.name, "Types should be equal")
	}

	def static dispatch void assertUmlTypeEquals(Enumeration enum1, Enumeration enum2) {
		assertEquals(enum1.name, enum2.name, "Types should be equal")
	}

	// DataType is a SubClass of Classifier.
	// PrimitiveType and Enumeration are SubClasses of DataTypes.
	// We need extra dispatch-Method for Enumeration and Classifiers so they are not
	// dispatched to DataType.
	def static dispatch void assertUmlTypeEquals(PrimitiveType primtype1, PrimitiveType primtype2) {
		assertEquals(primtype1.name, primtype2.name, "Types should be equal")
	}

	def static dispatch void assertUmlTypeEquals(DataType dataType1, DataType dataType2) {
		assertEquals(dataType1.name, dataType2.name, "Types should be equal")
		assertTrue(dataType1.ownedAttributes.nullOrEmpty == dataType2.ownedAttributes.nullOrEmpty, [
			String.format("Either of the datatypes %s and %s has no attributes", dataType1, dataType2)
		])
		if (!dataType1.ownedAttributes.nullOrEmpty) {
			// We currently do not support CollectionTypes with more than 1 inner type
			assertEquals(1, dataType1.ownedAttributes.size, [
				String.format(
					"Data type %s should have exactly one attribute as we do not yet support collection types",
					dataType1)
			])
			assertEquals(1, dataType2.ownedAttributes.size, [
				String.format(
					"Data type %s should have exactly one attribute as we do not yet support collection types",
					dataType2)
			])
			assertUmlTypeEquals(dataType1.ownedAttributes.head.type, dataType2.ownedAttributes.head.type)
		}
	}

	def static dispatch void assertUmlTypeEquals(Void type1, Void type2) {
		// Do nothing, Assertion passed. 
	}

	def static dispatch void assertUmlTypeEquals(Type type1, Type type2) {
		fail(type1 + " and " + type2 +
			" are not comparable or are neither Classifiers nor PrimitiveTypes nor DataTypes")
	}

	def static void assertUmlOperationHasReturntype(Operation uOperation, Type type) {
		assertUmlTypeEquals(uOperation.type, type)
	}

	def static void assertUmlClassHasUniqueOperation(Class uClass, String operationName) {
		assertFalse(uClass.ownedOperations.filter[name == operationName].nullOrEmpty, [
			String.format("Class %s should contain operation with name %s but does not", uClass, operationName)
		])
	}

	def static void assertUmlClassDontHaveOperation(Class uClass, String operationName) {
		assertTrue(uClass.ownedOperations.filter[name == operationName].nullOrEmpty, [
			String.format("Class %s should not contain operation with name %s but does", uClass, operationName)
		])
	}

	def static void assertUmlInterfaceHasUniqueOperation(Interface uInterface, String operationName) {
		assertFalse(uInterface.ownedOperations.filter[name == operationName].nullOrEmpty, [
			String.format("Interface %s should contain operation with name %s but does not", uInterface, operationName)
		])
	}

	def static void assertUmlInterfaceDontHaveOperation(Interface uInterface, String operationName) {
		assertTrue(uInterface.ownedOperations.filter[name == operationName].nullOrEmpty, [
			String.format("Interface %s should not contain operation with name %s but does", uInterface, operationName)
		])
	}

	def static void assertUmlClassHasUniqueProperty(Class uClass, String propertyName) {
		assertFalse(uClass.ownedAttributes.filter[name == propertyName].nullOrEmpty, [
			String.format("Class %s should contain property with name %s but does not", uClass, propertyName)
		])
	}

	def static void assertUmlClassDontHaveProperty(Class uClass, String propertyName) {
		assertTrue(uClass.ownedAttributes.filter[name == propertyName].nullOrEmpty, [
			String.format("Class %s should not contain property with name %s but does", uClass, propertyName)
		])
	}

	def static void assertUmlOperationHasUniqueParameter(Operation uOperation, String paramName) {
		assertFalse(uOperation.ownedParameters.filter[name == paramName].nullOrEmpty, [
			String.format("Operation %s should contain parameter with name %s but does not", uOperation, paramName)
		])
	}

	def static void assertUmlOperationDontHaveParameter(Operation uOperation, String paramName) {
		assertTrue(uOperation.ownedParameters.filter[name == paramName].nullOrEmpty, [
			String.format("Operation %s should not contain parameter with name %s but does", uOperation, paramName)
		])
	}

	def static void assertUmlClassifierHasSuperClassifier(Classifier childClassifier, Classifier parentClassifier) {
		assertNotNull(childClassifier.getGeneral(parentClassifier.name), [
			String.format("Classifier %s should have super classifier %s but has not", childClassifier,
				parentClassifier)
		])
	}

	def static void assertUmlClassifierDontHaveSuperClassifier(Classifier childClassifier,
		Classifier parentClassifier) {
		assertNull(childClassifier.getGeneral(parentClassifier.name), [
			String.format("Classifier %s should not have super classifier %s but has", childClassifier,
				parentClassifier)
		])
	}

	def static void assertUmlClassHasImplement(Class uClass, Interface implementedInterface) {
		assertFalse(uClass.implementedInterfaces.filter[name == implementedInterface.name].nullOrEmpty, [
			String.format("Class %s should implement interface %s but does not", uClass, implementedInterface)
		])
	}

	def static void assertUmlClassDontHaveImplement(Class uClass, Interface implementedInterface) {
		assertTrue(uClass.implementedInterfaces.filter[name == implementedInterface.name].nullOrEmpty, [
			String.format("Class %s should not implement interface %s but does", uClass, implementedInterface)
		])
	}

	def static void assertUmlEnumHasLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertFalse(uEnum.ownedLiterals.filter[name == uLiteral.name].nullOrEmpty, [
			String.format("Enumeration %s should have literal %s but does not", uEnum, uLiteral)
		])
	}

	def static void assertUmlEnumDontHaveLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertTrue(uEnum.ownedLiterals.filter[name == uLiteral.name].nullOrEmpty, [
			String.format("Enumeration %s should not have literal %s but does", uEnum, uLiteral)
		])
	}
}
