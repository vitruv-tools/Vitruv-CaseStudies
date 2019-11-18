package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public void callRoutine1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateOperationInterfaceName(interface_, contractsPackage_);
    }
  }
  
  public OperationInterface_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterface_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.contractsPackage_ = contractsPackage_;
  }
  
  private Interface interface_;
  
  private org.eclipse.uml2.uml.Package contractsPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   contractsPackage_: " + this.contractsPackage_);
    
    userExecution.callRoutine1(interface_, contractsPackage_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
