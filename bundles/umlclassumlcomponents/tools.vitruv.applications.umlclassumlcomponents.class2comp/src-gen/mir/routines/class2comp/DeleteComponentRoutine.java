package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final String expectedTag, final Component umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceUmlComponent(final org.eclipse.uml2.uml.Class umlClass, final String expectedTag) {
      return umlClass;
    }
  }
  
  public DeleteComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final String expectedTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.expectedTag = expectedTag;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteComponentRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   String: " + this.expectedTag);
    
    Component umlComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponent(umlClass, expectedTag), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, expectedTag));
    if (umlComponent == null) {
    	return;
    }
    registerObjectUnderModification(umlComponent);
    deleteObject(userExecution.getElement1(umlClass, expectedTag, umlComponent));
    
    postprocessElements();
  }
}
