package mir.reactions.reactionsUmlToUml.class2comp;

import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class InterfaceDeletedReaction extends AbstractReactionRealization {
  public InterfaceDeletedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization> typedChange = (RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization>)change;
    org.eclipse.uml2.uml.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    InterfaceRealization oldValue = typedChange.getOldValue();
    mir.routines.class2comp.RoutinesFacade routinesFacade = new mir.routines.class2comp.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.class2comp.InterfaceDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.class2comp.InterfaceDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization> relevantChange = (RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof InterfaceRealization)) {
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final InterfaceRealization oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteInterfaceRealization(oldValue);
    }
  }
}
