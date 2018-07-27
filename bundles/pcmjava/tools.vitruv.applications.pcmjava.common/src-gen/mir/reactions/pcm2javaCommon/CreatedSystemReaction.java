package mir.reactions.pcm2javaCommon;

import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
public class CreatedSystemReaction extends AbstractReactionRealization {
  private CreateEObject<org.palladiosimulator.pcm.system.System> createChange;
  
  private InsertRootEObject<org.palladiosimulator.pcm.system.System> insertChange;
  
  private int currentlyMatchedChange;
  
  public CreatedSystemReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.system.System newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcm2javaCommon.CreatedSystemReaction.ActionUserExecution userExecution = new mir.reactions.pcm2javaCommon.CreatedSystemReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, newValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<org.palladiosimulator.pcm.system.System> _localTypedChange = (CreateEObject<org.palladiosimulator.pcm.system.System>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.system.System)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.palladiosimulator.pcm.system.System>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertRootEObject<?>) {
    	InsertRootEObject<org.palladiosimulator.pcm.system.System> _localTypedChange = (InsertRootEObject<org.palladiosimulator.pcm.system.System>) change;
    	if (!(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.system.System)) {
    		return false;
    	}
    	this.insertChange = (InsertRootEObject<org.palladiosimulator.pcm.system.System>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertRootEObject insertChange, final org.palladiosimulator.pcm.system.System newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      final org.palladiosimulator.pcm.system.System system = newValue;
      _routinesFacade.createJavaPackage(system, null, system.getEntityName(), "root_system");
      _routinesFacade.createImplementationForSystem(system);
    }
  }
}
