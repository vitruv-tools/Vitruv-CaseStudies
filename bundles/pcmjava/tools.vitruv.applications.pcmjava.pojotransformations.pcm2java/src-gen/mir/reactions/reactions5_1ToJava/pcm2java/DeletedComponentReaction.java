package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedComponentReaction extends AbstractReactionRealization {
  public DeletedComponentReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveAndDeleteNonRoot<Repository, RepositoryComponent> typedChange = (RemoveAndDeleteNonRoot<Repository, RepositoryComponent>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedComponentReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedComponentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteNonRoot<Repository, RepositoryComponent> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof RepositoryComponent)) {
    	return false;
    }
    // Check affected object
    if (!(change.getRemoveChange().getAffectedEObject() instanceof Repository)) {
    	return false;
    }
    // Check feature
    if (!change.getRemoveChange().getAffectedFeature().getName().equals("components__Repository")) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof RepositoryComponent)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
    	return false;
    }
    RemoveAndDeleteNonRoot<Repository, RepositoryComponent> typedChange = (RemoveAndDeleteNonRoot<Repository, RepositoryComponent>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteNonRoot<Repository, RepositoryComponent> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveEReference<Repository, RepositoryComponent> _removeChange = change.getRemoveChange();
      RepositoryComponent _oldValue = _removeChange.getOldValue();
      RemoveEReference<Repository, RepositoryComponent> _removeChange_1 = change.getRemoveChange();
      RepositoryComponent _oldValue_1 = _removeChange_1.getOldValue();
      String _entityName = _oldValue_1.getEntityName();
      _routinesFacade.deleteJavaPackage(_oldValue, _entityName, "");
      RemoveEReference<Repository, RepositoryComponent> _removeChange_2 = change.getRemoveChange();
      RepositoryComponent _oldValue_2 = _removeChange_2.getOldValue();
      _routinesFacade.deleteJavaClassifier(_oldValue_2);
    }
  }
}
