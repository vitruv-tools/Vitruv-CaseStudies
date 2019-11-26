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
public class OperationInterface_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OperationInterface operationInterface_, final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateInterfaceName(operationInterface_, repository_);
    }
  }
  
  public OperationInterface_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterface_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.repository_ = repository_;
  }
  
  private OperationInterface operationInterface_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   repository_: " + this.repository_);
    
    userExecution.callRoutine1(operationInterface_, repository_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
