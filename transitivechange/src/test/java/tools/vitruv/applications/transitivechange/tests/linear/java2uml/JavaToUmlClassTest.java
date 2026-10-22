package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.testutility.integration.JavaUmlElementEqualityValidation.assertElementsEqual;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassDontHaveImplement;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassHasImplement;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassTraits;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassifierDontHaveSuperClassifier;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlClassifierHasSuperClassifier;
import static tools.vitruv.applications.testutility.integration.UmlElementsTestAssertions.assertUmlNamedElementHasVisibility;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setAbstract;
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.setFinal;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaFilePath;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A Test class to test classes and their traits.
 *
 * @author Fei
 */
public class JavaToUmlClassTest extends JavaToUmlTransformationTest {
	private static final String CLASS_NAME = "ClassName";
	private static final String STANDARD_CLASS_NAME = "StandardClassName";
	private static final String CLASS_RENAMED = "ClassRenamed";
	private static final String SUPER_CLASS_NAME = "SuperClassName";
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String INTERFACE_NAME2 = "InterfaceName2";

	private org.emftext.language.java.classifiers.Class jClass;

	@BeforeEach
	public void before() {
		jClass = createSimpleJavaClassWithCompilationUnit(CLASS_NAME);
	}

	/**
	 * Tests if a corresponding java class is created when an uml class is created.
	 */
	@Test
	public void testCreateClass() {
		var cls = createSimpleJavaClassWithCompilationUnit(STANDARD_CLASS_NAME);

		var uClass = getCorrespondingClass(cls);
		assertUmlClassTraits(uClass, STANDARD_CLASS_NAME, VisibilityKind.PUBLIC_LITERAL, false, false,
			getRegisteredUmlModel());
		assertElementsEqual(uClass, cls);
	}

	/**
	 * Tests if renaming a java class also renames the corresponding uml class.
	 */
	@Test
	public void testRenameClass() {
		jClass.setName(CLASS_RENAMED);
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertEquals(CLASS_RENAMED, uClass.getName());
		assertElementsEqual(uClass, jClass);
	}

	/**
	 * Tests if deleting a java class also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	public void testDeleteClass() {
		assertNotNull(getCorrespondingClass(jClass));
		jClass.getContainingCompilationUnit();

		EcoreUtil.delete(jClass);
		propagate();

		var uClass = Iterables.getFirst(getUmlPackagedElementsbyName(Class.class, CLASS_NAME), null);
		assertNull(uClass);
	}

	/**
	 * Tests if deleting a java compilation unit also cause the deleting of the corresponding
	 * uml class.
	 */
	@Test
	public void testDeleteCompilationUnit() {
		var compUnitFilePath = buildJavaFilePath(jClass.getContainingCompilationUnit());
		assertNotNull(getCorrespondingClass(jClass));
		propagate(resourceAt(Path.of(compUnitFilePath)), resource -> {
			try {
				resource.delete(null);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});

		assertTrue(isNullOrEmpty(getUmlPackagedElementsbyName(Class.class, CLASS_NAME)));
	}

	/**
	 * Checks if visibility changes are propagated to the uml class.
	 */
	@Test
	public void testChangeClassVisibility() {
		jClass.makeProtected();
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PROTECTED_LITERAL);
		assertElementsEqual(uClass, jClass);

		jClass.makePrivate();
		propagate();

		uClass = getCorrespondingClass(jClass);
		assertUmlNamedElementHasVisibility(uClass, VisibilityKind.PRIVATE_LITERAL);
		assertElementsEqual(uClass, jClass);
	}

	/**
	 * Tests the change of the abstract value in uml.
	 */
	@Test
	public void testChangeAbstractClass() {
		setAbstract(jClass, true);
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertTrue(uClass.isAbstract());
		assertElementsEqual(uClass, jClass);

		setAbstract(jClass, false);
		propagate();

		uClass = getCorrespondingClass(jClass);
		assertFalse(uClass.isAbstract());
		assertElementsEqual(uClass, jClass);
	}

	/**
	 * Checks if the changing the final value in the java class
	 * causes the correct change in the uml class.
	 */
	@Test
	public void testChangeFinalClass() {
		setFinal(jClass, true);
		propagate();

		var uClass = getCorrespondingClass(jClass);
		assertTrue(uClass.isFinalSpecialization());
		assertElementsEqual(uClass, jClass);

		setFinal(jClass, false);
		propagate();

		uClass = getCorrespondingClass(jClass);
		assertFalse(uClass.isFinalSpecialization());
		assertElementsEqual(uClass, jClass);
	}

	/**
	 * Tests if add a super class is correctly reflected on the uml side.
	 */
	@Test
	public void testSuperClassChanged() {
		var superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME);
		jClass.setExtends(createNamespaceClassifierReference(superClass));
		propagate();

		var uClass = getCorrespondingClass(jClass);
		var uSuperClass = getCorrespondingClass(superClass);
		assertUmlClassifierHasSuperClassifier(uClass, uSuperClass);
		assertElementsEqual(uClass, jClass);

	}

	/**
	 * Tests if removing a super class is reflected on the uml side.
	 */
	@Test
	public void testRemoveSuperClass() {
		var superClass = createSimpleJavaClassWithCompilationUnit(SUPER_CLASS_NAME);
		jClass.setExtends(createNamespaceClassifierReference(superClass));
		propagate();

		var uClass = getCorrespondingClass(jClass);
		var uSuperClass = getCorrespondingClass(superClass);
		assertUmlClassifierHasSuperClassifier(uClass, uSuperClass);

		EcoreUtil.delete(jClass.getExtends());
		propagate();
		uClass = getCorrespondingClass(jClass);
		assertUmlClassifierDontHaveSuperClassifier(uClass, uSuperClass);
	}

	/**
	 * Check the creation of an interface implementation on the uml side.
	 * The test does currently NOT work, due to an error in the EMF monitor:
	 * 	 The contract of an InterfaceImplementation is also represented in the supplier feature
	 *   and automatically added to that feature. So one change description for setting the contract
	 *   and one for adding the supplier are generated by our monitor. When rolling them back, the supplier is
	 *   first removed by the second change description, which results in an also removed
	 *   contract (due to their dependency). When rolling back the second change description
	 *   for the contract, nothing happens. Reversing this procedure results in a missing
	 *   contract, because the change description lost the information as the supplier removal
	 *   removed the contract as well
	 */
	@Test
	public void testAddClassImplement() {
		var implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
		jClass.getImplements().add(createNamespaceClassifierReference(implInterface));
		propagate();

		var uClass = getCorrespondingClass(jClass);
		var uInterface = getCorrespondingInterface(implInterface);
		assertUmlClassHasImplement(uClass, uInterface);
		assertElementsEqual(uClass, jClass);
	}

	/**
	 * Tests if Removing an implementation relation is correctly reflected on the java side.
	 * The test does currently NOT work, due to an error in the EMF monitor:
	 * 	 The contract of an InterfaceImplementation is also represented in the supplier feature
	 *   and automatically added to that feature. So one change description for setting the contract
	 *   and one for adding the supplier are generated by our monitor. When rolling them back, the supplier is
	 *   first removed by the second change description, which results in an also removed
	 *   contract (due to their dependency). When rolling back the second change description
	 *   for the contract, nothing happens. Reversing this procedure results in a missing
	 *   contract, because the change description lost the information as the supplier removal
	 *   removed the contract as well
	 */
	@Test
	public void testRemoveClassImplement() {
		var implInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME);
		var implInterface2 = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME2);
		jClass.getImplements().add(createNamespaceClassifierReference(implInterface));
		jClass.getImplements().add(createNamespaceClassifierReference(implInterface2));
		propagate();

		var uClass = getCorrespondingClass(jClass);
		var uInterface = getCorrespondingInterface(implInterface);
		var uInterface2 = getCorrespondingInterface(implInterface2);
		assertUmlClassHasImplement(uClass, uInterface);
		assertUmlClassHasImplement(uClass, uInterface2);

		jClass.getImplements().remove(0);
		propagate();

		uClass = getCorrespondingClass(jClass);
		assertUmlClassDontHaveImplement(uClass, uInterface);
		assertUmlClassHasImplement(uClass, uInterface2);
	}

}
