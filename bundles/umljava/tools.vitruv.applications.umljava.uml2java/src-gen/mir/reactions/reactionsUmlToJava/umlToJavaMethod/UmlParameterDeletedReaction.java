package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

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
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class UmlParameterDeletedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> typedChange = ((RemoveAndDeleteNonRoot<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>)change).getRemoveChange();
    org.eclipse.uml2.uml.Operation affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Parameter oldValue = typedChange.getOldValue();
    mir.routines.umlToJavaMethod.RoutinesFacade routinesFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> relevantChange = ((RemoveAndDeleteNonRoot<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Operation)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedParameter")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Parameter)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> typedChange = ((RemoveAndDeleteNonRoot<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>)change).getRemoveChange();
    org.eclipse.uml2.uml.Operation affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Parameter oldValue = typedChange.getOldValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Operation affectedEObject, final EReference affectedFeature, final Parameter oldValue) {
    ParameterDirectionKind _direction = oldValue.getDirection();
    boolean _equals = Objects.equal(_direction, ParameterDirectionKind.IN_LITERAL);
    return _equals;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Operation affectedEObject, final EReference affectedFeature, final Parameter oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaParameter(oldValue);
    }
  }
}
