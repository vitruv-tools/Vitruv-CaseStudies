package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangeOperationRequiredRoleEntityReaction extends AbstractReactionRealization {
  public ChangeOperationRequiredRoleEntityReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity> typedChange = (ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity> change) {
    // Check affected object
    if (!(change.getAffectedEObject() instanceof OperationRequiredRole)) {
    	return false;
    }
    	
    // Check feature
    if (!change.getAffectedFeature().getName().equals("requiringEntity_RequiredRole")) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity> typedChange = (ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute<OperationRequiredRole, InterfaceRequiringEntity> change, @Extension final RoutinesFacade _routinesFacade) {
      final OperationRequiredRole requiredRole = change.getAffectedEObject();
      InterfaceRequiringEntity _oldValue = change.getOldValue();
      _routinesFacade.removeRequiredRole(requiredRole, _oldValue);
      _routinesFacade.addRequiredRole(requiredRole);
    }
  }
}
