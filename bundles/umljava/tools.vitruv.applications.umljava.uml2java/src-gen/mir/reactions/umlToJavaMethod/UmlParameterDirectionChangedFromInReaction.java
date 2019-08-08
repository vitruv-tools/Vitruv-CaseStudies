package mir.reactions.umlToJavaMethod;

import com.google.common.base.Objects;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
public class UmlParameterDirectionChangedFromInReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEAttribute<Parameter, ParameterDirectionKind> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UmlParameterDirectionChangedFromInReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Parameter affectedEObject = replaceChange.getAffectedEObject();
    EAttribute affectedFeature = replaceChange.getAffectedFeature();
    org.eclipse.uml2.uml.ParameterDirectionKind oldValue = replaceChange.getOldValue();
    org.eclipse.uml2.uml.ParameterDirectionKind newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromInReaction.ActionUserExecution userExecution = new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromInReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.ParameterDirectionKind> _localTypedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.ParameterDirectionKind>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Parameter)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("direction")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.ParameterDirectionKind)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.ParameterDirectionKind)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.ParameterDirectionKind>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEAttribute replaceChange, final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterDirectionKind oldValue, final ParameterDirectionKind newValue) {
    boolean _equals = Objects.equal(oldValue, ParameterDirectionKind.IN_LITERAL);
    return _equals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute replaceChange, final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterDirectionKind oldValue, final ParameterDirectionKind newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaParameter(affectedEObject);
    }
  }
}
