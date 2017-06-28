package tools.vitruv.applications.umljava.uml2java.constructionsimulationtest

import org.junit.Test
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI

class UmlConstructionSimulationTest extends Uml2JavaTransformationTest {
	
	@Test
    def void testModel1() {
        //selections are needed when collection data types are created in java
        //due to 0..* associations. The user chooses a collection datatype.
        userInteractor.addNextSelections(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        testUmlModel("resources/model1.uml")
        //Check manually in the JUnit-workspace.
    }
    
    /**
     * Tests the uml model from "myproject" by suresh519
     * https://repository.genmymodel.com/suresh519/MyProject (12.5.2017)
     * 
     */
    @Test
    def void testMyProject() {
        testUmlModel("resources/MyProject.uml")
        //Check manually in the JUnit-workspace.
    }
    
    /** 
     * Tests the (generated) uml model from the logger project by orhan obut
     * https://github.com/orhanobut/logger (12.5.2017)
     * 
     */
    @Test
    def void testRootModelName() {
    	testUmlModel("resources/rootModelName.uml")
    	//Check manually in the JUnit-workspace.
    }
    
    
    def private void testUmlModel(String modelPath) {
		val resourceSet = new ResourceSetImpl();
		val res = resourceSet.getResource(URI.createFileURI(modelPath), true);
   	    createAndSynchronizeModel("model/model.uml", res.allContents.head)
    }
    
	
}
