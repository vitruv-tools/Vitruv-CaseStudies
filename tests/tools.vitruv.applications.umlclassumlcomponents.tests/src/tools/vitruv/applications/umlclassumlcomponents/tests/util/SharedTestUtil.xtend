package tools.vitruv.applications.umlclassumlcomponents.tests.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class SharedTestUtil {
	/***************
	 * Constants:*
	 ****************/
	// Class
	public static val CLASS_NAME = "TestUmlClass"
	public static val CLASS_NAME2 = "TestUmlClass2"
	public static val ATTR_NAME = "TestAttribute"
	public static val PACKAGE_NAME = "TestPackage"
	public static val PACKAGE_NAME2 = "TestPackage2"
	public static val INTERFACE_NAME = "TestInterface"
	public static val INTERFACE_REALIZATION_NAME = "TestInterfaceRealization"
	public static val INTERFACE_REALIZATION_NAME2 = "TestInterfaceRealization2"
	public static val USAGE_NAME = "TestUsage"
	public static val USAGE_NAME2 = "TestUsage2"

	// Component	
	public static val COMP_NAME = "TestUmlComp"
	public static val COMP_NAME2 = "TestUmlComp2"
	public static val COMP_NAME3 = "TestUmlComp3"
	public static val DATATYPE_NAME = "TestDataType"
	public static val PROPERTY_NAME = "TestProperty"
	public static val OPERATION_NAME = "TestOperation"

	/***************
	 * Assert Helper:*
	 ****************/
	static def void assertTypeAndName(EObject umlObject, Class<? extends NamedElement> umlType, String name) {
		assertTrue(umlObject.class.isInstance(umlType) || umlObject.class.genericInterfaces.contains(umlType))
		// Second condition encloses 'impl'-Classes		
		assertEquals(name, (umlObject as NamedElement).name)
	}
}
