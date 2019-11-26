package mir.reactions.umlXpcmRoles_R2L;

import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
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
public class OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<OperationRequiredRole, OperationInterface> replaceChange;
  
  private int currentlyMatchedChange;
  
  public OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.OperationRequiredRole affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationInterface oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.OperationInterface newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.OperationRequiredRole)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("requiredInterface__OperationRequiredRole")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationRequiredRole, org.palladiosimulator.pcm.repository.OperationInterface>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final OperationRequiredRole affectedEObject, final EReference affectedFeature, final OperationInterface oldValue, final OperationInterface newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_ElementUpdatedCheck(affectedEObject);
    }
  }
}
