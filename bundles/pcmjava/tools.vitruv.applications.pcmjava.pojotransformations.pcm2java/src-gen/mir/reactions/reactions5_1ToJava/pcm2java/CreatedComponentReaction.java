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
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedComponentReaction extends AbstractReactionRealization {
  public CreatedComponentReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<Repository, RepositoryComponent> typedChange = (CreateAndInsertNonRoot<Repository, RepositoryComponent>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.CreatedComponentReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.CreatedComponentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<Repository, RepositoryComponent> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof RepositoryComponent)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof Repository)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("components__Repository")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof RepositoryComponent)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<Repository, RepositoryComponent> typedChange = (CreateAndInsertNonRoot<Repository, RepositoryComponent>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<Repository, RepositoryComponent> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<Repository, RepositoryComponent> _insertChange = change.getInsertChange();
      RepositoryComponent _newValue = _insertChange.getNewValue();
      _routinesFacade.createComponentImplementation(_newValue);
    }
  }
}
