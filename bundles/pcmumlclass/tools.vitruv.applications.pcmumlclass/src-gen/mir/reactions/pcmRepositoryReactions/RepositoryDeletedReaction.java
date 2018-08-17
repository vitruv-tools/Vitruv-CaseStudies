package mir.reactions.pcmRepositoryReactions;

import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

@SuppressWarnings("all")
public class RepositoryDeletedReaction extends AbstractReactionRealization {
  private RemoveRootEObject<Repository> removeChange;
  
  private int currentlyMatchedChange;
  
  public RepositoryDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Repository oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmRepositoryReactions.RepositoryDeletedReaction.ActionUserExecution userExecution = new mir.reactions.pcmRepositoryReactions.RepositoryDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveRootEObject<?>) {
    	RemoveRootEObject<org.palladiosimulator.pcm.repository.Repository> _localTypedChange = (RemoveRootEObject<org.palladiosimulator.pcm.repository.Repository>) change;
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.Repository)) {
    		return false;
    	}
    	this.removeChange = (RemoveRootEObject<org.palladiosimulator.pcm.repository.Repository>) change;
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
    
    public void callRoutine1(final RemoveRootEObject removeChange, final Repository oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteCorrespondingRepositoryPackages(oldValue);
    }
  }
}
