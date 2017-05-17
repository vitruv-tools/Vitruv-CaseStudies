package tools.vitruv.applications.umljava.uml2java.constructionsimulationtest

import org.junit.Test
import tools.vitruv.applications.umljava.constructionsimulation.strategy.UmlIntegrationStrategy
import tools.vitruv.applications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.eclipse.uml2.uml.Model
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import java.io.File
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.uml2.uml.UMLPackage
import java.util.Map
import org.eclipse.uml2.uml.resource.XMI2UMLResource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.uml2.common.util.UML2Util
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl
import org.apache.log4j.PropertyConfigurator

class UmlConstructionSimulationTest extends Uml2JavaTransformationTest {
	
	override protected setup() {
        PropertyConfigurator.configure("log4j.properties")
    }
    
	@Test
    def void testModel1() {
        userInteractor.addNextSelections(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        testUmlModel("resources/model1.uml")
        //Noch keine assertions. Überprüfe manuell im JUnit-workspace.
    }
    
    @Test
    def void testModel2() {
    	testUmlModel("resources/model2.uml")
    }
    
    @Test
    def void testMode4() {
        userInteractor.addNextSelections(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        //testUmlModel("resources/datatypes.uml")
        testUmlModel("resources/ProjectManagement.uml")
    }
    
    def private void testUmlModel(String modelPath) {
    	
    	val iStrategy = new UmlIntegrationStrategy
        val res = iStrategy.loadModel(modelPath)
        createAndSynchronizeModel("model/model.uml", res.allContents.head)
    }
    
	
}