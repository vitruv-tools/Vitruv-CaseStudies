package tools.vitruv.applications.umlclassumlcomponents.tests.util

import org.eclipse.emf.ecore.EObject

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
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

	def static <T extends PackageableElement> T claimPackagedElementWithName(Package in, Class<T> containedElementType,
		String containedElementName) {
		return in.claimPackagedElementWithName(in.packagedElements, containedElementType, containedElementName)
	}

	def static void claimNoPackagedElementWithName(Package in, Class<? extends PackageableElement> containedElementType,
		String containedElementName) {
		in.claimNoPackagedElementWithName(in.packagedElements, containedElementType, containedElementName)
	}

	def static <T extends PackageableElement> T claimPackagedElementWithName(Component in,
		Class<T> containedElementType, String containedElementName) {
		return in.claimPackagedElementWithName(in.packagedElements, containedElementType, containedElementName)
	}

	def static void claimNoPackagedElementWithName(Component in,
		Class<? extends PackageableElement> containedElementType, String containedElementName) {
		in.claimNoPackagedElementWithName(in.packagedElements, containedElementType, containedElementName)
	}

	private def static <T extends PackageableElement> T claimPackagedElementWithName(EObject container,
		Iterable<PackageableElement> packagedElements, Class<T> containedElementType, String containedElementName) {
		val foundElements = packagedElements.filterCandidates(containedElementType, containedElementName)
		assertEquals(1, foundElements.size,
			"There were " + foundElements.size + " " + containedElementType.name + "s with name '" +
				containedElementName + "' in " + container)
		return foundElements.get(0)
	}

	private def static void claimNoPackagedElementWithName(EObject container,
		Iterable<PackageableElement> packagedElements, Class<? extends PackageableElement> containedElementType,
		String containedElementName) {
		val foundElements = packagedElements.filterCandidates(containedElementType, containedElementName)
		assertTrue(foundElements.empty,
			"There were " + foundElements.size + " " + containedElementType.name + "s with name '" +
				containedElementName + "' in " + container)
	}

	private def static <T extends PackageableElement> Iterable<T> filterCandidates(
		Iterable<PackageableElement> packagedElements, Class<T> containedElementType, String containedElementName) {
		return packagedElements.filter(containedElementType).filter[name == containedElementName]
	}

	def static InterfaceRealization claimInterfaceRealization(BehavioredClassifier in, String interfaceRelalizationName,
		Interface realizedInterface) {
		val foundElements = in.interfaceRealizations.filterCandidates(interfaceRelalizationName, realizedInterface)
		assertEquals(1, foundElements.size,
			"There were " + foundElements.size + " interface realizations with name '" + interfaceRelalizationName +
				"' for interface " + realizedInterface + " in classifier " + in)
		return foundElements.get(0)
	}

	def static void claimNoInterfaceRealization(BehavioredClassifier in, String interfaceRelalizationName,
		Interface realizedInterface) {
		val foundElements = in.interfaceRealizations.filterCandidates(interfaceRelalizationName, realizedInterface)
		assertTrue(foundElements.empty,
			"Interface realization with name '" + interfaceRelalizationName + "' for interface " + realizedInterface +
				" found in classifier " + in)
	}

	private def static Iterable<InterfaceRealization> filterCandidates(
		Iterable<InterfaceRealization> interfaceRealizations, String interfaceRelalizationName,
		Interface realizedInterface) {
		return interfaceRealizations.filter[name == interfaceRelalizationName].filter [
			suppliers.size == 1 && suppliers.contains(realizedInterface)
		]
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
			"There were " + foundElements.size + " properties with name '" + expectedName + "' in " + container)
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
			"There were " + foundElements.size + " operations with name '" + expectedName + "' in " + container)
		assertTrue(foundElements.size > 0, "No operation with name '" + expectedName + "' found in " + container)
		return foundElements.get(0)
	}

}
