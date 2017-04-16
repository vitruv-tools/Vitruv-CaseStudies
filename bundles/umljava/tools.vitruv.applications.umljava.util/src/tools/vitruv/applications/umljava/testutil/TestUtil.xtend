package tools.vitruv.application.umljava.testutil

import org.eclipse.uml2.uml.Feature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Static

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.modifiers.Abstract
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.types.Void
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.NamespaceClassifierReference
import java.util.List
import org.eclipse.uml2.uml.ParameterDirectionKind

class TestUtil {
	private new() {
		
	}
	
	/**
	 * Does not compare the methods and attributes of the classes
	 */
	def static assertClassEquals(org.eclipse.uml2.uml.Class uClass, org.emftext.language.java.classifiers.Class jClass) {
		assertEquals(uClass.name, jClass.name)
		assertAbstractClassEquals(uClass, jClass)
		assertFinalClassEquals(uClass, jClass)
		assertVisibilityEquals(uClass, jClass)
	}
	
	def static assertMethodEquals(Operation uMethod, Method jMethod) {
		assertEquals(uMethod.name, jMethod.name)
		assertStaticEquals(uMethod, jMethod)
		assertAbstractMethodEquals(uMethod, jMethod)
		assertVisibilityEquals(uMethod, jMethod)
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParameterListEquals(uMethod.ownedParameters, jMethod.parameters)
	}
	
	def static assertStaticEquals(Feature uElement, AnnotableAndModifiable jElement) {
		if (uElement.static) {
			assertTrue(jElement.hasModifier(Static))
		} else {
			assertFalse(jElement.hasModifier(Static))
		}
	}
	
	def static assertFinalClassEquals(org.eclipse.uml2.uml.Class uClass, org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.finalSpecialization) {
			assertTrue(jClass.hasModifier(Final))
		} else {
			assertFalse(jClass.hasModifier(Final))
		}
	}
	
	def static assertFinalAttributeEquals(Property uAttribute, Field jAttribute) {
		if (uAttribute.readOnly) {
			assertTrue(jAttribute.hasModifier(Final))
		} else {
			assertFalse(jAttribute.hasModifier(Final))
		}
	}
	
	def static assertAbstractClassEquals(org.eclipse.uml2.uml.Class uClass, org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.abstract) {
			assertTrue(jClass.hasModifier(Abstract))
		} else {
			assertFalse(jClass.hasModifier(Abstract))
		}
	}
	
	def static assertAbstractMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.abstract) {
			assertTrue(jMethod.hasModifier(Abstract))
		} else {
			assertFalse(jMethod.hasModifier(Abstract))
		}
	}
	
	def static assertVisibilityEquals(org.eclipse.uml2.uml.NamedElement uElement, AnnotableAndModifiable jElement) {
		switch (uElement.visibility) {
			case VisibilityKind.PUBLIC_LITERAL: {
				assertTrue(jElement.hasModifier(Public))
				assertFalse(jElement.hasModifier(Private))
				assertFalse(jElement.hasModifier(Protected))
			}
			case VisibilityKind.PROTECTED_LITERAL: {
				assertTrue(jElement.hasModifier(Protected))
				assertFalse(jElement.hasModifier(Private))
				assertFalse(jElement.hasModifier(Public))
			}
			case VisibilityKind.PRIVATE_LITERAL: {
				assertTrue(jElement.hasModifier(Private))
				assertFalse(jElement.hasModifier(Public))
				assertFalse(jElement.hasModifier(Protected))
			}
			case VisibilityKind.PACKAGE_LITERAL: {
				assertFalse(jElement.hasModifier(Private))
				assertFalse(jElement.hasModifier(Public))
				assertFalse(jElement.hasModifier(Protected))
			}
			default: throw new IllegalArgumentException("Invalid VisibilityKind: " + uElement.visibility)
		}
	}
	
	def static assertTypeEquals(org.eclipse.uml2.uml.Type uType, TypeReference jTypeReference) {
		if (uType == null) {
			assertTrue(jTypeReference instanceof Void)
		} else if (jTypeReference instanceof org.emftext.language.java.types.PrimitiveType) {
			assertPrimitiveTypeEquals(uType, jTypeReference)
		} else if (jTypeReference instanceof NamespaceClassifierReference) {
			assertNamespaceClassifierReferenceTypeEquals(uType, jTypeReference)
		} else {
			throw new IllegalArgumentException("The java TypeReference is neither a PrimitiveZype nor a NamespaceClassifierReference")
		}
	}
	
	def static assertPrimitiveTypeEquals(org.eclipse.uml2.uml.Type uPrimitiveType, org.emftext.language.java.types.PrimitiveType jPrimitiveType) {
		assertEquals(uPrimitiveType.name, jPrimitiveType.eClass.name.toLowerCase)
	}
	
	def static assertNamespaceClassifierReferenceTypeEquals(org.eclipse.uml2.uml.Type uType, NamespaceClassifierReference jNamespaceClassifierReference) {
		 assertEquals(uType.name, getClassifierfromTypeRef(jNamespaceClassifierReference).name)
	}
	
	def static assertParameterListEquals(List<org.eclipse.uml2.uml.Parameter> uParamList, List<org.emftext.language.java.parameters.Parameter> jParamList) {
		val uParamListWithoutReturn = uParamList.filter[direction != ParameterDirectionKind.RETURN_LITERAL]
		if (uParamListWithoutReturn == null) {
			assertNull(jParamList)
		} else {
			assertEquals(uParamListWithoutReturn.size, jParamList.size)
			for (uParam : uParamListWithoutReturn) {
				val jParams = jParamList.filter[name == uParam.name]
				if (jParams.nullOrEmpty) {
					fail("There is no corresponding java parameter with the name '" + uParam.name + "'")
				} else if (jParams.size > 1) {
					println("There are more than one parameter with the name '" + uParam.name + "'")
				} else {
					assertParameterEquals(uParam, jParams.head)
				}
			}
		}
	}
	
	def static assertParameterEquals(org.eclipse.uml2.uml.Parameter uParameter, org.emftext.language.java.parameters.Parameter jParameter) {
		assertEquals(uParameter.name , jParameter.name)
		assertTypeEquals(uParameter.type, jParameter.typeReference)
	}
	
	
	
}