package tools.vitruv.applications.testutility.integration

import org.emftext.language.java.modifiers.AnnotableAndModifiable
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.members.MemberContainer
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.classifiers.ConcreteClassifier
import java.util.List
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.ClassMethod

import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * Utility class for assertions that only involves java elements.
 */
@Utility
class JavaElementsTestAssertions {

	/**
	 * Asserts that the given Java class has the given traits.
	 */
	def static void assertJavaClassTraits(Class jClass, String name, JavaVisibility visibility, boolean isAbstract,
		boolean isFinal) {
		assertEquals(name, jClass.name, "Class name is not as expected")
		assertJavaModifiableHasVisibility(jClass, visibility)
		assertJavaModifiableFinal(jClass, isFinal)
		assertJavaModifiableAbstract(jClass, isAbstract)
	}

	/**
	 * Asserts that the given Java enumeration has the given traits.
	 */
	def static void assertJavaEnumTraits(Enumeration jEnum, String name, JavaVisibility visibility,
		List<EnumConstant> constantsList) {
		assertEquals(name, jEnum.name, "Enumeration name is not as expected")
		assertJavaModifiableHasVisibility(jEnum, visibility)
		assertJavaEnumConstantListEquals(constantsList, jEnum.constants)
	}

	/**
	 * Asserts that the enumeration constants of both given lists correspond pairwise by name.
	 */
	def static void assertJavaEnumConstantListEquals(List<EnumConstant> expectedList, List<EnumConstant> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty, [
				String.format("Enumeration literal list should be empty but is %s", actualList)
			])
		} else {
			assertEquals(expectedList.size, actualList.size, "Enumeration literal lists have different sizes")
			for (const : expectedList) {
				val correspondingConstants = actualList.filter[name == const.name]
				assertEquals(1, correspondingConstants.size, [
					String.format("There is not exactly one corresponding enumeration constants with name %s", const.name)
				])
			}
		}

	}

	/**
	 * Asserts that the given Java interface has the given traits.
	 */
	def static void assertJavaInterfaceTraits(Interface jInterface, String name, JavaVisibility visibility) {
		assertEquals(name, jInterface.name, "Interface name is not as expected")
		assertJavaModifiableHasVisibility(jInterface, visibility)
	}

	/**
	 * Asserts that the given Java class method has the given traits.
	 */
	def static void assertJavaClassMethodTraits(ClassMethod jMethod, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isStatic, boolean isAbstract, List<Parameter> parameterList,
		ConcreteClassifier containingClassifier) {
		assertEquals(name, jMethod.name, "Class method name is not as expected")
		assertJavaModifiableHasVisibility(jMethod, visibility)
		assertJavaModifiableStatic(jMethod, isStatic)
		assertJavaModifiableAbstract(jMethod, isAbstract)
		assertEquals(containingClassifier.name, (jMethod.eContainer as ConcreteClassifier).name,
			"Method is not contained in correct classifier")
		assertJavaParameterListEquals(jMethod.parameters, parameterList)
	}

	/**
	 * Asserts that the given Java interface method has the given traits.
	 * Asserts that the given interface method has public visibility.
	 * Asserts that the given interface method is not static.
	 */
	def static void assertJavaInterfaceMethodTraits(InterfaceMethod jMethod, String name, TypeReference typeRef,
		List<Parameter> parameterList, Interface containingInterface) {
		assertEquals(name, jMethod.name, "Interface method name is not as expected")
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PUBLIC)
		assertJavaModifiableStatic(jMethod, false)
		assertEquals(containingInterface.name, (jMethod.eContainer as Interface).name,
			"Method is not contained in correct classifier")
		assertJavaParameterListEquals(jMethod.parameters, parameterList)
	}

	/**
	 * Asserts that the given Java parameter has the given traits.
	 */
	def static void assertJavaParameterTraits(Parameter jParam, String name, TypeReference typeRef) {
		assertEquals(name, jParam.name, "Parameter name is not as expected")
		assertJavaElementHasTypeRef(jParam, typeRef)
	}

	/**
	 * Asserts that the parameter in both given lists correspond pairwise by name.
	 */
	def static void assertJavaParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty, [String.format("Parameter list should be empty but is %s", actualList)])
		} else {
			assertEquals(expectedList.size, expectedList.size, "Parameter literal lists have different sizes")
			for (param : expectedList) {
				val correspondingParams = actualList.filter[name == param.name]
				assertEquals(1, correspondingParams.size, [
					String.format("There are 0 or more than 1 parameters with the name %s", param.name)
				])
				assertJavaParameterTraits(correspondingParams.head, param.name, param.typeReference)
			}
		}
	}

	/**
	 * Asserts that the given Java field has the given traits.
	 */
	def static void assertJavaAttributeTraits(Field jAttribute, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isFinal, boolean isStatic, ConcreteClassifier containingClassifier) {
		assertEquals(name, jAttribute.name, "Attribute name is not as expected")
		assertJavaModifiableHasVisibility(jAttribute, visibility)
		assertJavaElementHasTypeRef(jAttribute, typeRef)
		assertJavaModifiableFinal(jAttribute, isFinal)
		assertJavaModifiableStatic(jAttribute, isStatic)
		assertEquals(containingClassifier.name, (jAttribute.eContainer as ConcreteClassifier).name,
			"Attribute is not contained in correct classifier")
	}

	def static void assertJavaModifiableFinal(AnnotableAndModifiable modifiable, boolean isFinal) {
		assertModifierExisistenceAsExpected(modifiable, Final, isFinal)
	}

	def static void assertJavaModifiableStatic(AnnotableAndModifiable modifiable, boolean isStatic) {
		assertModifierExisistenceAsExpected(modifiable, Static, isStatic)
	}

	def static void assertJavaModifiableAbstract(AnnotableAndModifiable modifiable, boolean isAbstract) {
		assertModifierExisistenceAsExpected(modifiable, Abstract, isAbstract)
	}

	def static void assertModifierExisistenceAsExpected(AnnotableAndModifiable modifiable,
		java.lang.Class<? extends Modifier> modifierClass, boolean exists) {
		if (exists) {
			assertJavaModifiableHasModifier(modifiable, modifierClass)
		} else {
			assertJavaModifiableDontHaveModifier(modifiable, modifierClass)
		}
	}

	def static void assertJavaModifiableHasVisibility(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
		switch (visibility) {
			case JavaVisibility.PUBLIC: {
				assertJavaModifiableHasModifier(modifiable, Public)
				assertJavaModifiableDontHaveModifier(modifiable, Private)
				assertJavaModifiableDontHaveModifier(modifiable, Protected)
			}
			case JavaVisibility.PRIVATE: {
				assertJavaModifiableHasModifier(modifiable, Private)
				assertJavaModifiableDontHaveModifier(modifiable, Public)
				assertJavaModifiableDontHaveModifier(modifiable, Protected)
			}
			case JavaVisibility.PROTECTED: {
				assertJavaModifiableHasModifier(modifiable, Protected)
				assertJavaModifiableDontHaveModifier(modifiable, Private)
				assertJavaModifiableDontHaveModifier(modifiable, Public)
			}
			case JavaVisibility.PACKAGE: {
				assertJavaModifiableDontHaveModifier(modifiable, Public)
				assertJavaModifiableDontHaveModifier(modifiable, Private)
				assertJavaModifiableDontHaveModifier(modifiable, Protected)
			}
			default:
				throw new IllegalArgumentException("Unknown VisibilityKind: " + visibility)
		}
	}

	def static <T extends Modifier> void assertJavaModifiableHasModifier(AnnotableAndModifiable modifiable,
		java.lang.Class<T> mod) {
		if (modifiable === null) {
			fail("The modifiable is null")
		} else if (mod === null) {
			fail("Cannot check modifier null")
		}
		assertTrue(modifiable.hasModifier(mod), [
			String.format("Element %s should have modifier %s but has not", modifiable, mod)
		])
	}

	def static <T extends Modifier> void assertJavaModifiableDontHaveModifier(AnnotableAndModifiable modifiable,
		java.lang.Class<T> mod) {
		if (modifiable === null) {
			fail("The modifiable is null")
		} else if (mod === null) {
			fail("Cannot check modifier null")
		}
		assertFalse(modifiable.hasModifier(mod), [
			String.format("Element %s should not have modifier %s but has", modifiable, mod)
		])
	}

	/**
	 * Asserts that a member container (class, interface, enumeration) does not contain an element
	 * with the given name.
	 */
	def static void assertJavaMemberContainerDontHaveMember(MemberContainer memContainer, String name) {
		assertTrue(memContainer.getMembersByName(name).nullOrEmpty, [
			String.format("Element %s should not have member %s but has", memContainer, name)
		])
	}

	/**
	 * Asserts that an enumeration has a constant with the given name.
	 */
	def static void assertJavaEnumHasConstant(Enumeration jEnum, String constantName) {
		assertNotNull(jEnum.getContainedConstant(constantName), [
			String.format("Enumeration %s should have constant %s but has not", jEnum, constantName)
		])
	}

	/**
	 * Asserts that an enumeration does not have a constant with the given name.
	 */
	def static void assertJavaEnumDontHaveConstant(Enumeration jEnum, String constantName) {
		assertNull(jEnum.getContainedConstant(constantName), [
			String.format("Enumeration %s should not have constant %s but has", jEnum, constantName)
		])
	}

	/**
	 * Asserts that a memberContainer has exactly one method with the name methodName
	 */
	def static void assertHasUniqueMethod(MemberContainer memContainer, String methodName) {
		// getContainedMethod returns null if there is no method with the name methodName or
		// if there are more than one method with the name methodName
		assertNotNull(memContainer.getContainedMethod(methodName), [
			String.format("Element %s should have method %s but has not", memContainer, methodName)
		])
	}

	/**
	 * Asserts that a memberContainer has a field with the name fieldName
	 */
	def static void assertHasUniqueField(MemberContainer memContainer, String fieldName) {
		assertNotNull(memContainer.getContainedField(fieldName), [
			String.format("Element %s should have field %s but has not", memContainer, fieldName)
		])
	}

	/**
	 * Asserts that a given method has exactly one parameter with the given name and the given type.
	 */
	def static void assertJavaMethodHasUniqueParameter(Method jMethod, String paramName, TypeReference paramTypeRef) {
		assertFalse(jMethod.parameters.nullOrEmpty, [
			String.format("Method %s should have parameter %s but has noone", jMethod, paramName)
		])
		val params = jMethod.parameters.filter[it.name == paramName]
		assertEquals(1, params.size, [
			String.format("Method %s should have single parameter %s but has %s", jMethod, paramName, params)
		])
		val paramToVerify = params.head
		assertEquals(paramName, paramToVerify.name, "Method parameter name is not as expected")
		assertJavaElementHasTypeRef(paramToVerify, paramTypeRef)
	}

	/**
	 * Asserts that a method does not have a parameter with the given name.
	 */
	def static void assertJavaMethodDontHaveParameter(Method jMethod, String paramName) {
		assertTrue(jMethod.parameters.filter[it.name == paramName].nullOrEmpty, [
			String.format("Method %s should not have parameter %s but has", jMethod, paramName)
		])
	}

	/**
	 * Asserts that a TypedElement (parameter, attribute, method) has the given type reference
	 */
	def static void assertJavaElementHasTypeRef(TypedElement jTypedElement, TypeReference typeRef) {
		assertTypeEquals(typeRef, jTypedElement.typeReference)
	}

	def static void assertTypeEquals(TypeReference expected, TypeReference actual) {
		assertTrue(typeReferenceEquals(expected, actual), [
			String.format("Type should be %s but is %s", expected, actual)
		])
	}

	/**
	 * Asserts that the given childClass has the given superclass by checking their names.
	 * @param childClass Java Child class
	 * @param superClass Java Super class
	 */
	def static void assertHasSuperClass(Class childClass, Class superClass) {
		assertEquals(superClass.name, getClassifierFromTypeReference(childClass.extends).name,
			"Class has unexpected super class")
	}
}
