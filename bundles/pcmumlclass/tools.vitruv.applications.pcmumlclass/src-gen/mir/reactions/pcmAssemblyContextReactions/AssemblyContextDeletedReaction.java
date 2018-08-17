package mir.reactions.pcmAssemblyContextReactions;

import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
public class AssemblyContextDeletedReaction extends AbstractReactionRealization {
  private DeleteEObject<AssemblyContext> deleteChange;
  
  private int currentlyMatchedChange;
  
  public AssemblyContextDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.composition.AssemblyContext affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmAssemblyContextReactions.AssemblyContextDeletedReaction.ActionUserExecution userExecution = new mir.reactions.pcmAssemblyContextReactions.AssemblyContextDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(deleteChange, affectedEObject, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext> _localTypedChange = (DeleteEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.composition.AssemblyContext)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
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
    
    public void callRoutine1(final DeleteEObject deleteChange, final AssemblyContext affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteCorrespondingAssemblyContextProperty(affectedEObject);
    }
  }
}
