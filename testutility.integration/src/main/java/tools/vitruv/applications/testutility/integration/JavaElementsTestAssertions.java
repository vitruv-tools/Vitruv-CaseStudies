package tools.vitruv.applications.testutility.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.typeReferenceEquals;

import java.util.List;
import java.util.Objects;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MemberContainer;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Abstract;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Final;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;
import org.emftext.language.java.modifiers.Static;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypedElement;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * Utility class for assertions that only involves java elements.
 */
public final class JavaElementsTestAssertions {

	private JavaElementsTestAssertions() {
	}

	/**
	 * Asserts that the given Java class has the given traits.
	 */
	public static void assertJavaClassTraits(Class jClass, String name, JavaVisibility visibility, boolean isAbstract,
		boolean isFinal) {
		assertEquals(name, jClass.getName(), "Class name is not as expected");
		assertJavaModifiableHasVisibility(jClass, visibility);
		assertJavaModifiableFinal(jClass, isFinal);
		assertJavaModifiableAbstract(jClass, isAbstract);
	}

	/**
	 * Asserts that the given Java enumeration has the given traits.
	 */
	public static void assertJavaEnumTraits(Enumeration jEnum, String name, JavaVisibility visibility,
		List<EnumConstant> constantsList) {
		assertEquals(name, jEnum.getName(), "Enumeration name is not as expected");
		assertJavaModifiableHasVisibility(jEnum, visibility);
		assertJavaEnumConstantListEquals(constantsList, jEnum.getConstants());
	}

	/**
	 * Asserts that the enumeration constants of both given lists correspond pairwise by name.
	 */
	public static void assertJavaEnumConstantListEquals(List<EnumConstant> expectedList, List<EnumConstant> actualList) {
		if (isNullOrEmpty(expectedList)) {
			assertTrue(isNullOrEmpty(actualList),
				() -> String.format("Enumeration literal list should be empty but is %s", actualList));
		} else {
			assertEquals(expectedList.size(), actualList.size(), "Enumeration literal lists have different sizes");
			for (EnumConstant constant : expectedList) {
				List<EnumConstant> correspondingConstants = actualList.stream()
					.filter(it -> Objects.equals(it.getName(), constant.getName())).toList();
				assertEquals(1, correspondingConstants.size(), () -> String.format(
					"There is not exactly one corresponding enumeration constants with name %s", constant.getName()));
			}
		}

	}

	/**
	 * Asserts that the given Java interface has the given traits.
	 */
	public static void assertJavaInterfaceTraits(Interface jInterface, String name, JavaVisibility visibility) {
		assertEquals(name, jInterface.getName(), "Interface name is not as expected");
		assertJavaModifiableHasVisibility(jInterface, visibility);
	}

	/**
	 * Asserts that the given Java class method has the given traits.
	 */
	public static void assertJavaClassMethodTraits(ClassMethod jMethod, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isStatic, boolean isAbstract, List<Parameter> parameterList,
		ConcreteClassifier containingClassifier) {
		assertEquals(name, jMethod.getName(), "Class method name is not as expected");
		assertJavaModifiableHasVisibility(jMethod, visibility);
		assertJavaModifiableStatic(jMethod, isStatic);
		assertJavaModifiableAbstract(jMethod, isAbstract);
		assertEquals(containingClassifier.getName(), ((ConcreteClassifier) jMethod.eContainer()).getName(),
			"Method is not contained in correct classifier");
		assertJavaParameterListEquals(jMethod.getParameters(), parameterList);
	}

	/**
	 * Asserts that the given Java interface method has the given traits.
	 * Asserts that the given interface method has public visibility.
	 * Asserts that the given interface method is not static.
	 */
	public static void assertJavaInterfaceMethodTraits(InterfaceMethod jMethod, String name, TypeReference typeRef,
		List<Parameter> parameterList, Interface containingInterface) {
		assertEquals(name, jMethod.getName(), "Interface method name is not as expected");
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PUBLIC);
		assertJavaModifiableStatic(jMethod, false);
		assertEquals(containingInterface.getName(), ((Interface) jMethod.eContainer()).getName(),
			"Method is not contained in correct classifier");
		assertJavaParameterListEquals(jMethod.getParameters(), parameterList);
	}

	/**
	 * Asserts that the given Java parameter has the given traits.
	 */
	public static void assertJavaParameterTraits(Parameter jParam, String name, TypeReference typeRef) {
		assertEquals(name, jParam.getName(), "Parameter name is not as expected");
		assertJavaElementHasTypeRef(jParam, typeRef);
	}

	/**
	 * Asserts that the parameter in both given lists correspond pairwise by name.
	 */
	public static void assertJavaParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (isNullOrEmpty(expectedList)) {
			assertTrue(isNullOrEmpty(actualList),
				() -> String.format("Parameter list should be empty but is %s", actualList));
		} else {
			assertEquals(expectedList.size(), expectedList.size(), "Parameter literal lists have different sizes");
			for (Parameter param : expectedList) {
				List<Parameter> correspondingParams = actualList.stream()
					.filter(it -> Objects.equals(it.getName(), param.getName())).toList();
				assertEquals(1, correspondingParams.size(),
					() -> String.format("There are 0 or more than 1 parameters with the name %s", param.getName()));
				assertJavaParameterTraits(correspondingParams.get(0), param.getName(), param.getTypeReference());
			}
		}
	}

	/**
	 * Asserts that the given Java field has the given traits.
	 */
	public static void assertJavaAttributeTraits(Field jAttribute, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isFinal, boolean isStatic, ConcreteClassifier containingClassifier) {
		assertEquals(name, jAttribute.getName(), "Attribute name is not as expected");
		assertJavaModifiableHasVisibility(jAttribute, visibility);
		assertJavaElementHasTypeRef(jAttribute, typeRef);
		assertJavaModifiableFinal(jAttribute, isFinal);
		assertJavaModifiableStatic(jAttribute, isStatic);
		assertEquals(containingClassifier.getName(), ((ConcreteClassifier) jAttribute.eContainer()).getName(),
			"Attribute is not contained in correct classifier");
	}

	public static void assertJavaModifiableFinal(AnnotableAndModifiable modifiable, boolean isFinal) {
		assertModifierExisistenceAsExpected(modifiable, Final.class, isFinal);
	}

	public static void assertJavaModifiableStatic(AnnotableAndModifiable modifiable, boolean isStatic) {
		assertModifierExisistenceAsExpected(modifiable, Static.class, isStatic);
	}

	public static void assertJavaModifiableAbstract(AnnotableAndModifiable modifiable, boolean isAbstract) {
		assertModifierExisistenceAsExpected(modifiable, Abstract.class, isAbstract);
	}

	public static void assertModifierExisistenceAsExpected(AnnotableAndModifiable modifiable,
		java.lang.Class<? extends Modifier> modifierClass, boolean exists) {
		if (exists) {
			assertJavaModifiableHasModifier(modifiable, modifierClass);
		} else {
			assertJavaModifiableDontHaveModifier(modifiable, modifierClass);
		}
	}

	public static void assertJavaModifiableHasVisibility(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
		if (visibility == null) {
			throw new IllegalArgumentException("Unknown VisibilityKind: " + visibility);
		}
		switch (visibility) {
			case PUBLIC -> {
				assertJavaModifiableHasModifier(modifiable, Public.class);
				assertJavaModifiableDontHaveModifier(modifiable, Private.class);
				assertJavaModifiableDontHaveModifier(modifiable, Protected.class);
			}
			case PRIVATE -> {
				assertJavaModifiableHasModifier(modifiable, Private.class);
				assertJavaModifiableDontHaveModifier(modifiable, Public.class);
				assertJavaModifiableDontHaveModifier(modifiable, Protected.class);
			}
			case PROTECTED -> {
				assertJavaModifiableHasModifier(modifiable, Protected.class);
				assertJavaModifiableDontHaveModifier(modifiable, Private.class);
				assertJavaModifiableDontHaveModifier(modifiable, Public.class);
			}
			case PACKAGE -> {
				assertJavaModifiableDontHaveModifier(modifiable, Public.class);
				assertJavaModifiableDontHaveModifier(modifiable, Private.class);
				assertJavaModifiableDontHaveModifier(modifiable, Protected.class);
			}
			default -> throw new IllegalArgumentException("Unknown VisibilityKind: " + visibility);
		}
	}

	public static <T extends Modifier> void assertJavaModifiableHasModifier(AnnotableAndModifiable modifiable,
		java.lang.Class<T> mod) {
		if (modifiable == null) {
			fail("The modifiable is null");
		} else if (mod == null) {
			fail("Cannot check modifier null");
		}
		assertTrue(modifiable.hasModifier(mod),
			() -> String.format("Element %s should have modifier %s but has not", modifiable, mod));
	}

	public static <T extends Modifier> void assertJavaModifiableDontHaveModifier(AnnotableAndModifiable modifiable,
		java.lang.Class<T> mod) {
		if (modifiable == null) {
			fail("The modifiable is null");
		} else if (mod == null) {
			fail("Cannot check modifier null");
		}
		assertFalse(modifiable.hasModifier(mod),
			() -> String.format("Element %s should not have modifier %s but has", modifiable, mod));
	}

	/**
	 * Asserts that a member container (class, interface, enumeration) does not contain an element
	 * with the given name.
	 */
	public static void assertJavaMemberContainerDontHaveMember(MemberContainer memContainer, String name) {
		assertTrue(isNullOrEmpty(memContainer.getMembersByName(name)),
			() -> String.format("Element %s should not have member %s but has", memContainer, name));
	}

	/**
	 * Asserts that an enumeration has a constant with the given name.
	 */
	public static void assertJavaEnumHasConstant(Enumeration jEnum, String constantName) {
		assertNotNull(jEnum.getContainedConstant(constantName),
			() -> String.format("Enumeration %s should have constant %s but has not", jEnum, constantName));
	}

	/**
	 * Asserts that an enumeration does not have a constant with the given name.
	 */
	public static void assertJavaEnumDontHaveConstant(Enumeration jEnum, String constantName) {
		assertNull(jEnum.getContainedConstant(constantName),
			() -> String.format("Enumeration %s should not have constant %s but has", jEnum, constantName));
	}

	/**
	 * Asserts that a memberContainer has exactly one method with the name methodName
	 */
	public static void assertHasUniqueMethod(MemberContainer memContainer, String methodName) {
		// getContainedMethod returns null if there is no method with the name methodName or
		// if there are more than one method with the name methodName
		assertNotNull(memContainer.getContainedMethod(methodName),
			() -> String.format("Element %s should have method %s but has not", memContainer, methodName));
	}

	/**
	 * Asserts that a memberContainer has a field with the name fieldName
	 */
	public static void assertHasUniqueField(MemberContainer memContainer, String fieldName) {
		assertNotNull(memContainer.getContainedField(fieldName),
			() -> String.format("Element %s should have field %s but has not", memContainer, fieldName));
	}

	/**
	 * Asserts that a given method has exactly one parameter with the given name and the given type.
	 */
	public static void assertJavaMethodHasUniqueParameter(Method jMethod, String paramName, TypeReference paramTypeRef) {
		assertFalse(isNullOrEmpty(jMethod.getParameters()),
			() -> String.format("Method %s should have parameter %s but has noone", jMethod, paramName));
		List<Parameter> params = jMethod.getParameters().stream()
			.filter(it -> Objects.equals(it.getName(), paramName)).toList();
		assertEquals(1, params.size(),
			() -> String.format("Method %s should have single parameter %s but has %s", jMethod, paramName, params));
		Parameter paramToVerify = params.get(0);
		assertEquals(paramName, paramToVerify.getName(), "Method parameter name is not as expected");
		assertJavaElementHasTypeRef(paramToVerify, paramTypeRef);
	}

	/**
	 * Asserts that a method does not have a parameter with the given name.
	 */
	public static void assertJavaMethodDontHaveParameter(Method jMethod, String paramName) {
		assertTrue(jMethod.getParameters().stream().noneMatch(it -> Objects.equals(it.getName(), paramName)),
			() -> String.format("Method %s should not have parameter %s but has", jMethod, paramName));
	}

	/**
	 * Asserts that a TypedElement (parameter, attribute, method) has the given type reference
	 */
	public static void assertJavaElementHasTypeRef(TypedElement jTypedElement, TypeReference typeRef) {
		assertTypeEquals(typeRef, jTypedElement.getTypeReference());
	}

	public static void assertTypeEquals(TypeReference expected, TypeReference actual) {
		assertTrue(typeReferenceEquals(expected, actual),
			() -> String.format("Type should be %s but is %s", expected, actual));
	}

	/**
	 * Asserts that the given childClass has the given superclass by checking their names.
	 * @param childClass Java Child class
	 * @param superClass Java Super class
	 */
	public static void assertHasSuperClass(Class childClass, Class superClass) {
		assertEquals(superClass.getName(), getClassifierFromTypeReference(childClass.getExtends()).getName(),
			"Class has unexpected super class");
	}

	private static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
