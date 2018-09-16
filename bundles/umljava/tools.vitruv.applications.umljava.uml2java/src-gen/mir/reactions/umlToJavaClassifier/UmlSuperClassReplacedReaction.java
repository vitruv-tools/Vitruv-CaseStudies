package mir.reactions.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
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
public class UmlSuperClassReplacedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<Generalization, org.eclipse.uml2.uml.Class> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UmlSuperClassReplacedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Generalization affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.eclipse.uml2.uml.Class oldValue = replaceChange.getOldValue();
    org.eclipse.uml2.uml.Class newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaClassifier.UmlSuperClassReplacedReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaClassifier.UmlSuperClassReplacedReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Class> _localTypedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Class>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Generalization)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("general")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Generalization, org.eclipse.uml2.uml.Class>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final Generalization affectedEObject, final EReference affectedFeature, final org.eclipse.uml2.uml.Class oldValue, final org.eclipse.uml2.uml.Class newValue) {
    return ((affectedEObject.getSpecific() != null) && (affectedEObject.getSpecific() instanceof org.eclipse.uml2.uml.Class));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final Generalization affectedEObject, final EReference affectedFeature, final org.eclipse.uml2.uml.Class oldValue, final org.eclipse.uml2.uml.Class newValue, @Extension final RoutinesFacade _routinesFacade) {
      final Generalization uGeneralization = affectedEObject;
      Classifier _specific = affectedEObject.getSpecific();
      final org.eclipse.uml2.uml.Class uClass = ((org.eclipse.uml2.uml.Class) _specific);
      if ((oldValue != null)) {
        _routinesFacade.deleteJavaSuperClass(uGeneralization);
      }
      if ((newValue != null)) {
        _routinesFacade.addJavaSuperClass(uClass, uGeneralization);
      }
    }
  }
}
