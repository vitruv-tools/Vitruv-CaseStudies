package tools.vitruv.applications.umlclassumlcomponents.sharedutil

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement

import static org.junit.Assert.*

class SharedTestUtil {
	
	/***************
	*Constants:*
	****************/		
	//Class
	public static val CLASS_NAME = "TestUmlClass"
	public static val CLASS_NAME2 = "TestUmlClass2"
	public static val ATTR_NAME = "TestAttribute"
	public static val PACKAGE_NAME = "TestPackage"
	public static val PACKAGE_NAME2 = "TestPackage2"
	public static val INTERFACE_NAME = "TestInterface"
	public static val INTERFACE_REALIZATION_NAME = "TestInterfaceRealization"
	public static val USAGE_NAME = "TestUsage"
	
	//Component
	
	
	/***************
	*Assert Helper:*
	****************/		
	
	public static def void assertTypeAndName(EObject umlObject, Class<? extends NamedElement> umlType, String name) {	
		assertTrue(umlObject.class.isInstance(umlType) || umlObject.class.genericInterfaces.contains(umlType))
		//Second condition encloses 'impl'-Classes		
		assertEquals(name, (umlObject as NamedElement).name)
	}

}