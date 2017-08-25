package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
class ChangedMultiplicityUpperReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.MultiplicityElement, java.lang.Integer> typedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.MultiplicityElement, java.lang.Integer>)change;
    org.eclipse.uml2.uml.MultiplicityElement affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    java.lang.Integer oldValue = typedChange.getOldValue();
    java.lang.Integer newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.MultiplicityElement, java.lang.Integer> relevantChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.MultiplicityElement, java.lang.Integer>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.MultiplicityElement)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("upper")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof java.lang.Integer)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof java.lang.Integer)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
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
    
    public void callRoutine1(final MultiplicityElement affectedEObject, final EAttribute affectedFeature, final Integer oldValue, final Integer newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateMultiplicityType(affectedEObject);
    }
  }
}
