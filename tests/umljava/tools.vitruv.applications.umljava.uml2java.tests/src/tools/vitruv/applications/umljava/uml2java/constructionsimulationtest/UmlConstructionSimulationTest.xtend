package tools.vitruv.applications.umljava.uml2java.constructionsimulationtest

import org.junit.Test
import tools.vitruv.applications.umljava.constructionsimulation.strategy.UmlIntegrationStrategy
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.apache.log4j.PropertyConfigurator

class UmlConstructionSimulationTest extends Uml2JavaTransformationTest {
	
	override protected setup() {
        PropertyConfigurator.configure("log4j.properties")
    }
    
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
    
    
    /**
     * @param modelPath path relative to the project root
     */
    def private void testUmlModel(String modelPath) {
    	val iStrategy = new UmlIntegrationStrategy
        val res = iStrategy.loadModel(modelPath)
        createAndSynchronizeModel("model/model.uml", res.allContents.head)
    }
    
	
}
