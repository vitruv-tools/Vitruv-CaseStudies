package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

class Comp2ClassTest extends AbstractComp2ClassTest {
	private static val COMP_NAME = "TestUmlComp"
	private static val COMP_NAME2 = "TestUmlComp2"
	private static val DATATYPE_NAME = "TestDataType"
	private static val PROPERTY_NAME = "TestProperty"
	private static val OPERATION_NAME = "TestOperation"

	
	/***************
	*Assert Helper:*
	****************/	

	private def void assertClass(PackageableElement compElement, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTypeAndName(umlClass, Class, name)
	}
	
	private def void assertClassAndPackage(Component umlComp, String name) {
		assertClassAndPackage(umlComp, name, name + PACKAGE_SUFFIX, true)
	}
	
	private def void assertClassAndPackage(PackageableElement compElement, String name, String packageName, Boolean packageCorrCheck) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten
		
		//Check Class
		val umlClass = correspondingElements.filter(Class).get(0)
		
		assertTypeAndName(umlClass, Class, name)
		
		//Check Package
		val classPackage = (umlClass as Class).package
		assertEquals(packageName, classPackage.name)
		val packageOwnedTypes = classPackage.ownedTypes
		assertEquals(1, packageOwnedTypes.size)
		val classFromPackage = packageOwnedTypes.get(0)
		assertTypeAndName(classFromPackage, Class, name)
		
		if (packageCorrCheck) {
			//Check Package correspondence 
			val classPackageFromCorrespondence = correspondingElements.filter(Package).get(0)		
			assertTypeAndName(classPackageFromCorrespondence, Package, name + PACKAGE_SUFFIX)		
		}
	}	
	
	/*******
	*Model:*
	********/	
	
	@Test
	//This test covers usage of one as well as two Models
	public def void testModelCreation() {
		//Check Model:
		assertModelExists(FOLDER_NAME + MODEL_NAME + "." + MODEL_FILE_EXTENSION)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		val classModel = correspondingElements.filter(Model).get(0)
		assertTypeAndName(classModel, Model, MODEL_NAME)
		
		//Check DataType Package
		val packagedElements = (classModel as Model).packagedElements
		assertEquals(1, packagedElements.size)
		val dataTypePackage = packagedElements.get(0)
		assertTypeAndName(dataTypePackage, Package, CLASS_DATATYPES_PACKAGE_NAME)
//		val dataTypePackageFromCorr = correspondingElements.filter(Package).get(0)
//		assertTypeAndName(dataTypePackageFromCorr, Package, CLASS_DATATYPES_PACKAGE_NAME)
	}
	
		
	/***********
	*Component:*
	************/
	
	private def Component createComponent(String name) {
		val umlComponent = UMLFactory.eINSTANCE.createComponent()
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		return umlComponent
	}

	
	@Test
	public def void testCreateClassForComponent() {
		val umlComp = createComponent(COMP_NAME)
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
	}
	
	@Test
    public def void testRenameComponent() {
    	val umlComp = createComponent("Old")
		saveAndSynchronizeChanges(rootElement)
		
		//Change name:
		umlComp.name = "New"
		saveAndSynchronizeChanges(rootElement)
		
		//Check if rename happened in Class & Package:
		assertClassAndPackage(umlComp, "New")
    }
    
	@Test
    public def void testDeleteComponentWithPackage() {
    	val umlComp = createComponent(COMP_NAME)	 
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		val umlClass = correspondingElements.filter(Class).get(0)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeChanges(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
    }
	
	@Test
    public def void testDeleteComponentWithoutPackage() {
    	val umlComp = createComponent(COMP_NAME)    	
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		val umlClass = correspondingElements.filter(Class).get(0)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Package gets more members:
    	val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		classPackage.packagedElements += umlClass2 //TODO fix, illegal write. transaction?
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()
		queueUserInteractionSelections(1) //Decide to not delete Package
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeChanges(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertTrue(classModel.packagedElements.contains(classPackage))
		assertTrue(classPackage.packagedElements.contains(umlClass2))		
    }
    
	@Test
    public def void testDeleteComponentWithPackageAndContents() {
    	val umlComp = createComponent(COMP_NAME)    	
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		val umlClass = correspondingElements.filter(Class).get(0)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//Package gets more members:
    	val umlClass2 = UMLFactory.eINSTANCE.createClass()
		umlClass2.name = CLASS_NAME2
		//Add new Class to Package in transaction:
//		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(() -> {
//    		classPackage.packagedElements += umlClass2 //TODO More here? What else?
//		}, TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resource.getResourceSet()); //TODO How?
		classPackage.packagedElements += umlClass2 //TODO fix, illegal write. use transaction
		
		//Remove Component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()
		queueUserInteractionSelections(0) //Decide to delete Package and Contents
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeChanges(rootElement)
		
		//Check if Class or Package exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(classPackage))
		assertFalse(classModel.packagedElements.contains(umlClass2))		
    }
    
	@Test
	public def void testCreate2ClassFor2Component() {
		val umlComp1 = createComponent(COMP_NAME)
		val umlComp2 = createComponent(COMP_NAME2)
		saveAndSynchronizeChanges(rootElement)
		assertClassAndPackage(umlComp1, COMP_NAME)
		assertClassAndPackage(umlComp2, COMP_NAME2)
	}
	
	
    /**********
	*Datatype:*
	***********/
	
	@Test
	public def void testCreateClassForDataType() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		assertClassAndPackage(compDataType, DATATYPE_NAME, CLASS_DATATYPES_PACKAGE_NAME, false)			
	}
	
    private def DataType createDataType(String name, int createClass) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		this.userInteractor.addNextSelections(createClass) //Decide to create corresponding class
		dataType.name = name
		rootElement.packagedElements += dataType
		return dataType
	}
	
	@Test
	public def void testAddPropertyToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = PROPERTY_NAME
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		
		assertEquals(1, umlClass.ownedAttributes.size)		
		val classProperty = umlClass.ownedAttributes.get(0)
		assertTypeAndName(classProperty, Property, PROPERTY_NAME)				
	}
	
	@Test
    public def void testRenameDataTypeProperty() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compProperty = UMLFactory.eINSTANCE.createProperty()
		compProperty.name = "Old"
		compDataType.ownedAttributes += compProperty
		saveAndSynchronizeChanges(compDataType)
		
		//Change name:
		compProperty.name = "New"
		saveAndSynchronizeChanges(compProperty)
		
		//Check if rename happened in Class Property:
		val classProperty = umlClass.ownedAttributes.get(0)
		assertEquals("New", (classProperty as Property).name)
    }
	
	@Test
	public def void testAddOperationToDataType(){
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = OPERATION_NAME
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)		
		
		assertEquals(1, umlClass.ownedOperations.size)		
		val classOperation = umlClass.ownedOperations.get(0)
		assertTypeAndName(classOperation, Operation, OPERATION_NAME)			
	}
		
	@Test
    public def void testRenameDataTypeOperation() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compDataType]).flatten		
		val umlClass = (correspondingElements.get(0) as Class)
		val compOperation = UMLFactory.eINSTANCE.createOperation()
		compOperation.name = "Old"
		compDataType.ownedOperations += compOperation
		saveAndSynchronizeChanges(compDataType)
		
		//Change name:
		compOperation.name = "New"
		saveAndSynchronizeChanges(compOperation)
		
		//Check if rename happened in Class Operation:
		val classOperation = umlClass.ownedOperations.get(0)
		assertEquals("New", (classOperation as Operation).name)
    }
		   
		    	
   	/***********
	*Visibility:*
	************/
	
	private def void assertVisibility(NamedElement element) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[element]).flatten
		for (EObject corrElement : correspondingElements) {
			assertTrue((corrElement as NamedElement).visibility == element.visibility)
		} 
		
	}
	
	@Test
	public def void testVisibilityChange() {
		val umlComp = createComponent(COMP_NAME)
		umlComp.visibility = VisibilityKind.PRIVATE_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
		
		umlComp.visibility = VisibilityKind.PUBLIC_LITERAL
		saveAndSynchronizeChanges(umlComp)		
		assertVisibility(umlComp)
	}			
}

