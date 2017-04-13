package tools.vitruv.applications.umljava.uml2java.constructionsimulationtest

import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import org.junit.Test
import tools.vitruv.applications.umljava.constructionsimulation.strategy.UmlIntegrationStrategy
import tools.vitruv.applications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest

class ConstructionTransformationTest extends AbstractUmlJavaTest {
	
	@Test
    def void testModel1() {
        testUmlModel("resources/model1.uml")
        //Noch keine assertions. Überprüfe manuell im JUnit-workspace.
    }
    
    @Test
    def void testModel2() {
    	testUmlModel("resources/model2.uml")
    }
    
    def private void testUmlModel(String modelPath) {
    	
    	val iStrategy = new UmlIntegrationStrategy
        val res = iStrategy.loadModel(modelPath)
        //val li = iStrategy.createChangeModels(null, res)
        createAndSynchronizeModel("model/model.uml", res.allContents.head)
        //triggerSynchronization(li)
    }
    override protected initializeTestModel() {
    	
    }

	
}