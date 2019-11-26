package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
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
    
    public EObject getElement1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
  }
  
  public RequiredRole_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.RequiredRole_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.role_ = role_;this.operationInterface_ = operationInterface_;this.requiringEntity_ = requiringEntity_;
  }
  
  private OperationRequiredRole role_;
  
  private OperationInterface operationInterface_;
  
  private InterfaceProvidingRequiringEntity requiringEntity_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_DeleteMappingRoutine with input:");
    getLogger().debug("   role_: " + this.role_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   requiringEntity_: " + this.requiringEntity_);
    
    deleteObject(userExecution.getElement1(role_, operationInterface_, requiringEntity_));
    
    postprocessElements();
    
    return true;
  }
}
