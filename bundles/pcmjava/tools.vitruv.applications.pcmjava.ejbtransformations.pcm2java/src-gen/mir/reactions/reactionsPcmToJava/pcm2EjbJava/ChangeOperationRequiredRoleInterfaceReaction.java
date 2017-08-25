package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import mir.routines.pcm2EjbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class ChangeOperationRequiredRoleInterfaceReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface> typedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface>)change;
    org.palladiosimulator.pcm.repository.OperationRequiredRole affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationInterface oldValue = typedChange.getOldValue();
    org.palladiosimulator.pcm.repository.OperationInterface newValue = typedChange.getNewValue();
    mir.routines.pcm2EjbJava.RoutinesFacade routinesFacade = new mir.routines.pcm2EjbJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface> relevantChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.OperationRequiredRole)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("requiredInterface__OperationRequiredRole")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
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
    
    public void callRoutine1(final OperationRequiredRole affectedEObject, final EReference affectedFeature, final OperationInterface oldValue, final OperationInterface newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.reinitializeOperationRequiredRole(affectedEObject);
    }
  }
}
