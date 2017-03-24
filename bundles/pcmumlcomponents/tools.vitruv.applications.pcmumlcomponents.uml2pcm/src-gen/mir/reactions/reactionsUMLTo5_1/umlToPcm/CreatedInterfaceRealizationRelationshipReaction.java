package mir.reactions.reactionsUMLTo5_1.umlToPcm;

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
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedInterfaceRealizationRelationshipReaction extends AbstractReactionRealization {
  public CreatedInterfaceRealizationRelationshipReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<Component, InterfaceRealization> typedChange = (InsertEReference<Component, InterfaceRealization>)change;
    Component affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    InterfaceRealization newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceRealizationRelationshipReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceRealizationRelationshipReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<Component, InterfaceRealization> relevantChange = (InsertEReference<Component, InterfaceRealization>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Component)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof InterfaceRealization)) {
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
