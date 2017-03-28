package tools.vitruv.applications.umljava.constructionsimulation.tests

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test
import org.junit.runner.Description
import tools.vitruv.applications.umljava.constructionsimulation.strategy.UmlIntegrationStrategy
import tools.vitruv.applications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.modelsynchronization.ChangePropagationAbortCause
import tools.vitruv.framework.tests.TestUserInteractor
import tools.vitruv.framework.tests.VitruviusCasestudyTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

class ConstructionSimulationTest extends VitruviusCasestudyTest {
    
    @Test
    def void test() {
        val iStrategy = new UmlIntegrationStrategy
        val res = iStrategy.loadModel("resources/model.uml")
        val li = iStrategy.createChangeModels(null, res)
        createAndSynchronizeModel("model/model.uml", res.allContents.head)
        triggerSynchronization(li)
        //Noch keine assertions. Überprüfe manuell im JUnit-workspace.
    }
    
    override protected beforeTest(Description description) {
        super.beforeTest(description);
        this.testUserInteractor = new TestUserInteractor();
        this.getVirtualModel().setUserInteractor(this.testUserInteractor);
        this.resourceSet = new ResourceSetImpl();
    }
    
    override protected createChangePropagationSpecifications() {
        return #[new UmlToJavaChangePropagationSpecification()]; 
    }
    
    override protected createMetamodels() {
        return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
        
    }
    def protected void triggerSynchronization(List<VitruviusChange> changeList) {
        for (VitruviusChange change : changeList) {
            triggerSynchronization(change)
        }
    }
    def protected void triggerSynchronization(VitruviusChange change) {
        this.getVirtualModel().propagateChange(change);
    }
    
    override protected getCorrespondenceModel() throws Throwable {
        val iter = metamodels.iterator();
        return getVirtualModel().getCorrespondenceModel(iter.next().getURI(), iter.next().getURI());
    }
    
    override abortedChangePropagation(ChangePropagationAbortCause cause) {
        
    }
    
    override finishedChangePropagation() {
        
    }
    
    override startedChangePropagation() {
       
    }
    
    override protected afterTest(Description description) {
       
    }
    
    protected def void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
        if (modelPathInProject.isNullOrEmpty || rootElement == null) {
            throw new IllegalArgumentException();
        }
        val resource = this.resourceSet.createResource(
            URI.createPlatformResourceURI(modelPathInProject.platformModelPath, true)
        );
        EcoreResourceBridge.saveResource(resource);
        resource.contents.add(rootElement);
    }

    protected def String getPlatformModelPath(String modelPathInProject) {
        return this.currentTestProjectName + "/" + modelPathInProject
    }
}