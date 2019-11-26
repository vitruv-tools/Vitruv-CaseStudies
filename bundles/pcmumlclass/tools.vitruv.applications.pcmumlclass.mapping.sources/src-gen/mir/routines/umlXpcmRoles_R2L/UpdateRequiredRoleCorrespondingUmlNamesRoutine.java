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
public class UpdateRequiredRoleCorrespondingUmlNamesRoutine extends AbstractRepairRoutineRealization {
  private UpdateRequiredRoleCorrespondingUmlNamesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateRequiredRoleCorrespondingUmlNamesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.UpdateRequiredRoleCorrespondingUmlNamesRoutine.ActionUserExecution(getExecutionState(), this);
    this.role = role;this.operationInterface = operationInterface;this.requiringEntity = requiringEntity;
  }
  
  private OperationRequiredRole role;
  
  private OperationInterface operationInterface;
  
  private InterfaceProvidingRequiringEntity requiringEntity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRequiredRoleCorrespondingUmlNamesRoutine with input:");
    getLogger().debug("   role: " + this.role);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   requiringEntity: " + this.requiringEntity);
    
    userExecution.executeAction1(role, operationInterface, requiringEntity, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
