package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*

class InterfaceTest extends AbstractComp2ClassTest {
		    	
   	/*******
	*Tests:*
	********/	
	
	@Test
	public def void testInterfaceRealized() {
		//Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		//Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		saveAndSynchronizeChanges(rootElement)
		
		//Assert Class
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)		
		//Assert Class Interface:
		val classInterface = assertInterface(compInterface)
		
		//Assert Class InterfaceRealization:
		val classIFRealization = assertInterfaceRealization(compIFRealization)
		//Check for correct realization setup:
		assertTrue(classIFRealization.clients.contains(umlClass))
		assertTrue(classIFRealization.contract == classInterface)
		assertTrue(classIFRealization.suppliers.contains(classInterface))
		assertTrue(umlClass.interfaceRealizations.contains(classIFRealization))		
	}
	
	@Test
	public def void testDeleteInterfaceRealization() {
		//Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME)
		//Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		saveAndSynchronizeChanges(rootElement)
		
		
		//Get Class and Class InterfaceRealization:
		val umlClass = assertClassAndPackage(umlComp, COMP_NAME)
		val classIFRealization = assertInterfaceRealization(compIFRealization)
		
		//Remove Component interfaceRealization and check if the corresponding one is removed, too
		compIFRealization.destroy
		saveAndSynchronizeChanges(rootElement)		
		assertFalse(umlClass.interfaceRealizations.contains(classIFRealization))
	}
	
	@Test	
	public def void testInterfaceUsed() { 
		//***First add InterfaceRealization***
		
		//Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME) 
		//Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		saveAndSynchronizeChanges(rootElement)
		
		//Assert Class
		val umlClass1 = assertClassAndPackage(umlComp, COMP_NAME)		
		//Assert Class Interface:
		val classInterface = assertInterface(compInterface)
		
		//Assert Class InterfaceRealization:
		val classIFRealization1 = assertInterfaceRealization(compIFRealization)
		//Check for correct realization setup:
		assertTrue(classIFRealization1.clients.contains(umlClass1))
		assertTrue(classIFRealization1.contract == classInterface)
		assertTrue(classIFRealization1.suppliers.contains(classInterface))
		assertTrue(umlClass1.interfaceRealizations.contains(classIFRealization1))			
		
		//***Now add uses Relationship***
		
		//Create second Class in Package for a Component
		val umlComp2 = createComponent(COMP_NAME2)
		//Create Usage for Component Interface
		val compUsage = createUsage(USAGE_NAME, umlComp2)
		compUsage.suppliers += compInterface	
		
		saveAndSynchronizeChanges(rootElement)
		
		//Assert Class
		val umlClass2 = assertClassAndPackage(umlComp2, COMP_NAME2)
				
		//Assert Class InterfaceRealization:
		val classIFRealization2 = assertUsage(compUsage)
		//Assert correct Package:
		assertTrue(classInterface.package == umlClass1.package)
				
		//Check for correct realization setup:
		assertTrue(classIFRealization2.clients.contains(umlClass2))
		assertFalse(classIFRealization2.clients.contains(umlClass1))
		assertTrue(classIFRealization2.contract == classInterface)
		assertTrue(classIFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(classIFRealization2))
		assertFalse(umlClass2.interfaceRealizations.contains(classIFRealization1))
	}
		
	@Test
	public def void testDeleteUsage() {
		//***First add InterfaceRealization***
		
		//Create Class in Package for a Component
		val umlComp = createComponent(COMP_NAME) 
		//Create Interface and Realization
		val compInterface = createInterface(INTERFACE_NAME)
		val compIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlComp)
		compIFRealization.suppliers += compInterface
		saveAndSynchronizeChanges(rootElement)

		//***Now add uses Relationship***
		
		//Create second Class in Package for a Component
		val umlComp2 = createComponent(COMP_NAME2)
		//Create Usage for Component Interface
		val compUsage = createUsage(USAGE_NAME, umlComp2)
		compUsage.suppliers += compInterface	
		
		saveAndSynchronizeChanges(rootElement)
		
		//***Now delete the Usage***
		
		//Get second Class and InterfaceReazation
		val umlClass2 = assertClassAndPackage(umlComp2, COMP_NAME2)
		val classIFRealization2 = assertUsage(compUsage)
		
		//Remove Usage and check if the corresponding InterfaceRealization is removed, too
		compUsage.destroy
		saveAndSynchronizeChanges(rootElement)		
		assertFalse(umlClass2.interfaceRealizations.contains(classIFRealization2))
	}
	
	
	/***************
	*Assert Helper:*
	****************/		
	
	private def Interface assertInterface(Interface compInterface) {		
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compInterface]).flatten.filter(Interface)
		assertEquals(1, correspondingElements.size)
		val classInterface = correspondingElements.get(0)
		assertTypeAndName(classInterface, Interface, INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		return classInterface
	}
	
	private def InterfaceRealization assertInterfaceRealization(InterfaceRealization compIFRealization) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compIFRealization]).flatten.filter(InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val classIFRealization = correspondingElements.get(0)		
		assertTypeAndName(classIFRealization, InterfaceRealization, INTERFACE_REALIZATION_NAME + CLASS_IFR_AND_USAGE_SUFFIX)
		return classIFRealization
	}

	private def InterfaceRealization assertUsage(Usage compUsage) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compUsage]).flatten.filter(InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val classIFRealization = correspondingElements.get(0)
		assertTypeAndName(classIFRealization, InterfaceRealization, USAGE_NAME + CLASS_IFR_AND_USAGE_SUFFIX)
		return classIFRealization
	}
	
				
	/*****************
	*Creation Helper:*
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