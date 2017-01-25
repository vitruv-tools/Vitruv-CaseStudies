package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedRequiredRoleReaction extends AbstractReactionRealization {
  public DeletedRequiredRoleReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole> typedChange = (RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedRequiredRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedRequiredRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof OperationRequiredRole)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof OperationRequiredRole)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole> typedChange = (RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<InterfaceRequiringEntity, OperationRequiredRole> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<InterfaceRequiringEntity, OperationRequiredRole> _removeChange = change.getRemoveChange();
      OperationRequiredRole _oldValue = _removeChange.getOldValue();
      RemoveEReference<InterfaceRequiringEntity, OperationRequiredRole> _removeChange_1 = change.getRemoveChange();
      InterfaceRequiringEntity _affectedEObject = _removeChange_1.getAffectedEObject();
      _routinesFacade.removeRequiredRole(_oldValue, ((RepositoryComponent) _affectedEObject));
    }
  }
}
