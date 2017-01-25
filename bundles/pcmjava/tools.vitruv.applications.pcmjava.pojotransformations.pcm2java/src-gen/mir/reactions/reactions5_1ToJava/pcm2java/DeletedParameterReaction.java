package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedParameterReaction extends AbstractReactionRealization {
  public DeletedParameterReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<OperationSignature, Parameter> typedChange = (RemoveAndDeleteNonRoot<OperationSignature, Parameter>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedParameterReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedParameterReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<OperationSignature, Parameter> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof Parameter)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof OperationSignature)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("parameters__OperationSignature")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof Parameter)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<OperationSignature, Parameter> typedChange = (RemoveAndDeleteNonRoot<OperationSignature, Parameter>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<OperationSignature, Parameter> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<OperationSignature, Parameter> _removeChange = change.getRemoveChange();
      OperationSignature _affectedEObject = _removeChange.getAffectedEObject();
      RemoveEReference<OperationSignature, Parameter> _removeChange_1 = change.getRemoveChange();
      Parameter _oldValue = _removeChange_1.getOldValue();
      _routinesFacade.deleteParameter(_affectedEObject, _oldValue);
    }
  }
}
