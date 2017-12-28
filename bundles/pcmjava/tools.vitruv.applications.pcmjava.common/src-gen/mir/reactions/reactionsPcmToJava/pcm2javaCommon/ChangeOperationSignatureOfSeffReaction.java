package mir.reactions.reactionsPcmToJava.pcm2javaCommon;

import mir.routines.pcm2javaCommon.RoutinesFacade;
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

@SuppressWarnings("all")
class ChangeOperationSignatureOfSeffReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<ResourceDemandingSEFF, OperationSignature> replaceChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.seff.ResourceDemandingSEFF affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationSignature oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.OperationSignature newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.pcm2javaCommon.RoutinesFacade routinesFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureOfSeffReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureOfSeffReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    replaceChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchReplaceChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchReplaceChange(final EChange change) {
    if (change instanceof ReplaceSingleValuedEReference<?, ?>) {
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.seff.ResourceDemandingSEFF, org.palladiosimulator.pcm.repository.OperationSignature> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.seff.ResourceDemandingSEFF, org.palladiosimulator.pcm.repository.OperationSignature>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.seff.ResourceDemandingSEFF)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("describedService__SEFF")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationSignature)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.OperationSignature)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.seff.ResourceDemandingSEFF, org.palladiosimulator.pcm.repository.OperationSignature>) change;
    	return true;
    }
    
    return false;
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
