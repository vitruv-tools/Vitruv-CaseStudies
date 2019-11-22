package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateInterfaceNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateInterfaceNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationInterface operationInterface, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateInterfaceNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.UpdateInterfaceNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface = operationInterface;this.repository = repository;
  }
  
  private OperationInterface operationInterface;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateInterfaceNameRoutine with input:");
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   repository: " + this.repository);
    
    userExecution.executeAction1(operationInterface, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
