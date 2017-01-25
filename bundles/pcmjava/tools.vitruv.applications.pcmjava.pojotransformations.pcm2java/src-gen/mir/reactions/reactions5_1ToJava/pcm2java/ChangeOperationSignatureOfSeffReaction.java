package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangeOperationSignatureOfSeffReaction extends AbstractReactionRealization {
  public ChangeOperationSignatureOfSeffReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> typedChange = (ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> change) {
    // Check affected object
    if (!(change.getAffectedEObject() instanceof ResourceDemandingSEFF)) {
    	return false;
    }
    // Check feature
    if (!change.getAffectedFeature().getName().equals("describedService__SEFF")) {
    	return false;
    }
    if (change.isFromNonDefaultValue() && !(change.getOldValue() instanceof OperationSignature)
    ) {
    	return false;
    }
    if (change.isToNonDefaultValue() && !(change.getNewValue() instanceof OperationSignature)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> typedChange = (ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature>)change;
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
    
    public void callRoutine1(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> change, @Extension final RoutinesFacade _routinesFacade) {
      ResourceDemandingSEFF _affectedEObject = change.getAffectedEObject();
      _routinesFacade.changeMethodForSeff(_affectedEObject);
    }
  }
}
