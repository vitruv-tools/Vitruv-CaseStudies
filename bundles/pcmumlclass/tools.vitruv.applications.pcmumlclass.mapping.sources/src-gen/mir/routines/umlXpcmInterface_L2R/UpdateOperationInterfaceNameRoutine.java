package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateOperationInterfaceNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateOperationInterfaceNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateOperationInterfaceNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.UpdateOperationInterfaceNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.contractsPackage = contractsPackage;
  }
  
  private Interface interface_;
  
  private org.eclipse.uml2.uml.Package contractsPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateOperationInterfaceNameRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   contractsPackage: " + this.contractsPackage);
    
    userExecution.executeAction1(interface_, contractsPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
