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
import org.emftext.language.java.members.Constructor

/**
 * Utility class for assertions that works bidirectional.
 * 
 */
@Utility
class TestUtil {

	static val logger = Logger.getLogger(TestUtil)

	/**
	 * Does not compare the methods and attributes of the classes
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		assertEquals(uClass.name, jClass.name, "Class names must be equal")
		assertAbstractClassEquals(uClass, jClass)
		assertFinalClassEquals(uClass, jClass)
		assertVisibilityEquals(uClass, jClass)
		assertPackageEquals(uClass.namespace as org.eclipse.uml2.uml.Package,
			(jClass.eContainer as CompilationUnit).namespaces)
	}
	
	/**
	 * Does not compare the methods and attributes of the datatypes
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.DataType uDataType,
		org.emftext.language.java.classifiers.Class jClass) {
		assertEquals(uDataType.name, jClass.name, "Data type and class names must be equal")
		assertVisibilityEquals(uDataType, jClass)
		assertPackageEquals(uDataType.namespace as org.eclipse.uml2.uml.Package,
			(jClass.eContainer as CompilationUnit).namespaces)
	}

	/**
	 * Does not compare the methods and attributes of the interfaces
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Interface uInterface,
		org.emftext.language.java.classifiers.Interface jInterface) {
		assertEquals(uInterface.name, jInterface.name, "Interface names must be equal")
		assertVisibilityEquals(uInterface, jInterface)
		assertPackageEquals(uInterface.namespace as org.eclipse.uml2.uml.Package,
			(jInterface.eContainer as CompilationUnit).namespaces)
	}

	def static dispatch void assertElementsEqual(Property uAttribute, Field jAttribute) {
		assertEquals(uAttribute.name, jAttribute.name, "Attribute and field names must be equal")
		assertVisibilityEquals(uAttribute, jAttribute)
		assertFinalAttributeEquals(uAttribute, jAttribute)
		assertStaticEquals(uAttribute, jAttribute)
		assertTypeEquals(uAttribute.type, jAttribute.typeReference)
	}

	/**
	 * Does not compare the package contents
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Package uPackage,
		org.emftext.language.java.containers.Package jPackage) {
		assertEquals(uPackage.name, jPackage.name, "Package names must be equal")
		assertEquals(getUmlParentNamespaceAsStringList(uPackage), jPackage.namespaces,
			"Package namespaces names must be equal")
	}

	private def static dispatch void assertPackageEquals(org.eclipse.uml2.uml.Package uPackage,
		List<String> packageStringList) {
		assertThat("Package namespaces must be equal", getUmlNamespaceAsStringList(uPackage), is(packageStringList))
	}

	private def static dispatch void assertPackageEquals(Model uModel, java.lang.Void empty) {
		// Do Nothing, assertion passed (Default package)
	}

	private def static dispatch void assertPackageEquals(Model uModel, List<String> packageStringList) {
		assertTrue(packageStringList.nullOrEmpty, [
			String.format("UML models do not have a namespace but %s was expected", packageStringList)
		])
	}

	/**
	 * It does compare enumeration literals of the enumerations
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Enumeration uEnum,
		org.emftext.language.java.classifiers.Enumeration jEnum) {
		assertEquals(uEnum.name, jEnum.name, "Enumeration names must be equal")
		assertVisibilityEquals(uEnum, jEnum)
		assertEnumConstantListEquals(uEnum.ownedLiterals, jEnum.constants)
	}

	private def static void assertEnumConstantListEquals(List<EnumerationLiteral> uEnumLiteralList,
		List<EnumConstant> jEnumConstantList) {
		assertEquals(uEnumLiteralList.size, jEnumConstantList.size,
			"There must be the same number of enumeration literals")
		for (uLiteral : uEnumLiteralList) {
			val jConstants = jEnumConstantList.filter[name == uLiteral.name]
			if (jConstants.nullOrEmpty) {
				fail("There is no corresponding enumeration literal with name '" + uLiteral.name + "'")
			} else if (jConstants.size > 1) {
				logger.warn("There is more than one enumeration literal with name '" + uLiteral.name + "'")
			} else {
				assertElementsEqual(uLiteral, jConstants.head)
			}
		}
	}

	def static dispatch void assertElementsEqual(EnumerationLiteral uLiteral, EnumConstant jConstant) {
		assertEquals(uLiteral.name, jConstant.name, "Enumeration literal names must be equal")
	}

	def static dispatch void assertElementsEqual(Operation uMethod, Constructor jConstructor) {
		assertEquals(uMethod.name, jConstructor.name, "Constructor names must be equal")
		assertStaticEquals(uMethod, jConstructor)
		assertVisibilityEquals(uMethod, jConstructor)
		assertEquals(null, uMethod.type, "Constructors must not have a return type")
		assertParameterListEquals(uMethod.ownedParameters, jConstructor.parameters)
	}

	/**
	 * It does compare the parameter of the methods
	 */
	def static dispatch void assertElementsEqual(Operation uMethod, ClassMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name, "Method names must be equal")
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
	 */
	def static dispatch void assertElementsEqual(Operation uMethod, InterfaceMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name, "Method names must be equal")
		assertTrue(uMethod.abstract) // jMethod is always implicitly abstract
		assertUmlNamedElementHasVisibility(uMethod, VisibilityKind.PUBLIC_LITERAL) // JMehod is always implicitly public
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParameterListEquals(uMethod.ownedParameters, jMethod.parameters)
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Parameter uParameter,
		org.emftext.language.java.parameters.Parameter jParameter) {
		assertEquals(uParameter.name, jParameter.name, "Parameter names must be equal")
		assertTypeEquals(uParameter.type, jParameter.typeReference)
	}

	def static void assertStaticEquals(Feature uElement, AnnotableAndModifiable jElement) {
		if (uElement.static) {
			assertTrue(jElement.hasModifier(Static), [
				String.format("Element %s is expected to be static but is not", jElement)
			])
		} else {
			assertFalse(jElement.hasModifier(Static), [
				String.format("Element %s is not expected to be static but is", jElement)
			])
		}
	}

	private def static void assertFinalClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.finalSpecialization) {
			assertTrue(
				jClass.hasModifier(Final), [String.format("Class %s is expected to be final but is not", jClass)])
		} else {
			assertFalse(
				jClass.hasModifier(Final), [String.format("Class %s is not expected to be final but is", jClass)])
		}
	}

	def static void assertFinalAttributeEquals(Property uAttribute, Field jAttribute) {
		if (uAttribute.readOnly) {
			assertTrue(jAttribute.hasModifier(Final), [
				String.format("Attribute %s is expected to be final but is not", jAttribute)
			])
		} else {
			assertFalse(jAttribute.hasModifier(Final), [
				String.format("Attribute %s is not expected to be final but is", jAttribute)
			])
		}
	}

	private def static void assertAbstractClassEquals(org.eclipse.uml2.uml.Class uClass,
		org.emftext.language.java.classifiers.Class jClass) {
		if (uClass.abstract) {
			assertTrue(jClass.hasModifier(Abstract), [
				String.format("Class %s is expected to be abstract but is not", jClass)
			])
		} else {
			assertFalse(jClass.hasModifier(Abstract), [
				String.format("Class %s is not expected to be abstract but is", jClass)
			])
		}
	}

	def static void assertFinalMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.isLeaf) {
			assertTrue(jMethod.hasModifier(Final), [
				String.format("Method %s is expected to be final but is not", jMethod)
			])
		} else {
			assertFalse(jMethod.hasModifier(Final), [
				String.format("Method %s is not expected to be final but is", jMethod)
			])
		}
	}

	private def static void assertAbstractMethodEquals(Operation uMethod, Method jMethod) {
		if (uMethod.abstract) {
			assertTrue(jMethod.hasModifier(Abstract), [
				String.format("Method %s is expected to be abstract but is not", jMethod)
			])
		} else {
			assertFalse(jMethod.hasModifier(Abstract), [
				String.format("Method %s is not expected to be abstract but is", jMethod)
			])
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
		assertEquals(getInterfaceFromTypeReference(namespaceRef).name, uInterface.name,
			"Interface name is not as expected")
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.Class uClass,
		NamespaceClassifierReference namespaceRef) {
		assertEquals(getClassifierFromTypeReference(namespaceRef).name, uClass.name, "Class name is not as expected")
	}

	def static dispatch void assertTypeEquals(java.lang.Void nullReference, Void voidType) {
		// umlType is null, javaType is void -> Do nothing, assertion passed.
	}

	// IMPORTANT EXCEPTION: String is NOT a primitive in the Java model, which means this dispatch case needs to exist:
	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		NamespaceClassifierReference namespaceRef) {
		assertNotNull(uPrimType, "Primitive type to check must not be null")
		val jTypeMapped = UmlJavaTypePropagationHelper.mapUmlPrimitiveToJavaPrimitive(uPrimType)
		assertFalse(jTypeMapped instanceof Void, [String.format("Mapped primitive type for original type %s is void but should not", uPrimType)]) // if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(getClassifierFromTypeReference(namespaceRef).name,
			getClassifierFromTypeReference(jTypeMapped).name, "Type is not as expected")
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		org.emftext.language.java.types.PrimitiveType jPrimType) {
		assertNotNull(uPrimType, "Primitive type to check must not be null")
		val jTypeMapped = UmlJavaTypePropagationHelper.mapUmlPrimitiveToJavaPrimitive(uPrimType)
		assertFalse(jTypeMapped instanceof Void, [String.format("Mapped primitive type for original type %s is void but should not", uPrimType)]) // if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(jPrimType.class, jTypeMapped.class, "Type is not as expected")
	}

	private def static void assertParameterListEquals(List<org.eclipse.uml2.uml.Parameter> uParamList,
		List<org.emftext.language.java.parameters.Parameter> jParamList) {
		val uParamListWithoutReturn = uParamList.filter[direction != ParameterDirectionKind.RETURN_LITERAL]
		if (uParamListWithoutReturn === null) {
			assertNull(jParamList, "Parameter should not have a return type but has one")
		} else {
			assertEquals(uParamListWithoutReturn.size, jParamList.size, "Parameter lists must be of equal size")
			for (uParam : uParamListWithoutReturn) {
				val jParams = jParamList.filter[name == uParam.name]
				if (jParams.nullOrEmpty) {
					fail("There is no corresponding parameter with the name '" + uParam.name + "'")
				} else if (jParams.size > 1) {
					println("There is more than one parameter with the name '" + uParam.name + "'")
				} else {
					assertElementsEqual(uParam, jParams.head)
				}
			}
		}
	}

}
