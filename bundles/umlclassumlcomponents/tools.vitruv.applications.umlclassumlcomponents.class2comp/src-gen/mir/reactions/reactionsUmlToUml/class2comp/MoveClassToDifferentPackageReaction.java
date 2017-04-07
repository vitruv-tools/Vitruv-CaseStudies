package mir.reactions.reactionsUmlToUml.class2comp;

import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class MoveClassToDifferentPackageReaction extends AbstractReactionRealization {
  public MoveClassToDifferentPackageReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Package> typedChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Package>)change;
    org.eclipse.uml2.uml.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Package oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.Package newValue = typedChange.getNewValue();
    mir.routines.class2comp.RoutinesFacade routinesFacade = new mir.routines.class2comp.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Package> relevantChange = (ReplaceSingleValuedEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Package>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("package")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Package)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.Package)) {
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final org.eclipse.uml2.uml.Package oldValue, final org.eclipse.uml2.uml.Package newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.movedClassToDifferentPackage(affectedEObject, oldValue, newValue);
    }
  }
}
