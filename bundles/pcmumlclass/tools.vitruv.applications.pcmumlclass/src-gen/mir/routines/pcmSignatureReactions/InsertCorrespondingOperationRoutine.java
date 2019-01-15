package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingOperationRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingOperation(pcmSignature, pcmInterface);
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateOperationCandidate(pcmSignature, pcmInterface);
    }
  }
  
  public InsertCorrespondingOperationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.InsertCorrespondingOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmInterface = pcmInterface;
  }
  
  private OperationSignature pcmSignature;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingOperationRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    userExecution.callRoutine1(pcmSignature, pcmInterface, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmSignature, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
