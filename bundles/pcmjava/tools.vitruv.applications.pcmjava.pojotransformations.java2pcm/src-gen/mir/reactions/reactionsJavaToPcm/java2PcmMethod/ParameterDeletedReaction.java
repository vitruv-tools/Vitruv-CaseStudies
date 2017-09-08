package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class ParameterDeletedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.emftext.language.java.parameters.Parametrizable, org.emftext.language.java.parameters.OrdinaryParameter> typedChange = (RemoveEReference<org.emftext.language.java.parameters.Parametrizable, org.emftext.language.java.parameters.OrdinaryParameter>)change;
    org.emftext.language.java.parameters.Parametrizable affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.parameters.OrdinaryParameter oldValue = typedChange.getOldValue();
    mir.routines.java2PcmMethod.RoutinesFacade routinesFacade = new mir.routines.java2PcmMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.emftext.language.java.parameters.Parametrizable, org.emftext.language.java.parameters.OrdinaryParameter> relevantChange = (RemoveEReference<org.emftext.language.java.parameters.Parametrizable, org.emftext.language.java.parameters.OrdinaryParameter>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.parameters.Parametrizable)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("parameters")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.emftext.language.java.parameters.OrdinaryParameter)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parametrizable affectedEObject, final EReference affectedFeature, final OrdinaryParameter oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteParameter(oldValue);
    }
  }
}
