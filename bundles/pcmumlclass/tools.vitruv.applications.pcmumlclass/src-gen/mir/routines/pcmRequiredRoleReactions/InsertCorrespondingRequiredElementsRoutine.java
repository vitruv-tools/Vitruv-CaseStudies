package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingRequiredField(pcmRequired, pcmIPRE);
    }
    
    public void callRoutine3(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingRequiredElements(pcmRequired, pcmIPRE);
    }
    
    public void callRoutine1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingRequiredConstructorParameter(pcmRequired, pcmIPRE);
    }
  }
  
  public InsertCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.InsertCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRequiredElementsRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    userExecution.callRoutine1(pcmRequired, pcmIPRE, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmRequired, pcmIPRE, this.getRoutinesFacade());
    
    userExecution.callRoutine3(pcmRequired, pcmIPRE, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
