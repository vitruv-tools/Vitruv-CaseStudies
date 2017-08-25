package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreatedInterfaceRealizationRelationshipReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.InterfaceRealization> typedChange = (InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.InterfaceRealization>)change;
    org.eclipse.uml2.uml.Component affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.InterfaceRealization newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.InterfaceRealization> relevantChange = (InsertEReference<org.eclipse.uml2.uml.Component, org.eclipse.uml2.uml.InterfaceRealization>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Component)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
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
    
    public void callRoutine1(final Component affectedEObject, final EReference affectedFeature, final InterfaceRealization newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createProvidedRole(affectedEObject, newValue);
    }
  }
}
