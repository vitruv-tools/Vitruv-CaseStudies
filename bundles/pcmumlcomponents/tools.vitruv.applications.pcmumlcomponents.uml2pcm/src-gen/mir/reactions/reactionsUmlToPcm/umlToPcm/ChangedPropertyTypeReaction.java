package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class ChangedPropertyTypeReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.DataType> typedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.DataType>)change;
    org.eclipse.uml2.uml.Property affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.DataType oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.DataType newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.DataType> relevantChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.DataType>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Property)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("type")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.DataType)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.DataType)) {
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
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Property affectedEObject, final EReference affectedFeature, final DataType oldValue, final DataType newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changePropertyType(affectedEObject, newValue);
    }
  }
}
