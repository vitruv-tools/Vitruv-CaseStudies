package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class AddedUsesRelationshipReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.Usage> typedChange = (InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.Usage>)change;
    org.eclipse.uml2.uml.Component affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Usage newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.Usage> relevantChange = (InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.Usage>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Component)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("packagedElement")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.Usage)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
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
    
    public void callRoutine1(final Component affectedEObject, final EReference affectedFeature, final Usage newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createRequiredRole(affectedEObject, newValue);
    }
  }
}
