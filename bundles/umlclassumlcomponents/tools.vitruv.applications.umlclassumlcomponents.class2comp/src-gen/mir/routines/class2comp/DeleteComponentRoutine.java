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
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Component umlComp) {
      return umlComp;
    }
    
    public EObject getCorrepondenceSourceUmlComp(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
  }
  
  public DeleteComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteComponentRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    
    org.eclipse.uml2.uml.Component umlComp = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComp(umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Component.class,
    	(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlComp == null) {
    	return false;
    }
    registerObjectUnderModification(umlComp);
    deleteObject(userExecution.getElement1(umlClass, umlComp));
    
    postprocessElements();
    
    return true;
  }
}
