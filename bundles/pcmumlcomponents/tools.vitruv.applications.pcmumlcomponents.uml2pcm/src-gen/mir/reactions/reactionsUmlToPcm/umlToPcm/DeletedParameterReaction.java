package mir.reactions.reactionsUmlToPcm.umlToPcm;

import com.google.common.base.Objects;
import mir.routines.umlToPcm.RoutinesFacade;
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
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedParameterReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> typedChange = (RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>)change;
    org.eclipse.uml2.uml.Operation affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Parameter oldValue = typedChange.getOldValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter> relevantChange = (RemoveEReference<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.Parameter>)change;
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
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Operation affectedEObject, final EReference affectedFeature, final Parameter oldValue, @Extension final RoutinesFacade _routinesFacade) {
      ParameterDirectionKind _direction = oldValue.getDirection();
      boolean _equals = Objects.equal(_direction, ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        _routinesFacade.unsetInterfaceOperationType(affectedEObject);
      } else {
        _routinesFacade.deleteParameter(oldValue);
      }
    }
  }
}
