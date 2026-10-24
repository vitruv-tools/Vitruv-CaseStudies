package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaFilePath;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlClassAndAddToPackage;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlPackageAndAddToSuperPackage;

import java.nio.file.Path;
import java.util.List;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.containers.ContainersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;

/**
 * This test class contains basic test cases for package creation, renaming and deletion.
 *
 * @author Fei
 */
public class UmlToJavaPackageTest extends UmlToJavaTransformationTest {
	private static final String PACKAGE_LEVEL_1 = "level1";
	private static final String PACKAGE_LEVEL_2 = "level2";
	private static final String PACKAGE_NAME = "packagename";
	private static final String PACKAGE_RENAMED = "packagerenamed";
	private static final String CLASS_NAME = "ClassName";

	private Package uPackageLevel1;

	@BeforeEach
	public void before() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY);
		getUserInteraction().addNextTextInput("model/model.repository");
		uPackageLevel1 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_1, getRootElement());
		createUmlClassAndAddToPackage(uPackageLevel1, CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false);
		propagate();
	}

	@Test
	public void testCreatePackage() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		var uPackage = createUmlPackageAndAddToSuperPackage(PACKAGE_NAME, getRootElement());
		propagate();

		var jPackage = getCorrespondingPackage(uPackage);
		assertEquals(PACKAGE_NAME, jPackage.getName());
		assertElementsEqual(uPackage, jPackage);
	}

	@Test
	public void testCreateNestedPackage() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		var uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, uPackageLevel1);
		propagate();

		var jPackageLevel1 = getCorrespondingPackage(uPackageLevel1);
		var jPackageLevel2 = getCorrespondingPackage(uPackageLevel2);
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.getName());
		assertEquals(List.of(jPackageLevel1.getName()), jPackageLevel2.getNamespaces());
		assertElementsEqual(uPackageLevel2, jPackageLevel2);
	}

	@Test
	public void testMovePackage() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		var uPackageLevel2 = createUmlPackageAndAddToSuperPackage(PACKAGE_LEVEL_2, getRootElement());
		propagate();

		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		uPackageLevel1.getPackagedElements().add(uPackageLevel2);
		propagate();

		var jPackageLevel1 = getCorrespondingPackage(uPackageLevel1);
		var jPackageLevel2 = getCorrespondingPackage(uPackageLevel2);
		assertEquals(PACKAGE_LEVEL_2, jPackageLevel2.getName());
		assertEquals(List.of(jPackageLevel1.getName()), jPackageLevel2.getNamespaces());
		assertElementsEqual(uPackageLevel2, jPackageLevel2);
	}

	@Test
	public void testDeletePackage() {
		var expectedPackage = ContainersFactory.eINSTANCE.createPackage();
		expectedPackage.setName(PACKAGE_LEVEL_1);
		var expectedPackagePath = buildJavaFilePath(expectedPackage);
		assertNotNull(getCorrespondingPackage(uPackageLevel1), "Corresponding Java package does not exist");
		assertFalse(resourceAt(Path.of(expectedPackagePath)).getContents().isEmpty(), "Java package does not exist");

		uPackageLevel1.destroy();
		propagate();

		disposeViewResources();
		assertTrue(resourceAt(Path.of(expectedPackagePath)).getContents().isEmpty(), "Java package still exists");
	}

	@Test
	public void testRenamePackage() {
		uPackageLevel1.setName(PACKAGE_RENAMED);
		propagate();

		var jPackage = getCorrespondingPackage(uPackageLevel1);
		assertEquals(PACKAGE_RENAMED, jPackage.getName());
		assertElementsEqual(uPackageLevel1, jPackage);
	}
}
