package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import java.util.Collections
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Interface

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedIntegrationTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.tests.util.UserInteractionTestUtil.*
import org.eclipse.uml2.uml.Component
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

import static org.junit.jupiter.api.Assertions.assertTrue
import java.nio.file.Path
import org.eclipse.emf.ecore.EObject

class Comp2ClassIntegrationTest extends AbstractComp2ClassTest {
	
	override protected setup() {
		//Don't initialize a model here, as we are loading and creating one
	}
	
	def Model integrationTest(String fileName) {		
		sendCollectedUserInteractionSelections(this.userInteraction)
		resourceAt(Path.of(OUTPUT_NAME)).startRecordingChanges => [
			contents += EObject.from(fileName.testModelResource)
		]
		propagate
		
		val umlModel = correspondenceModel.getAllEObjectsOfTypeInCorrespondences(Model).get(0)
		umlModel.eResource.save(Collections.EMPTY_MAP)
		
		return umlModel as Model
	}
	
	/*******
	*Tests:*
	********/

	@Test
	def void integrationTestWith2Components() {
		val umlModel = integrationTest("TestModel2Components.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, Package, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, Class, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, Class, 1) //Class Package 2 should have 1 Class
	}
	
	@Test
	def void integrationTestWithComponentAnd2DataTypes() {
		//Decide to create a Class for the first DataType:
		queueUserInteractionSelections(1)
		//Decide to create a Class for the second DataType:
		queueUserInteractionSelections(1)
		
		val umlModel = integrationTest("TestModelWith2DataTypesAndComponent.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 1) //There should be 1 original Component
		assertCountOfTypeInList(modelElements, DataType, 2) //There should be 2 original DataTypes
		assertCountOfTypeInList(modelElements, Package, 2) //There should be 2 new Packages
		assertCountOfTypeInPackage(modelElements, 0, Class, 2) //Class DataTypes Package should have 2 Classes
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
	}
	
	@Test
	def void integrationTestWithComponentAndDataTypeWithoutClassRepresentation() {
		//Decide to create no corresponding Class for the first DataType:
		queueUserInteractionSelections(0)
		//Decide to create a Class for the second DataType:
		queueUserInteractionSelections(1)
		
		val umlModel = integrationTest("TestModelWith2DataTypesAndComponent.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 1) //There should be 1 original Components
		assertCountOfTypeInList(modelElements, DataType, 2) //There should be 2 original DataTypes
		assertCountOfTypeInList(modelElements, Package, 2) //There should be 2 new Packages
		assertCountOfTypeInPackage(modelElements, 0, Class, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
	}
	
	@Test
	def void integrationTestWithDataTypeWithPropertyAndOperation() {
		//Decide to create a Class for the DataType:
		queueUserInteractionSelections(1)
		
		val umlModel = integrationTest("TestModelDataTypeWithPropertyAndOperation.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, DataType, 1) //There should be 1 original DataType
		assertCountOfTypeInList(modelElements, Package, 1) //There should be 1 new Package
		assertCountOfTypeInPackage(modelElements, 0, Class, 1) //Class DataTypes Package should have 1 Class
		
		//Check new Class for Property & Operation:
		val dataTypeClass = modelElements.filter(Package).get(0).packagedElements.filter(Class).get(0)
		val ownedAttributes = dataTypeClass.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, Property, 1) //There should be 1 Property
		val ownedOperations = dataTypeClass.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, Operation, 1) //There should be 1 Operation
	}
	
	@Test
	def void integrationTestWith2ComponentsAndRealizedInterface() {
		val umlModel = integrationTest("TestModel2ComponentsWithRealizedInterface.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, Package, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, Class, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, Class, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, Interface, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, Interface, 0) //Class Package 2 should have no Interfaces
		
		//Check new Class for InterfaceRealization and inspect if it's setup correctly:
		val umlClass = modelElements.filter(Package).get(1).packagedElements.filter(Class).get(0)
		val iFRealizations = umlClass.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealization, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(Package).get(1).packagedElements.filter(Interface).get(0)
		assertTrue(iFRealization.clients.contains(umlClass))
		assertTrue(iFRealization.contract == classInterface)
		assertTrue(iFRealization.suppliers.contains(classInterface))
		assertTrue(umlClass.interfaceRealizations.contains(iFRealization))	
	}
	
	@Test
	def void integrationTestWith2ComponentsAndUsage() {
		val umlModel = integrationTest("TestModel2ComponentsWithUsage.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, org.eclipse.uml2.uml.Package, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, org.eclipse.uml2.uml.Class, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, org.eclipse.uml2.uml.Class, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, Interface, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, Interface, 0) //Class Package 2 should have no Interfaces
		
		//Check Class 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass1 = modelElements.filter(Package).get(1).packagedElements.filter(Class).get(0)
		val iFRealizations1 = umlClass1.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations1, InterfaceRealization, 1) //There should be 1 InterfaceRealization
		val iFRealization1 = iFRealizations1.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(Package).get(1).packagedElements.filter(Interface).get(0)
		assertTrue(iFRealization1.clients.contains(umlClass1))
		assertTrue(iFRealization1.contract == classInterface)
		assertTrue(iFRealization1.suppliers.contains(classInterface))
		assertTrue(umlClass1.interfaceRealizations.contains(iFRealization1))	
		
		//Check Class 2 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass2 = modelElements.filter(Package).get(1).packagedElements.filter(Class).get(0)
		val iFRealizations2 = umlClass2.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations2, InterfaceRealization, 1) //There should be 1 InterfaceRealization
		val iFRealization2 = iFRealizations2.get(0) as InterfaceRealization
		assertTrue(iFRealization2.clients.contains(umlClass2))
		assertTrue(iFRealization2.contract == classInterface)
		assertTrue(iFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(iFRealization2))	
	}
	
	@Disabled
	@Test
	def void integrationTestAllCombined() {
		//Decide to create a Class for the first DataType:
		queueUserInteractionSelections(1)
		//Decide to create no corresponding Class for the second DataType:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModelAllCombined.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, Component, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, Package, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, Class, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, Class, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, Class, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, Interface, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, Interface, 0) //Class Package 2 should have no Interfaces
		
				
		//Check Class 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass = modelElements.filter(Package).get(1).packagedElements.filter(Class).get(0)
		val iFRealizations = umlClass.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealization, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(Package).get(1).packagedElements.filter(Interface).get(0)
		assertTrue(iFRealization.clients.contains(umlClass))
		assertTrue(iFRealization.contract == classInterface)
		assertTrue(iFRealization.suppliers.contains(classInterface))
		assertTrue(umlClass.interfaceRealizations.contains(iFRealization))
		
		//Check Class 2 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass2 = modelElements.filter(Package).get(1).packagedElements.filter(Class).get(0)
		val iFRealizations2 = umlClass2.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations2, InterfaceRealization, 1) //There should be 1 InterfaceRealization
		val iFRealization2 = iFRealizations2.get(0) as InterfaceRealization
		assertTrue(iFRealization2.clients.contains(umlClass2))
		assertTrue(iFRealization2.contract == classInterface)
		assertTrue(iFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(iFRealization2))	
		
				
		//Check DataType Class for Property & Operation:
		val dataTypeClass = modelElements.filter(Package).get(0).packagedElements.filter(Class).get(0)
		val ownedAttributes = dataTypeClass.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, Property, 1) //There should be 1 Property
		val ownedOperations = dataTypeClass.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, Operation, 1) //There should be 1 Operation
	}
	
	@Test
	def void integrationTestMatthiasSmallExampleComponentResult() {
		//This model was retrieved out of the BA PCM->Comp as the result of an integration
		
		//Decide to create a Class for the first DataType:
		queueUserInteractionSelections(1)
		//Decide to create a Class for the second DataType:
		queueUserInteractionSelections(1)
		//Decide to create a Class for the third DataType:
		queueUserInteractionSelections(1)
		
		integrationTest("Matthias_small_example_Component_Result.uml")	
		
		//Too complex for manual asserts, check result in junit-workspace	
	}
		
	@Test
	def void integrationTestCoCOME() {
		//This model originates out of the CoCoME project
		
		integrationTest("UMLCoCoME.uml")	
		
		//Too complex for manual asserts, check result in junit-workspace	
	}
	
}