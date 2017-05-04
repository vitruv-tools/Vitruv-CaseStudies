package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

class Class2CompTest extends AbstractClass2CompTest {

	/***************
	*Assert Helper:*
	****************/		

	private def void assertComponentForClass(Class umlClass, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, name)
	}	
	
	private def void assertPackageLinkedToComponent(Package classPackage, String componentName) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, componentName)
	}
	
		
	/*******
	*Model:*
	********/
	//Model creation currently unused due to usage of one singular Model
	
	@Test
		public def void testModelCreation() {
			assertModelExists(FOLDER_NAME + MODEL_NAME + "." + MODEL_FILE_EXTENSION)
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTypeAndName(umlModel, Model, MODEL_NAME)
	}
	
	
	/*****************
	*Creation Helper:*
	******************/		

	private def Class createClassWithoutInteraction(String name) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClassWithoutInteraction(name, classPackage)
	}
	
	private def Class createClassWithoutInteraction(String name, Package classPackage) {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = name
		classPackage.packagedElements += umlClass
	
		return umlClass
	}
			
	private def Class createClass(String name, int createNoComponent) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClass(name, classPackage, createNoComponent)
	}
	
	private def Class createClass(String name, Package classPackage, int createNoComponent) {
		val umlClass = createClassWithoutInteraction(name, classPackage)		
				
		//Decide whether to create corresponding Component or not:
		queueUserInteractionSelections(createNoComponent) 
		
		return umlClass
	} 
	
	private def Package createPackage(String name) {
		createPackage(name, 1, 0)
	}
	
	private def Package createPackage(String name, int linkToNoComponent, int componentSelection) {
		val classPackage = UMLFactory.eINSTANCE.createPackage()
		classPackage.name = name + PACKAGE_SUFFIX
		rootElement.packagedElements += classPackage
		
		//Decide whether to link this Package to an existing Component
		if (linkToNoComponent == 1)
			queueUserInteractionSelections(linkToNoComponent)
		else
			queueUserInteractionSelections(linkToNoComponent, componentSelection)
		
		return classPackage
	}
	
	
	/*******
	*Class:*
	********/	
	
	@Test
	public def void testCreateComponentForClass() {
		val umlClass = createClass(CLASS_NAME, 0)
		saveAndSynchronizeWithInteractions(umlClass)
		
		assertComponentForClass(umlClass, CLASS_NAME)
	}
	
	@Test
	public def void testCreate2ComponentsFor2ClassesInSamePackage() {
		val classPackage = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage, 0)
		val umlClass2 = createClassWithoutInteraction(CLASS_NAME2, classPackage)
		saveAndSynchronizeWithInteractions(rootElement)
		
		//First Class should have Component
		assertComponentForClass(umlClass1, CLASS_NAME)
		
		//Second Class should receive no Component
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass2]).flatten
		assertEquals(0, correspondingElements.size)
	}
	
	@Test
	public def void testCreate2ComponentsFor2ClassesInDifferentPackages() {
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0)
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Both Classes should have corresponding Components
		assertComponentForClass(umlClass1, CLASS_NAME)
		assertPackageLinkedToComponent(classPackage1, CLASS_NAME)
		assertComponentForClass(umlClass2, CLASS_NAME2)
		assertPackageLinkedToComponent(classPackage2, CLASS_NAME2)
	}
	
	@Test
	public def void testCreateNoComponentForClass() {
		val umlClass = createClass(CLASS_NAME, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(0, correspondingElements.size)
		assertFalse(rootElement.packagedElements.contains(Component))
	}
	
	//TODO: Currently unused, remove?
	/*@Test
	public def void testCreateComponentForClassPackageCheck() {
		val umlClass = createClass(CLASS_NAME, 0, 1)
		umlClass.package.name = "default"
		saveAndSynchronizeWithInteractions(umlClass)
		 
		checkComponent(umlClass, CLASS_NAME)
		assertEquals(CLASS_NAME + "-Package", umlClass.package.name)
	}*/

	@Test
    public def void testRenameClass() {
    	val umlClass = createClass("Old", 0)
		saveAndSynchronizeWithInteractions(umlClass)
		
		//Change name:
		umlClass.name = "New"
		saveAndSynchronizeWithInteractions(umlClass)
		
		//Check if rename happened in Component:
		assertComponentForClass(umlClass, "New")
    }
    
	@Test
    public def void testDeleteClass() {
    	val classPackage = createPackage(CLASS_NAME)
    	val umlClass = createClass(CLASS_NAME, classPackage, 0)	
		saveAndSynchronizeWithInteractions(umlClass)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		val umlComponent = correspondingElements.get(0)		
		
		//Remove Class		
		assertTrue(classPackage.packagedElements.contains(umlClass))
		umlClass.destroy()		
		assertFalse(classPackage.packagedElements.contains(umlClass))
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Check if Component still exists:		
		assertFalse(classPackage.packagedElements.contains(umlComponent))
    }    
    
    @Test
	public def void testCreateComponentForClassInPackage() {
		val classPackage = createPackage(CLASS_NAME)
		val umlClass = createClass(CLASS_NAME, classPackage, 0)
		saveAndSynchronizeWithInteractions(umlClass)
		
		assertComponentForClass(umlClass, CLASS_NAME)
		assertPackageLinkedToComponent(classPackage, CLASS_NAME)
	}
		   
	    	
   	/**********
	*Packages:*
	***********/
	
	@Test
	public def void testCreatePackageWithoutLink() {
		//Create Component without Package:
		val umlClass = createClass(CLASS_NAME, 0)
		saveAndSynchronizeWithInteractions(umlClass)
		assertComponentForClass(umlClass, CLASS_NAME)
		
		val classPackage = createPackage(CLASS_NAME)		
		saveAndSynchronizeWithInteractions(rootElement)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(0, correspondingElements.size)
	}
		
	@Test
	public def void testPackageIllegalRename1() {
		//Create Package and try to rename to DataType-Package name:
		val classPackage = createPackage(PACKAGE_NAME)				
		saveAndSynchronizeWithInteractions(rootElement)
		classPackage.name = CLASS_DATATYPES_PACKAGE
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertFalse(classPackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
	
	@Test
	public def void testPackageIllegalRename2() {
		//Create Package and try to overwrite the pre-existing DataType-Package name:
		val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE)
		val classPackage = createPackage(PACKAGE_NAME)				
		saveAndSynchronizeWithInteractions(rootElement)
		classPackage.name = CLASS_DATATYPES_PACKAGE
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertFalse(classPackage.name == CLASS_DATATYPES_PACKAGE_NAME)
		assertTrue(dataTypePackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
			
	@Test
	public def void testPackageIllegalRename3() {
		//Create Package with legal DataType-Package name, then try to rename it:
		val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE)
				
		//Try to rename DataType-Package:
		dataTypePackage.name = PACKAGE_NAME
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertTrue(dataTypePackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
	
//	@Test
//	public def void testCreatePackageWithLinkSuccess() { //TODO Fix later with new package linking
//		//Create Component without Package: //TODO not possible anymore
//		val umlClass = createClass(CLASS_NAME, 0)
//		saveAndSynchronizeWithInteractions(umlClass)
//		assertComponent(umlClass, CLASS_NAME)
//		
//		//Create Package and try to link it:
//		val classPackage = createPackage(PACKAGE_NAME2, 0, 0)
//		saveAndSynchronizeWithInteractions(rootElement)
//		
//		assertPackageLinkedToComponent(classPackage, COMPONENT_NAME)		
//	}
		
	//TODO implement this
	//@Test
	//public def void testCreatePackageWithLinkSuccessMultiOptions() {
	//	assertTrue(false)
	//}
	
	@Test
	public def void testCreatePackageWithLinkButNoComponent() {
		//Create Package and try to link it no non-existing Component
		val classPackage = createPackage(PACKAGE_NAME, 0, 0)
		saveAndSynchronizeWithInteractions(classPackage)
		
		//No links should exist:
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(0, correspondingElements.size)
	}
	
//	@Test
//	public def void testCreatePackageWithLinkButComponentHasLink() { //TODO Fix later with new package linking
//		//Create a Class with a corresponding Component:
//		val classPackageOld = createPackage(CLASS_NAME)
//		val umlClass = createClass(CLASS_NAME, classPackageOld, 0)
//		saveAndSynchronizeWithInteractions(umlClass)
//		assertComponent(umlClass, CLASS_NAME)		
//		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
//		
//		//Create a new Package and try to link it:
//		val classPackageNew = createPackage(PACKAGE_NAME2, 0, 0)
//		saveAndSynchronizeWithInteractions(rootElement)
//		
//		//No new link should have been created
//		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackageNew]).flatten
//		assertEquals(0, correspondingElements.size)
//		
//		//Old link should still exist
//		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
//	}	
    	
    	
   	/***********
	*DataTypes:*
	************/
	
    @Test
    public def void testCreateDataTypeForClass() {
    	val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE, 0, 0)
    	val umlClass = createClassWithoutInteraction(CLASS_NAME, dataTypePackage)
		saveAndSynchronizeWithInteractions(umlClass)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val compDataType = correspondingElements.get(0)
		assertTypeAndName(compDataType, DataType, CLASS_NAME)
	}
	
	
	//TODO further tests with tags, property ect
	    	
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
		val umlClass = createClass(CLASS_NAME, 0)
		umlClass.visibility = VisibilityKind.PRIVATE_LITERAL
		saveAndSynchronizeWithInteractions(umlClass)		
		assertVisibility(umlClass)
		
		umlClass.visibility = VisibilityKind.PUBLIC_LITERAL
		saveAndSynchronizeWithInteractions(umlClass)		
		assertVisibility(umlClass)
	}	
		
		
   	/***********
	*Interfaces:*
	************/
	
	//TODO Interface tests
	
    
}

