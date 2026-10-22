package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getClassifierFromTypeReference;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createSimpleUmlInterface;
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.createUmlInterfaceAndAddToPackage;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Interface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains tests that deal with changes with interfaces.
 * (creating, deleting, renaming,...)
 * @author Fei
 */
public class UmlToJavaInterfaceTest extends UmlToJavaTransformationTest {
	private static final String INTERFACE_NAME = "InterfaceName";
	private static final String INTERFACE_RENAME = "InterfaceRename";
	private static final String SUPERINTERFACENAME_1 = "SuperInterfaceOne";
	private static final String SUPERINTERFACENAME_2 = "SuperInterfaceTwo";
	private static final String STANDARD_INTERFACE_NAME = "StandardInterfaceName";
	private Interface uInterface;

	@BeforeEach
	public void before() {
		uInterface = createSimpleUmlInterface(getRootElement(), INTERFACE_NAME);
		propagate();

	}

	@Test
	public void testCreateInterface() {
		var interface_ = createSimpleUmlInterface(getRootElement(), STANDARD_INTERFACE_NAME);
		propagate();

		assertJavaFileExists(STANDARD_INTERFACE_NAME, new String[] {});
		var jInterface = getCorrespondingInterface(interface_);
		assertEquals(STANDARD_INTERFACE_NAME, jInterface.getName());
	}

	@Test
	public void testRenameInterface() {
		uInterface.setName(INTERFACE_RENAME);
		propagate();

		assertJavaFileExists(INTERFACE_RENAME, new String[] {});
		var jInterface = getCorrespondingInterface(uInterface);
		assertEquals(INTERFACE_RENAME, jInterface.getName());
		assertJavaFileNotExists(INTERFACE_NAME, new String[] {});
	}

	@Test
	public void testDeleteInterface() {
		uInterface.destroy();
		propagate();

		assertJavaFileNotExists(INTERFACE_NAME, new String[] {});
	}

	@Test
	public void testAddSuperInterface() {
		var interface_ = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1,
			SUPERINTERFACENAME_2);
		propagate();
		var jI = getCorrespondingInterface(interface_);
		assertEquals(SUPERINTERFACENAME_1, getClassifierFromTypeReference(jI.getExtends().get(0)).getName());
		assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.getExtends().get(1)).getName());
	}

	@Test
	public void testRemoveSuperInterface() {
		var uInterface = createInterfaceWithTwoSuperInterfaces(STANDARD_INTERFACE_NAME, SUPERINTERFACENAME_1,
			SUPERINTERFACENAME_2);
		propagate();
		uInterface.getGeneralizations().remove(0);
		propagate();
		var jI = getCorrespondingInterface(uInterface);
		assertTrue(jI.getExtends().size() == 1, String.valueOf(jI.getExtends().size()));
		assertEquals(SUPERINTERFACENAME_2, getClassifierFromTypeReference(jI.getExtends().get(0)).getName());
		assertJavaFileExists(SUPERINTERFACENAME_1, new String[] {});
	}

	/**
	 * @return an interface named iName which inherits from two other interfaces named superName1 an superName2
	 */
	private Interface createInterfaceWithTwoSuperInterfaces(String iName, String superName1, String superName2) {
		var super1 = createSimpleUmlInterface(getRootElement(), superName1);
		var super2 = createSimpleUmlInterface(getRootElement(), superName2);
		EList<Interface> supers = new BasicEList<Interface>();
		supers.add(super1);
		supers.add(super2);
		return createUmlInterfaceAndAddToPackage(getRootElement(), iName, supers);
	}

}
