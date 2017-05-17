package tools.vitruv.applications.umljava.testutil

import static org.junit.Assert.*
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.members.Member
import org.emftext.language.java.members.MemberContainer
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.classifiers.ConcreteClassifier
import java.util.List
import org.emftext.language.java.modifiers.Abstract

class JavaTestUtil {
	
	private new() {}
	
	def static void assertJavaClassTraits(Class jClass, String name, JavaVisibility visibility, 
		boolean isAbstract, boolean isFinal) {
	    assertEquals(name, jClass.name)
	    assertJavaModifiableHasVisibility(jClass, visibility)
	    assertJavaModifiableFinal(jClass, isFinal)
	    assertJavaModifiableAbstract(jClass, isAbstract)
	}
	def static void assertJavaEnumTraits(Enumeration jEnum, String name, JavaVisibility visibility,
		List<EnumConstant> constantsList) {
		assertEquals(name, jEnum.name)
		assertJavaModifiableHasVisibility(jEnum, visibility)
		assertJavaEnumConstantListEquals(constantsList, jEnum.constants)
	}
	
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
	
	def static void assertJavaInterfaceTraits(Interface jInterface, String name, JavaVisibility visibility) {
	    assertEquals(name, jInterface.name)
	    assertJavaModifiableHasVisibility(jInterface, visibility)
	}
	
	def static void assertJavaMethodTraits(Method jMethod, String name, JavaVisibility visibility, 
		TypeReference typeRef, boolean isStatic, boolean isAbstract, List<Parameter> parameterList,
		ConcreteClassifier containedClassifier) {
		assertEquals(name, jMethod.name)
		assertJavaModifiableHasVisibility(jMethod, visibility)
		assertJavaModifiableStatic(jMethod, isStatic)
		assertJavaModifiableAbstract(jMethod, isAbstract)
		assertEquals(containedClassifier.name, (jMethod.eContainer as ConcreteClassifier).name)
		assertJavaParameterListEquals(jMethod.parameters, parameterList)
	}
	
	def static void assertJavaParameterTraits(Parameter jParam, String name, TypeReference typeRef) {
		assertEquals(name, jParam.name)
		assertJavaElementHasTypeRef(jParam, typeRef)
	}
	

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
	
	
	def static void assertJavaAttributeTraits(Field jAttribute, String name, JavaVisibility visibility, 
		TypeReference typeRef, boolean isFinal, boolean isStatic, ConcreteClassifier containedClassifier) {
		assertEquals(name, jAttribute.name)
		assertJavaModifiableHasVisibility(jAttribute, visibility)
        assertJavaElementHasTypeRef(jAttribute, typeRef)
        assertJavaModifiableFinal(jAttribute, isFinal)
        assertJavaModifiableStatic(jAttribute, isStatic)
        assertEquals(containedClassifier.name , (jAttribute.eContainer as ConcreteClassifier).name)
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
	
	def static void assertModifierExisistenceAsExpected(AnnotableAndModifiable modifiable, java.lang.Class<? extends Modifier> modifierClass, boolean exists) {
	    if (exists) {
            assertJavaModifiableHasModifier(modifiable, modifierClass)
        } else {
            assertJavaModifiableDontHaveModifier(modifiable, modifierClass)
        }
	}
	
	def static void assertJavaModifiableHasVisibility(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
        switch(visibility) {
            case JavaVisibility.PUBLIC: {
                assertJavaModifiableHasModifier(modifiable, Public);
                assertJavaModifiableDontHaveModifier(modifiable, Private);
                assertJavaModifiableDontHaveModifier(modifiable, Protected);
            }
            case JavaVisibility.PRIVATE: {
                assertJavaModifiableHasModifier(modifiable, Private);
                assertJavaModifiableDontHaveModifier(modifiable, Public);
                assertJavaModifiableDontHaveModifier(modifiable, Protected);
            }
            case JavaVisibility.PROTECTED: {
                assertJavaModifiableHasModifier(modifiable, Protected);
                assertJavaModifiableDontHaveModifier(modifiable, Private);
                assertJavaModifiableDontHaveModifier(modifiable, Public);
            }
            case JavaVisibility.PACKAGE: {
                assertJavaModifiableDontHaveModifier(modifiable, Public);
                assertJavaModifiableDontHaveModifier(modifiable, Private);
                assertJavaModifiableDontHaveModifier(modifiable, Protected);
            }
            default: throw new IllegalArgumentException("Unknown VisibilityKind: " + visibility)
        }
    }
    def static <T extends Modifier> void assertJavaModifiableHasModifier(AnnotableAndModifiable modifiable, java.lang.Class<T> mod) {
        if (modifiable === null) {
        	fail("The modifiable is null")
        } else if (mod === null) {
        	fail("Cannot check modifier null")
        }
        assertTrue(modifiable.hasModifier(mod))
    }
    
    def  static <T extends Modifier> void assertJavaModifiableDontHaveModifier(AnnotableAndModifiable modifiable, java.lang.Class<T> mod) {
        if (modifiable === null) {
        	fail("The modifiable is null")
        } else if (mod === null) {
        	fail("Cannot check modifier null")
        }
        assertFalse(modifiable.hasModifier(mod))
    }
    
    def static void assertJavaMemberContainerDontHaveMember(MemberContainer memContainer, String name) {
    	assertTrue(memContainer.getMembersByName(name).nullOrEmpty)
    }
    
    def static void assertJavaEnumHasConstant(Enumeration jEnum, String constantName) {
    	assertNotNull(jEnum.getContainedConstant(constantName))
    }
    def static void assertJavaEnumDontHaveConstant(Enumeration jEnum, String constantName) {
    	assertNull(jEnum.getContainedConstant(constantName))
    }
    
    /**
     * Asserts that a memberContainer has exactly one method with the name methodName
     */
    def static void assertHasUniqueMethod(MemberContainer memContainer, String methodName) {
    	//getContainedMethod returns null if there is no method with the name methodName or
    	//if there are more than one method with the name methodName
    	assertNotNull(memContainer.getContainedMethod(methodName))
    }
    
    /**
     * Asserts that a memberContainer has a field with the name fieldName
     */
    def static void assertHasUniqueField(MemberContainer memContainer, String fieldName) {
    	assertNotNull(memContainer.getContainedField(fieldName))
    }
    
    /**
     * @param jMethod Java-Methode
     * @param param Java-Parameter
     */
    def static void assertJavaMethodHasUniqueParameter(Method jMethod, String paramName, TypeReference paramTypeRef) {
        assertFalse(jMethod.parameters.nullOrEmpty);
        val params = jMethod.parameters.filter[it.name == paramName]
        assertTrue(params.size == 1);
        val paramToVerify = params.head;
        assertEquals(paramName, paramToVerify.name);
        assertJavaElementHasTypeRef(paramToVerify, paramTypeRef)
    }
    
    def static void assertJavaMethodDontHaveParameter(Method jMethod, String paramName) {
    	assertTrue(jMethod.parameters.filter[it.name == paramName].nullOrEmpty)
    }
    
    /**
     * @param jMem Java-TypedElement (Attribute, Method, Parameter)
     * @param uType Uml-Typ
     */
    def static void assertJavaElementHasTypeRef(TypedElement jTypedElement, TypeReference typeRef) {
        assertTypeEquals(typeRef, jTypedElement.typeReference)
    }
    
    def static void assertTypeEquals(TypeReference typeRef1, TypeReference typeRef2) {
        assertTrue(typeReferenceEquals(typeRef1, typeRef2))
    }
    
    /**
     * @param childClass Java-Kindklasse
     * @param superClass Java-Superclass
     */
	def static void assertHasSuperClass(Class childClass, Class superClass) {
	    assertEquals(superClass.name, getClassifierFromTypeReference(childClass.extends).name);
	}
}