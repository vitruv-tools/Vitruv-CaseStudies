package tools.vitruv.applications.transitivechange.tests.linear.uml2java

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil

import static tools.vitruv.applications.umljava.tests.util.JavaTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This class tests the change of parameter traits.
 * 
 * @author Fei
 */
class UmlToJavaParameterTest extends UmlToJavaTransformationTest {
	static val CLASS_NAME = "ClassName"
	static val TYPE_NAME = "TypeName"
	static val OPERATION_NAME = "classMethod"
	static val PARAMETER_NAME = "parameterName"
	static val STANDARD_PARAMETER_NAME = "standardParameterName"
	static val PARAMETER_RENAME = "parameterRenamed"

	var Class uClass
	var Class typeClass
	var Parameter uParam
	var PrimitiveType pType
	var Operation uOperation

	@BeforeEach
	def void before() {
		uClass = createSimpleUmlClass(rootElement, CLASS_NAME)
		typeClass = createSimpleUmlClass(rootElement, TYPE_NAME)
		pType = UmlTypeUtil.getSupportedPredefinedUmlPrimitiveTypes(resourceRetriever).findFirst[it.name == "Integer"]
		uParam = createUmlParameter(PARAMETER_NAME, pType)
		uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, #[uParam])
		uClass.ownedOperations += uOperation
		rootElement.packagedElements += uClass
		rootElement.packagedElements += typeClass
		propagate
	}

	@Test
	def testCreateParameter() {
		val uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass)
		uOperation.ownedParameters += uParam
		propagate

		val jParam = getCorrespondingParameter(uParam)
		val jTypeClass = getCorrespondingClass(typeClass)
		assertJavaParameterTraits(jParam, STANDARD_PARAMETER_NAME, createNamespaceReferenceFromClassifier(jTypeClass))
		assertParameterEquals(uParam, jParam)
	}

	@Test
	def testRenameParameter() {
		uParam.name = PARAMETER_RENAME
		propagate

		val jParam = getCorrespondingParameter(uParam)
		val jMethod = getCorrespondingClassMethod(uOperation)
		assertEquals(PARAMETER_RENAME, jParam.name)
		assertJavaMethodHasUniqueParameter(jMethod, PARAMETER_RENAME, TypesFactory.eINSTANCE.createInt)
		assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
	}

	@Test
	def testDeleteParameter() {
		uParam.destroy
		propagate

		val jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME)
	}

	@Test
	def testChangeParameterType() {
		uParam.type = typeClass
		propagate

		val jParam = getCorrespondingParameter(uParam)
		val jTypeClass = getCorrespondingClass(typeClass)
		assertJavaParameterTraits(jParam, PARAMETER_NAME, createNamespaceReferenceFromClassifier(jTypeClass))
		assertParameterEquals(uParam, jParam)
	}

	@Test
	def testChangeParameterDirectionToReturn() {
		uParam.direction = ParameterDirectionKind.RETURN_LITERAL
		propagate
		assertNull(getCorrespondingParameter(uParam))
		var jMethod = getCorrespondingClassMethod(uOperation)
		assertJavaElementHasTypeRef(jMethod, TypesFactory.eINSTANCE.createInt)

	}
}
