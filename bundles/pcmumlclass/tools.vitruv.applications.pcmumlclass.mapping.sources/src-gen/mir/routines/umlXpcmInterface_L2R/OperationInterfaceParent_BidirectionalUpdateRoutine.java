package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public void executeAction1(final Interface interface_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public OperationInterfaceParent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.generalization_ = generalization_;
  }
  
  private Interface interface_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    userExecution.executeAction1(interface_, generalization_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
