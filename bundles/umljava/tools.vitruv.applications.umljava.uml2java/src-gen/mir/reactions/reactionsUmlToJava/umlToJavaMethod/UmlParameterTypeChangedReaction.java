package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import com.google.common.base.Objects;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class UmlParameterTypeChangedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type> typedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type>)change;
    org.eclipse.uml2.uml.Parameter affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Type oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.Type newValue = typedChange.getNewValue();
    mir.routines.umlToJavaMethod.RoutinesFacade routinesFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type> relevantChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Parameter)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("type")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Type)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.Type)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type> typedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Parameter, org.eclipse.uml2.uml.Type>)change;
    org.eclipse.uml2.uml.Parameter affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Type oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.Type newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Parameter affectedEObject, final EReference affectedFeature, final Type oldValue, final Type newValue) {
    ParameterDirectionKind _direction = affectedEObject.getDirection();
    boolean _equals = Objects.equal(_direction, ParameterDirectionKind.IN_LITERAL);
    return _equals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parameter affectedEObject, final EReference affectedFeature, final Type oldValue, final Type newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeJavaParameterType(affectedEObject, newValue);
    }
  }
}
