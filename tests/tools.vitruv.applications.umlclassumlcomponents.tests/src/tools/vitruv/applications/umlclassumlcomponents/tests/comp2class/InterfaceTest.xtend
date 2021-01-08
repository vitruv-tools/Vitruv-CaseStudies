package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals

class InterfaceTest extends AbstractComp2ClassTest {

	/*******
	 * Tests:*
	 ********/
	@Test
	def void testInterfaceRealized() {
		// Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		// Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		propagate

		// Assert Class
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		// Assert Class Interface:
		val classInterface = assertInterface(compInterface)

		// Assert Class InterfaceRealization:
		val classIFRealization = assertInterfaceRealization(compIFRealization)
		// Check for correct realization setup:
		assertTrue(classIFRealization.clients.contains(umlClass))
		assertTrue(classIFRealization.contract == classInterface)
		assertTrue(classIFRealization.suppliers.contains(classInterface))
		assertTrue(umlClass.interfaceRealizations.contains(classIFRealization))
	}

	@Test
	def void testDeleteInterfaceRealization() {
		// Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		// Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		propagate

		// Get Class and Class InterfaceRealization:
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classIFRealization = assertInterfaceRealization(compIFRealization)

		// Remove Component interfaceRealization and check if the corresponding one is removed, too
		compIFRealization.destroy
		propagate
		assertFalse(umlClass.interfaceRealizations.contains(classIFRealization))
	}

	@Test
	def void testInterfaceUsed() {
		// ***First add InterfaceRealization***
		// Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		// Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		propagate

		// Assert Class
		val umlClass1 = assertClassAndPackage(umlComp, COMP_NAME)
		// Assert Class Interface:
		val classInterface = assertInterface(compInterface)

		// Assert Class InterfaceRealization:
		val classIFRealization1 = assertInterfaceRealization(compIFRealization)
		// Check for correct realization setup:
		assertTrue(classIFRealization1.clients.contains(umlClass1))
		assertTrue(classIFRealization1.contract == classInterface)
		assertTrue(classIFRealization1.suppliers.contains(classInterface))
		assertTrue(umlClass1.interfaceRealizations.contains(classIFRealization1))

		// ***Now add uses Relationship***
		// Create second Class in Package for a Component
		val umlComp2 = createComponent(COMP_NAME2)
		// Create Usage for Component Interface
		val compUsage = createUsage(USAGE_NAME, umlComp2)
		compUsage.suppliers += compInterface

		propagate

		// Assert Class
		val umlClass2 = assertClassAndPackage(umlComp2, COMP_NAME2)

		// Assert Class InterfaceRealization:
		val classIFRealization2 = assertUsage(compUsage)
		// Assert correct Package:
		assertTrue(classInterface.package == umlClass1.package)

		// Check for correct realization setup:
		assertTrue(classIFRealization2.clients.contains(umlClass2))
		assertFalse(classIFRealization2.clients.contains(umlClass1))
		assertTrue(classIFRealization2.contract == classInterface)
		assertTrue(classIFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(classIFRealization2))
		assertFalse(umlClass2.interfaceRealizations.contains(classIFRealization1))
	}

	@Test
	def void testDeleteUsage() {
		// ***First add InterfaceRealization***
		// Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		// Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		propagate

		// ***Now add uses Relationship***
		// Create second Class in Package for a Component
		val umlComp2 = createComponent(COMP_NAME2)
		// Create Usage for Component Interface
		val compUsage = createUsage(USAGE_NAME, umlComp2)
		compUsage.suppliers += compInterface

		propagate

		// ***Now delete the Usage***
		// Get second Class and InterfaceReazation
		val umlClass2 = assertClassAndPackage(umlComp2, COMP_NAME2)
		val classIFRealization2 = assertUsage(compUsage)

		// Remove Usage and check if the corresponding InterfaceRealization is removed, too
		compUsage.destroy
		propagate
		assertFalse(umlClass2.interfaceRealizations.contains(classIFRealization2))
	}

	@Test
	def void testInterfaceUsedTwice() {
		// ***First add InterfaceRealization***
		// Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		// Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		propagate

		// Assert Class
		val umlClass1 = assertClassAndPackage(umlComp, COMP_NAME)
		// Assert Class Interface:
		val classInterface = assertInterface(compInterface)

		// Assert Class InterfaceRealization:
		val classIFRealization1 = assertInterfaceRealization(compIFRealization)
		// Check for correct realization setup:
		assertTrue(classIFRealization1.clients.contains(umlClass1))
		assertTrue(classIFRealization1.contract == classInterface)
		assertTrue(classIFRealization1.suppliers.contains(classInterface))
		assertTrue(umlClass1.interfaceRealizations.contains(classIFRealization1))

		// ***Now add uses Relationships***
		// Create a second Class in Package for a Component
		val umlComp2 = createComponent(COMP_NAME2)
		// Create Usage for Component Interface
		val compUsage = createUsage(USAGE_NAME, umlComp2)
		compUsage.suppliers += compInterface

		// Create a third Class in Package for a Component
		val umlComp3 = createComponent(COMP_NAME3)
		// Create Usage for Component Interface
		val compUsage2 = createUsage(USAGE_NAME2, umlComp3)
		compUsage2.suppliers += compInterface

		propagate

		// Assert Classes
		val umlClass2 = assertClassAndPackage(umlComp2, COMP_NAME2)
		val umlClass3 = assertClassAndPackage(umlComp3, COMP_NAME3)

		// Assert Class Usages:
		val classIFRealization2 = assertUsage(compUsage)
		val classIFRealization3 = assertUsage(compUsage2, USAGE_NAME2)
		// Assert correct Package:
		assertTrue(classInterface.package == umlClass1.package)

		// Check for correct usage setup:
		// Usage 1
		assertTrue(classIFRealization2.clients.contains(umlClass2))
		assertFalse(classIFRealization2.clients.contains(umlClass1))
		assertTrue(classIFRealization2.contract == classInterface)
		assertTrue(classIFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(classIFRealization2))
		assertFalse(umlClass2.interfaceRealizations.contains(classIFRealization1))
		// Usage 2
		assertTrue(classIFRealization3.clients.contains(umlClass3))
		assertFalse(classIFRealization3.clients.contains(umlClass1))
		assertTrue(classIFRealization3.contract == classInterface)
		assertTrue(classIFRealization3.suppliers.contains(classInterface))
		assertTrue(umlClass3.interfaceRealizations.contains(classIFRealization3))
		assertFalse(umlClass3.interfaceRealizations.contains(classIFRealization1))
	}

	/***************
	 * Assert Helper:*
	 ****************/
	private def Interface assertInterface(Interface compInterface) {
		assertInterface(compInterface, INTERFACE_NAME)
	}

	private def Interface assertInterface(Interface compInterface, String name) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compInterface]).flatten.filter(
			Interface)
		assertEquals(1, correspondingElements.size)
		val classInterface = correspondingElements.get(0)
		assertTypeAndName(classInterface, Interface, name + CLASS_INTERFACE_SUFFIX)
		return classInterface
	}

	private def InterfaceRealization assertInterfaceRealization(InterfaceRealization compIFRealization) {
		return assertInterfaceRealization(compIFRealization, INTERFACE_REALIZATION_NAME)
	}

	private def InterfaceRealization assertInterfaceRealization(InterfaceRealization compIFRealization, String name) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compIFRealization]).flatten.filter(
			InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val classIFRealization = correspondingElements.get(0)
		assertTypeAndName(classIFRealization, InterfaceRealization, name + CLASS_IFR_AND_USAGE_SUFFIX)
		return classIFRealization
	}

	private def InterfaceRealization assertUsage(Usage compUsage) {
		assertUsage(compUsage, USAGE_NAME)
	}

	private def InterfaceRealization assertUsage(Usage compUsage, String name) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compUsage]).flatten.filter(
			InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val classIFRealization = correspondingElements.get(0)
		assertTypeAndName(classIFRealization, InterfaceRealization, name + CLASS_IFR_AND_USAGE_SUFFIX)
		return classIFRealization
	}

	/*****************
	 * Creation Helper:*
	 ******************/
	private def Interface createInterface(String name) {
		val classInterface = UMLFactory.eINSTANCE.createInterface()
		classInterface.name = name
		rootElement.packagedElements += classInterface
		return classInterface
	}

	private def InterfaceRealization createInterfaceRealization(String name, Component umlComp) {
		val compInterfaceRealization = UMLFactory.eINSTANCE.createInterfaceRealization()
		compInterfaceRealization.name = name
		compInterfaceRealization.clients += umlComp
		umlComp.interfaceRealizations += compInterfaceRealization
		return compInterfaceRealization
	}

	private def Usage createUsage(String name, Component umlComp) {
		val compUsage = UMLFactory.eINSTANCE.createUsage()
		compUsage.name = name
		compUsage.clients += umlComp
		umlComp.packagedElements += compUsage
		return compUsage
	}
}
