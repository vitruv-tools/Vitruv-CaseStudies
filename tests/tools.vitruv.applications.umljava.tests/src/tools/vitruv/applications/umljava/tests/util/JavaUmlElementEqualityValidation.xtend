package tools.vitruv.applications.umljava.tests.util

import org.eclipse.uml2.uml.Feature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import tools.mdsd.jamopp.model.java.members.Field
import tools.mdsd.jamopp.model.java.members.Method
import tools.mdsd.jamopp.model.java.modifiers.AnnotableAndModifiable
import tools.mdsd.jamopp.model.java.modifiers.Final
import tools.mdsd.jamopp.model.java.modifiers.Static
import tools.mdsd.jamopp.model.java.modifiers.Abstract
import tools.mdsd.jamopp.model.java.types.Void
import tools.mdsd.jamopp.model.java.types.TypeReference
import tools.mdsd.jamopp.model.java.types.NamespaceClassifierReference
import java.util.List
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.EnumerationLiteral
import tools.mdsd.jamopp.model.java.members.EnumConstant
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Model
import tools.mdsd.jamopp.model.java.members.InterfaceMethod
import tools.mdsd.jamopp.model.java.members.ClassMethod
import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.mdsd.jamopp.model.java.members.Constructor
import org.eclipse.uml2.uml.Classifier
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier
import tools.mdsd.jamopp.model.java.parameters.Parametrizable
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.DataType
import tools.mdsd.jamopp.model.java.parameters.VariableLengthParameter
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import static tools.vitruv.applications.umljava.tests.util.UmlElementsTestAssertions.*
import static tools.vitruv.applications.umljava.tests.util.JavaElementsTestAssertions.*

/**
 * Utility class for assertions that works bidirectional.
 */
@Utility
class JavaUmlElementEqualityValidation {

	static val logger = Logger.getLogger(JavaUmlElementEqualityValidation)

	// **************
	// PACKAGES
	// **************
	/**
	 * Does not compare the package contents
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Package uPackage,
		tools.mdsd.jamopp.model.java.containers.Package jPackage) {
		assertEquals(uPackage.name, jPackage.name, "Package names must be equal")
		assertEquals(getUmlParentNamespaceAsStringList(uPackage), jPackage.namespaces,
			"Package namespaces names must be equal")
	}

	private def static dispatch void assertPackageEquals(org.eclipse.uml2.uml.Namespace umlNamespace,
		List<String> packageStringList) {
		assertThat("Package namespaces must be equal", getUmlNamespaceAsStringList(umlNamespace), is(packageStringList))
	}

	private def static dispatch void assertPackageEquals(Model umlModel, java.lang.Void empty) {
		// Do Nothing, assertion passed (Default package)
	}

	private def static dispatch void assertPackageEquals(Model umlModel, List<String> packageStringList) {
		assertTrue(packageStringList.nullOrEmpty, [
			String.format("UML models do not have a namespace but %s was expected", packageStringList)
		])
	}

	// **************
	// CLASSIFIERS
	// **************
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Class uClass,
		tools.mdsd.jamopp.model.java.classifiers.Class jClass) {
		assertClassifiersCommonDataEquals(uClass, jClass)
		assertAbstractClassEquals(uClass, jClass)
		assertFinalClassEquals(uClass, jClass)
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.DataType uDataType,
		tools.mdsd.jamopp.model.java.classifiers.Class jClass) {
		assertClassifiersCommonDataEquals(uDataType, jClass)
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Interface uInterface,
		tools.mdsd.jamopp.model.java.classifiers.Interface jInterface) {
		assertClassifiersCommonDataEquals(uInterface, jInterface)
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Enumeration uEnum,
		tools.mdsd.jamopp.model.java.classifiers.Enumeration jEnum) {
		assertClassifiersCommonDataEquals(uEnum, jEnum)
		assertEnumConstantListEquals(uEnum.ownedLiterals, jEnum.constants)
	}

	private static def assertClassifiersCommonDataEquals(Classifier umlClassifier, ConcreteClassifier javaClassifier) {
		assertEquals(umlClassifier.name, javaClassifier.name, [
			String.format("UML %s and Java %s names must be equal", umlClassifier.class.simpleName,
				javaClassifier.class.simpleName)
		])
		assertVisibilityEquals(umlClassifier, javaClassifier)
		assertPackageEquals(umlClassifier.namespace, javaClassifier.containingCompilationUnit.namespaces)
		assertAttributesEquals(umlClassifier, javaClassifier)
		assertMethodsEquals(umlClassifier, javaClassifier)
	}

	private static def assertAttributesEquals(Classifier umlClassifier, ConcreteClassifier javaClassifier) {
		for (var attributeNumber = 0; attributeNumber < umlClassifier.attributes.size; attributeNumber++) {
			val umlAttribute = umlClassifier.attributes.get(attributeNumber)
			val correspondingJavaField = javaClassifier.fields.findFirst[name == umlAttribute.name]
			assertNotNull(correspondingJavaField, [
				String.format("No corresponding Java field found for UML attribute %s", umlAttribute)
			])
			assertElementsEqual(umlAttribute, correspondingJavaField)
		}
		for (javaField : javaClassifier.fields) {
			val correspondingUmlAttribute = umlClassifier.attributes.findFirst[name == javaField.name]
			assertNotNull(correspondingUmlAttribute, [
				String.format("No corresponding UML attribute found for Java field %s", javaField)
			])
			assertElementsEqual(correspondingUmlAttribute, javaField)
		}
	}

	private static def assertMethodsEquals(Classifier umlClassifier, ConcreteClassifier javaClassifier) {
		for (var methodNumber = 0; methodNumber < umlClassifier.operations.size; methodNumber++) {
			val umlMethod = umlClassifier.operations.get(methodNumber)
			val potentialMethods = if (umlMethod.name == umlClassifier.name) {
					javaClassifier.constructors
				} else {
					javaClassifier.methods
				}
			// Match methods in the order in which they were generated, as this is how the transformations implement it
			// Otherwise we need to perform a complete matching of the signature here
			val correspondingJavaMethod = potentialMethods.filter[name == umlMethod.name].get(
				umlClassifier.operations.filter[name == umlMethod.name].toList.indexOf(umlMethod))
			assertNotNull(correspondingJavaMethod, [
				String.format("No corresponding Java method found for UML method %s", umlMethod)
			])
			assertElementsEqual(umlMethod, correspondingJavaMethod)
		}
		for (javaMethod : javaClassifier.methods) {
			// Exclude getter and setter methods
			if (javaMethod.name.length < 3 || !javaClassifier.fields.exists [
				name.toFirstLower == javaMethod.name.substring(3).toFirstLower
			]) {
				// Match methods in the order in which they were generated, as this is how the transformations implement it
				// Otherwise we need to perform a complete matching of the signature here
				val correspondingUmlMethod = umlClassifier.operations.filter[name == javaMethod.name].get(
					javaClassifier.methods.filter[name == javaMethod.name].toList.indexOf(javaMethod))
				assertNotNull(correspondingUmlMethod, [
					String.format("No corresponding UML method found for Java method %s", javaMethod)
				])
				assertElementsEqual(correspondingUmlMethod, javaMethod)
			}
		}
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

	// **************
	// MEMBERS
	// **************
	def static dispatch void assertElementsEqual(Property uAttribute, Field jAttribute) {
		assertEquals(uAttribute.name, jAttribute.name, "Attribute and field names must be equal")
		assertVisibilityEquals(uAttribute, jAttribute)
		assertFinalAttributeEquals(uAttribute, jAttribute)
		assertStaticEquals(uAttribute, jAttribute)
		if (uAttribute.upperBound == 0 || uAttribute.upperBound == 1) {
			assertTypeEquals(uAttribute.type, jAttribute.typeReference)
		} else {
			assertTypeEquals(uAttribute.type, getInnerTypeReferenceOfCollectionTypeReference(jAttribute.typeReference))
		}
	}

	def static dispatch void assertElementsEqual(Operation uMethod, Constructor jConstructor) {
		assertEquals(uMethod.name, jConstructor.name, "Constructor names must be equal")
		assertStaticEquals(uMethod, jConstructor)
		assertVisibilityEquals(uMethod, jConstructor)
		assertEquals(null, uMethod.type, "Constructors must not have a return type")
		assertParametersEquals(uMethod, jConstructor)
	}

	def static dispatch void assertElementsEqual(Operation uMethod, ClassMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name, "Method names must be equal")
		assertStaticEquals(uMethod, jMethod)
		assertFinalMethodEquals(uMethod, jMethod)
		assertAbstractMethodEquals(uMethod, jMethod)
		assertVisibilityEquals(uMethod, jMethod)
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParametersEquals(uMethod, jMethod)
	}

	def static dispatch void assertElementsEqual(EnumerationLiteral uLiteral, EnumConstant jConstant) {
		assertEquals(uLiteral.name, jConstant.name, "Enumeration literal names must be equal")
	}

	/**
	 * Interface methods = methods without body.
	 * Also checks if uMethod is abstract.
	 */
	def static dispatch void assertElementsEqual(Operation uMethod, InterfaceMethod jMethod) {
		assertEquals(uMethod.name, jMethod.name, "Method names must be equal")
		assertUmlNamedElementHasVisibility(uMethod, VisibilityKind.PUBLIC_LITERAL) // JMehod is always implicitly public
		assertTypeEquals(uMethod.type, jMethod.typeReference)
		assertParametersEquals(uMethod, jMethod)
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Parameter uParameter,
		tools.mdsd.jamopp.model.java.parameters.Parameter jParameter) {
		assertEquals(uParameter.name, jParameter.name, "Parameter names must be equal")
		if (jParameter instanceof VariableLengthParameter) {
			assertEquals(LiteralUnlimitedNatural.UNLIMITED, uParameter.upper, "UML parameter for Java variable length parameter must have multiplicity *")
		}
		assertTypeEquals(uParameter.type, jParameter.typeReference)
	}

	private def static void assertParametersEquals(Operation umlOperation, Parametrizable javaMethod) {
		val uParamListWithoutReturn = umlOperation.ownedParameters.filter [
			direction != ParameterDirectionKind.RETURN_LITERAL
		]
		if (uParamListWithoutReturn === null) {
			assertNull(javaMethod.parameters, "Parameter should not have a return type but has one")
		} else {
			assertEquals(uParamListWithoutReturn.size, javaMethod.parameters.size,
				"Parameter lists must be of equal size")
			for (var parameterNumber = 0; parameterNumber < uParamListWithoutReturn.size; parameterNumber++) {
				val umlParameter = uParamListWithoutReturn.get(parameterNumber)
				val javaParameter = javaMethod.parameters.get(parameterNumber)
				assertElementsEqual(umlParameter, javaParameter)
			}
		}
	}

	// **************
	// PROPERTIES
	// **************
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
		tools.mdsd.jamopp.model.java.classifiers.Class jClass) {
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
		tools.mdsd.jamopp.model.java.classifiers.Class jClass) {
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

	// **************
	// TYPES
	// **************
	def static dispatch void assertTypeEquals(java.lang.Void nullReference, Void voidType) {
		// umlType is null, javaType is void -> Do nothing, assertion passed.
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

	def static dispatch void assertTypeEquals(Enumeration uEnum, NamespaceClassifierReference namespaceRef) {
		assertEquals(getClassifierFromTypeReference(namespaceRef).name, uEnum.name,
			"Enumeration name is not as expected")
	}

	def static dispatch void assertTypeEquals(DataType uDataType, NamespaceClassifierReference namespaceRef) {
		assertEquals(getClassifierFromTypeReference(namespaceRef).name, uDataType.name,
			"DataType name is not as expected")
	}

	// IMPORTANT EXCEPTION: String is NOT a primitive in the Java model, which means this dispatch case needs to exist
	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		NamespaceClassifierReference namespaceRef) {
		assertNotNull(uPrimType, "Primitive type to check must not be null")
		val jTypeMapped = UmlJavaTypePropagationHelper.getJavaTypeReferenceForUmlPrimitiveType(uPrimType)
		assertFalse(jTypeMapped instanceof Void, [
			String.format("Mapped primitive type for original type %s is void but should not", uPrimType)
		]) // if the UML type is non null and supported by the transformations, then it should not be mapped to void
		val javaTypeName = getClassifierFromTypeReference(namespaceRef).name
		val javaMappedTypeName = getClassifierFromTypeReference(jTypeMapped).name
		assertEquals(if(javaTypeName == "CharSequence") "String" else javaTypeName, javaMappedTypeName,
			"Type is not as expected")
	}

	def static dispatch void assertTypeEquals(org.eclipse.uml2.uml.PrimitiveType uPrimType,
		tools.mdsd.jamopp.model.java.types.PrimitiveType jPrimType) {
		assertNotNull(uPrimType, "Primitive type to check must not be null")
		val jTypeMapped = UmlJavaTypePropagationHelper.getJavaTypeReferenceForUmlPrimitiveType(uPrimType)
		assertFalse(jTypeMapped instanceof Void, [
			String.format("Mapped primitive type for original type %s is void but should not", uPrimType)
		]) // if the uml type is non null and supported by the transformations, then it should not be mapped to void
		assertEquals(jPrimType.class, jTypeMapped.class, "Type is not as expected")
	}

}
