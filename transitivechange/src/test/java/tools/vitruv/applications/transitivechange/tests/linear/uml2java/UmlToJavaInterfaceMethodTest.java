package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaElementHasTypeRef;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaInterfaceMethodTraits;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaMemberContainerDontHaveMember;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlInterface;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlInterfaceOperation;

import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class provides basic tests for creating, deleting and changing traits of interface methods.
 *
 * @author Fei
 */
public class UmlToJavaInterfaceMethodTest extends UmlToJavaTransformationTest {
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String TYPE_NAME = "TypeName";
	private static final String IOPERATION_NAME = "interfaceMethod";
	private static final String STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
	private static final String IOPERATION_RENAME = "interfaceMethodRenamed";

	private org.eclipse.uml2.uml.Interface uInterface;
	private org.eclipse.uml2.uml.Class typeClass;
	private org.eclipse.uml2.uml.Operation uOperation;

	@BeforeEach
	public void before() {
		uInterface = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME);
		uOperation = createUmlInterfaceOperation(IOPERATION_NAME, null, null);
		uInterface.getOwnedOperations().add(uOperation);
		typeClass = createSimpleUmlClass(getRootElement(), TYPE_NAME);
		getRootElement().getPackagedElements().add(uInterface);
		getRootElement().getPackagedElements().add(typeClass);
		propagate();
	}

	@Test
	public void testCreateInterfaceMethod() {
		var interfaceMethod = createUmlInterfaceOperation(STANDARD_IOPERATION_NAME, null, null);
		uInterface.getOwnedOperations().add(interfaceMethod);
		propagate();

		var jMethod = getCorrespondingInterfaceMethod(interfaceMethod);
		var jInterface = getCorrespondingInterface(uInterface);
		assertJavaInterfaceMethodTraits(jMethod, STANDARD_IOPERATION_NAME, TypesFactory.eINSTANCE.createVoid(), null,
			jInterface);
		assertElementsEqual(interfaceMethod, jMethod);
	}

	@Test
	public void testRenameInterfaceMethod() {
		uOperation.setName(IOPERATION_RENAME);
		propagate();

		var jMethod = getCorrespondingInterfaceMethod(uOperation);
		var jInterface = getCorrespondingInterface(uInterface);
		assertEquals(IOPERATION_RENAME, jMethod.getName());
		assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME);
		assertElementsEqual(uOperation, jMethod);
	}

	@Test
	public void testDeleteInterfaceMethod() {
		assertNotNull(uOperation);
		uOperation.destroy();
		propagate();

		var jInterface = getCorrespondingInterface(uInterface);
		assertJavaMemberContainerDontHaveMember(jInterface, IOPERATION_NAME);
	}

	@Test
	public void testMoveInterfaceMethod() {
		var uInterface2 = createSimpleUmlInterface(getRootElement(), "InterfaceName2");
		uInterface2.getOwnedOperations().add(uOperation);
		propagate();

		var jInterface = getCorrespondingInterface(uInterface);
		var jInterface2 = getCorrespondingInterface(uInterface2);
		assertJavaMemberContainerDontHaveMember(jInterface, uOperation.getName());
		assertFalse(isNullOrEmpty(jInterface2.getMembersByName(uOperation.getName())));
	}

	@Test
	public void testChangeInterfaceMethodReturnType() {
		uOperation.setType(typeClass);
		propagate();

		var jMethod = getCorrespondingInterfaceMethod(uOperation);
		var jTypeClass = getCorrespondingClass(typeClass);
		assertJavaElementHasTypeRef(jMethod, createNamespaceClassifierReference(jTypeClass));
		assertElementsEqual(uOperation, jMethod);
	}
}
