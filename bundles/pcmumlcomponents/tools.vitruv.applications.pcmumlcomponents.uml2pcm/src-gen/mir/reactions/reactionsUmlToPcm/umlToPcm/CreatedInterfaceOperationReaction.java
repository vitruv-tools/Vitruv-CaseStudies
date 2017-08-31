package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreatedInterfaceOperationReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Interface, org.eclipse.uml2.uml.Operation> typedChange = ((CreateAndInsertNonRoot<org.eclipse.uml2.uml.Interface, org.eclipse.uml2.uml.Operation>)change).getInsertChange();
    org.eclipse.uml2.uml.Interface affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Operation newValue = typedChange.getNewValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Interface, org.eclipse.uml2.uml.Operation> relevantChange = ((CreateAndInsertNonRoot<org.eclipse.uml2.uml.Interface, org.eclipse.uml2.uml.Operation>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Interface)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedOperation")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.Operation)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
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
    
    public void callRoutine1(final Interface affectedEObject, final EReference affectedFeature, final Operation newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createInterfaceOperation(newValue);
    }
  }
}
