package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals

class InterfaceTest extends AbstractClass2CompTest {
		
   	/*******
	*Tests:*
	********/
	
	@Test
	def void testInterfaceRequiredAndProvided() {
		//Create 2 Components:
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class Package 1	and provide it here:
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealizationProv = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass1)
		classIFRealizationProv.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Realize the Interface in Class 2:
		val classIFRealizationReq = createInterfaceRealization(INTERFACE_REALIZATION_NAME2, umlClass2)
		classIFRealizationReq.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(classIFRealizationReq)
		
		//Assert and get Components:
		val umlComp1 = assertComponentForClass(umlClass1, CLASS_NAME)
		val umlComp2 = assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//Assert Component Interface:
		val compInterface = assertInterface(classInterface)
		
		//Assert Component InterfaceRealization:
		val compIFRealization = assertInterfaceRealization(classIFRealizationProv, INTERFACE_REALIZATION_NAME)
		//Check for correct provided role setup:
		assertTrue(compIFRealization.clients.contains(umlComp1))
		assertTrue(compIFRealization.contract == compInterface)
		assertTrue(compIFRealization.suppliers.contains(compInterface))
		assertTrue(umlComp1.interfaceRealizations.contains(compIFRealization))
		
		//Assert Component Usage:
		val compUsage = assertUsage(classIFRealizationReq, INTERFACE_REALIZATION_NAME2)
		//Check for correct required role setup:
		assertTrue(compUsage.clients.contains(umlComp2))
		assertTrue(compUsage.suppliers.contains(compInterface))
		assertTrue(umlComp2.packagedElements.contains(compUsage))
	}	
	
	@Test
	def void testInterfaceHasNoReachOutwards() {
		//Create 2 Components:
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class Package 1	and provide it here:
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealizationProv = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass1)
		classIFRealizationProv.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Realize the Interface in Class 1:
		//This should result in no executed interface reaction
		val classIFRealizationReq = createInterfaceRealization(INTERFACE_REALIZATION_NAME2, umlClass1)
		classIFRealizationReq.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(classIFRealizationReq)
		
		//Assert existence of Components:
		assertComponentForClass(umlClass1, CLASS_NAME)
		assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//There should be no Component Interface:
		var correspondingElements1 = correspondenceModel.getCorrespondingEObjects(#[classInterface]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements1.size)
		
		//There should be no Component InterfaceRealization:
		var correspondingElements2 = correspondenceModel.getCorrespondingEObjects(#[classIFRealizationProv]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements2.size)
		
		//There should be no Component Usage:
		var correspondingElements3 = correspondenceModel.getCorrespondingEObjects(#[classIFRealizationReq]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements3.size)
	}
    
	@Test
	def void testInterfaceRemoved() {
		//***First create a realized and used Interface***
		
		//Create 2 Components:
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class Package 1	and provide it here:
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealizationProv = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass1)
		classIFRealizationProv.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Realize the Interface in Class 2:
		val classIFRealizationReq = createInterfaceRealization(INTERFACE_REALIZATION_NAME2, umlClass2)
		classIFRealizationReq.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(classIFRealizationReq)
		
		//Assert and get Components:
		assertComponentForClass(umlClass1, CLASS_NAME)
		val umlComp2 = assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//Assert Component Interface:
		val compInterface = assertInterface(classInterface)			
		//Assert Component Usage:
		val compUsage = assertUsage(classIFRealizationReq, INTERFACE_REALIZATION_NAME2)
		
		///***Then remove the Interface***
				
		classInterface.destroy
		saveAndSynchronizeWithInteractions(rootElement)
		//There should be no corresponding Interface and Usage anymore
		assertFalse(umlComp2.model.packagedElements.contains(compInterface))
		assertFalse(umlComp2.packagedElements.contains(compUsage))
	}
	
	@Test
	def void testInterfaceRealizaionRemoved() {
		//***First create a realized and used Interface***
		
		//Create 2 Components:
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class Package 1	and provide it here:
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealizationProv = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass1)
		classIFRealizationProv.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Realize the Interface in Class 2:
		val classIFRealizationReq = createInterfaceRealization(INTERFACE_REALIZATION_NAME2, umlClass2)
		classIFRealizationReq.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(classIFRealizationReq)
		
		//Assert and get Components:
		assertComponentForClass(umlClass1, CLASS_NAME)
		val umlComp2 = assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//Assert Component Interface:
		assertInterface(classInterface)		
		//Assert Component InterfaceRealization:
		val compIFRealization = assertInterfaceRealization(classIFRealizationProv, INTERFACE_REALIZATION_NAME)		
		//Assert Component Usage:
		val compUsage = assertUsage(classIFRealizationReq, INTERFACE_REALIZATION_NAME2)
		
		
		///***Then remove the InterfaceRealizaions***
		
		classIFRealizationReq.destroy 
		saveAndSynchronizeWithInteractions(rootElement)
		//There should be no corresponding InterfaceRealization anymore
		assertFalse(umlComp2.interfaceRealizations.contains(compIFRealization))
		
		classIFRealizationProv.destroy
		saveAndSynchronizeWithInteractions(rootElement)
		//There should be no corresponding InterfaceRealization anymore
		assertFalse(umlComp2.packagedElements.contains(compUsage))
	}
	
	/***************
	*Assert Helper:*
	****************/		
	
	private def Interface assertInterface(Interface classInterface) {		
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classInterface]).flatten.filter(Interface)
		assertEquals(1, correspondingElements.size)
		val compInterface = correspondingElements.get(0)
		assertTypeAndName(compInterface, Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
		return compInterface
	}
	
	private def InterfaceRealization assertInterfaceRealization(InterfaceRealization classIFRealization, String name) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val compIFRealization = correspondingElements.get(0)		
		assertTypeAndName(compIFRealization, InterfaceRealization, name + COMP_IFR_AND_USAGE_SUFFIX)
		return compIFRealization
	}
	
	private def Usage assertUsage(InterfaceRealization classIFRealization, String name) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(Usage)
		assertEquals(1, correspondingElements.size)
		val compUsage = correspondingElements.get(0)
		assertTypeAndName(compUsage, Usage, name + COMP_IFR_AND_USAGE_SUFFIX)
		return compUsage
	}
	
			
	/*****************
	*Creation Helper:*
	******************/		
	
	private def Interface createInterface(String name, Package classPackage) {
		val classInterface = UMLFactory.eINSTANCE.createInterface()
		classInterface.name = name
		classPackage.packagedElements += classInterface	
		return classInterface
	}
	
	private def InterfaceRealization createInterfaceRealization(String name, Class umlClass) {
		val classInterfaceRealization = UMLFactory.eINSTANCE.createInterfaceRealization()
		classInterfaceRealization.name = name
		classInterfaceRealization.clients += umlClass
		umlClass.interfaceRealizations += classInterfaceRealization	
		return classInterfaceRealization
	}
}