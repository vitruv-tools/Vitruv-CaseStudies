package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.eclipse.uml2.uml.Package

import static org.junit.Assert.*
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.VisibilityKind

class Class2CompTest extends AbstractClass2CompTest {
	private static val CLASS_NAME = "TestUmlClass"
	private static val CLASS_NAME2 = "TestUmlClass2"
	private static val ATTR_NAME = "TestAttribute"
	private static val PACKAGE_NAME = "TestPackage"
	private static val PACKAGE_NAME2 = "TestPackage2"
	private static val COMPONENT_NAME = "TestComponent"
	
	/*******
	*Model:*
	********/
	//Model creation currently unused due to usage of one singular Model
	
	@Test
		public def void testModelCreation() {
			assertModelExists("model/" + MODEL_NAME + ".uml")
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTypeAndName(umlModel, Model, MODEL_NAME)
	}
	
	
	/***************
	*Assert Helper:*
	****************/		
	
	private def void assertTypeAndName(EObject umlObject, java.lang.Class<? extends NamedElement> umlType, String name) {	
		assertTrue(umlObject.class.isInstance(umlType) || umlObject.class.genericInterfaces.contains(umlType))
		//Second condition encloses 'impl'-Classes		
		assertEquals(name, (umlObject as NamedElement).name)
	}
	
	private def void assertComponent(Class umlClass, String name) {
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
	*Class:*
	********/	
		
	private def Class createClass(String name, int createNoNewElement, int createNoDatatype) {
		val classPackage = createPackage(name + " Package")
		return createClass(name, classPackage, createNoNewElement, createNoDatatype)
	}
	
	private def Class createClass(String name, Package classPackage, int createNoNewElement, int createNoDatatype) {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = name
		classPackage.packagedElements += umlClass
		
		//Decide whether to create corresponding Component, DataType or nothing:
		if (createNoNewElement == 1)
			Class2CompTestUtil.queueUserInteractionSelections(createNoNewElement)
		else
			Class2CompTestUtil.queueUserInteractionSelections(createNoNewElement, createNoDatatype) 
				
		return umlClass
	}
	
	private def Package createPackage(String name) {
		createPackage(name, 1, 0)
	}
	
	private def Package createPackage(String name, int linkToNoComponent, int componentSelection) {
		val classPackage = UMLFactory.eINSTANCE.createPackage()
		classPackage.name = name + " Package"
		rootElement.packagedElements += classPackage
		
		//Decide whether to link this Package to an existing Component
		if (linkToNoComponent == 1)
			Class2CompTestUtil.queueUserInteractionSelections(linkToNoComponent)
		else
			Class2CompTestUtil.queueUserInteractionSelections(linkToNoComponent, componentSelection)
		
		return classPackage
	}
	
	
	@Test
	public def void testCreateComponentForClass() {
		val umlClass = createClass(CLASS_NAME, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		
		assertComponent(umlClass, CLASS_NAME)
	}
	
	@Test
	public def void testCreate2ComponentsFor2ClassesInSamePackage() {
		val classPackage = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage, 0, 1)
		val umlClass2 = createClass(CLASS_NAME2, classPackage, 0, 1)
		saveAndSynchronizeWithInteractions(rootElement)
		
		//First Class should have Component
		assertComponent(umlClass1, CLASS_NAME)
		
		//Second Class should receive no Component
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass2]).flatten
		assertEquals(0, correspondingElements.size)
	}
	
	@Test
	public def void testCreate2ComponentsFor2ClassesInDifferentPackages() {
		val classPackage1 = createPackage(CLASS_NAME)
		val umlClass1 = createClass(CLASS_NAME, classPackage1, 0, 1)
		val classPackage2 = createPackage(CLASS_NAME2)
		val umlClass2 = createClass(CLASS_NAME2, classPackage2, 0, 1)
		saveAndSynchronizeWithInteractions(rootElement)
		
		//Both Classes should have corresponding Components
		assertComponent(umlClass1, CLASS_NAME)
		assertPackageLinkedToComponent(classPackage1, CLASS_NAME)
		assertComponent(umlClass2, CLASS_NAME2)
		assertPackageLinkedToComponent(classPackage2, CLASS_NAME2)
	}
	
	@Test
	public def void testCreateNoComponentForClass() {
		val umlClass = createClass(CLASS_NAME, 1, 1)
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
    	val umlClass = createClass("Old", 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		
		//Change name:
		umlClass.name = "New"
		saveAndSynchronizeWithInteractions(umlClass)
		
		//Check if rename happened in Component:
		assertComponent(umlClass, "New")
    }
    
	@Test
    public def void testDeleteClass() {
    	val classPackage = createPackage(CLASS_NAME)
    	val umlClass = createClass(CLASS_NAME, classPackage, 0, 1)	
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
		val umlClass = createClass(CLASS_NAME, classPackage, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		
		assertComponent(umlClass, CLASS_NAME)
		assertPackageLinkedToComponent(classPackage, CLASS_NAME)
	}
		   
	    	
   	/**********
	*Packages:*
	***********/
	
	@Test
	public def void testCreatePackageWithoutLink() {
		//Create Component without Package:
		val umlClass = createClass(CLASS_NAME, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		assertComponent(umlClass, CLASS_NAME)
		
		val classPackage = createPackage(CLASS_NAME)		
		saveAndSynchronizeWithInteractions(rootElement)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(0, correspondingElements.size)
	}	
	
	@Test
	public def void testCreatePackageWithLinkSuccess() {
		//Create Component without Package:
		val umlClass = createClass(CLASS_NAME, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		assertComponent(umlClass, CLASS_NAME)
		
		//Create Package and try to link it:
		val classPackage = createPackage(PACKAGE_NAME2, 0, 0)
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertPackageLinkedToComponent(classPackage, COMPONENT_NAME)		
	}
		
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
	
	@Test
	public def void testCreatePackageWithLinkButComponentHasLink() {
		//Create a Class with a corresponding Component:
		val classPackageOld = createPackage(CLASS_NAME)
		val umlClass = createClass(CLASS_NAME, classPackageOld, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		assertComponent(umlClass, CLASS_NAME)		
		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
		
		//Create a new Package and try to link it:
		val classPackageNew = createPackage(PACKAGE_NAME2, 0, 0)
		saveAndSynchronizeWithInteractions(rootElement)
		
		//No new link should have been created
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackageNew]).flatten
		assertEquals(0, correspondingElements.size)
		
		//Old link should still exist
		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
	}	
    	
    	
   	/***********
	*DataTypes:*
	************/
	
    @Test
    public def void testCreateDataTypeForClass() {
    	val umlClass = createClass(CLASS_NAME, 0, 0)
		saveAndSynchronizeWithInteractions(umlClass)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val compDataType = correspondingElements.get(0)
		assertTypeAndName(compDataType, DataType, CLASS_NAME)
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
		val umlClass = createClass(CLASS_NAME, 0, 0)
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
	
	
   	/***********
	*Attributes:*
	************/
	
	//TODO all broken, even needed?
	/*@Test
	public def void testCreateClassAttribute() {
	    val umlClassAttribute = createClass(CLASS_NAME2, 1, 1) //TODO change class to attribute
	    saveAndSynchronizeWithInteractions(umlClassAttribute)
	    val umlClass = createClass(CLASS_NAME, 0, 1)
	    umlClass.createOwnedAttribute(ATTR_NAME, umlClassAttribute)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		saveAndSynchronizeWithInteractions(umlClass)
		
		//get corresponding component:
		val umlComponent = (correspondingElements.get(0) as Component)		
		assertEquals(1, umlComponent.attributes.size)
		val umlComponentAttribute = umlComponent.attributes.get(0)
		assertNotNull(umlComponentAttribute)
		assertEquals(CLASS_NAME2, umlComponentAttribute.name)
	}
	
	@Test
    public def void testRenameClassAttribute() {
    	assertTrue(false)
		//Change name:
		//TODO implement
    }
    
    @Test
    public def void testDeleteClassAttribute() {
    	assertTrue(false)
		//TODO implement
    }*/
    
}

