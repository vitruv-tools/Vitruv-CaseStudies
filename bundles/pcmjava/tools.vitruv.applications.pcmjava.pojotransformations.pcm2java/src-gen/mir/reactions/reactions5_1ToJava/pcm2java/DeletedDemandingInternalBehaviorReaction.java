package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedDemandingInternalBehaviorReaction extends AbstractReactionRealization {
  public DeletedDemandingInternalBehaviorReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> typedChange = (RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof ResourceDemandingInternalBehaviour)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof BasicComponent)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("resourceDemandingInternalBehaviours__BasicComponent")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof ResourceDemandingInternalBehaviour)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> typedChange = (RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<BasicComponent, ResourceDemandingInternalBehaviour> _removeChange = change.getRemoveChange();
      ResourceDemandingInternalBehaviour _oldValue = _removeChange.getOldValue();
      _routinesFacade.deleteMethodForResourceDemandingBehavior(_oldValue);
    }
  }
}
