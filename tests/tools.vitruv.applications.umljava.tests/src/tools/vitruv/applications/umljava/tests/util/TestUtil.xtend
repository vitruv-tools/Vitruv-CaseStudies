package tools.vitruv.applications.umljava.tests.util

import org.eclipse.uml2.uml.Feature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Static
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.types.Void
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.NamespaceClassifierReference
import java.util.List
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.EnumerationLiteral
import org.emftext.language.java.members.EnumConstant
import org.apache.log4j.Logger
import org.emftext.language.java.containers.CompilationUnit
import org.eclipse.uml2.uml.Model
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.ClassMethod
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * Util class for assertions that works bidirectional.
 * 
 */
@Utility
class TestUtil {

	static val logger = Logger.getLogger(TestUtil)

	/**
	 * Does not compare the methods and attributes of the classes
	 */
	def static void assertClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		assertEquals(uClass.name, jClass.name)
		assertAbstractClassEquals(uClass, jClass)
		assertFinalClassEquals(uClass, jClass)
		assertVisibilityEquals(uClass, jClass)
		assertPackageEquals(uClass.namespace as org.eclipse.uml2.uml.Package,
			(jClass.eContainer as CompilationUnit).namespaces)
	}

	/**
	 * Does not compare the methods and attributes of the interfaces
	 */
	def static void assertInterfaceEquals(org.eclipse.uml2.uml.Interface uInterface,
		org.emftext.language.java.classifiers.Interface jInterface) {
		assertEquals(uInterface.name, jInterface.name)
		assertVisibilityEquals(uInterface, jInterface)
		assertPackageEquals(uInterface.namespace as org.eclipse.uml2.uml.Package,
			(jInterface.eContainer as CompilationUnit).namespaces)
	}

	def static void assertAttributeEquals(Property uAttribute, Field jAttribute) {
		assertEquals(uAttribute.name, jAttribute.name)
		assertVisibilityEquals(uAttribute, jAttribute)
		assertFinalAttributeEquals(uAttribute, jAttribute)
		assertStaticEquals(uAttribute, jAttribute)
		assertTypeEquals(uAttribute.type, jAttribute.typeReference)
	}

	/**
	 * Does not compare the package contents
	 */
	def static dispatch void assertPackageEquals(org.eclipse.uml2.uml.Package uPackage,
		org.emftext.language.java.containers.Package jPackage) {
		assertEquals(uPackage.name, jPackage.name)
		assertEquals(getUmlParentNamespaceAsStringList(uPackage), jPackage.namespaces)
	}

	def static dispatch void assertPackageEquals(org.eclipse.uml2.uml.Package uPackage,
		List<String> packageStringList) {
		assertThat(getUmlNamespaceAsStringList(uPackage), is(packageStringList))
	}

	def static dispatch void assertPackageEquals(Model uModel, java.lang.Void empty) {
		// Do Nothing, assertion passed (Default package)
	}

	def static dispatch void assertPackageEquals(Model uModel, List<String> list) {
		assertTrue(list.nullOrEmpty)
	}

	/**
	 * It does compare enum literals of the enums
	 */
	def static void assertEnumEquals(org.eclipse.uml2.uml.Enumeration uEnum,
		org.emftext.language.java.classifiers.Enumeration jEnum) {
		assertEquals(uEnum.name, jEnum.name)
		assertVisibilityEquals(uEnum, jEnum)
		assertEnumConstantListEquals(uEnum.ownedLiterals, jEnum.constants)
	}

	def static void assertEnumConstantListEquals(List<EnumerationLiteral> uEnumLiteralList,
		List<EnumConstant> jEnumConstantList) {
		assertEquals(uEnumLiteralList.size, jEnumConstantList.size)
		for (uLiteral : uEnumLiteralList) {
			val jConstants = jEnumConstantList.filter[name == uLiteral.name]
			if (jConstants.nullOrEmpty) {
				fail("There is no corresponding java enum constant with the name '" + uLiteral.name + "'")
			} else if (jConstants.size > 1) {
				logger.warn("There are more than one parameter with the name '" + uLiteral.name + "'")
			} else {
				assertEnumConstantEquals(uLiteral, jConstants.head)
			}
		}
	}

	def static void assertEnumConstantEquals(EnumerationLiteral uLiteral, EnumConstant jConstant) {
		assertEquals(uLiteral.name, jConstant.name)
	}

	/**
	 * It does compare the parameter of the methods
	 */
	def static void assertClassMethodEquals(Operation uMethod, ClassMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name)
		assertStaticEquals(uMethod, jMethod)
		assertFinalMethodEquals(uMethod, jMethod)
		assertAbstractMethodEquals(uMethod, jMethod)
		assertVisibilityEquals(uMethod, jMethod)
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParameterListEquals(uMethod.ownedParameters, jMethod.parameters)
	}

	/**
	 * Interface methods = methods without body.
	 * Also checks if uMethod is abstract.
	 * 
	 */
	def static void assertInterfaceMethodEquals(Operation uMethod, InterfaceMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name)
		assertTrue(uMethod.abstract) // jMethod is always implicitly abstract
		assertUmlNamedElementHasVisibility(uMethod, VisibilityKind.PUBLIC_LITERAL) // JMehod is always implicitly public
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParameterListEquals(uMethod.ownedParameters, jMethod.parameters)
	}

	def static void assertStaticEquals(Feature uElement, AnnotableAndModifiable jElement) {
		if (uElement.static) {
			assertTrue(jElement.hasModifier(Static))
		} else {
			assertFalse(jElement.hasModifier(Static))
		}
	}

	def static void assertFinalClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.finalSpecialization) {
			assertTrue(jClass.hasModifier(Final))
		} else {
			assertFalse(jClass.hasModifier(Final))
		}
	}

	def static void assertFinalAttributeEquals(Property uAttribute, Field jAttribute) {
		if (uAttribute.readOnly) {
			assertTrue(jAttribute.hasModifier(Final))
		} else {
			assertFalse(jAttribute.hasModifier(Final))
		}
	}

	def static void assertAbstractClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.abstract) {
			assertTrue(jClass.hasModifier(Abstract))
		} else {
			assertFalse(jClass.hasModifier(Abstract))
		}
	}

	def static void assertFinalMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.isLeaf) {
			assertTrue(jMethod.hasModifier(Final))
		} else {
			assertFalse(jMethod.hasModifier(Final))
		}
	}

	def static void assertAbstractMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.abstract) {
			assertTrue(jMethod.hasModifier(Abstract))
		} else {
			assertFalse(jMethod.hasModifier(Abstract))
		}
	}

	def static void assertVisibilityEquals(org.eclipse.uml2.uml.NamedElement uElement,
		AnnotableAndModifiable jElement) {
		val jVisibility = getJavaVisibilityConstantFromUmlVisibilityKind(uElement.visibility)
		assertJavaModifiableHasVisibility(jElement, jVisibility)
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.Type uType, TypeReference jTypeReference) {
		throw new IllegalArgumentException("The java TypeReference " + jTypeReference +
			" is neither a PrimitiveType nor a NamespaceClassifierReference")
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.Interface uInterface,
		NamespaceClassifierReference namespaceRef) {
		assertEquals(uInterface.name, getInterfaceFromTypeReference(namespaceRef).name)
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.Class uClass,
		NamespaceClassifierReference namespaceRef) {
		assertEquals(uClass.name, getClassifierFromTypeReference(namespaceRef).name)
	}

	def static dispatch void assertTypeEquals(java.lang.Void nullReference, Void voidType) {
		// umlType is null, javaType is void -> Do nothing, assertion passed.
	}

	// IMPORTANT EXCEPTION: String is NOT a primitive in the Java model, which means this dispatch case needs to exist:
	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		NamespaceClassifierReference namespaceRef) {
		assertNotNull(uPrimType)
		val jTypeMapped = UmlJavaTypePropagationHelper.mapUmlPrimitiveToJavaPrimitive(uPrimType)
		assertFalse(jTypeMapped instanceof Void) // if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(getClassifierFromTypeReference(jTypeMapped).name,
			getClassifierFromTypeReference(namespaceRef).name)
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		org.emftext.language.java.types.PrimitiveType jPrimType) {
		assertNotNull(uPrimType)
		val jTypeMapped = UmlJavaTypePropagationHelper.mapUmlPrimitiveToJavaPrimitive(uPrimType)
		assertFalse(jTypeMapped instanceof Void) // if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(jTypeMapped.class, jPrimType.class)
	}

	def static void assertParameterListEquals(List<org.eclipse.uml2.uml.Parameter> uParamList,
		List<org.emftext.language.java.parameters.Parameter> jParamList) {
		val uParamListWithoutReturn = uParamList.filter[direction != ParameterDirectionKind.RETURN_LITERAL]
		if (uParamListWithoutReturn === null) {
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

	def static void assertParameterEquals(org.eclipse.uml2.uml.Parameter uParameter,
		org.emftext.language.java.parameters.Parameter jParameter) {
		assertEquals(uParameter.name, jParameter.name)
		assertTypeEquals(uParameter.type, jParameter.typeReference)
	}

}
