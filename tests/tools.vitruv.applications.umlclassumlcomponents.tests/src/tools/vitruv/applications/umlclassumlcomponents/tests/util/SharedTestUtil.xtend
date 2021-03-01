package tools.vitruv.applications.umlclassumlcomponents.tests.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.NamedElement

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.BehavioredClassifier
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.StructuredClassifier
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Operation

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

	def static void claimNoPackagedElementWithName(Package in, Class<? extends PackageableElement> containedElementType,
		String containedElementName) {
		val foundElement = in.getPackagedElementWithName(containedElementType, containedElementName)
		assertNull(foundElement,
			containedElementType.name + " with name '" + containedElementName + "' found in package " + in)
	}

	def static <T extends PackageableElement> T claimPackagedElementWithName(Package in, Class<T> containedElementType,
		String containedElementName) {
		val foundElement = in.getPackagedElementWithName(containedElementType, containedElementName)
		assertNotNull(foundElement,
			"No " + containedElementType.name + " with name '" + containedElementName + "' found in package " + in)
		return foundElement
	}

	def static <T extends PackageableElement> T getPackagedElementWithName(Package in, Class<T> containedElementType,
		String containedElementName) {
		val foundElements = in.packagedElements.filter(containedElementType).filter[name == containedElementName]
		assertTrue(foundElements.size <= 1,
			"There were " + foundElements.size + " " + containedElementType.name + "s with name '" +
				containedElementName + "' in package " + in)
		return foundElements.findFirst[true]
	}

	def static <T extends PackageableElement> T claimPackagedElementWithName(Component in,
		Class<T> containedElementType, String containedElementName) {
		val foundElement = in.getPackagedElementWithName(containedElementType, containedElementName)
		assertNotNull(foundElement,
			"No " + containedElementType.name + " with name '" + containedElementName + "' found in component " + in)
		return foundElement
	}

	def static <T extends PackageableElement> T getPackagedElementWithName(Component in, Class<T> containedElementType,
		String containedElementName) {
		val foundElements = in.packagedElements.filter(containedElementType).filter[name == containedElementName]
		assertTrue(foundElements.size <= 1,
			"There were " + foundElements.size + " " + containedElementType.name + "s with name '" +
				containedElementName + "' in component " + in)
		return foundElements.findFirst[true]
	}

	def static void claimNoInterfaceRealization(BehavioredClassifier in, String interfaceRelalizationName,
		Interface realizedInterface) {
		val foundElement = in.getInterfaceRealization(interfaceRelalizationName, realizedInterface)
		assertNull(foundElement,
			"Interface realization with name '" + interfaceRelalizationName + "' for interface " + realizedInterface +
				" found in classifier " + in)
	}

	def static InterfaceRealization claimInterfaceRealization(BehavioredClassifier in, String interfaceRelalizationName,
		Interface realizedInterface) {
		val foundElement = getInterfaceRealization(in, interfaceRelalizationName, realizedInterface)
		assertNotNull(foundElement,
			"No interface realization with name '" + interfaceRelalizationName + "' for interface " +
				realizedInterface + " found in classifier " + in)
		return foundElement
	}

	def static InterfaceRealization getInterfaceRealization(BehavioredClassifier in, String interfaceRelalizationName,
		Interface realizedInterface) {
		val foundElements = in.interfaceRealizations.filter[name == interfaceRelalizationName].filter [
			suppliers.size == 1 && suppliers.contains(realizedInterface)
		]
		assertTrue(foundElements.size <= 1,
			"There were " + foundElements.size + " interface realizations with name '" + interfaceRelalizationName +
				"' for interface " + realizedInterface + " in classifier " + in)
		return foundElements.findFirst[true]
	}

	def static Property claimOwnedAttributeWithName(StructuredClassifier in, String containedAttributeName) {
		return in.claimSingleProperty(in.ownedAttributes, containedAttributeName)
	}

	def static Property claimOwnedAttributeWithName(DataType in, String containedAttributeName) {
		return in.claimSingleProperty(in.ownedAttributes, containedAttributeName)
	}

	private def static claimSingleProperty(EObject container, Iterable<Property> properties, String expectedName) {
		val foundElements = properties.filter[name == expectedName]
		assertTrue(foundElements.size <= 1,
			"There were " + foundElements.size + " properties with name '" + expectedName +
				"' in classifier " + container)
		assertTrue(foundElements.size > 0, "No property with name '" + expectedName + "' found in " + container)
		return foundElements.get(0)
	}
	
	def static Operation claimOwnedOperationWithName(org.eclipse.uml2.uml.Class in, String containedOperationName) {
		return in.claimSingleOperation(in.ownedOperations, containedOperationName)
	}

	def static Operation claimOwnedOperationWithName(DataType in, String containedOperationName) {
		return in.claimSingleOperation(in.ownedOperations, containedOperationName)
	}

	private def static claimSingleOperation(EObject container, Iterable<Operation> properties, String expectedName) {
		val foundElements = properties.filter[name == expectedName]
		assertTrue(foundElements.size <= 1,
			"There were " + foundElements.size + " operations with name '" + expectedName +
				"' in classifier " + container)
		assertTrue(foundElements.size > 0, "No operation with name '" + expectedName + "' found in " + container)
		return foundElements.get(0)
	}

}
