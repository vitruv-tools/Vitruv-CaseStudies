package mir.reactions.pcmRepositoryReactions;

import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
public class RepositoryCreatedReaction extends AbstractReactionRealization {
  private InsertRootEObject<Repository> insertChange;
  
  private int currentlyMatchedChange;
  
  public RepositoryCreatedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Repository newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmRepositoryReactions.RepositoryCreatedReaction.ActionUserExecution userExecution = new mir.reactions.pcmRepositoryReactions.RepositoryCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, newValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertRootEObject<?>) {
    	InsertRootEObject<org.palladiosimulator.pcm.repository.Repository> _localTypedChange = (InsertRootEObject<org.palladiosimulator.pcm.repository.Repository>) change;
    	if (!(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.Repository)) {
    		return false;
    	}
    	this.insertChange = (InsertRootEObject<org.palladiosimulator.pcm.repository.Repository>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertRootEObject insertChange, final Repository newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.initializePcmRepositoryUmlPackagesCorrespondence(newValue);
    }
  }
}
