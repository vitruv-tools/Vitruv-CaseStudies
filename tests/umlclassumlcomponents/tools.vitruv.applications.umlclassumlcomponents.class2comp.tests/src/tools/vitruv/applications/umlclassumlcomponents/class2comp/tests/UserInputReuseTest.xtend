package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.junit.Test

import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*
import org.junit.Assert
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class UserInputReuseTest extends AbstractClass2CompTest {
    
    @Test
    public def void testChangeRevertRedo() {
        val className = "MyNewTestClass"
        
        val createComponent = 0
        ////queueUserInteractionSelections(createComponent) 
        
        // create UML class, createComponent = 0 as user interaction selection means also create component
        //// userInteractor.addNextSingleSelection(createComponent) // should be done in createClass!
        val umlClass = createClass(className, createComponent) // calls createPackage which causes 1-2 user interactions
        // and causes 1 user interaction itself afterwards.
        val propagatedChanges = saveAndSynchronizeWithInteractions(umlClass)
        ///val originalChange = propagatedChanges.head.originalChange
        val originalChangesComposite = VitruviusChangeFactory.instance.createCompositeContainerChange()
        propagatedChanges.map[ it.originalChange ].forEach[ originalChangesComposite.addChange(it)]
        
        //(originalChange as CompositeTransactionalChange)
        assertComponentForClass(umlClass, className)
        
        // revert the changes (creation of UML class => creation of component)
        virtualModel.reverseChanges(propagatedChanges)
        
        // should have no corresponding component for umlClass now:
        val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
        Assert.assertEquals(0, correspondingElements.size)
        
        ///(originalChange as TransactionalChange).setUserInputs(propagatedChanges.head.userInputDecisions)
        
        // re-apply original change
        ////queueUserInteractionSelections(1, 0)
        virtualModel.propagateChange(originalChangesComposite)
        
        // TEST:
        ///(originalChange as TransactionalChange).setUserInputs(#[ InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInput() ])
        
        
        assertComponentForClass(umlClass, className)
    }
}