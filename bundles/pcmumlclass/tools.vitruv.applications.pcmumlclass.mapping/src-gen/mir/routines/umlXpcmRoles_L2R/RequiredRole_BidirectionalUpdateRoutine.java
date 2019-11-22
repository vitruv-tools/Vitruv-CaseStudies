package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingRequiredRole(property_, parameter_, implementation_, interface_, operation_);
    }
    
    public void callRoutine1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateRequiredRoleName(property_, parameter_, implementation_, interface_, operation_);
    }
  }
  
  public RequiredRole_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.RequiredRole_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.property_ = property_;this.parameter_ = parameter_;this.implementation_ = implementation_;this.interface_ = interface_;this.operation_ = operation_;
  }
  
  private Property property_;
  
  private Parameter parameter_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  private Operation operation_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   property_: " + this.property_);
    getLogger().debug("   parameter_: " + this.parameter_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation_: " + this.operation_);
    
    userExecution.callRoutine1(property_, parameter_, implementation_, interface_, operation_, this.getRoutinesFacade());
    
    userExecution.callRoutine2(property_, parameter_, implementation_, interface_, operation_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
