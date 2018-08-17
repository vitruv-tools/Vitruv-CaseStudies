package mir.reactions.umlProvidedRoleGeneralizationReactions;

import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
public class ProvidedRoleGeneralizationDeletedReaction extends AbstractReactionRealization {
  private DeleteEObject<Generalization> deleteChange;
  
  private int currentlyMatchedChange;
  
  public ProvidedRoleGeneralizationDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Generalization affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationDeletedReaction.ActionUserExecution userExecution = new mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(deleteChange, affectedEObject, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.eclipse.uml2.uml.Generalization> _localTypedChange = (DeleteEObject<org.eclipse.uml2.uml.Generalization>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Generalization)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.eclipse.uml2.uml.Generalization>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchDeleteChange(change)) {
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
    
    public void callRoutine1(final DeleteEObject deleteChange, final Generalization affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteCorrespondingProvidedRole(affectedEObject);
    }
  }
}
