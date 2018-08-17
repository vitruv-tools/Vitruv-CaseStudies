package mir.reactions.pcmProvidedRoleReactions;

import com.google.common.base.Objects;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class ProvidedRoleInterfaceChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> replaceChange;
  
  private int currentlyMatchedChange;
  
  public ProvidedRoleInterfaceChangedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.OperationProvidedRole affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationInterface oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.OperationInterface newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInterfaceChangedReaction.ActionUserExecution userExecution = new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInterfaceChangedReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.repository.OperationInterface> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.repository.OperationInterface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.OperationProvidedRole)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("providedInterface__OperationProvidedRole")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.repository.OperationInterface>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final OperationProvidedRole affectedEObject, final EReference affectedFeature, final OperationInterface oldValue, final OperationInterface newValue) {
    OperationInterface _providedInterface__OperationProvidedRole = affectedEObject.getProvidedInterface__OperationProvidedRole();
    return Objects.equal(_providedInterface__OperationProvidedRole, newValue);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final OperationProvidedRole affectedEObject, final EReference affectedFeature, final OperationInterface oldValue, final OperationInterface newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeInterfaceOfCorrespondingProvidedGeneralization(affectedEObject, newValue);
    }
  }
}
