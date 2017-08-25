package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedProvidedRoleFromComponentReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.repository.RepositoryComponent, org.palladiosimulator.pcm.repository.ProvidedRole> typedChange = ((RemoveAndDeleteNonRoot<org.palladiosimulator.pcm.repository.RepositoryComponent, org.palladiosimulator.pcm.repository.ProvidedRole>)change).getRemoveChange();
    org.palladiosimulator.pcm.repository.RepositoryComponent affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.ProvidedRole oldValue = typedChange.getOldValue();
    mir.routines.pcm2depInjectJava.RoutinesFacade routinesFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.repository.RepositoryComponent, org.palladiosimulator.pcm.repository.ProvidedRole> relevantChange = ((RemoveAndDeleteNonRoot<org.palladiosimulator.pcm.repository.RepositoryComponent, org.palladiosimulator.pcm.repository.ProvidedRole>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.RepositoryComponent)) {
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
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
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
    
    public void callRoutine1(final RepositoryComponent affectedEObject, final EReference affectedFeature, final ProvidedRole oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeProvidedRole(oldValue);
    }
  }
}
