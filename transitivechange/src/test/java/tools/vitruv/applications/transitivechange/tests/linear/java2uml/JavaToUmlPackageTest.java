package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlPackageableElementIsInPackage;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.getContainingCompilationUnit;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaFilePath;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import org.eclipse.uml2.uml.Package;
import org.emftext.language.java.classifiers.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;

/**
 * This class contains basis tests for java packages.
 *
 * @author Fei
 */
public class JavaToUmlPackageTest extends JavaToUmlTransformationTest {

	private static final String PACKAGE_LEVEL_1 = "level1";
	private static final String PACKAGE_NAME = "packagename";
	private static final String PACKAGE_RENAMED = "packagerenamed";
	private static final String CLASS_NAME = "ClassName";
	private static final String CLASS_NAME2 = "ClassName2";

	private org.emftext.language.java.containers.Package jPackageLevel1;
	private Class jClass;

	@BeforeEach
	public void testSetup() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY);
		getUserInteraction().addNextTextInput("model/model.repository");
		jPackageLevel1 = createJavaPackageAsModel(PACKAGE_LEVEL_1, null);
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
		jPackageLevel1.getCompilationUnits().add(getContainingCompilationUnit(jClass));
		propagate();
	}

	@Test
	public void testCreatePackage() {
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__NOTHING);
		var jPackage = createJavaPackageAsModel(PACKAGE_NAME, null);

		var uPackage = getCorrespondingPackage(jPackage);
		assertNotNull(uPackage);
		assertEquals(PACKAGE_NAME, uPackage.getName());
		assertElementsEqual(uPackage, jPackage);
	}

	@Test
	public void testRenamePackage() {
		jPackageLevel1.setName(PACKAGE_RENAMED);
		propagate();

		var uPackage = getCorrespondingPackage(jPackageLevel1);
		assertEquals(PACKAGE_RENAMED, uPackage.getName());
		assertElementsEqual(uPackage, jPackageLevel1);
	}

	@Test
	public void testDeletePackage() {
		propagate(resourceAt(Path.of(buildJavaFilePath(jPackageLevel1))), resource -> {
			try {
				resource.delete(null);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		assertTrue(isNullOrEmpty(getUmlPackagedElementsbyName(Package.class, PACKAGE_LEVEL_1)));
	}

	@Test
	@Disabled("Java in-memory model is not correctly updated")
	//https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/issues/130
	public void testAddClassToPackage() {
		var javaClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME2);
		jPackageLevel1.getCompilationUnits().add(getContainingCompilationUnit(javaClass));
		propagate();

		var uPackage = getCorrespondingPackage(jPackageLevel1);
		var uClass = getCorrespondingClass(javaClass);
		assertNotNull(uClass, "UML class");
		assertNotNull(uPackage, "UML package");
		assertUmlPackageableElementIsInPackage(uClass, uPackage);
	}

}
