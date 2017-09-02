package mir.reactions.reactionsPcmToUml.pcmToUml;

import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedProvidedRoleReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity, org.palladiosimulator.pcm.repository.ProvidedRole> typedChange = (RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity, org.palladiosimulator.pcm.repository.ProvidedRole>)change;
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.ProvidedRole oldValue = typedChange.getOldValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity, org.palladiosimulator.pcm.repository.ProvidedRole> relevantChange = (RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity, org.palladiosimulator.pcm.repository.ProvidedRole>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.ProvidedRole)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InterfaceProvidingEntity affectedEObject, final EReference affectedFeature, final ProvidedRole oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteProvidedRole(oldValue);
    }
  }
}
