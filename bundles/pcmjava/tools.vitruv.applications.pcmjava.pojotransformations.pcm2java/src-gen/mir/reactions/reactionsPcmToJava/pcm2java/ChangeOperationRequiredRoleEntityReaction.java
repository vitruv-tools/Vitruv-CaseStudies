package mir.reactions.reactionsPcmToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangeOperationRequiredRoleEntityReaction extends AbstractReactionRealization {
  public ChangeOperationRequiredRoleEntityReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity> typedChange = (ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity>)change;
    OperationRequiredRole affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    InterfaceRequiringEntity oldValue = typedChange.getOldValue();
    InterfaceRequiringEntity newValue = typedChange.getNewValue();
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity> relevantChange = (ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity>)change;
    if (!(relevantChange.getAffectedEObject() instanceof OperationRequiredRole)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("requiringEntity_RequiredRole")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OperationRequiredRole affectedEObject, final EReference affectedFeature, final InterfaceRequiringEntity oldValue, final InterfaceRequiringEntity newValue, @Extension final RoutinesFacade _routinesFacade) {
      final OperationRequiredRole requiredRole = affectedEObject;
      _routinesFacade.removeRequiredRole(requiredRole, oldValue);
      _routinesFacade.addRequiredRole(requiredRole);
    }
  }
}
