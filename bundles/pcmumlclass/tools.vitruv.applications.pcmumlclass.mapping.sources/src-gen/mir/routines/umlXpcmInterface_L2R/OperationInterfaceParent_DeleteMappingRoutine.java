package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return parentInterface_;
    }
  }
  
  public OperationInterfaceParent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.parentInterface_ = parentInterface_;
  }
  
  private OperationInterface operationInterface_;
  
  private OperationInterface parentInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_DeleteMappingRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   parentInterface_: " + this.parentInterface_);
    
    deleteObject(userExecution.getElement1(operationInterface_, parentInterface_));
    
    postprocessElements();
    
    return true;
  }
}
