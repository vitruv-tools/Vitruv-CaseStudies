package mir.reactions.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
public class UmlLowerMultiplicityChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEAttribute<LiteralInteger, Integer> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UmlLowerMultiplicityChangedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.LiteralInteger affectedEObject = replaceChange.getAffectedEObject();
    EAttribute affectedFeature = replaceChange.getAffectedFeature();
    java.lang.Integer oldValue = replaceChange.getOldValue();
    java.lang.Integer newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.LiteralInteger, java.lang.Integer> _localTypedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.LiteralInteger, java.lang.Integer>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.LiteralInteger)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("value")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof java.lang.Integer)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof java.lang.Integer)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.LiteralInteger, java.lang.Integer>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEAttribute replaceChange, final LiteralInteger affectedEObject, final EAttribute affectedFeature, final Integer oldValue, final Integer newValue) {
    Element _owner = affectedEObject.getOwner();
    return (_owner instanceof Property);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute replaceChange, final LiteralInteger affectedEObject, final EAttribute affectedFeature, final Integer oldValue, final Integer newValue, @Extension final RoutinesFacade _routinesFacade) {
      Element _owner = affectedEObject.getOwner();
      _routinesFacade.changeJavaAttributeType(((Property) _owner));
    }
  }
}
