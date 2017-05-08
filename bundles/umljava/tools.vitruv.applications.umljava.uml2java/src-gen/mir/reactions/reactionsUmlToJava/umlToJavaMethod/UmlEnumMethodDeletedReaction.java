package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class UmlEnumMethodDeletedReaction extends AbstractReactionRealization {
  public UmlEnumMethodDeletedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<Enumeration, Operation> typedChange = ((RemoveAndDeleteNonRoot<Enumeration, Operation>)change).getRemoveChange();
    Enumeration affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Operation oldValue = typedChange.getOldValue();
    mir.routines.umlToJavaMethod.RoutinesFacade routinesFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlEnumMethodDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlEnumMethodDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<Enumeration, Operation> relevantChange = ((RemoveAndDeleteNonRoot<Enumeration, Operation>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof Enumeration)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedOperation")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof Operation)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
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
    
    public void callRoutine1(final Enumeration affectedEObject, final EReference affectedFeature, final Operation oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaMethod(oldValue);
    }
  }
}
