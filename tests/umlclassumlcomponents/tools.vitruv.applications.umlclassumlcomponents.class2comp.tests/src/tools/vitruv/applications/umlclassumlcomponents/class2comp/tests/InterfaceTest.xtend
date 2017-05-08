package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*

class InterfaceTest extends AbstractClass2CompTest {
		
   	/*******
	*Tests:*
	********/
	
	@Test
	public def void testInterfaceRequiredAndProvided() {
		//Create 2 Components:
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class 1 and realize it in Class 2:
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass2)
		classIFRealization.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Assert and get Components:
		val umlComp1 = assertComponentForClass(umlClass1, CLASS_NAME)
		val umlComp2 = assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//Assert Component Interface:
		val compInterface = assertInterface(classInterface)
		
		//Assert Component InterfaceRealization:
		val compIFRealization = assertInterfaceRealization(classIFRealization)
		//Check for correct provided role setup:
		assertTrue(compIFRealization.clients.contains(umlComp1))
		assertTrue(compIFRealization.contract == compInterface)
		assertTrue(compIFRealization.suppliers.contains(compInterface))
		assertTrue(umlComp1.interfaceRealizations.contains(compIFRealization))
		
		//Assert Component Usage:
		val compUsage = assertUsage(classIFRealization)
		//Check for correct required role setup:
		assertTrue(compUsage.clients.contains(umlComp2))
		assertTrue(compUsage.suppliers.contains(compInterface))
		assertTrue(umlComp2.packagedElements.contains(compUsage))
	}	
    
    @Test
	public def void testInterfaceHasNoReachOutwards() {
		//Create 2 Components
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		
		//Create a new Interface in Class 1 and realize it in Class 1
		//This should result in no executed interface reaction
		val classInterface = createInterface(INTERFACE_NAME, classPackage1)
		val classIFRealization = createInterfaceRealization(INTERFACE_REALIZATION_NAME, umlClass1)
		classIFRealization.suppliers += classInterface
		
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Assert existence of Components:
		assertComponentForClass(umlClass1, CLASS_NAME)
		assertComponentForClass(umlClass2, CLASS_NAME2)
		
		//There should be no Component Interface:
		var correspondingElements1 = correspondenceModel.getCorrespondingEObjects(#[classInterface]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements1.size)
		
		//There should be no Component InterfaceRealization:
		var correspondingElements2 = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements2.size)
		
		//There should be no Component Usage:
		var correspondingElements3 = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(InterfaceRealization)
		assertEquals(0, correspondingElements3.size)
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
	
	private def InterfaceRealization assertInterfaceRealization(InterfaceRealization classIFRealization) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(InterfaceRealization)
		assertEquals(1, correspondingElements.size)
		val compIFRealization = correspondingElements.get(0)		
		assertTypeAndName(compIFRealization, InterfaceRealization, INTERFACE_REALIZATION_NAME + COMP_IF_REALIZATION_SUFFIX)
		return compIFRealization
	}
	
	private def Usage assertUsage(InterfaceRealization classIFRealization) {
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classIFRealization]).flatten.filter(Usage)
		assertEquals(1, correspondingElements.size)
		val compUsage = correspondingElements.get(0)
		assertTypeAndName(compUsage, Usage, INTERFACE_REALIZATION_NAME)
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