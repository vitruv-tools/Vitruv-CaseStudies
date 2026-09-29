package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassDontHaveOperation;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassHasUniqueOperation;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlFeatureHasStaticValue;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlNamedElementHasVisibility;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationDontHaveParameter;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasAbstractValue;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasFinalValue;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasReturntype;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasUniqueParameter;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationTraits;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlParameterTraits;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaClassMethod;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaConstructorAndAddToClass;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaParameter;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createSimpleJavaOperation;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setAbstract;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setFinal;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setStatic;

import java.util.List;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * A test class to test the class method reactions.
 * @author Fei
 */
public class JavaToUmlClassMethodTest extends JavaToUmlTransformationTest {
	private static final String CLASS_NAME = "ClassName";
	private static final String TYPE_NAME = "TypeName";
	private static final String TYPE_NAME2 = "TypeName2";
	private static final String OPERATION_NAME = "classMethod";
	private static final String OPERATION_NAME2 = "classMethod2";
	private static final String STANDARD_OPERATION_NAME = "standardMethod";
	private static final String OPERATION_RENAME = "classMethodRenamed";
	private static final String PARAMETER_NAME = "parameterName";
	private static final String PARAMETER_NAME2 = "parameterName2";
	private static final String PARAMETER_RENAME = "parameterRenamed";

	private Class jClass;
	private Class typeClass;
	private Class typeClass2;
	private ClassMethod jMeth;
	private ClassMethod jParamMeth;
	private OrdinaryParameter jParam;

	/**
	 * Initializes and synchronizes three classes. One class has two methods.
	 * One of the methods owns a parameter.
	 */
	@BeforeEach
	public void before() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
		typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME);
		typeClass2 = createSimpleJavaClassWithCompilationUnit(TYPE_NAME2);
		jMeth = createSimpleJavaOperation(OPERATION_NAME);
		jParam = createJavaParameter(PARAMETER_NAME, createNamespaceClassifierReference(typeClass2));
		jParamMeth = createJavaClassMethod(OPERATION_NAME2, TypesFactory.eINSTANCE.createBoolean(), JavaVisibility.PUBLIC,
			false, false, List.of(jParam));
		jClass.getMembers().add(jMeth);
		jClass.getMembers().add(jParamMeth);
		propagate();
	}

	/**
	 * Tests if a corresponding uml method is created when a java method is created.
	 */
	@Test
	public void testCreateMethod() {
		var meth = createSimpleJavaOperation(STANDARD_OPERATION_NAME);
		jClass.getMembers().add(meth);
		propagate();
		var uOperation = getCorrespondingMethod(meth);
		var uClass = getCorrespondingClass(jClass);
		assertUmlOperationTraits(uOperation, STANDARD_OPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, false,
			uClass, null);
		assertElementsEqual(uOperation, meth);
	}

	/**
	 * Tests if a change of the return type is correctly reflected on the uml method.
	 */
	@Test
	public void testChangeReturnType() {
		jMeth.setTypeReference(createNamespaceClassifierReference(typeClass));
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		var uTypeClass = getCorrespondingClass(typeClass);
		assertUmlOperationHasReturntype(uOperation, uTypeClass);
		assertElementsEqual(uOperation, jMeth);
	}

	/**
	 * Tests if renaming the java method also renames the corresponding uml method.
	 */
	@Test
	public void testRenameMethod() {
		jMeth.setName(OPERATION_RENAME);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		var uClass = getCorrespondingClass(jClass);
		assertEquals(OPERATION_RENAME, uOperation.getName());
		assertUmlClassHasUniqueOperation(uClass, OPERATION_RENAME);
		assertElementsEqual(uOperation, jMeth);
	}

	/**
	 * Tests if a deletion is reflected on the uml method.
	 */
	@Test
	public void testDeleteMethod() {
		assertNotNull(getCorrespondingMethod(jMeth));

		EcoreUtil.delete(jMeth);
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertUmlClassDontHaveOperation(uClass, OPERATION_RENAME);
	}

	/**
	 * Checks if changing the static modifier also changes the static property of
	 * the corresponding uml method.
	 */
	@Test
	public void testStaticMethod() {
		setStatic(jMeth, true);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlFeatureHasStaticValue(uOperation, true);
		assertElementsEqual(uOperation, jMeth);

		setStatic(jMeth, false);
		propagate();

		uOperation = getCorrespondingMethod(jMeth);
		assertUmlFeatureHasStaticValue(uOperation, false);
		assertElementsEqual(uOperation, jMeth);

	}

	/**
	 * Tests if changing the abstract modifier also changes the abstract property of
	 * the corresponding uml method.
	 */
	@Test
	public void testAbstractMethod() {
		setAbstract(jMeth, true);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationHasAbstractValue(uOperation, true);
		assertElementsEqual(uOperation, jMeth);

		setAbstract(jMeth, false);
		propagate();

		uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationHasAbstractValue(uOperation, false);
		assertElementsEqual(uOperation, jMeth);
	}

	/**
	 * Asserts that changing the final modifier also changes the final property of
	 * the corresponding uml method.
	 */
	@Test
	public void testFinalMethod() {
		setFinal(jMeth, true);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationHasFinalValue(uOperation, true);
		assertElementsEqual(uOperation, jMeth);

		setFinal(jMeth, false);
		propagate();

		uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationHasFinalValue(uOperation, false);
		assertElementsEqual(uOperation, jMeth);
	}

	/**
	 * Checks if changing the visibility modifier also changes the visibility of
	 * the corresponding uml method.
	 */
	@Test
	public void testMethodVisibility() {
		jMeth.makeProtected();
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PROTECTED_LITERAL);
		assertElementsEqual(uOperation, jMeth);

		jMeth.makePrivate();
		propagate();

		uOperation = getCorrespondingMethod(jMeth);
		assertUmlNamedElementHasVisibility(uOperation, VisibilityKind.PRIVATE_LITERAL);
		assertElementsEqual(uOperation, jMeth);
	}

	/**
	 * Checks if a uml parameter is created after a java parameter is created.
	 */
	@Test
	public void testCreateParameter() {
		var param = createJavaParameter(PARAMETER_NAME2, createNamespaceClassifierReference(typeClass));
		jMeth.getParameters().add(param);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		var uParam = getCorrespondingParameter(param);
		assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME2);
		assertElementsEqual(uParam, param);
	}

	/**
	 * Tests the rename reaction in the context of parameters.
	 */
	@Test
	public void testRenameParameter() {
		jParam.setName(PARAMETER_RENAME);
		propagate();

		var uOperation = getCorrespondingMethod(jParamMeth);
		var uParam = getCorrespondingParameter(jParam);
		assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_RENAME);
		assertElementsEqual(uParam, jParam);
	}

	/**
	 * Tests if the corresponding uml parameter is deleted when the java parameter is deleted.
	 */
	@Test
	public void testDeleteParameter() {
		assertNotNull(jParam);
		EcoreUtil.delete(jParam);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationDontHaveParameter(uOperation, PARAMETER_NAME);
	}

	/**
	 * Tests if a change of the java parameter type is correctly propagated to the uml parameter.
	 */
	@Test
	public void testChangeParameterType() {
		jParam.setTypeReference(createNamespaceClassifierReference(typeClass));
		propagate();

		var uParam = getCorrespondingParameter(jParam);
		var uTypeClass = getCorrespondingClass(typeClass);
		assertUmlParameterTraits(uParam, PARAMETER_NAME, uTypeClass);
		assertElementsEqual(uParam, jParam);
	}

	/**
	 * Tests if a constructor creates a fitting uml operation.
	 */
	@Test
	public void testCreateConstructor() {
		var jConstr = createJavaConstructorAndAddToClass(jClass, JavaVisibility.PUBLIC);
		propagate();

		var uConstr = getCorrespondingMethod(jConstr);
		assertNotNull(uConstr);
		assertEquals(jConstr.getName(), uConstr.getName());
		assertUmlNamedElementHasVisibility(uConstr, VisibilityKind.PUBLIC_LITERAL);

	}

}
