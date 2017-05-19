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
        userInteractor.addNextSelections(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        testUmlModel("resources/model1.uml")
        //Check manually in the JUnit-workspace.
    }
    
    @Test
    def void testMyProject() {
        testUmlModel("resources/MyProject.uml")
        //Check manually in the JUnit-workspace.
    }
    
    @Test
    def void testRootModelName() {
    	testUmlModel("resources/rootModelName.uml")
    	//Check manually in the JUnit-workspace.
    }
    
    
    def private void testUmlModel(String modelPath) {
    	val iStrategy = new UmlIntegrationStrategy
        val res = iStrategy.loadModel(modelPath)
        createAndSynchronizeModel("model/model.uml", res.allContents.head)
    }
    
	
}