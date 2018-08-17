package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteCorrespondingRequiredField(pcmRequired);
    }
    
    public void callRoutine1(final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteCorrespondingRequiredConstructorParameter(pcmRequired);
    }
  }
  
  public DeleteCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;
  }
  
  private OperationRequiredRole pcmRequired;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRequiredElementsRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    
    userExecution.callRoutine1(pcmRequired, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmRequired, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
