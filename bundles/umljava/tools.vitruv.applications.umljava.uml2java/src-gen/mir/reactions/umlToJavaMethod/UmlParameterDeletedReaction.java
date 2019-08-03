package mir.reactions.umlToJavaMethod;

import com.google.common.base.Objects;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class UmlParameterDeletedReaction extends AbstractReactionRealization {
  private RemoveEReference<Operation, Parameter> removeChange;
  
  private DeleteEObject<Parameter> deleteChange;
  
  private int currentlyMatchedChange;
  
  public UmlParameterDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Operation affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.eclipse.uml2.uml.Parameter oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(removeChange, affectedEObject, affectedFeature, oldValue, index)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaMethod.UmlParameterDeletedReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaMethod.UmlParameterDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.eclipse.uml2.uml.Parameter> _localTypedChange = (DeleteEObject<org.eclipse.uml2.uml.Parameter>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Parameter)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.eclipse.uml2.uml.Parameter>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    removeChange = null;
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> _localTypedChange = (RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Operation)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("ownedParameter")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.Parameter)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>) change;
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
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchDeleteChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final RemoveEReference removeChange, final Operation affectedEObject, final EReference affectedFeature, final Parameter oldValue, final int index) {
    return (Objects.equal(oldValue.getDirection(), ParameterDirectionKind.IN_LITERAL) || Objects.equal(oldValue.getDirection(), ParameterDirectionKind.INOUT_LITERAL));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference removeChange, final Operation affectedEObject, final EReference affectedFeature, final Parameter oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaParameter(oldValue);
    }
  }
}
