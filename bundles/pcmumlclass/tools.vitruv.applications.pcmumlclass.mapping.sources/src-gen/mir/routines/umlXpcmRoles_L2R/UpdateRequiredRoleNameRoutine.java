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
public class UpdateRequiredRoleNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateRequiredRoleNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateRequiredRoleNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.UpdateRequiredRoleNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.property = property;this.parameter = parameter;this.implementation = implementation;this.interface_ = interface_;this.operation = operation;
  }
  
  private Property property;
  
  private Parameter parameter;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Interface interface_;
  
  private Operation operation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRequiredRoleNameRoutine with input:");
    getLogger().debug("   property: " + this.property);
    getLogger().debug("   parameter: " + this.parameter);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation: " + this.operation);
    
    userExecution.executeAction1(property, parameter, implementation, interface_, operation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
