package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.junit.Test

import org.junit.Assert
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class UserInputReuseTest extends AbstractClass2CompTest {
    
    @Test
    public def void testChangeRevertRedo() {
        val className = "MyNewTestClass"
        
        val createComponent = 0
        
        // create UML class, createComponent = 0 as user interaction selection means also create component
        val umlClass = createClass(className, createComponent) // calls createPackage which causes 1-2 user interactions
        // and causes 1 user interaction itself afterwards.
        val propagatedChanges = saveAndSynchronizeWithInteractions(umlClass)
        val originalChangesComposite = VitruviusChangeFactory.instance.createCompositeContainerChange()
        propagatedChanges.map[ it.originalChange ].forEach[ originalChangesComposite.addChange(it)]
        
        //(originalChange as CompositeTransactionalChange)
        assertComponentForClass(umlClass, className)
        
        // revert the changes (creation of UML class => creation of component)
        virtualModel.reverseChanges(propagatedChanges)
        
        // should have no corresponding component for umlClass now:
        val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
        Assert.assertEquals(0, correspondingElements.size)
        
        // re-apply original change
        virtualModel.propagateChange(originalChangesComposite)
        
        assertComponentForClass(umlClass, className)
    }
}