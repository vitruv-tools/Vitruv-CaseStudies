package mir.reactions.reactionsUMLTo5_1.umlToPcm;

import com.google.common.base.Objects;
import mir.routines.umlToPcm.RoutinesFacade;
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
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangedParameterTypeReaction extends AbstractReactionRealization {
  public ChangedParameterTypeReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<Parameter, Type> typedChange = (ReplaceSingleValuedEReference<Parameter, Type>)change;
    Parameter affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Type oldValue = typedChange.getOldValue();
    Type newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<Parameter, Type> relevantChange = (ReplaceSingleValuedEReference<Parameter, Type>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Parameter)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("type")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof Type)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof Type)) {
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
    ReplaceSingleValuedEReference<Parameter, Type> typedChange = (ReplaceSingleValuedEReference<Parameter, Type>)change;
    Parameter affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Type oldValue = typedChange.getOldValue();
    Type newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Parameter affectedEObject, final EReference affectedFeature, final Type oldValue, final Type newValue) {
    ParameterDirectionKind _direction = affectedEObject.getDirection();
    boolean _notEquals = (!Objects.equal(_direction, ParameterDirectionKind.RETURN_LITERAL));
    return _notEquals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parameter affectedEObject, final EReference affectedFeature, final Type oldValue, final Type newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeParameterType(affectedEObject);
    }
  }
}
