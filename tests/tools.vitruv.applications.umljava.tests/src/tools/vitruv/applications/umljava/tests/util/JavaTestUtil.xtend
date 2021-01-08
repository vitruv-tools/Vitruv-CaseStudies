package tools.vitruv.applications.umljava.tests.util

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
 * Util class for assertions that only involves java elements.
 * 
 * @author Fei
 */
@Utility
class JavaTestUtil {

	/**
	 * Asserts that the given java class has the given traits.
	 */
	def static void assertJavaClassTraits(Class jClass, String name, JavaVisibility visibility, boolean isAbstract,
		boolean isFinal) {
		assertEquals(name, jClass.name)
		assertJavaModifiableHasVisibility(jClass, visibility)
		assertJavaModifiableFinal(jClass, isFinal)
		assertJavaModifiableAbstract(jClass, isAbstract)
	}

	/**
	 * Asserts that the given java enum has the given traits.
	 */
	def static void assertJavaEnumTraits(Enumeration jEnum, String name, JavaVisibility visibility,
		List<EnumConstant> constantsList) {
		assertEquals(name, jEnum.name)
		assertJavaModifiableHasVisibility(jEnum, visibility)
		assertJavaEnumConstantListEquals(constantsList, jEnum.constants)
	}

	/**
	 * Asserts that the enum constants of both given lists correspond pairwise by name.
	 */
	def static void assertJavaEnumConstantListEquals(List<EnumConstant> expectedList, List<EnumConstant> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty)
		} else {
			assertEquals(expectedList.size, actualList.size)
			for (const : expectedList) {
				val correspondingConstants = actualList.filter[name == const.name]
				if (correspondingConstants.size != 1) {
					fail("There are 0 or more than 1 constant with the name " + const.name)
				}
			}
		}

	}

	/**
	 * Asserts that the given java interface has the given traits.
	 */
	def static void assertJavaInterfaceTraits(Interface jInterface, String name, JavaVisibility visibility) {
		assertEquals(name, jInterface.name)
		assertJavaModifiableHasVisibility(jInterface, visibility)
	}

	/**
	 * Asserts that the given java class method has the given traits.
	 */
	def static void assertJavaClassMethodTraits(ClassMethod jMethod, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isStatic, boolean isAbstract, List<Parameter> parameterList,
		ConcreteClassifier containedClassifier) {
		assertEquals(name, jMethod.name)
		assertJavaModifiableHasVisibility(jMethod, visibility)
		assertJavaModifiableStatic(jMethod, isStatic)
		assertJavaModifiableAbstract(jMethod, isAbstract)
		assertEquals(containedClassifier.name, (jMethod.eContainer as ConcreteClassifier).name)
		assertJavaParameterListEquals(jMethod.parameters, parameterList)
	}

	/**
	 * Asserts that the given java interface method has the given traits.
	 * Asserts that the given interface method has public visibility.
	 * Asserts that the given interface method is not static.
	 */
	def static void assertJavaInterfaceMethodTraits(InterfaceMethod jMethod, String name, TypeReference typeRef,
		List<Parameter> parameterList, Interface containedInterface) {
		assertEquals(name, jMethod.name)
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PUBLIC)
		assertJavaModifiableStatic(jMethod, false)
		assertEquals(containedInterface.name, (jMethod.eContainer as Interface).name)
		assertJavaParameterListEquals(jMethod.parameters, parameterList)
	}

	/**
	 * Asserts that the given java parameter has the given traits.
	 */
	def static void assertJavaParameterTraits(Parameter jParam, String name, TypeReference typeRef) {
		assertEquals(name, jParam.name)
		assertJavaElementHasTypeRef(jParam, typeRef)
	}

	/**
	 * Asserts that the parameter in both given lists correspond pairwise by name.
	 */
	def static void assertJavaParameterListEquals(List<Parameter> expectedList, List<Parameter> actualList) {
		if (expectedList.nullOrEmpty) {
			assertTrue(actualList.nullOrEmpty)
		} else {
			assertEquals(expectedList.size, expectedList.size)
			for (param : expectedList) {
				val correspondingParams = actualList.filter[name == param.name]
				if (correspondingParams.size != 1) {
					fail("There are 0 or more than 1 Parameter with the name " + param.name)
				}
				assertJavaParameterTraits(correspondingParams.head, param.name, param.typeReference)
			}
		}
	}

	/**
	 * Asserts that the given java field has the given traits.
	 */
	def static void assertJavaAttributeTraits(Field jAttribute, String name, JavaVisibility visibility,
		TypeReference typeRef, boolean isFinal, boolean isStatic, ConcreteClassifier containedClassifier) {
		assertEquals(name, jAttribute.name)
		assertJavaModifiableHasVisibility(jAttribute, visibility)
		assertJavaElementHasTypeRef(jAttribute, typeRef)
		assertJavaModifiableFinal(jAttribute, isFinal)
		assertJavaModifiableStatic(jAttribute, isStatic)
		assertEquals(containedClassifier.name, (jAttribute.eContainer as ConcreteClassifier).name)
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
		assertTrue(modifiable.hasModifier(mod))
	}

	def static <T extends Modifier> void assertJavaModifiableDontHaveModifier(AnnotableAndModifiable modifiable,
		java.lang.Class<T> mod) {
		if (modifiable === null) {
			fail("The modifiable is null")
		} else if (mod === null) {
			fail("Cannot check modifier null")
		}
		assertFalse(modifiable.hasModifier(mod))
	}

	/**
	 * Asserts that a member container (class, interface, enum) does not contain an element
	 * with the given name.
	 */
	def static void assertJavaMemberContainerDontHaveMember(MemberContainer memContainer, String name) {
		assertTrue(memContainer.getMembersByName(name).nullOrEmpty)
	}

	/**
	 * Asserts that an enum has a constant with the given name.
	 */
	def static void assertJavaEnumHasConstant(Enumeration jEnum, String constantName) {
		assertNotNull(jEnum.getContainedConstant(constantName))
	}

	/**
	 * Asserts that an enum does not have a constant with the given name.
	 */
	def static void assertJavaEnumDontHaveConstant(Enumeration jEnum, String constantName) {
		assertNull(jEnum.getContainedConstant(constantName))
	}

	/**
	 * Asserts that a memberContainer has exactly one method with the name methodName
	 */
	def static void assertHasUniqueMethod(MemberContainer memContainer, String methodName) {
		// getContainedMethod returns null if there is no method with the name methodName or
		// if there are more than one method with the name methodName
		assertNotNull(memContainer.getContainedMethod(methodName))
	}

	/**
	 * Asserts that a memberContainer has a field with the name fieldName
	 */
	def static void assertHasUniqueField(MemberContainer memContainer, String fieldName) {
		assertNotNull(memContainer.getContainedField(fieldName))
	}

	/**
	 * Asserts that a given method has exactly one parameter with the given name and the given type.
	 */
	def static void assertJavaMethodHasUniqueParameter(Method jMethod, String paramName, TypeReference paramTypeRef) {
		assertFalse(jMethod.parameters.nullOrEmpty)
		val params = jMethod.parameters.filter[it.name == paramName]
		assertTrue(params.size == 1)
		val paramToVerify = params.head
		assertEquals(paramName, paramToVerify.name)
		assertJavaElementHasTypeRef(paramToVerify, paramTypeRef)
	}

	/**
	 * Asserts that a method does not have a parameter with the given name.
	 */
	def static void assertJavaMethodDontHaveParameter(Method jMethod, String paramName) {
		assertTrue(jMethod.parameters.filter[it.name == paramName].nullOrEmpty)
	}

	/**
	 * Asserts that a TypedElement (parameter, attribute, method)
	 * has the given type reference
	 */
	def static void assertJavaElementHasTypeRef(TypedElement jTypedElement, TypeReference typeRef) {
		assertTypeEquals(typeRef, jTypedElement.typeReference)
	}

	def static void assertTypeEquals(TypeReference typeRef1, TypeReference typeRef2) {
		assertTrue(typeReferenceEquals(typeRef1, typeRef2))
	}

	/**
	 * Asserts that the given childClass has the given superclass by checking their names.
	 * @param childClass Java Child class
	 * @param superClass Java Super class
	 */
	def static void assertHasSuperClass(Class childClass, Class superClass) {
		assertEquals(superClass.name, getClassifierFromTypeReference(childClass.extends).name)
	}
}
