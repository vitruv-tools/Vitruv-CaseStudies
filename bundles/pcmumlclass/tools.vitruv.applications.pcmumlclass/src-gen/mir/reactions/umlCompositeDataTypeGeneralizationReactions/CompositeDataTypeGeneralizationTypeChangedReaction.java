package mir.reactions.umlCompositeDataTypeGeneralizationReactions;

import mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class CompositeDataTypeGeneralizationTypeChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<Generalization, Classifier> replaceChange;
  
  private int currentlyMatchedChange;
  
  public CompositeDataTypeGeneralizationTypeChangedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Generalization affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.eclipse.uml2.uml.Classifier oldValue = replaceChange.getOldValue();
    org.eclipse.uml2.uml.Classifier newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlCompositeDataTypeGeneralizationReactions.CompositeDataTypeGeneralizationTypeChangedReaction.ActionUserExecution userExecution = new mir.reactions.umlCompositeDataTypeGeneralizationReactions.CompositeDataTypeGeneralizationTypeChangedReaction.ActionUserExecution(this.executionState, this);
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
    if (change instanceof ReplaceSingleValuedEReference<?, ?>) {
    	ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Classifier> _localTypedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Classifier>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Generalization)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("general")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.Classifier)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Classifier)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Classifier>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final Generalization affectedEObject, final EReference affectedFeature, final Classifier oldValue, final Classifier newValue) {
    Classifier _specific = affectedEObject.getSpecific();
    return (_specific instanceof org.eclipse.uml2.uml.Class);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final Generalization affectedEObject, final EReference affectedFeature, final Classifier oldValue, final Classifier newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.replaceCollectionDatatypeParent(affectedEObject, newValue, oldValue);
    }
  }
}
