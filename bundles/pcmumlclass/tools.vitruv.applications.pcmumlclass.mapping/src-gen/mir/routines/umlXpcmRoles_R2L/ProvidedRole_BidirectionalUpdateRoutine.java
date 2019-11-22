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
public class ProvidedRole_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateInterfaceRealizationName(role_, operationInterface_, providingEntity_);
    }
  }
  
  public ProvidedRole_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.ProvidedRole_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.role_ = role_;this.operationInterface_ = operationInterface_;this.providingEntity_ = providingEntity_;
  }
  
  private OperationProvidedRole role_;
  
  private OperationInterface operationInterface_;
  
  private InterfaceProvidingRequiringEntity providingEntity_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   role_: " + this.role_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   providingEntity_: " + this.providingEntity_);
    
    userExecution.callRoutine1(role_, operationInterface_, providingEntity_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
