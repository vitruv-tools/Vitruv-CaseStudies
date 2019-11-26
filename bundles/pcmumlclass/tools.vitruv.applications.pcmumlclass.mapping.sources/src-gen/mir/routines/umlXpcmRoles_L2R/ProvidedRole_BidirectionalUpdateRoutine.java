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
public class ProvidedRole_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateProvidedRoleName(interfaceRealization_, implementation_, interface_);
    }
  }
  
  public ProvidedRole_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.ProvidedRole_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceRealization_ = interfaceRealization_;this.implementation_ = implementation_;this.interface_ = interface_;
  }
  
  private InterfaceRealization interfaceRealization_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   interfaceRealization_: " + this.interfaceRealization_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    
    userExecution.callRoutine1(interfaceRealization_, implementation_, interface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
