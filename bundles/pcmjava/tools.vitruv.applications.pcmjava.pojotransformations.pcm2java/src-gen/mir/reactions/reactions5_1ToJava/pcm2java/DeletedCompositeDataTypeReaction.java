package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedCompositeDataTypeReaction extends AbstractReactionRealization {
  public DeletedCompositeDataTypeReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<Repository, CompositeDataType> typedChange = (RemoveAndDeleteNonRoot<Repository, CompositeDataType>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedCompositeDataTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedCompositeDataTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<Repository, CompositeDataType> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof CompositeDataType)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof Repository)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("dataTypes__Repository")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof CompositeDataType)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<Repository, CompositeDataType> typedChange = (RemoveAndDeleteNonRoot<Repository, CompositeDataType>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<Repository, CompositeDataType> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<Repository, CompositeDataType> _removeChange = change.getRemoveChange();
      CompositeDataType _oldValue = _removeChange.getOldValue();
      _routinesFacade.deleteJavaClassifier(_oldValue);
    }
  }
}
