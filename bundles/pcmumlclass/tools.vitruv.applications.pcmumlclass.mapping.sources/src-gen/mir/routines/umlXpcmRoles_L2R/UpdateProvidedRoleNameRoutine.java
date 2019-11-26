package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateProvidedRoleNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateProvidedRoleNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateProvidedRoleNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.UpdateProvidedRoleNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceRealization = interfaceRealization;this.implementation = implementation;this.interface_ = interface_;
  }
  
  private InterfaceRealization interfaceRealization;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateProvidedRoleNameRoutine with input:");
    getLogger().debug("   interfaceRealization: " + this.interfaceRealization);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   interface_: " + this.interface_);
    
    userExecution.executeAction1(interfaceRealization, implementation, interface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
