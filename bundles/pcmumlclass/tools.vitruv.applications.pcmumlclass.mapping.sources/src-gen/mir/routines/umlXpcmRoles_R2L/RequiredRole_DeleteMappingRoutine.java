package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return property_;
    }
    
    public EObject getElement2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return parameter_;
    }
  }
  
  public RequiredRole_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.RequiredRole_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.property_ = property_;this.parameter_ = parameter_;this.implementation_ = implementation_;this.interface_ = interface_;this.operation_ = operation_;
  }
  
  private Property property_;
  
  private Parameter parameter_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  private Operation operation_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_DeleteMappingRoutine with input:");
    getLogger().debug("   property_: " + this.property_);
    getLogger().debug("   parameter_: " + this.parameter_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation_: " + this.operation_);
    
    deleteObject(userExecution.getElement1(property_, parameter_, implementation_, interface_, operation_));
    
    deleteObject(userExecution.getElement2(property_, parameter_, implementation_, interface_, operation_));
    
    postprocessElements();
    
    return true;
  }
}
