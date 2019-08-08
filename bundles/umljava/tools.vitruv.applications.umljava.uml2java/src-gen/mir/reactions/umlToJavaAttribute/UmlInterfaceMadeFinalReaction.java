package mir.reactions.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
public class UmlInterfaceMadeFinalReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEAttribute<Interface, Boolean> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UmlInterfaceMadeFinalReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Interface affectedEObject = replaceChange.getAffectedEObject();
    EAttribute affectedFeature = replaceChange.getAffectedFeature();
    java.lang.Boolean oldValue = replaceChange.getOldValue();
    java.lang.Boolean newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaAttribute.UmlInterfaceMadeFinalReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaAttribute.UmlInterfaceMadeFinalReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Interface, java.lang.Boolean> _localTypedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Interface, java.lang.Boolean>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("isFinalSpecialization")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof java.lang.Boolean)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof java.lang.Boolean)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Interface, java.lang.Boolean>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEAttribute replaceChange, final Interface affectedEObject, final EAttribute affectedFeature, final Boolean oldValue, final Boolean newValue) {
    return ((newValue).booleanValue() == true);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute replaceChange, final Interface affectedEObject, final EAttribute affectedFeature, final Boolean oldValue, final Boolean newValue, @Extension final RoutinesFacade _routinesFacade) {
      UmlToJavaHelper.showMessage(this.userInteractor, (("We do not support making final interfaces. Please change " + affectedEObject) + " to non-final. "));
    }
  }
}
