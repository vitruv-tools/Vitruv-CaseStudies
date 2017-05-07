package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import java.util.Collections
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.internal.impl.ClassImpl
import org.eclipse.uml2.uml.internal.impl.ComponentImpl
import org.eclipse.uml2.uml.internal.impl.DataTypeImpl
import org.eclipse.uml2.uml.internal.impl.PackageImpl
import org.junit.Test

import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedIntegrationTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

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
		val modelElements = umlModel.packagedElements
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class Package 1 should have 1 original Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 2 should have 1 original Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 new Components
	}
	
	@Test
	def void integrationTestWith2ClasssesWithoutPackage() {
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		//Decide to create an empty Package:
		queueUserInteractionSelections(0, 0)
		//Decide to create a Component for the Class:
		queueUserInteractionSelections(0)
		
		val umlModel = integrationTest("TestModel2ClassesWithoutPackage.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class Package 1 should have 1 original Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 2 should have 1 original Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 new Components
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
		val modelElements = umlModel.packagedElements
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 original Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInList(modelElements, ComponentImpl, 1) //There should be 2 new Components
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 1 new DataType
	}
}