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
import org.junit.Test

import static org.junit.Assert.*

class Comp2ClassTest extends AbstractComp2ClassTest {
	private static val COMP_NAME = "TestUmlComp"
	private static val COMP_NAME2 = "TestUmlComp2"
	private static val DATATYPE_NAME = "TestDataType"
	private static val PROPERTY_NAME = "TestProperty"
	private static val OPERATION_NAME = "TestOperation"

	@Test
		public def void testModelCreation() {
			assertModelExists("model/" + MODEL_NAME + ".uml")
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTrue(umlModel instanceof Model)
			assertEquals(MODEL_NAME, (umlModel as Model).name)
	}
	
	/***********
	*Component:*
	***********/
	private def Component createComponent(String name) {
		val umlComponent = UMLFactory.eINSTANCE.createComponent()
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		return umlComponent
	}
	
	private def void assertTypeAndName(EObject umlObject, java.lang.Class<? extends NamedElement> umlType, String name) {	
		assertTrue(umlObject.class.isInstance(umlType) || umlObject.class.genericInterfaces.contains(umlType))
		//second condition encloses 'impl'-classes		
		assertEquals(name, (umlObject as NamedElement).name)
	}
	
	private def void assertClass(PackageableElement compElement, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTypeAndName(umlClass, Class, name)
	}
	
	private def void assertClassAndPackage(Component umlComp, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		assertEquals(2, correspondingElements.size) //Class & Package
		
		//Check class
		val umlClass = correspondingElements.filter(Class).get(0)
		
		assertTypeAndName(umlClass, Class, name)
		
		//Check Package
		val classPackage = (umlClass as Class).package
		assertEquals(name + " Package", classPackage.name)
		val packageOwnedTypes = classPackage.ownedTypes
		assertEquals(1, packageOwnedTypes.size)
		val classFromPackage = packageOwnedTypes.get(0)
		assertTypeAndName(classFromPackage, Class, name)
		
		//Check package correspondence 
		val classPackageFromCorrespondence = correspondingElements.filter(Package).get(0)		
		assertTypeAndName(classPackageFromCorrespondence, Package, name + " Package")
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
		//change name:
		umlComp.name = "New"
		saveAndSynchronizeChanges(rootElement)
		//check if rename happened in class & package:
		assertClassAndPackage(umlComp, "New")
    }
    
	@Test
    public def void testDeleteComponent() {
    	val umlComp = createComponent(COMP_NAME)	 
		saveAndSynchronizeChanges(umlComp)
		
		assertClassAndPackage(umlComp, COMP_NAME)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComp]).flatten
		val umlClass = correspondingElements.filter(Class).get(0)
		val classPackage = (umlClass as Class).package
		val classModel = (umlClass as Class).model
		
		//remove component		
		assertTrue(rootElement.packagedElements.contains(umlComp))
		umlComp.destroy()		
		assertFalse(rootElement.packagedElements.contains(umlComp))
		saveAndSynchronizeChanges(rootElement)
		
		//check if class exists:		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		assertFalse(classModel.packagedElements.contains(umlClass))
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
	**********/
	@Test
	public def void testCreateClassForDataType() {
		val compDataType = createDataType(DATATYPE_NAME, 1)
		saveAndSynchronizeChanges(compDataType)
		assertClass(compDataType, DATATYPE_NAME)		
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
		
		//change name:
		compProperty.name = "New"
		saveAndSynchronizeChanges(compProperty)
		
		//check if rename happened in class property:
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
		
		//change name:
		compOperation.name = "New"
		saveAndSynchronizeChanges(compOperation)
		
		//check if rename happened in class operation:
		val classOperation = umlClass.ownedOperations.get(0)
		assertEquals("New", (classOperation as Operation).name)
    }
	
}

