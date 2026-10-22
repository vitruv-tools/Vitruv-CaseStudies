package tools.vitruv.applications.testutility.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableHasVisibility;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlNamedElementHasVisibility;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.getJavaVisibilityConstantFromUmlVisibilityKind;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getInnerTypeReferenceOfCollectionTypeReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getInterfaceFromTypeReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.getUmlNamespaceAsStringList;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.getUmlParentNamespaceAsStringList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Abstract;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Final;
import org.emftext.language.java.modifiers.Static;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.parameters.VariableLengthParameter;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;
import tools.vitruv.applications.util.temporary.java.UmlJavaTypePropagationHelper;

/**
 * Utility class for assertions that works bidirectional.
 */
public final class JavaUmlElementEqualityValidation {

	private static final Logger logger = Logger.getLogger(JavaUmlElementEqualityValidation.class);

	private JavaUmlElementEqualityValidation() {
	}

	/**
	 * Asserts that the given UML element and Java element are equal. Supported pairs are packages, classifiers
	 * (class, data type, interface, enumeration), attributes, constructors, methods, enumeration literals and
	 * parameters.
	 *
	 * @throws IllegalArgumentException if the given pair of elements is not supported
	 */
	public static void assertElementsEqual(Element uElement, org.emftext.language.java.commons.NamedElement jElement) {
		if (uElement instanceof org.eclipse.uml2.uml.Class uClass
			&& jElement instanceof org.emftext.language.java.classifiers.Class jClass) {
			assertClassesEqual(uClass, jClass);
		} else if (uElement instanceof Enumeration uEnum
			&& jElement instanceof org.emftext.language.java.classifiers.Enumeration jEnum) {
			assertEnumerationsEqual(uEnum, jEnum);
		} else if (uElement instanceof DataType uDataType
			&& jElement instanceof org.emftext.language.java.classifiers.Class jClass) {
			assertDataTypeEqualsClass(uDataType, jClass);
		} else if (uElement instanceof org.eclipse.uml2.uml.Interface uInterface
			&& jElement instanceof org.emftext.language.java.classifiers.Interface jInterface) {
			assertInterfacesEqual(uInterface, jInterface);
		} else if (uElement instanceof Operation uMethod && jElement instanceof ClassMethod jMethod) {
			assertOperationEqualsClassMethod(uMethod, jMethod);
		} else if (uElement instanceof Operation uMethod && jElement instanceof InterfaceMethod jMethod) {
			assertOperationEqualsInterfaceMethod(uMethod, jMethod);
		} else if (uElement instanceof Property uAttribute && jElement instanceof Field jAttribute) {
			assertPropertyEqualsField(uAttribute, jAttribute);
		} else if (uElement instanceof Operation uMethod && jElement instanceof Constructor jConstructor) {
			assertOperationEqualsConstructor(uMethod, jConstructor);
		} else if (uElement instanceof org.eclipse.uml2.uml.Parameter uParameter
			&& jElement instanceof org.emftext.language.java.parameters.Parameter jParameter) {
			assertParameterEquals(uParameter, jParameter);
		} else if (uElement instanceof EnumerationLiteral uLiteral && jElement instanceof EnumConstant jConstant) {
			assertEnumerationLiteralEqualsConstant(uLiteral, jConstant);
		} else if (uElement instanceof org.eclipse.uml2.uml.Package uPackage
			&& jElement instanceof org.emftext.language.java.containers.Package jPackage) {
			assertPackagesEqual(uPackage, jPackage);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(uElement, jElement));
		}
	}

	// **************
	// PACKAGES
	// **************
	/**
	 * Does not compare the package contents
	 */
	private static void assertPackagesEqual(org.eclipse.uml2.uml.Package uPackage,
		org.emftext.language.java.containers.Package jPackage) {
		assertEquals(uPackage.getName(), jPackage.getName(), "Package names must be equal");
		assertEquals(getUmlParentNamespaceAsStringList(uPackage), jPackage.getNamespaces(),
			"Package namespaces names must be equal");
	}

	private static void assertPackageEquals(Namespace umlNamespace, List<String> packageStringList) {
		if (umlNamespace instanceof Model && packageStringList != null) {
			assertTrue(isNullOrEmpty(packageStringList),
				() -> String.format("UML models do not have a namespace but %s was expected", packageStringList));
		} else if (umlNamespace instanceof Model && packageStringList == null) {
			// Do Nothing, assertion passed (Default package)
		} else if (umlNamespace != null && packageStringList != null) {
			assertThat("Package namespaces must be equal", getUmlNamespaceAsStringList(umlNamespace),
				is(packageStringList));
		} else {
			throw new IllegalArgumentException(
				"Unhandled parameter types: " + Arrays.asList(umlNamespace, packageStringList));
		}
	}

	// **************
	// CLASSIFIERS
	// **************
	private static void assertClassesEqual(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		assertClassifiersCommonDataEquals(uClass, jClass);
		assertAbstractClassEquals(uClass, jClass);
		assertFinalClassEquals(uClass, jClass);
	}

	private static void assertDataTypeEqualsClass(DataType uDataType,
		org.emftext.language.java.classifiers.Class jClass) {
		assertClassifiersCommonDataEquals(uDataType, jClass);
	}

	private static void assertInterfacesEqual(org.eclipse.uml2.uml.Interface uInterface,
		org.emftext.language.java.classifiers.Interface jInterface) {
		assertClassifiersCommonDataEquals(uInterface, jInterface);
	}

	private static void assertEnumerationsEqual(Enumeration uEnum,
		org.emftext.language.java.classifiers.Enumeration jEnum) {
		assertClassifiersCommonDataEquals(uEnum, jEnum);
		assertEnumConstantListEquals(uEnum.getOwnedLiterals(), jEnum.getConstants());
	}

	private static void assertClassifiersCommonDataEquals(Classifier umlClassifier,
		ConcreteClassifier javaClassifier) {
		assertEquals(umlClassifier.getName(), javaClassifier.getName(),
			() -> String.format("UML %s and Java %s names must be equal", umlClassifier.getClass().getSimpleName(),
				javaClassifier.getClass().getSimpleName()));
		assertVisibilityEquals(umlClassifier, javaClassifier);
		assertPackageEquals(umlClassifier.getNamespace(), javaClassifier.getContainingCompilationUnit().getNamespaces());
		assertAttributesEquals(umlClassifier, javaClassifier);
		assertMethodsEquals(umlClassifier, javaClassifier);
	}

	private static void assertAttributesEquals(Classifier umlClassifier, ConcreteClassifier javaClassifier) {
		for (int attributeNumber = 0; attributeNumber < umlClassifier.getAttributes().size(); attributeNumber++) {
			Property umlAttribute = umlClassifier.getAttributes().get(attributeNumber);
			Field correspondingJavaField = javaClassifier.getFields().stream()
				.filter(it -> Objects.equals(it.getName(), umlAttribute.getName())).findFirst().orElse(null);
			assertNotNull(correspondingJavaField,
				() -> String.format("No corresponding Java field found for UML attribute %s", umlAttribute));
			assertElementsEqual(umlAttribute, correspondingJavaField);
		}
		for (Field javaField : javaClassifier.getFields()) {
			Property correspondingUmlAttribute = umlClassifier.getAttributes().stream()
				.filter(it -> Objects.equals(it.getName(), javaField.getName())).findFirst().orElse(null);
			assertNotNull(correspondingUmlAttribute,
				() -> String.format("No corresponding UML attribute found for Java field %s", javaField));
			assertElementsEqual(correspondingUmlAttribute, javaField);
		}
	}

	private static void assertMethodsEquals(Classifier umlClassifier, ConcreteClassifier javaClassifier) {
		for (int methodNumber = 0; methodNumber < umlClassifier.getOperations().size(); methodNumber++) {
			Operation umlMethod = umlClassifier.getOperations().get(methodNumber);
			List<? extends Member> potentialMethods;
			if (Objects.equals(umlMethod.getName(), umlClassifier.getName())) {
				potentialMethods = javaClassifier.getConstructors();
			} else {
				potentialMethods = javaClassifier.getMethods();
			}
			// Match methods in the order in which they were generated, as this is how the transformations implement it
			// Otherwise we need to perform a complete matching of the signature here
			List<? extends Member> potentialMethodsWithSameName = potentialMethods.stream()
				.filter(it -> Objects.equals(it.getName(), umlMethod.getName())).toList();
			List<Operation> umlMethodsWithSameName = umlClassifier.getOperations().stream()
				.filter(it -> Objects.equals(it.getName(), umlMethod.getName())).toList();
			Member correspondingJavaMethod = potentialMethodsWithSameName.get(umlMethodsWithSameName.indexOf(umlMethod));
			assertNotNull(correspondingJavaMethod,
				() -> String.format("No corresponding Java method found for UML method %s", umlMethod));
			assertElementsEqual(umlMethod, correspondingJavaMethod);
		}
		for (Method javaMethod : javaClassifier.getMethods()) {
			// Exclude getter and setter methods
			if (javaMethod.getName().length() < 3 || javaClassifier.getFields().stream().noneMatch(
				it -> Objects.equals(toFirstLower(it.getName()), toFirstLower(javaMethod.getName().substring(3))))) {
				// Match methods in the order in which they were generated, as this is how the transformations implement it
				// Otherwise we need to perform a complete matching of the signature here
				List<Operation> umlMethodsWithSameName = umlClassifier.getOperations().stream()
					.filter(it -> Objects.equals(it.getName(), javaMethod.getName())).toList();
				List<Method> javaMethodsWithSameName = javaClassifier.getMethods().stream()
					.filter(it -> Objects.equals(it.getName(), javaMethod.getName())).toList();
				Operation correspondingUmlMethod = umlMethodsWithSameName.get(javaMethodsWithSameName.indexOf(javaMethod));
				assertNotNull(correspondingUmlMethod,
					() -> String.format("No corresponding UML method found for Java method %s", javaMethod));
				assertElementsEqual(correspondingUmlMethod, javaMethod);
			}
		}
	}

	private static void assertEnumConstantListEquals(List<EnumerationLiteral> uEnumLiteralList,
		List<EnumConstant> jEnumConstantList) {
		assertEquals(uEnumLiteralList.size(), jEnumConstantList.size(),
			"There must be the same number of enumeration literals");
		for (EnumerationLiteral uLiteral : uEnumLiteralList) {
			List<EnumConstant> jConstants = jEnumConstantList.stream()
				.filter(it -> Objects.equals(it.getName(), uLiteral.getName())).toList();
			if (jConstants.isEmpty()) {
				fail("There is no corresponding enumeration literal with name '" + uLiteral.getName() + "'");
			} else if (jConstants.size() > 1) {
				logger.warn("There is more than one enumeration literal with name '" + uLiteral.getName() + "'");
			} else {
				assertElementsEqual(uLiteral, jConstants.get(0));
			}
		}
	}

	// **************
	// MEMBERS
	// **************
	private static void assertPropertyEqualsField(Property uAttribute, Field jAttribute) {
		assertEquals(uAttribute.getName(), jAttribute.getName(), "Attribute and field names must be equal");
		assertVisibilityEquals(uAttribute, jAttribute);
		assertFinalAttributeEquals(uAttribute, jAttribute);
		assertStaticEquals(uAttribute, jAttribute);
		if (uAttribute.upperBound() == 0 || uAttribute.upperBound() == 1) {
			assertTypeEquals(uAttribute.getType(), jAttribute.getTypeReference());
		} else {
			assertTypeEquals(uAttribute.getType(),
				getInnerTypeReferenceOfCollectionTypeReference(jAttribute.getTypeReference()));
		}
	}

	private static void assertOperationEqualsConstructor(Operation uMethod, Constructor jConstructor) {
		assertEquals(uMethod.getName(), jConstructor.getName(), "Constructor names must be equal");
		assertStaticEquals(uMethod, jConstructor);
		assertVisibilityEquals(uMethod, jConstructor);
		assertEquals(null, uMethod.getType(), "Constructors must not have a return type");
		assertParametersEquals(uMethod, jConstructor);
	}

	private static void assertOperationEqualsClassMethod(Operation uMethod, ClassMethod jMethod) {
		assertEquals(uMethod.getName(), jMethod.getName(), "Method names must be equal");
		assertStaticEquals(uMethod, jMethod);
		assertFinalMethodEquals(uMethod, jMethod);
		assertAbstractMethodEquals(uMethod, jMethod);
		assertVisibilityEquals(uMethod, jMethod);
		assertTypeEquals(uMethod.getType(), jMethod.getTypeReference());
		assertParametersEquals(uMethod, jMethod);
	}

	private static void assertEnumerationLiteralEqualsConstant(EnumerationLiteral uLiteral, EnumConstant jConstant) {
		assertEquals(uLiteral.getName(), jConstant.getName(), "Enumeration literal names must be equal");
	}

	/**
	 * Interface methods = methods without body.
	 * Also checks if uMethod is abstract.
	 */
	private static void assertOperationEqualsInterfaceMethod(Operation uMethod, InterfaceMethod jMethod) {
		assertEquals(uMethod.getName(), jMethod.getName(), "Method names must be equal");
		assertUmlNamedElementHasVisibility(uMethod, VisibilityKind.PUBLIC_LITERAL); // JMehod is always implicitly public
		assertTypeEquals(uMethod.getType(), jMethod.getTypeReference());
		assertParametersEquals(uMethod, jMethod);
	}

	private static void assertParameterEquals(org.eclipse.uml2.uml.Parameter uParameter,
		org.emftext.language.java.parameters.Parameter jParameter) {
		assertEquals(uParameter.getName(), jParameter.getName(), "Parameter names must be equal");
		if (jParameter instanceof VariableLengthParameter) {
			assertEquals(LiteralUnlimitedNatural.UNLIMITED, uParameter.getUpper(),
				"UML parameter for Java variable length parameter must have multiplicity *");
		}
		assertTypeEquals(uParameter.getType(), jParameter.getTypeReference());
	}

	private static void assertParametersEquals(Operation umlOperation, Parametrizable javaMethod) {
		List<org.eclipse.uml2.uml.Parameter> uParamListWithoutReturn = umlOperation.getOwnedParameters().stream()
			.filter(it -> it.getDirection() != ParameterDirectionKind.RETURN_LITERAL).toList();
		if (uParamListWithoutReturn == null) {
			assertNull(javaMethod.getParameters(), "Parameter should not have a return type but has one");
		} else {
			assertEquals(uParamListWithoutReturn.size(), javaMethod.getParameters().size(),
				"Parameter lists must be of equal size");
			for (int parameterNumber = 0; parameterNumber < uParamListWithoutReturn.size(); parameterNumber++) {
				org.eclipse.uml2.uml.Parameter umlParameter = uParamListWithoutReturn.get(parameterNumber);
				org.emftext.language.java.parameters.Parameter javaParameter = javaMethod.getParameters()
					.get(parameterNumber);
				assertElementsEqual(umlParameter, javaParameter);
			}
		}
	}

	// **************
	// PROPERTIES
	// **************
	public static void assertStaticEquals(Feature uElement, AnnotableAndModifiable jElement) {
		if (uElement.isStatic()) {
			assertTrue(jElement.hasModifier(Static.class),
				() -> String.format("Element %s is expected to be static but is not", jElement));
		} else {
			assertFalse(jElement.hasModifier(Static.class),
				() -> String.format("Element %s is not expected to be static but is", jElement));
		}
	}

	private static void assertFinalClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.isFinalSpecialization()) {
			assertTrue(jClass.hasModifier(Final.class),
				() -> String.format("Class %s is expected to be final but is not", jClass));
		} else {
			assertFalse(jClass.hasModifier(Final.class),
				() -> String.format("Class %s is not expected to be final but is", jClass));
		}
	}

	public static void assertFinalAttributeEquals(Property uAttribute, Field jAttribute) {
		if (uAttribute.isReadOnly()) {
			assertTrue(jAttribute.hasModifier(Final.class),
				() -> String.format("Attribute %s is expected to be final but is not", jAttribute));
		} else {
			assertFalse(jAttribute.hasModifier(Final.class),
				() -> String.format("Attribute %s is not expected to be final but is", jAttribute));
		}
	}

	private static void assertAbstractClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.isAbstract()) {
			assertTrue(jClass.hasModifier(Abstract.class),
				() -> String.format("Class %s is expected to be abstract but is not", jClass));
		} else {
			assertFalse(jClass.hasModifier(Abstract.class),
				() -> String.format("Class %s is not expected to be abstract but is", jClass));
		}
	}

	public static void assertFinalMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.isLeaf()) {
			assertTrue(jMethod.hasModifier(Final.class),
				() -> String.format("Method %s is expected to be final but is not", jMethod));
		} else {
			assertFalse(jMethod.hasModifier(Final.class),
				() -> String.format("Method %s is not expected to be final but is", jMethod));
		}
	}

	private static void assertAbstractMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.isAbstract()) {
			assertTrue(jMethod.hasModifier(Abstract.class),
				() -> String.format("Method %s is expected to be abstract but is not", jMethod));
		} else {
			assertFalse(jMethod.hasModifier(Abstract.class),
				() -> String.format("Method %s is not expected to be abstract but is", jMethod));
		}
	}

	public static void assertVisibilityEquals(org.eclipse.uml2.uml.NamedElement uElement,
		AnnotableAndModifiable jElement) {
		JavaVisibility jVisibility = getJavaVisibilityConstantFromUmlVisibilityKind(uElement.getVisibility());
		assertJavaModifiableHasVisibility(jElement, jVisibility);
	}

	// **************
	// TYPES
	// **************
	/**
	 * Asserts that the given UML type and Java type reference are equal. A {@code null} UML type is expected
	 * to correspond to the Java void type.
	 *
	 * @throws IllegalArgumentException if the Java type reference is neither a primitive type nor a
	 *                                  namespace classifier reference
	 */
	public static void assertTypeEquals(Type uType, TypeReference jTypeReference) {
		if (uType instanceof org.eclipse.uml2.uml.Class uClass
			&& jTypeReference instanceof NamespaceClassifierReference namespaceRef) {
			assertEquals(getClassifierFromTypeReference(namespaceRef).getName(), uClass.getName(),
				"Class name is not as expected");
		} else if (uType instanceof Enumeration uEnum
			&& jTypeReference instanceof NamespaceClassifierReference namespaceRef) {
			assertEquals(getClassifierFromTypeReference(namespaceRef).getName(), uEnum.getName(),
				"Enumeration name is not as expected");
		} else if (uType instanceof org.eclipse.uml2.uml.PrimitiveType uPrimType
			&& jTypeReference instanceof NamespaceClassifierReference namespaceRef) {
			assertPrimitiveTypeEqualsClassifierReference(uPrimType, namespaceRef);
		} else if (uType instanceof org.eclipse.uml2.uml.PrimitiveType uPrimType
			&& jTypeReference instanceof org.emftext.language.java.types.PrimitiveType jPrimType) {
			assertPrimitiveTypesEqual(uPrimType, jPrimType);
		} else if (uType instanceof DataType uDataType
			&& jTypeReference instanceof NamespaceClassifierReference namespaceRef) {
			assertEquals(getClassifierFromTypeReference(namespaceRef).getName(), uDataType.getName(),
				"DataType name is not as expected");
		} else if (uType instanceof org.eclipse.uml2.uml.Interface uInterface
			&& jTypeReference instanceof NamespaceClassifierReference namespaceRef) {
			assertEquals(getInterfaceFromTypeReference(namespaceRef).getName(), uInterface.getName(),
				"Interface name is not as expected");
		} else if (uType != null && jTypeReference != null) {
			throw new IllegalArgumentException("The java TypeReference " + jTypeReference
				+ " is neither a PrimitiveType nor a NamespaceClassifierReference");
		} else if (uType == null && jTypeReference instanceof org.emftext.language.java.types.Void) {
			// umlType is null, javaType is void -> Do nothing, assertion passed.
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(uType, jTypeReference));
		}
	}

	// IMPORTANT EXCEPTION: String is NOT a primitive in the Java model, which means this case needs to exist
	private static void assertPrimitiveTypeEqualsClassifierReference(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		NamespaceClassifierReference namespaceRef) {
		assertNotNull(uPrimType, "Primitive type to check must not be null");
		TypeReference jTypeMapped = UmlJavaTypePropagationHelper.getJavaTypeReferenceForUmlPrimitiveType(uPrimType);
		assertFalse(jTypeMapped instanceof org.emftext.language.java.types.Void,
			() -> String.format("Mapped primitive type for original type %s is void but should not", uPrimType));
		// if the UML type is non null and supported by the transformations, then it should not be mapped to void
		String javaTypeName = getClassifierFromTypeReference(namespaceRef).getName();
		String javaMappedTypeName = getClassifierFromTypeReference(jTypeMapped).getName();
		assertEquals(Objects.equals(javaTypeName, "CharSequence") ? "String" : javaTypeName, javaMappedTypeName,
			"Type is not as expected");
	}

	private static void assertPrimitiveTypesEqual(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		org.emftext.language.java.types.PrimitiveType jPrimType) {
		assertNotNull(uPrimType, "Primitive type to check must not be null");
		TypeReference jTypeMapped = UmlJavaTypePropagationHelper.getJavaTypeReferenceForUmlPrimitiveType(uPrimType);
		assertFalse(jTypeMapped instanceof org.emftext.language.java.types.Void,
			() -> String.format("Mapped primitive type for original type %s is void but should not", uPrimType));
		// if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(jPrimType.getClass(), jTypeMapped.getClass(), "Type is not as expected");
	}

	private static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	private static String toFirstLower(String string) {
		if (string == null || string.isEmpty() || Character.isLowerCase(string.charAt(0))) {
			return string;
		}
		return string.substring(0, 1).toLowerCase() + string.substring(1);
	}
}
