package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import static org.junit.Assert.*
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.umlclassumlcomponents.class2comp.tests.Class2CompTestUtil

class Class2CompTest extends AbstractClass2CompTest {
	private static val CLASS_NAME = "TestUmlClass"
	private static val CLASS_NAME2 = "TestUmlClass2"
	private static val ATTR_NAME = "TestAttribute"

	@Test
		public def void testModelCreation() {
			assertModelExists("model/" + MODEL_NAME + ".uml")
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTrue(umlModel instanceof Model)
			assertEquals(MODEL_NAME, (umlModel as Model).name)
	}
	
	/*******
	*Class:*
	********/	
	
	private def org.eclipse.uml2.uml.Class createClass(String name, int createNoComponent, int createNoDatatype) {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		Class2CompTestUtil.queueUserInteractionSelections(createNoComponent, createNoDatatype) //Decide to create corresponding class or datatype
		umlClass.name = name
		rootElement.packagedElements += umlClass
		return umlClass
	}
	
	private def void checkComponent(org.eclipse.uml2.uml.Class umlClass, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTrue(umlComponent instanceof Component)
		assertEquals(name, (umlComponent as Component).name)
	}
	
	@Test
	public def void testCreateComponentForClass() {
		val umlClass = createClass(CLASS_NAME, 0, 1)
		saveAndSynchronizeWithInteractions(umlClass)
		checkComponent(umlClass, CLASS_NAME)
	}
	
	@Test
	public def void testCreate2ComponentFor2Class() {
		val umlClass1 = createClass(CLASS_NAME, 0, 1)
		val umlClass2 = createClass(CLASS_NAME2, 0, 1)
		saveAndSynchronizeWithInteractions(rootElement)
		//Check Class 1:
		checkComponent(umlClass1, CLASS_NAME)
		//Check Class 2:
		checkComponent(umlClass2, CLASS_NAME2)
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
		//change name:
		umlClass.name = "New" //rootElement.packagedElements.get(0).name = "New"
		saveAndSynchronizeWithInteractions(umlClass)
		//check if rename happened in component:
		checkComponent(umlClass, "New")
    }
    
	@Test
    public def void testDeleteClass() {
    	val umlClass = createClass(CLASS_NAME, 0, 1)	
		saveAndSynchronizeWithInteractions(umlClass)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		val umlComponent = correspondingElements.get(0)		
		//remove class		
		assertTrue(rootElement.packagedElements.contains(umlClass))
		umlClass.destroy()		
		assertFalse(rootElement.packagedElements.contains(umlClass))
		//rootElement.packagedElements.remove(umlClass)		
		saveAndSynchronizeWithInteractions(rootElement)
		//check if component exists:		
		assertFalse(rootElement.packagedElements.contains(umlComponent))
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
		assertTrue(compDataType instanceof org.eclipse.uml2.uml.DataType)
		assertEquals(CLASS_NAME, (compDataType as org.eclipse.uml2.uml.DataType).name)
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
	    val umlClassAttribute = createClass(CLASS_NAME2, 1, 1)
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
		//change name:
		
    }
    
    @Test
    public def void testDeleteClassAttribute() {
    	assertTrue(false)
		//change name:
		
    }*/
    
}

