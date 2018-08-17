package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationInterface pcmInterface, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingInterface(pcmInterface, pcmRepository);
    }
    
    public void callRoutine1(final OperationInterface pcmInterface, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateInterfaceCandidate(pcmInterface, pcmRepository);
    }
  }
  
  public InsertCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.InsertCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmRepository = pcmRepository;
  }
  
  private OperationInterface pcmInterface;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    userExecution.callRoutine1(pcmInterface, pcmRepository, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmInterface, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
