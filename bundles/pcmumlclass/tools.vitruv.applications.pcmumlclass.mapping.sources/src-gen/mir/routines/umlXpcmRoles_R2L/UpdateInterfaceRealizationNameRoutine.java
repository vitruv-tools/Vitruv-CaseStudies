package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateInterfaceRealizationNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateInterfaceRealizationNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateInterfaceRealizationNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.UpdateInterfaceRealizationNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.role = role;this.operationInterface = operationInterface;this.providingEntity = providingEntity;
  }
  
  private OperationProvidedRole role;
  
  private OperationInterface operationInterface;
  
  private InterfaceProvidingRequiringEntity providingEntity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateInterfaceRealizationNameRoutine with input:");
    getLogger().debug("   role: " + this.role);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   providingEntity: " + this.providingEntity);
    
    userExecution.executeAction1(role, operationInterface, providingEntity, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
