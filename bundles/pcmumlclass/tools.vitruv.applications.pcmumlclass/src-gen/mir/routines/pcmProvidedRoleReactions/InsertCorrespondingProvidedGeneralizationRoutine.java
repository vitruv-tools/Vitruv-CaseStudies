package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingProvidedGeneralization(pcmProvided, pcmIPRE);
    }
    
    public void callRoutine1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingProvidedGeneralization(pcmProvided, pcmIPRE);
    }
  }
  
  public InsertCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.InsertCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingProvidedGeneralizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    userExecution.callRoutine1(pcmProvided, pcmIPRE, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmProvided, pcmIPRE, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
