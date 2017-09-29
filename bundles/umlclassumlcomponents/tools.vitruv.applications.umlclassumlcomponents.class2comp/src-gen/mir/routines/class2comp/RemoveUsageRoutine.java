package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Usage;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveUsageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveUsageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization classIFRealization, final Usage compUsage) {
      return compUsage;
    }
    
    public EObject getCorrepondenceSourceCompUsage(final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
  }
  
  public RemoveUsageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classIFRealization) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RemoveUsageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classIFRealization = classIFRealization;
  }
  
  private InterfaceRealization classIFRealization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUsageRoutine with input:");
    getLogger().debug("   classIFRealization: " + this.classIFRealization);
    
    org.eclipse.uml2.uml.Usage compUsage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompUsage(classIFRealization), // correspondence source supplier
    	org.eclipse.uml2.uml.Usage.class,
    	(org.eclipse.uml2.uml.Usage _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compUsage == null) {
    	return false;
    }
    registerObjectUnderModification(compUsage);
    deleteObject(userExecution.getElement1(classIFRealization, compUsage));
    
    postprocessElements();
    
    return true;
  }
}
