package mir.reactions.reactions5_1ToJava.pcm2depInjectJava;

import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedSystemReaction extends AbstractReactionRealization {
  public DeletedSystemReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveRootEObject<org.palladiosimulator.pcm.system.System> typedChange = ((RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System>)change).getRemoveChange();
    org.palladiosimulator.pcm.system.System oldValue = typedChange.getOldValue();
    mir.routines.pcm2depInjectJava.RoutinesFacade routinesFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSystemReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSystemReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveRootEObject<org.palladiosimulator.pcm.system.System> relevantChange = ((RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System>)change).getRemoveChange();
    if (!(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteRoot)) {
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
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System oldValue, @Extension final RoutinesFacade _routinesFacade) {
      final org.palladiosimulator.pcm.system.System system = oldValue;
      String _entityName = system.getEntityName();
      _routinesFacade.deleteJavaPackage(system, _entityName, "root_system");
      _routinesFacade.deleteJavaClassifier(system);
    }
  }
}
