package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaClassMethodTraits;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaElementHasTypeRef;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaMemberContainerDontHaveMember;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableAbstract;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableFinal;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableHasVisibility;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableStatic;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.uml.UmlQueryUtil.loadUmlPrimitiveType;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlDataType;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createSimpleUmlOperation;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlOperation;
import static tools.vitruv.applications.util.temporary.uml.UmlOperationAndParameterUtil.createUmlParameter;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * A Test class to test class methods and its traits.
 *
 * @author Fei
 */
public class UmlToJavaClassMethodTest extends UmlToJavaTransformationTest {
	private static final String CLASS_NAME = "ClassName";
	private static final String TYPE_NAME = "TypeName";
	private static final String OPERATION_NAME = "classMethod";
	private static final String STANDARD_OPERATION_NAME = "standardMethod";
	private static final String OPERATION_RENAME = "classMethodRenamed";
	private static final String PARAMETER_NAME = "parameterName";
	private static final String DATATYPE_NAME = "DataTypeName";

	private Class uClass;
	private Class typeClass;
	private Parameter uParam;
	private PrimitiveType pType;
	private Operation uOperation;

	/**
	 * Initializes two uml classes and a primitive type. One uml class contains
	 * an operation with a parameter.
	 */
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

	/**
	 * Tests if creating a uml operation also causes the creating of an corresponding
	 * java method.
	 */
	@Test
	public void testCreateClassMethod() {
		var operation = uClass.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
		propagate();

		var jMethod = getCorrespondingClassMethod(operation);
		var jClass = getCorrespondingClass(uClass);
		assertNotNull(jMethod);
		assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createVoid(), false, false, null, jClass);
		assertElementsEqual(operation, jMethod);
	}

	/**
	 * Tests the change of the uml method return type. Checks if
	 * the corresponding java method adapated the corresponding type.
	 */
	@Test
	public void testChangeReturnType() {
		uOperation.setType(typeClass);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		var jTypeClass = getCorrespondingClass(typeClass);
		assertJavaElementHasTypeRef(jMethod, createNamespaceClassifierReference(jTypeClass));
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests if renaming a method is correctly reflected on the java side.
	 */
	@Test
	public void testRenameMethod() {
		uOperation.setName(OPERATION_RENAME);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		var jClass = getCorrespondingClass(uClass);
		assertEquals(OPERATION_RENAME, jMethod.getName());
		assertElementsEqual(uOperation, jMethod);
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME);
	}

	/**
	 * Tests if deleting a method is correctly reflected on the java side.
	 */
	@Test
	public void testDeleteMethod() {
		uOperation.destroy();
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME);
	}

	@Test
	public void testMoveMethod() {
		var uClass2 = createSimpleUmlClass(getRootElement(), "ClassName2");
		uClass2.getOwnedOperations().add(uOperation);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jClass2 = getCorrespondingClass(uClass2);
		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaMemberContainerDontHaveMember(jClass, OPERATION_NAME);
		assertFalse(isNullOrEmpty(jClass2.getMembersByName(OPERATION_NAME)));
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests if setting a method static correctly reflected on the java side.
	 */
	@Test
	public void testStaticMethod() {
		uOperation.setIsStatic(true);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaModifiableStatic(jMethod, true);
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests if setting a method final correctly reflected on the java side.
	 */
	@Test
	public void testFinalMethod() {
		uOperation.setIsLeaf(true);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaModifiableFinal(jMethod, true);
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests if setting a method abstract is correctly reflected on the java side.
	 */
	@Test
	public void testAbstractMethod() {
		uOperation.setIsAbstract(true);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaModifiableAbstract(jMethod, true);
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests if visibility changes are propagated to the java method.
	 */
	@Test
	public void testMethodVisibility() {
		uOperation.setVisibility(VisibilityKind.PRIVATE_LITERAL);
		propagate();

		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PRIVATE);
		assertElementsEqual(uOperation, jMethod);

		uOperation.setVisibility(VisibilityKind.PROTECTED_LITERAL);
		propagate();

		jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaModifiableHasVisibility(jMethod, JavaVisibility.PROTECTED);
		assertElementsEqual(uOperation, jMethod);
	}

	/**
	 * Tests the creation of a method that act as constructor and checks if a
	 * constructor is created on the java side.
	 */
	@Test
	public void testCreateConstructor() {
		var uConstr = createSimpleUmlOperation(uClass.getName());
		uClass.getOwnedOperations().add(uConstr);
		propagate();
		var jConstr = getCorrespondingConstructor(uConstr);
		assertNotNull(jConstr);
	}

	@Test
	public void testMoveConstructor() {
		var uConstr = createSimpleUmlOperation(uClass.getName());
		uClass.getOwnedOperations().add(uConstr);
		propagate();

		var jConstr = getCorrespondingConstructor(uConstr);
		assertNotNull(jConstr);

		var uClass2 = createSimpleUmlClass(getRootElement(), "ClassName2");
		uClass2.getOwnedOperations().add(uConstr);
		uConstr.setName(uClass2.getName());
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jClass2 = getCorrespondingClass(uClass2);
		jConstr = getCorrespondingConstructor(uConstr);
		assertJavaMemberContainerDontHaveMember(jClass, uClass.getName());
		assertJavaMemberContainerDontHaveMember(jClass, uClass2.getName());
		assertJavaMemberContainerDontHaveMember(jClass2, uClass.getName());
		assertFalse(isNullOrEmpty(jClass2.getMembersByName(uConstr.getName())));
		assertNotNull(jConstr);
	}

	/**
	 * Same as testMoveConstructor but the order of move and rename is switched
	 */
	@Test
	public void testMoveConstructor2() {
		var uConstr = createSimpleUmlOperation(uClass.getName());
		uClass.getOwnedOperations().add(uConstr);
		propagate();

		var jConstr = getCorrespondingConstructor(uConstr);
		assertNotNull(jConstr);

		var uClass2 = createSimpleUmlClass(getRootElement(), "ClassName2");
		uConstr.setName(uClass2.getName());
		uClass2.getOwnedOperations().add(uConstr);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jClass2 = getCorrespondingClass(uClass2);
		jConstr = getCorrespondingConstructor(uConstr);
		assertJavaMemberContainerDontHaveMember(jClass, uClass.getName());
		assertJavaMemberContainerDontHaveMember(jClass, uClass2.getName());
		assertJavaMemberContainerDontHaveMember(jClass2, uClass.getName());
		assertFalse(isNullOrEmpty(jClass2.getMembersByName(uConstr.getName())));
		assertNotNull(jConstr);
	}

	/**
	 * Checks if method creating in datatypes is reflected in the corresponding java class.
	 */
	@Test
	public void testCreateMethodInDataType() {
		var dataType = createUmlDataType(getRootElement(), DATATYPE_NAME);
		var operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
		propagate();

		var jMethod = getCorrespondingClassMethod(operation);
		var jClass = getCorrespondingClass(dataType);
		assertNotNull(jMethod);
		assertJavaClassMethodTraits(jMethod, STANDARD_OPERATION_NAME, JavaVisibility.PUBLIC,
			TypesFactory.eINSTANCE.createVoid(), false, false, null, jClass);
		assertElementsEqual(operation, jMethod);
	}

	/**
	 * Tests the deletion of methods in data types and if the deletion is
	 * propagated to the java model.
	 */
	@Test
	public void testDeleteMethodInDataType() {
		var dataType = createUmlDataType(getRootElement(), DATATYPE_NAME);
		var operation = dataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
		propagate();

		var jMethod = getCorrespondingClassMethod(operation);
		assertNotNull(jMethod);

		operation.destroy();
		propagate();

		var jClass = getCorrespondingClass(dataType);
		assertJavaMemberContainerDontHaveMember(jClass, STANDARD_OPERATION_NAME);
	}

	@Test
	public void testMoveMethodInDataType() {
		var uDataType = createUmlDataType(getRootElement(), DATATYPE_NAME);
		var uOperation = uDataType.createOwnedOperation(STANDARD_OPERATION_NAME, null, null, null);
		propagate();

		var uDataType2 = createUmlDataType(getRootElement(), "DataTypeName2");
		uDataType2.getOwnedOperations().add(uOperation);
		propagate();

		var jDataType = getCorrespondingClass(uDataType);
		var jDataType2 = getCorrespondingClass(uDataType2);
		var jMethod = getCorrespondingClassMethod(uOperation);
		assertJavaMemberContainerDontHaveMember(jDataType, uOperation.getName());
		assertFalse(isNullOrEmpty(jDataType2.getMembersByName(uOperation.getName())));
		assertElementsEqual(uOperation, jMethod);
	}
}
