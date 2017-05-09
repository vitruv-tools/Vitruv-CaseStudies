package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import java.util.Collections
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.internal.impl.ClassImpl
import org.eclipse.uml2.uml.internal.impl.ComponentImpl
import org.eclipse.uml2.uml.internal.impl.DataTypeImpl
import org.eclipse.uml2.uml.internal.impl.PackageImpl
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedIntegrationTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*
import org.eclipse.uml2.uml.internal.impl.InterfaceImpl
import org.eclipse.uml2.uml.internal.impl.InterfaceRealizationImpl
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.internal.impl.UsageImpl
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.internal.impl.PropertyImpl
import org.eclipse.uml2.uml.internal.impl.OperationImpl

class Class2CompIntegrationTest extends AbstractClass2CompTest {
	
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
	def void integrationTestWith2Classses() {
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModel2Classes.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class Package 1 should have 1 original Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 2 should have 1 original Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 new Components
	}
	
	@Test
	def void integrationTestWith2ClasssesWithoutPackage() {
		//Temporary test to check created changes while debugging
		val umlModel = integrationTest("TestModel2ClassesWithoutPackage.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 0) //There should be no original Packages
		assertCountOfTypeInList(modelElements, ClassImpl, 2) //There should be 2 original Classes
		assertCountOfTypeInList(modelElements, ComponentImpl, 0) //There should be no new Components
	}	

	@Test
	def void integrationTestWithClassAndDataType() {		
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)	
		//Decide to create a Class as a DataType (no Interactions)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModel1Class1Datatype.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 1) //There should be 1 new Component
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 1 new DataType
	}
	
	@Test
	def void integrationTestWithClassAndDataTypeWithPropertyAndOperation() {		
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)	
		//Decide to create a Class as a DataType (no Interactions)
		
		val umlModel = integrationTest("TestModel1DatatypeWithPropertyAndOperation.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 1) //There should be 1 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 0) //There should be no new Components
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 1 new DataType
		
		//Check new DataType for Property & Operation:
		val compDataType = modelElements.filter(DataTypeImpl).get(0)
		val ownedAttributes = compDataType.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, PropertyImpl, 1) //There should be 1 Property
		val ownedOperations = compDataType.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, OperationImpl, 1) //There should be 1 Operation
	}
	
	@Test
	def void integrationTestWithTestModel2ClassesAndRequiredAndProvidedInterface() {
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModel2ClassesWithInterfaceRequiredAndProvided.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class Package 1 should have 1 original Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 2 should have 1 original Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 new Components		
		assertCountOfTypeInPackage(modelElements, 0, InterfaceImpl, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 1, InterfaceImpl, 0) //Class Package 2 should have no Interfaces
		
		//Check Component 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlComp1 = modelElements.filter(ComponentImpl).get(0)
		val iFRealizations = umlComp1.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val compInterface = modelElements.filter(InterfaceImpl).get(0)
		assertTrue(iFRealization.clients.contains(umlComp1))
		assertTrue(iFRealization.contract == compInterface)
		assertTrue(iFRealization.suppliers.contains(compInterface))
		assertTrue(umlComp1.interfaceRealizations.contains(iFRealization))	
		
		//Check Component 2 for Usage and inspect if it's setup correctly:
		val umlComp2 = modelElements.filter(ComponentImpl).get(1)
		val usages = umlComp2.packagedElements.filter(UsageImpl).toList.map[e | e as NamedElement]
		assertCountOfTypeInList(usages, UsageImpl, 1) //There should be 1 Usage
		val compUsage = usages.get(0) as Usage
		assertTrue(compUsage.clients.contains(umlComp2))
		assertTrue(compUsage.suppliers.contains(compInterface))
		assertTrue(umlComp2.packagedElements.contains(compUsage))		
	}
	
	@Test
	def void integrationTestAllCombined() {		
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)	
		//Decide to create a Class as a DataType (no Interactions)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModelAllCombined.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements.map[e | e as NamedElement]
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 original Packages		
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 original Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 original Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 new Components		
		assertCountOfTypeInPackage(modelElements, 1, InterfaceImpl, 1) //Class Package 1 should have 1 Interface
		assertCountOfTypeInPackage(modelElements, 2, InterfaceImpl, 0) //Class Package 2 should have no Interfaces
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 2 new DataTypes
		
		//Check Component DataType for Property & Operation:
		val compDataType1 = modelElements.filter(DataTypeImpl).get(0)
		val ownedAttributes = compDataType1.ownedAttributes.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedAttributes, PropertyImpl, 1) //There should be 1 Property
		val ownedOperations = compDataType1.operations.map[e | e as NamedElement]
		assertCountOfTypeInList(ownedOperations, OperationImpl, 1) //There should be 1 Operation
				
		//Check Component 1 for InterfaceRealization and inspect if it's setup correctly:
		val umlComp1 = modelElements.filter(ComponentImpl).get(0)
		val iFRealizations = umlComp1.interfaceRealizations.map[e | e as NamedElement]
		assertCountOfTypeInList(iFRealizations, InterfaceRealizationImpl, 1) //There should be 1 InterfaceRealization
		val iFRealization = iFRealizations.get(0) as InterfaceRealization
		val compInterface = modelElements.filter(InterfaceImpl).get(0)
		assertTrue(iFRealization.clients.contains(umlComp1))
		assertTrue(iFRealization.contract == compInterface)
		assertTrue(iFRealization.suppliers.contains(compInterface))
		assertTrue(umlComp1.interfaceRealizations.contains(iFRealization))	
		
		//Check Component 2 for Usage and inspect if it's setup correctly:
		val umlComp2 = modelElements.filter(ComponentImpl).get(1)
		val usages = umlComp2.packagedElements.filter(UsageImpl).toList.map[e | e as NamedElement]
		assertCountOfTypeInList(usages, UsageImpl, 1) //There should be 1 Usage
		val compUsage = usages.get(0) as Usage
		assertTrue(compUsage.clients.contains(umlComp2))
		assertTrue(compUsage.suppliers.contains(compInterface))
		assertTrue(umlComp2.packagedElements.contains(compUsage))		
	}
	
}