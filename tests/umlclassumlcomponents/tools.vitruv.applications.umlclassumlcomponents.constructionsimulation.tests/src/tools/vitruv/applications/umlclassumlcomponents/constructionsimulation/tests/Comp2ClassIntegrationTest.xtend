package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

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

class Comp2ClassIntegrationTest extends AbstractConstructionTest {
		
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
		val modelElements = umlModel.packagedElements
		assertCountOfTypeInList(modelElements, ComponentImpl, 2) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 3) //There should be 3 new Packages
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 0) //Class DataTypes Package should have no Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
		assertCountOfTypeInPackage(modelElements, 2, ClassImpl, 1) //Class Package 2 should have 1 Class
	}
	

	@Test
	def void integrationTestWithComponentAndDatatype() {
		//Decide to create a Class for the DataType:
		queueUserInteractionSelections(1)
		
		val umlModel = integrationTest("TestModelWithDataType.uml")
		
		//Validate expected contents:
		val modelElements = umlModel.packagedElements
		assertCountOfTypeInList(modelElements, ComponentImpl, 1) //There should be 2 original Components
		assertCountOfTypeInList(modelElements, PackageImpl, 2) //There should be 2 new Packages
		assertCountOfTypeInList(modelElements, DataTypeImpl, 1) //There should be 1 original DataType
		assertCountOfTypeInPackage(modelElements, 0, ClassImpl, 1) //Class DataTypes Package should have 1 Class
		assertCountOfTypeInPackage(modelElements, 1, ClassImpl, 1) //Class Package 1 should have 1 Class
	}
}