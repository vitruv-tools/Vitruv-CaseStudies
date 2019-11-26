package mir.reactions.umlXpcmInterface_R2L;

import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction extends AbstractReactionRealization {
  private RemoveEReference<Interface, OperationInterface> removeChange;
  
  private int currentlyMatchedChange;
  
  public OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Interface affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationInterface oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.palladiosimulator.pcm.repository.Interface, org.palladiosimulator.pcm.repository.OperationInterface> _localTypedChange = (RemoveEReference<org.palladiosimulator.pcm.repository.Interface, org.palladiosimulator.pcm.repository.OperationInterface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.Interface)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("parentInterfaces__Interface")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationInterface)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.palladiosimulator.pcm.repository.Interface, org.palladiosimulator.pcm.repository.OperationInterface>) change;
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
    
    public void callRoutine1(final RemoveEReference removeChange, final Interface affectedEObject, final EReference affectedFeature, final OperationInterface oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterfaceParent_ElementDeletedCheck(affectedEObject);
    }
  }
}
