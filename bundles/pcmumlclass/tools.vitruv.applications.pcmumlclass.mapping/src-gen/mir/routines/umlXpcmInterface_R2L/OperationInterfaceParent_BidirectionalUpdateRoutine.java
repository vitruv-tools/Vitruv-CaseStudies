package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationInterface operationInterface_, final OperationInterface parentInterface_, @Extension final RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public OperationInterfaceParent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterfaceParent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.parentInterface_ = parentInterface_;
  }
  
  private OperationInterface operationInterface_;
  
  private OperationInterface parentInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   parentInterface_: " + this.parentInterface_);
    
    userExecution.executeAction1(operationInterface_, parentInterface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
