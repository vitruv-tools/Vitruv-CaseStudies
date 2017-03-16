package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model

class Class2CompTest extends AbstractClass2CompTest {
	private static val CLASS_NAME = "TestUmlClass"

	@Test
		public def void testModelCreation() {
			assertModelExists("model/" + MODEL_NAME + ".uml")
			val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
			assertEquals(1, correspondingElements.size)
			val umlModel = correspondingElements.get(0)
			assertTrue(umlModel instanceof Model)
			assertEquals(MODEL_NAME, (umlModel as Model).name)
	}

	@Test
	public def void testCreateClass() {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = CLASS_NAME
		rootElement.packagedElements += umlClass
		saveAndSynchronizeChanges(umlClass)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTrue(umlComponent instanceof Component)
		assertEquals(CLASS_NAME, (umlComponent as Component).name)
	}
	
	@Test
    public def void testRenameClass() {
    	//create class:
    	val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = "Before"
		rootElement.packagedElements += umlClass
		saveAndSynchronizeChanges(umlClass)
		//check if class correctly added:
		var correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten		
		assertEquals(1, correspondingElements.size);
		//change name:
		rootElement.packagedElements.get(0).name = "After"
		//check if rename successful:
		assertEquals("After", rootElement.packagedElements.get(0).name)
		//synchronize change
		saveAndSynchronizeChanges(umlClass)
		//check if rename happened in component:
		correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		val umlComponent = correspondingElements.get(0)
		assertTrue(umlComponent instanceof Component)
		assertEquals("After", (umlComponent as Component).name)
    }
	
}

