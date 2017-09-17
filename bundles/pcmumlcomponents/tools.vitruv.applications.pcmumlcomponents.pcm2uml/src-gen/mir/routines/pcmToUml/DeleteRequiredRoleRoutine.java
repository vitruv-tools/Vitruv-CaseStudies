package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Usage;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole requiredRole, final Usage usage) {
      return usage;
    }
    
    public EObject getCorrepondenceSourceUsage(final OperationRequiredRole requiredRole) {
      return requiredRole;
    }
  }
  
  public DeleteRequiredRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole requiredRole) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.DeleteRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.requiredRole = requiredRole;
  }
  
  private OperationRequiredRole requiredRole;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteRequiredRoleRoutine with input:");
    getLogger().debug("   requiredRole: " + this.requiredRole);
    
    org.eclipse.uml2.uml.Usage usage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUsage(requiredRole), // correspondence source supplier
    	org.eclipse.uml2.uml.Usage.class,
    	(org.eclipse.uml2.uml.Usage _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (usage == null) {
    	return false;
    }
    registerObjectUnderModification(usage);
    deleteObject(userExecution.getElement1(requiredRole, usage));
    
    postprocessElements();
    
    return true;
  }
}
