package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaElementHasTypeRef;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaMethodDontHaveParameter;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaMethodHasUniqueParameter;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaParameterTraits;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.uml.UmlQueryUtil.loadUmlPrimitiveType;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlOperation;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlParameter;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the change of parameter traits.
 *
 * @author Fei
 */
public class UmlToJavaParameterTest extends UmlToJavaTransformationTest {
	private static final String CLASS_NAME = "ClassName";
	private static final String TYPE_NAME = "TypeName";
	private static final String OPERATION_NAME = "classMethod";
	private static final String PARAMETER_NAME = "parameterName";
	private static final String STANDARD_PARAMETER_NAME = "standardParameterName";
	private static final String PARAMETER_RENAME = "parameterRenamed";

	private Class uClass;
	private Class typeClass;
	private Parameter uParam;
	private PrimitiveType pType;
	private Operation uOperation;

	@BeforeEach
	public void before() {
		uClass = createSimpleUmlClass(getRootElement(), CLASS_NAME);
		typeClass = createSimpleUmlClass(getRootElement(), TYPE_NAME);
		pType = loadUmlPrimitiveType("int");
		uParam = createUmlParameter(PARAMETER_NAME, pType);
		uOperation = createUmlOperation(OPERATION_NAME, null, VisibilityKind.PUBLIC_LITERAL, false, false, List.of(uParam));
		uClass.getOwnedOperations().add(uOperation);
		getRootElement().getPackagedElements().add(uClass);
		getRootElement().getPackagedElements().add(typeClass);
		propagate();
	}

	@Test
	public void testCreateParameter() {
		var uParam = createUmlParameter(STANDARD_PARAMETER_NAME, typeClass);
		uOperation.getOwnedParameters().add(uParam);
		propagate();

		var jParam = getCorrespondingParameter(uParam);
		var jTypeClass = getCorrespondingClass(typeClass);
		assertJavaParameterTraits(jParam, STANDARD_PARAMETER_NAME, createNamespaceClassifierReference(jTypeClass));
		assertElementsEqual(uParam, jParam);
	}

	@Test
	public void testRenameParameter() {
		uParam.setName(PARAMETER_RENAME);
		propagate();

		var jParam = getCorrespondingParameter(uParam);
		var jMethod = getCorrespondingClassMethod(uOperation);
		assertEquals(PARAMETER_RENAME, jParam.getName());
		assertJavaMethodHasUniqueParameter(jMethod, PARAMETER_RENAME, TypesFactory.eINSTANCE.createInt());
		assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME);
	}

	@Test
	public void testDeleteParameter() {
		uParam.destroy();
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaMethodDontHaveParameter(jMethod, PARAMETER_NAME);
	}

	@Test
	public void testChangeParameterType() {
		uParam.setType(typeClass);
		propagate();

		var jParam = getCorrespondingParameter(uParam);
		var jTypeClass = getCorrespondingClass(typeClass);
		assertJavaParameterTraits(jParam, PARAMETER_NAME, createNamespaceClassifierReference(jTypeClass));
		assertElementsEqual(uParam, jParam);
	}

	@Test
	public void testChangeParameterDirectionToReturn() {
		uParam.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		propagate();
		assertNull(getCorrespondingParameter(uParam));
		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaElementHasTypeRef(jMethod, TypesFactory.eINSTANCE.createInt());

	}
}
