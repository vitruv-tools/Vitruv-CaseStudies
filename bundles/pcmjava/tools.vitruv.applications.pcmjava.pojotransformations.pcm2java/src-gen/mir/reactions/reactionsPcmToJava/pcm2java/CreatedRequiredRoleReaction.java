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
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreatedRequiredRoleReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole> typedChange = (InsertEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole>)change;
    org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationRequiredRole newValue = typedChange.getNewValue();
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole> relevantChange = (InsertEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.OperationRequiredRole)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
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
    
    public void callRoutine1(final InterfaceRequiringEntity affectedEObject, final EReference affectedFeature, final OperationRequiredRole newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addRequiredRole(newValue);
    }
  }
}
