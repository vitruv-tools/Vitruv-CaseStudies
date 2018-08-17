package mir.reactions.pcmRepositoryComponentReactions;

import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class RepositoryComponentRemovedReaction extends AbstractReactionRealization {
  private RemoveEReference<Repository, RepositoryComponent> removeChange;
  
  private int currentlyMatchedChange;
  
  public RepositoryComponentRemovedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Repository affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.RepositoryComponent oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(removeChange, affectedEObject, affectedFeature, oldValue, index)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentRemovedReaction.ActionUserExecution userExecution = new mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentRemovedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent> _localTypedChange = (RemoveEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.Repository)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("components__Repository")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.RepositoryComponent)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
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
  
  private boolean checkUserDefinedPrecondition(final RemoveEReference removeChange, final Repository affectedEObject, final EReference affectedFeature, final RepositoryComponent oldValue, final int index) {
    boolean _contains = affectedEObject.getComponents__Repository().contains(oldValue);
    return (!_contains);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference removeChange, final Repository affectedEObject, final EReference affectedFeature, final RepositoryComponent oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeCorrespondingComponentPackage(oldValue, affectedEObject);
    }
  }
}
