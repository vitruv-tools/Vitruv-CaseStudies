package mir.reactions.pcmParameterReactions;

import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
public class ParameterModifierChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEAttribute<Parameter, ParameterModifier> replaceChange;
  
  private int currentlyMatchedChange;
  
  public ParameterModifierChangedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.Parameter affectedEObject = replaceChange.getAffectedEObject();
    EAttribute affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.ParameterModifier oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.ParameterModifier newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmParameterReactions.ParameterModifierChangedReaction.ActionUserExecution userExecution = new mir.reactions.pcmParameterReactions.ParameterModifierChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(replaceChange, affectedEObject, affectedFeature, oldValue, newValue, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    replaceChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchReplaceChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchReplaceChange(final EChange change) {
    if (change instanceof ReplaceSingleValuedEAttribute<?, ?>) {
    	ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier> _localTypedChange = (ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.Parameter)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("modifier__Parameter")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.ParameterModifier)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.ParameterModifier)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEAttribute replaceChange, final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterModifier oldValue, final ParameterModifier newValue) {
    ParameterModifier _modifier__Parameter = affectedEObject.getModifier__Parameter();
    boolean _tripleEquals = (_modifier__Parameter == newValue);
    return _tripleEquals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute replaceChange, final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterModifier oldValue, final ParameterModifier newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeDirectionOfCorrespondingRegularParameter(affectedEObject);
    }
  }
}
