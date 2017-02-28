package mir.reactions.reactions5_1ToJava.pcm2ejbJava;

import mir.routines.pcm2ejbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
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
    ResourceDemandingSEFF affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    OperationSignature oldValue = typedChange.getOldValue();
    OperationSignature newValue = typedChange.getNewValue();
    mir.routines.pcm2ejbJava.RoutinesFacade routinesFacade = new mir.routines.pcm2ejbJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2ejbJava.ChangeOperationSignatureOfSeffReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2ejbJava.ChangeOperationSignatureOfSeffReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> relevantChange = (ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature>)change;
    if (!(relevantChange.getAffectedEObject() instanceof ResourceDemandingSEFF)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("describedService__SEFF")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof OperationSignature)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof OperationSignature)) {
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
    
    public void callRoutine1(final ResourceDemandingSEFF affectedEObject, final EReference affectedFeature, final OperationSignature oldValue, final OperationSignature newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeMethodForSeff(affectedEObject);
    }
  }
}
