package mir.reactions.umlXpcmSignature_R2L;

import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<OperationSignature, DataType> replaceChange;
  
  private int currentlyMatchedChange;
  
  public OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.OperationSignature affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.DataType oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.DataType newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmSignature_R2L.OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmSignature_R2L.OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(replaceChange, affectedEObject, affectedFeature, oldValue, newValue, this.getRoutinesFacade());
    
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
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationSignature, org.palladiosimulator.pcm.repository.DataType> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationSignature, org.palladiosimulator.pcm.repository.DataType>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.OperationSignature)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("returnType__OperationSignature")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.DataType)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.DataType)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationSignature, org.palladiosimulator.pcm.repository.DataType>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final OperationSignature affectedEObject, final EReference affectedFeature, final DataType oldValue, final DataType newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.onSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalRepair(affectedEObject);
    }
  }
}
