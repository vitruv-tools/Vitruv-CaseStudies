package mir.reactions.umlXpcmComponent_L2R;

import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class OnRepositoryComponentOperationRemovedFromClassReaction extends AbstractReactionRealization {
  private RemoveEReference<org.eclipse.uml2.uml.Class, Operation> removeChange;
  
  private int currentlyMatchedChange;
  
  public OnRepositoryComponentOperationRemovedFromClassReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Class affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.eclipse.uml2.uml.Operation oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentOperationRemovedFromClassReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentOperationRemovedFromClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Operation> _localTypedChange = (RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Operation>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("ownedOperation")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.Operation)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Operation>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchRemoveChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference removeChange, final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final Operation oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repositoryComponent_ElementDeletedCheck(affectedEObject);
    }
  }
}
