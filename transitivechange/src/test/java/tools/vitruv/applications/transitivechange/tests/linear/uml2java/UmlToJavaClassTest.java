package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertHasSuperClass;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableAbstract;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableFinal;
import static tools.vitruv.applications.testutility.integration.JavaElementsTestAssertions.assertJavaModifiableHasVisibility;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlClass;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlInterface;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlClassAndAddToPackage;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlDataType;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlPackageAndAddToSuperPackage;

import com.google.common.collect.Iterables;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * This class provides tests for basic class tests in the uml to java direction
 * @author Fei
 */
public class UmlToJavaClassTest extends UmlToJavaTransformationTest {
	private static final String CLASS_NAME = "ClassName";
	private static final String DATATYPE_NAME = "DataTypeName";
	private static final String STANDARD_CLASS_NAME = "StandardClassName";
	private static final String CLASS_RENAME = "ClassRenamed";
	private static final String SUPER_CLASS_NAME = "SuperClassName";
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String INTERFACE_NAME2 = "InterfaceName2";

	private Class uClass;

	@BeforeEach
	public void before() {
		uClass = createUmlClassAndAddToPackage(getRootElement(), CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false);
		propagate();
	}

	@Test
	public void testCreateClass() {
		var c = createUmlClassAndAddToPackage(getRootElement(), STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false,
			false);
		propagate();

		var jClass = getCorrespondingClass(c);
		assertEquals(STANDARD_CLASS_NAME, jClass.getName());
		assertJavaFileExists(STANDARD_CLASS_NAME, new String[] {});
	}

	@Test
	public void testDeletedClass() {
		uClass.destroy();
		propagate();

		assertJavaFileNotExists(CLASS_NAME, new String[] {});
	}

	@Test
	public void testChangeClassVisibility() {
		uClass.setVisibility(VisibilityKind.PRIVATE_LITERAL);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaModifiableHasVisibility(jClass, JavaVisibility.PRIVATE);
		assertElementsEqual(uClass, jClass);

	}

	@Test
	public void testChangeClassVisibility2() {
		uClass.setVisibility(VisibilityKind.PACKAGE_LITERAL);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaModifiableHasVisibility(jClass, JavaVisibility.PACKAGE);
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testChangeAbstractClass() {
		uClass.setIsAbstract(true);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaModifiableAbstract(jClass, true);
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testRenameClass() {
		uClass.setName(CLASS_RENAME);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertEquals(CLASS_RENAME, jClass.getName());
		assertJavaFileExists(CLASS_RENAME, new String[] {});
		assertJavaFileNotExists(CLASS_NAME, new String[] {});
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testMoveClass() {
		var uPackage = createUmlPackageAndAddToSuperPackage("package", getRootElement());
		uPackage.getPackagedElements().add(uClass);
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaFileExists(CLASS_NAME, new String[] {uPackage.getName()});
		assertJavaFileNotExists(CLASS_NAME, new String[] {});
		assertElementsEqual(uClass, jClass);
		assertEquals(String.join(".", jClass.getContainingPackageName()), uPackage.getName());

		getRootElement().getPackagedElements().add(uClass);
		propagate();

		jClass = getCorrespondingClass(uClass);
		assertJavaFileNotExists(CLASS_NAME, new String[] {uPackage.getName()});
		assertJavaFileExists(CLASS_NAME, new String[] {});
		assertElementsEqual(uClass, jClass);
		assertEquals(String.join(".", jClass.getContainingPackageName()), "");
	}

	@Test
	public void testChangeFinalClass() {
		uClass.setIsFinalSpecialization(true);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertJavaModifiableFinal(jClass, true);
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testSuperClassChanged() {
		var superClass = createSimpleUmlClass(getRootElement(), SUPER_CLASS_NAME);
		uClass.getGenerals().add(superClass);
		propagate();
		var jClass = getCorrespondingClass(uClass);
		var jSuperClass = getCorrespondingClass(superClass);
		assertHasSuperClass(jClass, jSuperClass);
		assertElementsEqual(uClass, jClass);

		uClass.getGenerals().remove(superClass);
		propagate();
		jClass = getCorrespondingClass(uClass);
		jSuperClass = getCorrespondingClass(superClass);
		assertTrue(jClass.getExtends() == null);
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testDeleteClassImplement() {
		var uI = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME);
		var uI2 = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME2);
		uClass.createInterfaceRealization("InterfacRealization", uI);
		uClass.createInterfaceRealization("InterfacRealization2", uI2);
		propagate();

		uClass.getInterfaceRealizations().remove(0);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertTrue(jClass.getImplements().size() == 1, String.valueOf(jClass.getImplements().size()));
		assertEquals(INTERFACE_NAME2, getClassifierFromTypeReference(jClass.getImplements().get(0)).getName());
		assertJavaFileExists(INTERFACE_NAME, new String[] {});
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testAddClassImplement() {
		var uI = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME);
		uClass.createInterfaceRealization("InterfacRealization", uI);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(Iterables.getFirst(jClass.getImplements(), null)).getName());
		assertElementsEqual(uClass, jClass);
	}

	@Test
	public void testChangeInterfaceImplementer() {
		var uClass2 = createSimpleUmlClass(getRootElement(), STANDARD_CLASS_NAME);
		var uI = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME);
		var realization = uClass.createInterfaceRealization("InterfacRealization", uI);
		propagate();

		var jClass = getCorrespondingClass(uClass);
		var jClass2 = getCorrespondingClass(uClass2);
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(Iterables.getFirst(jClass.getImplements(), null)).getName());
		assertTrue(isNullOrEmpty(jClass2.getImplements()));

		realization.setImplementingClassifier(uClass2);
		propagate();

		jClass = getCorrespondingClass(uClass);
		jClass2 = getCorrespondingClass(uClass2);
		assertEquals(INTERFACE_NAME, getClassifierFromTypeReference(Iterables.getFirst(jClass2.getImplements(), null)).getName());
		assertTrue(isNullOrEmpty(jClass.getImplements()));

		assertElementsEqual(uClass, jClass);
		assertElementsEqual(uClass2, jClass2);

	}

	@Test
	public void testCreateDataType() {
		var dataType = createUmlDataType(getRootElement(), DATATYPE_NAME);
		propagate();

		var jClass = getCorrespondingClass(dataType);
		assertEquals(DATATYPE_NAME, jClass.getName());
		assertJavaFileExists(DATATYPE_NAME, new String[] {});
	}

	@Test
	public void testMoveDataType() {
		var uDataType = createUmlDataType(getRootElement(), DATATYPE_NAME);
		propagate();

		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		var uPackage = createUmlPackageAndAddToSuperPackage("package", getRootElement());
		uPackage.getPackagedElements().add(uDataType);
		propagate();

		var jDataType = getCorrespondingClass(uDataType);
		assertJavaFileExists(DATATYPE_NAME, new String[] {uPackage.getName()});
		assertJavaFileNotExists(DATATYPE_NAME, new String[] {});
		assertEquals(DATATYPE_NAME, jDataType.getName());
		assertEquals(String.join(".", jDataType.getContainingPackageName()), uPackage.getName());
	}
}
