package tools.vitruv.applications.testutility.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.getUmlNamespaceAsStringList;

import java.util.List;
import java.util.Objects;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * Class for assertions that only involves UML elements.
 */
public final class UmlElementsTestAssertions {

	private UmlElementsTestAssertions() {
	}

	/**
	 * Asserts that the given class has the given traits. Does not check operations/attributes of the class.
	 */
	public static void assertUmlClassTraits(Class uClass, String name, VisibilityKind visibility, boolean isAbstract,
		boolean isFinal, Package uPackage) {
		assertEquals(name, uClass.getName(), "Class name is not as expected");
		assertUmlNamedElementHasVisibility(uClass, visibility);
		assertUmlClassHasFinalValue(uClass, isFinal);
		assertUmlClassHasAbstractValue(uClass, isAbstract);
		assertUmlPackageableElementIsInPackage(uClass, uPackage);
	}

	/**
	 * Asserts that the given interface has the given traits. Does not check operations/attributes of the interface.
	 */
	public static void assertUmlInterfaceTraits(Interface uInterface, String name, VisibilityKind visibility,
		Package uPackage) {
		assertEquals(name, uInterface.getName(), "Interface name is not as expected");
		assertUmlNamedElementHasVisibility(uInterface, visibility);
		assertUmlPackageableElementIsInPackage(uInterface, uPackage);
	}

	/**
	 * Asserts that the given enum has the given traits. Does not check operations/attributes of the enum.
	 * But it checks the enum literals if they are pairwise corresponding  to the given literals list (by name)
	 */
	public static void assertUmlEnumTraits(Enumeration uEnum, String name, VisibilityKind visibility,
		boolean isAbstract, boolean isFinal, Package uPackage, List<EnumerationLiteral> enumLiteralList) {
		assertEquals(name, uEnum.getName(), "Enumeration name is not as expected");
		assertUmlNamedElementHasVisibility(uEnum, visibility);
		assertUmlPackageableElementIsInPackage(uEnum, uPackage);
		assertUmlEnumLiteralListEquals(enumLiteralList, uEnum.getOwnedLiterals());
	}

	/**
	 * Asserts that the given property has the given traits.
	 */
	public static void assertUmlPropertyTraits(Property uProperty, String name, VisibilityKind visibility, Type type,
		boolean isStatic, boolean isFinal, Classifier containingClassifier, ValueSpecification lowerMultiplicity,
		ValueSpecification upperMultiplicity) {
		assertEquals(name, uProperty.getName(), "Property name is not as expected");
		assertUmlNamedElementHasVisibility(uProperty, visibility);
		assertUmlTypedElementHasType(uProperty, type);
		assertUmlFeatureHasStaticValue(uProperty, isStatic);
		assertUmlPropertyHasFinalValue(uProperty, isFinal);
		assertEquals(containingClassifier.getName(), ((Classifier) uProperty.eContainer()).getName(),
			"Containing classifier is not as expected");
		assertUmlValueSpecificationEquals(lowerMultiplicity, uProperty.getLowerValue());
		assertUmlValueSpecificationEquals(upperMultiplicity, uProperty.getUpperValue());
	}

	/**
	 * Asserts that the given operation has the given traits.
	 */
	public static void assertUmlOperationTraits(Operation uOperation, String name, VisibilityKind visibility,
		Type returntype, boolean isStatic, boolean isAbstract, Classifier containingClassifier,
		List<Parameter> paramList) {
		assertEquals(name, uOperation.getName(), "Operation name is not as expected");
		assertUmlNamedElementHasVisibility(uOperation, visibility);
		assertUmlOperationHasReturntype(uOperation, returntype);
		assertUmlFeatureHasStaticValue(uOperation, isStatic);
		assertUmlOperationHasAbstractValue(uOperation, isAbstract);
		assertEquals(containingClassifier.getName(), ((Classifier) uOperation.eContainer()).getName(),
			"Containing classifier is not as expected");
		assertUmlParameterListEquals(paramList, uOperation.getOwnedParameters());
	}

	/**
	 * Asserts that the given parameter has the given traits.
	 */
	public static void assertUmlParameterTraits(Parameter uParam, String name, Type type) {
		assertEquals(name, uParam.getName(), "Parameter name is not as expected");
		assertUmlTypedElementHasType(uParam, type);
	}

	/**
	 * Asserts that the two given lists contain enum literals that correspond pairwise
	 * by comparing their name
	 */
	public static void assertUmlEnumLiteralListEquals(List<EnumerationLiteral> expectedList,
		List<EnumerationLiteral> actualList) {
		if (isNullOrEmpty(expectedList)) {
			assertTrue(isNullOrEmpty(actualList),
				() -> String.format("Enumeration literal list should be empty but is %s", actualList));
		} else {
			assertEquals(expectedList.size(), actualList.size(), "Enumeration literal lists have different sizes");
			for (EnumerationLiteral literal : expectedList) {
				List<EnumerationLiteral> correspondingLiterals = actualList.stream()
					.filter(it -> Objects.equals(it.getName(), literal.getName())).toList();
				assertEquals(1, correspondingLiterals.size(),
					() -> String.format("There is not exactly one corresponding enumeration literal with name %s",
						literal.getName()));
			}
		}

	}

	/**
	 * Asserts that the two given lists contain parameters that correspond pairwise
	 * by comparing their name
	 */
	public static void assertUmlParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (isNullOrEmpty(expectedList)) {
			if (!isNullOrEmpty(actualList)) {
				assertTrue(
					actualList.stream().allMatch(it -> it.getDirection() == ParameterDirectionKind.RETURN_LITERAL),
					() -> String.format("Parameter list should be empty but has entries %s", actualList));
			}
		} else {
			assertEquals(expectedList.size(), expectedList.size(), "Parameter lists have different sizes");
			for (Parameter param : expectedList) {
				List<Parameter> correspondingParams = actualList.stream()
					.filter(it -> Objects.equals(it.getName(), param.getName())).toList();
				assertEquals(1, correspondingParams.size(),
					() -> String.format("There is not exactly one corresponding parameter with name %s",
						param.getName()));
				assertUmlParameterTraits(correspondingParams.get(0), param.getName(), param.getType());
			}
		}
	}

	/**
	 * Only LiteralInteger and LiteralUnlimitedNatural are supported.
	 * If one of the values is null, the assertion passes.
	 */
	public static void assertUmlValueSpecificationEquals(ValueSpecification value1, ValueSpecification value2) {
		if (value1 instanceof LiteralInteger integer1 && value2 instanceof LiteralInteger integer2) {
			assertEquals(integer1.integerValue(), integer2.integerValue(), "Literal integers are expected to be equal");
		} else if (value1 instanceof LiteralUnlimitedNatural && value2 instanceof LiteralUnlimitedNatural) {
			// Do nothing -> Assertion passed
		} else if (value1 != null && value2 != null) {
			fail("We currently only support LiteralInteger and LiteralUnlimitedNatural ValueSpecifications");
		}
		// Otherwise (null value): Do nothing -> Assertion passed
	}

	public static void assertUmlPackageableElementIsInPackage(PackageableElement packageable, Package uPackage) {
		assertThat("Packageable element " + uPackage + " must be in expected package",
			getUmlNamespaceAsStringList(uPackage), is(getUmlNamespaceAsStringList(packageable.getNamespace())));
	}

	public static void assertUmlPackageableElementIsNotInPackage(PackageableElement packageable, Package uPackage) {
		for (PackageableElement elem : uPackage.getPackagedElements()) {
			assertFalse(elem.getName().equals(packageable.getName()) && elem.getClass().equals(packageable.getClass()),
				() -> String.format("Package %s contains element %s although it should not", uPackage, packageable));
		}
	}

	public static void assertUmlNamedElementHasVisibility(NamedElement uElement, VisibilityKind visibility) {
		assertEquals(visibility, uElement.getVisibility(),
			() -> String.format("Visibility of element %s not as expected", uElement));
	}

	public static void assertUmlFeatureHasStaticValue(Feature uFeature, boolean isStatic) {
		assertEquals(isStatic, uFeature.isStatic(),
			() -> String.format("Static property of feature %s not as expected", uFeature));
	}

	public static void assertUmlClassHasFinalValue(Class uClass, boolean isFinal) {
		assertEquals(isFinal, uClass.isFinalSpecialization(),
			() -> String.format("Final property of class %s not as expected", uClass));
	}

	public static void assertUmlPropertyHasFinalValue(Property uAttribute, boolean isFinal) {
		assertEquals(isFinal, uAttribute.isReadOnly(),
			() -> String.format("Final property of attribute %s not as expected", uAttribute));
	}

	public static void assertUmlClassHasAbstractValue(Class uClass, boolean isAbstract) {
		assertEquals(isAbstract, uClass.isAbstract(),
			() -> String.format("Abstract property of class %s not as expected", uClass));
	}

	// UmlClass and UmlOperation dont have a common superClass for abstract
	public static void assertUmlOperationHasAbstractValue(Operation uOperation, boolean isAbstract) {
		assertEquals(isAbstract, uOperation.isAbstract(),
			() -> String.format("Abstract property of operation %s not as expected", uOperation));
	}

	public static void assertUmlOperationHasFinalValue(Operation uOperation, boolean isFinal) {
		assertEquals(isFinal, uOperation.isLeaf(),
			() -> String.format("Final property of operation %s not as expected", uOperation));
	}

	/**
	 * Uml-TypedElement includes Property and Parameter, but NOT Operation
	 */
	public static void assertUmlTypedElementHasType(TypedElement typedElem, Type type) {
		assertUmlTypeEquals(type, typedElem.getType());
	}

	/**
	 * Compares two UML types by name. If one of the types is null, the assertion passes.
	 */
	public static void assertUmlTypeEquals(Type type1, Type type2) {
		// DataType is a SubClass of Classifier.
		// PrimitiveType and Enumeration are SubClasses of DataTypes.
		// We need extra cases for Enumeration and Classifiers so they are not
		// handled as DataType.
		if (type1 instanceof Enumeration enum1 && type2 instanceof Enumeration enum2) {
			assertEquals(enum1.getName(), enum2.getName(), "Types should be equal");
		} else if (type1 instanceof PrimitiveType primtype1 && type2 instanceof PrimitiveType primtype2) {
			assertEquals(primtype1.getName(), primtype2.getName(), "Types should be equal");
		} else if (type1 instanceof DataType dataType1 && type2 instanceof DataType dataType2) {
			assertDataTypeEquals(dataType1, dataType2);
		} else if (type1 instanceof Classifier classifier1 && type2 instanceof Classifier classifier2) {
			assertEquals(classifier1.getName(), classifier2.getName(), "Types should be equal");
		} else if (type1 != null && type2 != null) {
			fail(type1 + " and " + type2
				+ " are not comparable or are neither Classifiers nor PrimitiveTypes nor DataTypes");
		}
		// Otherwise (null type): Do nothing, Assertion passed.
	}

	private static void assertDataTypeEquals(DataType dataType1, DataType dataType2) {
		assertEquals(dataType1.getName(), dataType2.getName(), "Types should be equal");
		assertTrue(isNullOrEmpty(dataType1.getOwnedAttributes()) == isNullOrEmpty(dataType2.getOwnedAttributes()),
			() -> String.format("Either of the datatypes %s and %s has no attributes", dataType1, dataType2));
		if (!isNullOrEmpty(dataType1.getOwnedAttributes())) {
			// We currently do not support CollectionTypes with more than 1 inner type
			assertEquals(1, dataType1.getOwnedAttributes().size(), () -> String.format(
				"Data type %s should have exactly one attribute as we do not yet support collection types", dataType1));
			assertEquals(1, dataType2.getOwnedAttributes().size(), () -> String.format(
				"Data type %s should have exactly one attribute as we do not yet support collection types", dataType2));
			assertUmlTypeEquals(dataType1.getOwnedAttributes().get(0).getType(),
				dataType2.getOwnedAttributes().get(0).getType());
		}
	}

	public static void assertUmlOperationHasReturntype(Operation uOperation, Type type) {
		assertUmlTypeEquals(uOperation.getType(), type);
	}

	public static void assertUmlClassHasUniqueOperation(Class uClass, String operationName) {
		assertTrue(uClass.getOwnedOperations().stream().anyMatch(it -> Objects.equals(it.getName(), operationName)),
			() -> String.format("Class %s should contain operation with name %s but does not", uClass, operationName));
	}

	public static void assertUmlClassDontHaveOperation(Class uClass, String operationName) {
		assertTrue(uClass.getOwnedOperations().stream().noneMatch(it -> Objects.equals(it.getName(), operationName)),
			() -> String.format("Class %s should not contain operation with name %s but does", uClass, operationName));
	}

	public static void assertUmlInterfaceHasUniqueOperation(Interface uInterface, String operationName) {
		assertTrue(
			uInterface.getOwnedOperations().stream().anyMatch(it -> Objects.equals(it.getName(), operationName)),
			() -> String.format("Interface %s should contain operation with name %s but does not", uInterface,
				operationName));
	}

	public static void assertUmlInterfaceDontHaveOperation(Interface uInterface, String operationName) {
		assertTrue(
			uInterface.getOwnedOperations().stream().noneMatch(it -> Objects.equals(it.getName(), operationName)),
			() -> String.format("Interface %s should not contain operation with name %s but does", uInterface,
				operationName));
	}

	public static void assertUmlClassHasUniqueProperty(Class uClass, String propertyName) {
		assertTrue(uClass.getOwnedAttributes().stream().anyMatch(it -> Objects.equals(it.getName(), propertyName)),
			() -> String.format("Class %s should contain property with name %s but does not", uClass, propertyName));
	}

	public static void assertUmlClassDontHaveProperty(Class uClass, String propertyName) {
		assertTrue(uClass.getOwnedAttributes().stream().noneMatch(it -> Objects.equals(it.getName(), propertyName)),
			() -> String.format("Class %s should not contain property with name %s but does", uClass, propertyName));
	}

	public static void assertUmlOperationHasUniqueParameter(Operation uOperation, String paramName) {
		assertTrue(uOperation.getOwnedParameters().stream().anyMatch(it -> Objects.equals(it.getName(), paramName)),
			() -> String.format("Operation %s should contain parameter with name %s but does not", uOperation,
				paramName));
	}

	public static void assertUmlOperationDontHaveParameter(Operation uOperation, String paramName) {
		assertTrue(uOperation.getOwnedParameters().stream().noneMatch(it -> Objects.equals(it.getName(), paramName)),
			() -> String.format("Operation %s should not contain parameter with name %s but does", uOperation,
				paramName));
	}

	public static void assertUmlClassifierHasSuperClassifier(Classifier childClassifier, Classifier parentClassifier) {
		assertNotNull(childClassifier.getGeneral(parentClassifier.getName()),
			() -> String.format("Classifier %s should have super classifier %s but has not", childClassifier,
				parentClassifier));
	}

	public static void assertUmlClassifierDontHaveSuperClassifier(Classifier childClassifier,
		Classifier parentClassifier) {
		assertNull(childClassifier.getGeneral(parentClassifier.getName()),
			() -> String.format("Classifier %s should not have super classifier %s but has", childClassifier,
				parentClassifier));
	}

	public static void assertUmlClassHasImplement(Class uClass, Interface implementedInterface) {
		assertTrue(uClass.getImplementedInterfaces().stream()
			.anyMatch(it -> Objects.equals(it.getName(), implementedInterface.getName())),
			() -> String.format("Class %s should implement interface %s but does not", uClass, implementedInterface));
	}

	public static void assertUmlClassDontHaveImplement(Class uClass, Interface implementedInterface) {
		assertTrue(uClass.getImplementedInterfaces().stream()
			.noneMatch(it -> Objects.equals(it.getName(), implementedInterface.getName())),
			() -> String.format("Class %s should not implement interface %s but does", uClass, implementedInterface));
	}

	public static void assertUmlEnumHasLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertTrue(uEnum.getOwnedLiterals().stream().anyMatch(it -> Objects.equals(it.getName(), uLiteral.getName())),
			() -> String.format("Enumeration %s should have literal %s but does not", uEnum, uLiteral));
	}

	public static void assertUmlEnumDontHaveLiteral(Enumeration uEnum, EnumerationLiteral uLiteral) {
		assertTrue(uEnum.getOwnedLiterals().stream().noneMatch(it -> Objects.equals(it.getName(), uLiteral.getName())),
			() -> String.format("Enumeration %s should not have literal %s but does", uEnum, uLiteral));
	}

	private static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
