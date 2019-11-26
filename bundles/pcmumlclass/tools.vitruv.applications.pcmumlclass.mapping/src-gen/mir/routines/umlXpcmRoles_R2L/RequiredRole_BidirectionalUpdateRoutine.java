package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
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
    
    public void callRoutine1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateRequiredRoleCorrespondingUmlNames(role_, operationInterface_, requiringEntity_);
    }
  }
  
  public RequiredRole_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.RequiredRole_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.role_ = role_;this.operationInterface_ = operationInterface_;this.requiringEntity_ = requiringEntity_;
  }
  
  private OperationRequiredRole role_;
  
  private OperationInterface operationInterface_;
  
  private InterfaceProvidingRequiringEntity requiringEntity_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   role_: " + this.role_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   requiringEntity_: " + this.requiringEntity_);
    
    userExecution.callRoutine1(role_, operationInterface_, requiringEntity_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
