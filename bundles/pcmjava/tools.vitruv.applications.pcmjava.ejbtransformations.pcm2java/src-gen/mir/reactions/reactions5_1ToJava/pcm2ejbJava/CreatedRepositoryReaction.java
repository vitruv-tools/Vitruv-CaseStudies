package mir.reactions.reactions5_1ToJava.pcm2ejbJava;

import mir.routines.pcm2ejbJava.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedRepositoryReaction extends AbstractReactionRealization {
  public CreatedRepositoryReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertRootEObject<Repository> typedChange = ((CreateAndInsertRoot<Repository>)change).getInsertChange();
    Repository newValue = typedChange.getNewValue();
    mir.routines.pcm2ejbJava.RoutinesFacade routinesFacade = new mir.routines.pcm2ejbJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2ejbJava.CreatedRepositoryReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2ejbJava.CreatedRepositoryReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertRootEObject<Repository> relevantChange = ((CreateAndInsertRoot<Repository>)change).getInsertChange();
    if (!(relevantChange.getNewValue() instanceof Repository)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertRoot)) {
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
    
    public void callRoutine1(final Repository newValue, @Extension final RoutinesFacade _routinesFacade) {
      final Repository repository = newValue;
      String _entityName = repository.getEntityName();
      _routinesFacade.createJavaPackage(repository, null, _entityName, "repository_root");
      _routinesFacade.createRepositorySubPackages(repository);
    }
  }
}
