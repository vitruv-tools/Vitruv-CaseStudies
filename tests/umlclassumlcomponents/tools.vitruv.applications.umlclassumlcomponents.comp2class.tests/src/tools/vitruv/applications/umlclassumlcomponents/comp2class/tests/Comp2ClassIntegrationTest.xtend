package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import java.util.Collections
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.internal.impl.ClassImpl
import org.eclipse.uml2.uml.internal.impl.ComponentImpl
import org.eclipse.uml2.uml.internal.impl.DataTypeImpl
import org.eclipse.uml2.uml.internal.impl.InterfaceImpl
import org.eclipse.uml2.uml.internal.impl.InterfaceRealizationImpl
import org.eclipse.uml2.uml.internal.impl.OperationImpl
import org.eclipse.uml2.uml.internal.impl.PackageImpl
import org.eclipse.uml2.uml.internal.impl.PropertyImpl
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedIntegrationTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

class Comp2ClassIntegrationTest extends AbstractComp2ClassTest {
	
	override protected setup() {
		//Don't initialize a model here, as we are loading and creating one
	}
	
	public def Model integrationTest(String fileName) {		
		val Resource inputResource = getTestModelResource(fileName)
		sendCollectedUserInteractionSelections(this.userInteractor)
		createAndSynchronizeModel(OUTPUT_NAME, inputResource.allContents.head)
		
		val allCorr = correspondenceModel.allCorrespondences.get(0)
		val umlModel = allCorr.getAs().get(0)
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
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 Class
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
		assertCountOfTypeInList(modelElements, ComponentImpl, 1) //There should be 1 original Component
		assertCountOfTypeInList(modelElements, DataTypeImpl, 2) //There should be 2 original DataTypes
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 2) //Class DataTypes Package should have 2 Classes
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
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
		assertCountOfTypeInList(modelElements, ComponentImpl, 1) //There should be 1 original Components
		assertCountOfTypeInList(modelElements, DataTypeImpl, 2) //There should be 2 original DataTypes
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
	}
	
	@Test
	def void integrationTestWithDataTypeWithPropertyAndOperation() {
		//Decide to create a Class for the DataType:
		queueUserInteractionSelections(1)
		
		val umlModel = integrationTest("TestModelDataTypeWithPropertyAndOperation.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 1 original DataType
		assertCountOfTypeInList(modelElements, PackageImpl, 1) //There should be 1 new Package
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		
		//Check new Class for Property & Operation:
		val dataTypeClass = modelElements.filter(PackageImpl).get(0).packagedElements.filter(ClassImpl).get(0)
		val ownedAttributes = dataTypeClass.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, PropertyImpl, 1) //There should be 1 Property
		val ownedOperations = dataTypeClass.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, OperationImpl, 1) //There should be 1 Operation
	}
	
	@Test
	def void integrationTestWith2ComponentsAndRealizedInterface() {
		val umlModel = integrationTest("TestModel2ComponentsWithRealizedInterface.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, InterfaceImpl, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, InterfaceImpl, 0) //Class Package 2 should have no Interfaces
		
		//Check new Class for InterfaceRealization and inspect if it's setup correctly:
		val umlClass = modelElements.filter(PackageImpl).get(1).packagedElements.filter(ClassImpl).get(0)
		val iFRealizations = umlClass.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(PackageImpl).get(1).packagedElements.filter(InterfaceImpl).get(0)
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
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, InterfaceImpl, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, InterfaceImpl, 0) //Class Package 2 should have no Interfaces
		
		//Check Class 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass1 = modelElements.filter(PackageImpl).get(1).packagedElements.filter(ClassImpl).get(0)
		val iFRealizations1 = umlClass1.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations1, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization1 = iFRealizations1.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(PackageImpl).get(1).packagedElements.filter(InterfaceImpl).get(0)
		assertTrue(iFRealization1.clients.contains(umlClass1))
		assertTrue(iFRealization1.contract == classInterface)
		assertTrue(iFRealization1.suppliers.contains(classInterface))
		assertTrue(umlClass1.interfaceRealizations.contains(iFRealization1))	
		
		//Check Class 2 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass2 = modelElements.filter(PackageImpl).get(1).packagedElements.filter(ClassImpl).get(0)
		val iFRealizations2 = umlClass2.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations2, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization2 = iFRealizations2.get(0) as InterfaceRealization
		assertTrue(iFRealization2.clients.contains(umlClass2))
		assertTrue(iFRealization2.contract == classInterface)
		assertTrue(iFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(iFRealization2))	
	}
		
	@Test
	def void integrationTestAllCombined() {
		//Decide to create a Class for the first DataType:
		queueUserInteractionSelections(1)
		//Decide to create no corresponding Class for the second DataType:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModelAllCombined.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, InterfaceImpl, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, InterfaceImpl, 0) //Class Package 2 should have no Interfaces
		
				
		//Check Class 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass = modelElements.filter(PackageImpl).get(1).packagedElements.filter(ClassImpl).get(0)
		val iFRealizations = umlClass.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val classInterface = modelElements.filter(PackageImpl).get(1).packagedElements.filter(InterfaceImpl).get(0)
		assertTrue(iFRealization.clients.contains(umlClass))
		assertTrue(iFRealization.contract == classInterface)
		assertTrue(iFRealization.suppliers.contains(classInterface))
		assertTrue(umlClass.interfaceRealizations.contains(iFRealization))
		
		//Check Class 2 for InterfaceRealization and inspect if it's setup correctly:
		val umlClass2 = modelElements.filter(PackageImpl).get(1).packagedElements.filter(ClassImpl).get(0)
		val iFRealizations2 = umlClass2.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations2, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization2 = iFRealizations2.get(0) as InterfaceRealization
		assertTrue(iFRealization2.clients.contains(umlClass2))
		assertTrue(iFRealization2.contract == classInterface)
		assertTrue(iFRealization2.suppliers.contains(classInterface))
		assertTrue(umlClass2.interfaceRealizations.contains(iFRealization2))	
		
				
		//Check DataType Class for Property & Operation:
		val dataTypeClass = modelElements.filter(PackageImpl).get(0).packagedElements.filter(ClassImpl).get(0)
		val ownedAttributes = dataTypeClass.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, PropertyImpl, 1) //There should be 1 Property
		val ownedOperations = dataTypeClass.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, OperationImpl, 1) //There should be 1 Operation
	}
}