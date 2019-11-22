package mir.reactions.umlXpcmComponent_R2L;

import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class OnRepositoryComponentRepositoryComponentInsertedInRepositoryReaction extends AbstractReactionRealization {
  private InsertEReference<Repository, RepositoryComponent> insertChange;
  
  private int currentlyMatchedChange;
  
  public OnRepositoryComponentRepositoryComponentInsertedInRepositoryReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Repository affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.RepositoryComponent newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryComponentInsertedInRepositoryReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryComponentInsertedInRepositoryReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, this.getRoutinesFacade());
    
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
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent> _localTypedChange = (InsertEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.Repository)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("components__Repository")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.RepositoryComponent)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.palladiosimulator.pcm.repository.Repository, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final Repository affectedEObject, final EReference affectedFeature, final RepositoryComponent newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repositoryComponent_ElementCreatedCheck(affectedEObject);
    }
  }
}
