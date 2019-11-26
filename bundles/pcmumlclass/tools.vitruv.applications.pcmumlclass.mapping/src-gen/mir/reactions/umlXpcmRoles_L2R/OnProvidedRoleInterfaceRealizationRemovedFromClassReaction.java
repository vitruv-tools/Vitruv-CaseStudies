package mir.reactions.umlXpcmRoles_L2R;

import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class OnProvidedRoleInterfaceRealizationRemovedFromClassReaction extends AbstractReactionRealization {
  private RemoveEReference<BehavioredClassifier, InterfaceRealization> removeChange;
  
  private int currentlyMatchedChange;
  
  public OnProvidedRoleInterfaceRealizationRemovedFromClassReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.BehavioredClassifier affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.eclipse.uml2.uml.InterfaceRealization oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceRealizationRemovedFromClassReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceRealizationRemovedFromClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.eclipse.uml2.uml.BehavioredClassifier, org.eclipse.uml2.uml.InterfaceRealization> _localTypedChange = (RemoveEReference<org.eclipse.uml2.uml.BehavioredClassifier, org.eclipse.uml2.uml.InterfaceRealization>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.BehavioredClassifier)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.eclipse.uml2.uml.BehavioredClassifier, org.eclipse.uml2.uml.InterfaceRealization>) change;
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
    
    public void callRoutine1(final RemoveEReference removeChange, final BehavioredClassifier affectedEObject, final EReference affectedFeature, final InterfaceRealization oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.providedRole_ElementDeletedCheck(affectedEObject);
    }
  }
}
