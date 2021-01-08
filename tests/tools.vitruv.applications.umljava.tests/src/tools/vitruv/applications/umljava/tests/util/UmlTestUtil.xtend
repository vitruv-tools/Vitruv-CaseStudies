package tools.vitruv.applications.umljava.tests.util

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
 * Class for assertions that only involves uml elements.
 * 
 * @author Fei
 */
@Utility
class UmlTestUtil {

	/**
	 * Asserts that the given class has the given traits. Does not check operations/attributes of the class.
	 * 
	 */
	def static void assertUmlClassTraits(Class uClass, String name, VisibilityKind visibility, boolean isAbstract,
		boolean isFinal, Package uPackage) {
		assertEquals(name, uClass.name)
		assertUmlNamedElementHasVisibility(uClass, visibility)
		assertUmlClassHasFinalValue(uClass, isFinal)
		assertUmlClassHasAbstractValue(uClass, isAbstract)
		assertUmlPackageableElementIsInPackage(uClass, uPackage)
	}

	/**
	 * Asserts that the given interface has the given traits. Does not check operations/attributes of the interface.
	 * 
	 */
	def static void assertUmlInterfaceTraits(Interface uInterface, String name, VisibilityKind visibility,
		Package uPackage) {
		assertEquals(name, uInterface.name)
		assertUmlNamedElementHasVisibility(uInterface, visibility)
		assertUmlPackageableElementIsInPackage(uInterface, uPackage)
	}

	/**
	 * Asserts that the given enum has the given traits. Does not check operations/attributes of the enum.
	 * But it checks the enum literals if they are pairwise corresponding  to the given literals list (by name)
	 * 
	 */
	def static void assertUmlEnumTraits(Enumeration uEnum, String name, VisibilityKind visibility, boolean isAbstract,
		boolean isFinal, Package uPackage, List<EnumerationLiteral> enumLiteralList) {
		assertEquals(name, uEnum.name)
		assertUmlNamedElementHasVisibility(uEnum, visibility)
		assertUmlPackageableElementIsInPackage(uEnum, uPackage)
		assertUmlEnumLiteralListEquals(enumLiteralList, uEnum.ownedLiterals)
	}

	/**
	 * Asserts that the given property has the given traits.
	 * 
	 */
	def static void assertUmlPropertyTraits(Property uProperty, String name, VisibilityKind visibility, Type type,
		boolean isStatic, boolean isFinal, Classifier containingClassifier, ValueSpecification lowerMultiplicity,
		ValueSpecification upperMultiplicity) {
		assertEquals(name, uProperty.name)
		assertUmlNamedElementHasVisibility(uProperty, visibility)
		assertUmlTypedElementHasType(uProperty, type)
		assertUmlFeatureHasStaticValue(uProperty, isStatic)
		assertUmlPropertyHasFinalValue(uProperty, isFinal)
		assertEquals(containingClassifier.name, (uProperty.eContainer as Classifier).name)
		assertUmlValueSpecificationEquals(lowerMultiplicity, uProperty.lowerValue)
		assertUmlValueSpecificationEquals(upperMultiplicity, uProperty.upperValue)
	}

	/**
	 * Asserts that the given operation has the givven traits.
	 * 
	 */
	def static void assertUmlOperationTraits(Operation uOperation, String name, VisibilityKind visibility,
		Type returntype, boolean isStatic, boolean isAbstract, Classifier containingClassifier,
		List<Parameter> paramList) {
		assertEquals(name, uOperation.name)
		assertUmlNamedElementHasVisibility(uOperation, visibility)
		assertUmlOperationHasReturntype(uOperation, returntype)
		assertUmlFeatureHasStaticValue(uOperation, isStatic)
		assertUmlOperationHasAbstractValue(uOperation, isAbstract)
		assertEquals(containingClassifier.name, (uOperation.eContainer as Classifier).name)
		assertUmlParameterListEquals(paramList, uOperation.ownedParameters)
	}

	/**
	 * Asserts that the given parameter has the givven traits.
	 * 
	 */
	def static void assertUmlParameterTraits(Parameter uParam, String name, Type type) {
		assertEquals(name, uParam.name)
		assertUmlTypedElementHasType(uParam, type)
	}

	/**
	 * Asserts that the two given lists contain enum literals that correspond pairwise 
	 * by comparing their name
	 * 
	 */
	def static void assertUmlEnumLiteralListEquals(List<EnumerationLiteral> expectedList,
		List<EnumerationLiteral> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty)
		} else {
			assertEquals(expectedList.size, actualList.size)
			for (literal : expectedList) {
				val correspondingLiterals = actualList.filter[name == literal.name]
				if (correspondingLiterals.size != 1) {
					fail("There are 0 or more than 1 enumLiteral with the name " + literal.name)
				}
			}
		}

	}

	/**
	 * Asserts that the two given lists contain parameters that correspond pairwise 
	 * by comparing their name
	 * 
	 */
	def static void assertUmlParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (expectedList.nullOrEmpty) {
			if (!actualList.nullOrEmpty) {
				assertTrue(actualList.filter[direction != ParameterDirectionKind.RETURN_LITERAL].nullOrEmpty)
			}
		} else {
			assertEquals(expectedList.size, expectedList.size)
			for (param : expectedList) {
				val correspondingParams = actualList.filter[name == param.name]
				if (correspondingParams.size != 1) {
					fail("There are 0 or more than 1 Parameter with the name " + param.name)
				}
				assertUmlParameterTraits(correspondingParams.head, param.name, param.type)
			}
		}
	}

	def static dispatch void assertUmlValueSpecificationEquals(ValueSpecification value1, ValueSpecification value2) {
		fail("We currently only support LiteralInteger and LiteralUnlimitedNatural ValueSpecifications")
	}

	def static dispatch void assertUmlValueSpecificationEquals(LiteralInteger value1, LiteralInteger value2) {
		assertNotNull(value1.integerValue)
		assertNotNull(value2.integerValue)
		assertEquals(value1.integerValue, value2.integerValue)
	}

	def static dispatch void assertUmlValueSpecificationEquals(LiteralUnlimitedNatural value1,
		LiteralUnlimitedNatural value2) {
		// Do nothing -> Assertion passed
	}

	def static dispatch void assertUmlValueSpecificationEquals(Void value1, Void value2) {
		// Do nothing -> Assertion passed
	}

	def static void assertUmlPackageableElementIsInPackage(PackageableElement packageable, Package uPackage) {
		assertThat(getUmlNamespaceAsStringList(uPackage), is(getUmlNamespaceAsStringList(packageable.namespace)))
	}

	def static void assertUmlPackageableElementIsNotInPackage(PackageableElement packageable, Package uPackage) {
		for (elem : uPackage.packagedElements) {
			if (elem.name.equals(packageable.name) && elem.class.equals(packageable.class)) {
				fail()
			}
		}
	}

	def static void assertUmlNamedElementHasVisibility(NamedElement uElement, VisibilityKind visibility) {
		assertEquals(visibility, uElement.visibility)
	}

	def static void assertUmlFeatureHasStaticValue(Feature uFeature, boolean isStatic) {
		assertEquals(isStatic, uFeature.static)
	}

	def static void assertUmlClassHasFinalValue(Class uClass, boolean isFinal) {
		assertEquals(isFinal, uClass.finalSpecialization)
	}

	def static void assertUmlPropertyHasFinalValue(Property uAttribute, boolean isFinal) {
		assertEquals(isFinal, uAttribute.readOnly)
	}

	def static void assertUmlClassHasAbstractValue(Class uClass, boolean isAbstract) {
		assertEquals(isAbstract, uClass.abstract)
	}

	// UmlClass and UmlOperation dont have a common superClass for abstract
	def static void assertUmlOperationHasAbstractValue(Operation uOperation, boolean isAbstract) {
		assertEquals(isAbstract, uOperation.abstract)
	}

	def static void assertUmlOperationHasFinalValue(Operation uOperation, boolean isFinal) {
		assertEquals(isFinal, uOperation.isLeaf)
	}

	/**
	 * Uml-TypedElement includes Property and Parameter, but NOT Operation 
	 */
	def static void assertUmlTypedElementHasType(TypedElement typedElem, Type type) {
		assertUmlTypeEquals(type, typedElem.type)
	}

	def static dispatch void assertUmlTypeEquals(Classifier classifier1, Classifier classifier2) {
		assertEquals(classifier1.name, classifier2.name)
	}

	def static dispatch void assertUmlTypeEquals(Enumeration enum1, Enumeration enum2) {
		assertEquals(enum1.name, enum2.name)
	}

	// DataType is a SubClass of Classifier.
	// PrimitiveType and Enumeration are SubClasses of DataTypes.
	// We need extra dispatch-Method for Enumeration and Classifiers so they are not
	// dispatched to DataType.
	def static dispatch void assertUmlTypeEquals(PrimitiveType primtype1, PrimitiveType primtype2) {
		assertEquals(primtype1.name, primtype2.name)
	}

	def static dispatch void assertUmlTypeEquals(DataType dataType1, DataType dataType2) {
		assertEquals(dataType1.name, dataType2.name)
		assertTrue(dataType1.ownedAttributes.nullOrEmpty == dataType2.ownedAttributes.nullOrEmpty)
		if (!dataType1.ownedAttributes.nullOrEmpty) {
			assertTrue(1 == dataType1.ownedAttributes.size) // We currently dont support CollectionTypes
			assertTrue(1 == dataType2.ownedAttributes.size) // with more than 1 inner type
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
		assertFalse(uClass.ownedOperations.filter[name == operationName].nullOrEmpty)
	}

	def static void assertUmlClassDontHaveOperation(Class uClass, String operationName) {
		assertTrue(uClass.ownedOperations.filter[name == operationName].nullOrEmpty)
	}

	def static void assertUmlInterfaceHasUniqueOperation(Interface uInterface, String operationName) {
		assertFalse(uInterface.ownedOperations.filter[name == operationName].nullOrEmpty)
	}

	def static void assertUmlInterfaceDontHaveOperation(Interface uInterface, String operationName) {
		assertTrue(uInterface.ownedOperations.filter[name == operationName].nullOrEmpty)
	}

	def static void assertUmlClassHasUniqueProperty(Class uClass, String propertyName) {
		assertFalse(uClass.ownedAttributes.filter[name == propertyName].nullOrEmpty)
	}

	def static void assertUmlClassDontHaveProperty(Class uClass, String propertyName) {
		assertTrue(uClass.ownedAttributes.filter[name == propertyName].nullOrEmpty)
	}

	def static void assertUmlOperationHasUniqueParameter(Operation uOperation, String paramName) {
		assertFalse(uOperation.ownedParameters.filter[name == paramName].nullOrEmpty)
	}

	def static void assertUmlOperationDontHaveParameter(Operation uOperation, String paramName) {
		assertTrue(uOperation.ownedParameters.filter[name == paramName].nullOrEmpty)
	}

	def static void assertUmlClassifierHasSuperClassifier(Classifier childClassifier, Classifier parentClassifier) {
		assertNotNull(childClassifier.getGeneral(parentClassifier.name))
	}

	def static void assertUmlClassifierDontHaveSuperClassifier(Classifier childClassifier,
		Classifier parentClassifier) {
		assertNull(childClassifier.getGeneral(parentClassifier.name))
	}

	def static void assertUmlClassHasImplement(Class uClass, Interface implementedInterface) {
		assertFalse(uClass.implementedInterfaces.filter[name == implementedInterface.name].nullOrEmpty)
	}

	def static void assertUmlClassDontHaveImplement(Class uClass, Interface implementedInterface) {
		assertTrue(uClass.implementedInterfaces.filter[name == implementedInterface.name].nullOrEmpty)
	}

	def static void assertUmlEnumHasLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertFalse(uEnum.ownedLiterals.filter[name == uLiteral.name].nullOrEmpty)
	}

	def static void assertUmlEnumDontHaveLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertTrue(uEnum.ownedLiterals.filter[name == uLiteral.name].nullOrEmpty)
	}
}
