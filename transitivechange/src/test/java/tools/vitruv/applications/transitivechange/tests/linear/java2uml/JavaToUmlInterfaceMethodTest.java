package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlInterfaceDontHaveOperation;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasReturntype;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationHasUniqueParameter;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlOperationTraits;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaInterfaceMethod;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaParameter;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the creation, renaming and deleting of interface methods.
 * Plus, it checks the change of parameters and return types of interface methods.
 *
 * @author Fei
 */
public class JavaToUmlInterfaceMethodTest extends JavaToUmlTransformationTest {
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String TYPE_NAME = "TypeName";
	private static final String IOPERATION_NAME = "interfaceMethod";
	private static final String STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
	private static final String IOPERATION_RENAME = "interfaceMethodRenamed";
	private static final String PARAMETER_NAME = "parameterName";

	private org.emftext.language.java.classifiers.Interface jInterface;
	private org.emftext.language.java.classifiers.Class typeClass;
	private org.emftext.language.java.members.InterfaceMethod jMeth;

	@BeforeEach
	public void before() {
		jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
		typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME);
		jMeth = createJavaInterfaceMethod(IOPERATION_NAME, null, null);
		jInterface.getMembers().add(jMeth);
		propagate();
	}

	@Test
	public void testCreateInterfaceMethod() {
		var interfaceMethod = createJavaInterfaceMethod(STANDARD_IOPERATION_NAME, null, null);
		jInterface.getMembers().add(interfaceMethod);
		propagate();

		var uOperation = getCorrespondingMethod(interfaceMethod);
		var uInterface = getCorrespondingInterface(jInterface);
		assertUmlOperationTraits(uOperation, STANDARD_IOPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
			uInterface, null);
		assertElementsEqual(uOperation, interfaceMethod);
	}

	@Test
	public void testRenameInterfaceMethod() {
		jMeth.setName(IOPERATION_RENAME);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		var uInterface = getCorrespondingInterface(jInterface);
		assertEquals(IOPERATION_RENAME, uOperation.getName());
		assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME);
		assertElementsEqual(uOperation, jMeth);

	}

	@Test
	public void testDeleteInterfaceMethod() {
		assertNotNull(getCorrespondingMethod(jMeth));
		EcoreUtil.delete(jMeth);
		propagate();

		var uInterface = getCorrespondingInterface(jInterface);
		assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME);
	}

	@Test
	public void testChangeInterfaceMethodReturnType() {
		jMeth.setTypeReference(createNamespaceClassifierReference(typeClass));
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		var utypeClass = getCorrespondingClass(typeClass);
		assertUmlOperationHasReturntype(uOperation, utypeClass);
		assertElementsEqual(uOperation, jMeth);
	}

	@Test
	public void testCreateInterfaceParameter() {
		var jParam = createJavaParameter(PARAMETER_NAME, createNamespaceClassifierReference(typeClass));
		jMeth.getParameters().add(jParam);
		propagate();

		var uOperation = getCorrespondingMethod(jMeth);
		assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME);
		assertElementsEqual(uOperation, jMeth);
	}
}
