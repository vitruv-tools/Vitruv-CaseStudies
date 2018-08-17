package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final Parameter pcmParameter, final OperationSignature pcmSignature, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingRegularParameter(pcmParameter, pcmSignature);
    }
    
    public void callRoutine1(final Parameter pcmParameter, final OperationSignature pcmSignature, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateRegularParameterCandidate(pcmParameter, pcmSignature);
    }
  }
  
  public InsertCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParameter, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.InsertCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParameter = pcmParameter;this.pcmSignature = pcmSignature;
  }
  
  private Parameter pcmParameter;
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParameter: " + this.pcmParameter);
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    userExecution.callRoutine1(pcmParameter, pcmSignature, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmParameter, pcmSignature, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
