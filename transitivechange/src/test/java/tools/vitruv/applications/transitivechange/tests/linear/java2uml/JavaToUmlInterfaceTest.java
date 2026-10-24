package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassifierDontHaveSuperClassifier;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassifierHasSuperClassifier;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlInterfaceTraits;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.Interface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A Test class for interface tests. Checks their creation, renaming, deleting and the
 * change of their super interfaces.
 *
 * @author Fei
 */
public class JavaToUmlInterfaceTest extends JavaToUmlTransformationTest {
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String INTERFACE_RENAME = "InterfaceRename";
	private static final String STANDARD_INTERFACE_NAME = "StandardInterfaceName";
	private static final String SUPERINTERFACENAME_1 = "SuperInterfaceOne";
	private static final String SUPERINTERFACENAME_2 = "SuperInterfaceTwo";

	private Interface jInterface;

	@BeforeEach
	public void before() {
		jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
	}

	@Test
	public void testCreateInterface() {
		var jInterface = createSimpleJavaInterfaceWithCompilationUnit(STANDARD_INTERFACE_NAME);

		var uInterface = getCorrespondingInterface(jInterface);
		assertUmlInterfaceTraits(uInterface, STANDARD_INTERFACE_NAME, VisibilityKind.PUBLIC_LITERAL, getRegisteredUmlModel());
		assertElementsEqual(uInterface, jInterface);
	}

	@Test
	public void testRenameInterface() {
		jInterface.setName(INTERFACE_RENAME);
		propagate();

		var uInterface = getCorrespondingInterface(jInterface);
		assertEquals(INTERFACE_RENAME, uInterface.getName());
		assertElementsEqual(uInterface, jInterface);
	}

	@Test
	public void testDeleteInterface() {
		jInterface.getContainingCompilationUnit();
		EcoreUtil.delete(jInterface);
		propagate();
		assertTrue(isNullOrEmpty(getUmlPackagedElementsbyName(org.eclipse.uml2.uml.Interface.class, INTERFACE_NAME)));
	}

	@Test
	public void testAddSuperInterface() {
		var superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1);
		jInterface.getExtends().add(createNamespaceClassifierReference(superInterface));
		propagate();

		var uInterface = getCorrespondingInterface(jInterface);
		var uSuperInterface = getCorrespondingInterface(superInterface);
		assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface);
		assertElementsEqual(uInterface, jInterface);
	}

	@Test
	public void testRemoveSuperInterface() {
		var superInterface = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_1);
		var superInterface2 = createSimpleJavaInterfaceWithCompilationUnit(SUPERINTERFACENAME_2);
		jInterface.getExtends().add(createNamespaceClassifierReference(superInterface));
		jInterface.getExtends().add(createNamespaceClassifierReference(superInterface2));
		propagate();

		jInterface.getExtends().remove(0);
		propagate();

		var uInterface = getCorrespondingInterface(jInterface);
		var uSuperInterface = getCorrespondingInterface(superInterface);
		var uSuperInterface2 = getCorrespondingInterface(superInterface2);
		assertUmlClassifierHasSuperClassifier(uInterface, uSuperInterface2);
		assertUmlClassifierDontHaveSuperClassifier(uInterface, uSuperInterface);
		assertElementsEqual(uInterface, jInterface);
	}

}
