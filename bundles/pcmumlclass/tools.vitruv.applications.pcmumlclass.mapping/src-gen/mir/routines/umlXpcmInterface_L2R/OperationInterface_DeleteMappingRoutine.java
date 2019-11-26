package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface operationInterface_, final Repository repository_) {
      return operationInterface_;
    }
  }
  
  public OperationInterface_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterface_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.repository_ = repository_;
  }
  
  private OperationInterface operationInterface_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_DeleteMappingRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   repository_: " + this.repository_);
    
    deleteObject(userExecution.getElement1(operationInterface_, repository_));
    
    postprocessElements();
    
    return true;
  }
}
