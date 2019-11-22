package mir.reactions.umlXpcmRoles_R2L;

import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
public class OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction extends AbstractReactionRealization {
  private DeleteEObject<InterfaceProvidingRequiringEntity> deleteChange;
  
  private int currentlyMatchedChange;
  
  public OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(deleteChange, affectedEObject, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> _localTypedChange = (DeleteEObject<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity>) change;
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
    
    public void callRoutine1(final DeleteEObject deleteChange, final InterfaceProvidingRequiringEntity affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_ElementDeletedCheck(affectedEObject);
    }
  }
}
