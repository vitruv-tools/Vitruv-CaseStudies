package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedOperationSignatureReaction extends AbstractReactionRealization {
  public DeletedOperationSignatureReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<OperationInterface, OperationSignature> typedChange = (RemoveAndDeleteNonRoot<OperationInterface, OperationSignature>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedOperationSignatureReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedOperationSignatureReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<OperationInterface, OperationSignature> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof OperationSignature)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof OperationInterface)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("signatures__OperationInterface")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof OperationSignature)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<OperationInterface, OperationSignature> typedChange = (RemoveAndDeleteNonRoot<OperationInterface, OperationSignature>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<OperationInterface, OperationSignature> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<OperationInterface, OperationSignature> _removeChange = change.getRemoveChange();
      OperationSignature _oldValue = _removeChange.getOldValue();
      _routinesFacade.deleteMethodForOperationSignature(_oldValue);
    }
  }
}
