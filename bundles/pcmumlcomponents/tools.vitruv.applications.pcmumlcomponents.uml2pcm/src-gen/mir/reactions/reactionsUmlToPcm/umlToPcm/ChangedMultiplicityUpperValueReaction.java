package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class ChangedMultiplicityUpperValueReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.MultiplicityElement, org.eclipse.uml2.uml.ValueSpecification> typedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.MultiplicityElement, org.eclipse.uml2.uml.ValueSpecification>)change;
    org.eclipse.uml2.uml.MultiplicityElement affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.ValueSpecification oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.ValueSpecification newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.MultiplicityElement, org.eclipse.uml2.uml.ValueSpecification> relevantChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.MultiplicityElement, org.eclipse.uml2.uml.ValueSpecification>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.MultiplicityElement)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("upperValue")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.ValueSpecification)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.ValueSpecification)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
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
    
    public void callRoutine1(final MultiplicityElement affectedEObject, final EReference affectedFeature, final ValueSpecification oldValue, final ValueSpecification newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateMultiplicityType(affectedEObject);
    }
  }
}
