package mir.reactions.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class UmlInterfaceRealizationReplacedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<InterfaceRealization, Interface> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UmlInterfaceRealizationReplacedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.InterfaceRealization affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.eclipse.uml2.uml.Interface oldValue = replaceChange.getOldValue();
    org.eclipse.uml2.uml.Interface newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaClassifier.UmlInterfaceRealizationReplacedReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaClassifier.UmlInterfaceRealizationReplacedReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface> _localTypedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("contract")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final InterfaceRealization affectedEObject, final EReference affectedFeature, final Interface oldValue, final Interface newValue) {
    return ((oldValue != null) && (affectedEObject.getImplementingClassifier() instanceof org.eclipse.uml2.uml.Class));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final InterfaceRealization affectedEObject, final EReference affectedFeature, final Interface oldValue, final Interface newValue, @Extension final RoutinesFacade _routinesFacade) {
      BehavioredClassifier _implementingClassifier = affectedEObject.getImplementingClassifier();
      _routinesFacade.replaceJavaClassImplementsReference(affectedEObject, ((org.eclipse.uml2.uml.Class) _implementingClassifier));
    }
  }
}
