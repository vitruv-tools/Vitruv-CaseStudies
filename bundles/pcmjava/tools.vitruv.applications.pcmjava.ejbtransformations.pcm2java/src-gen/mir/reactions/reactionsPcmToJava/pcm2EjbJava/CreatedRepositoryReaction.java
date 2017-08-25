package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import mir.routines.pcm2EjbJava.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
class CreatedRepositoryReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertRootEObject<org.palladiosimulator.pcm.repository.Repository> typedChange = ((CreateAndInsertRoot<org.palladiosimulator.pcm.repository.Repository>)change).getInsertChange();
    org.palladiosimulator.pcm.repository.Repository newValue = typedChange.getNewValue();
    mir.routines.pcm2EjbJava.RoutinesFacade routinesFacade = new mir.routines.pcm2EjbJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertRootEObject<org.palladiosimulator.pcm.repository.Repository> relevantChange = ((CreateAndInsertRoot<org.palladiosimulator.pcm.repository.Repository>)change).getInsertChange();
    if (!(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.Repository)) {
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
      _routinesFacade.createJavaPackage(repository, null, repository.getEntityName(), "repository_root");
      _routinesFacade.createRepositorySubPackages(repository);
    }
  }
}
