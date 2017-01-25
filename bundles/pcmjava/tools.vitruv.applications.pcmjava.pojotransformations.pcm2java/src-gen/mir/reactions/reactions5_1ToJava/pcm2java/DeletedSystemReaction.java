package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
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
    RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System> typedChange = (RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.DeletedSystemReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.DeletedSystemReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteRoot.class;
  }
  
  private boolean checkChangeProperties(final RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System> change) {
    if (!(change.getDeleteChange().getAffectedEObject() instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    if (!(change.getRemoveChange().getOldValue() instanceof org.palladiosimulator.pcm.system.System)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteRoot)) {
    	return false;
    }
    RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System> typedChange = (RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System>)change;
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
    
    public void callRoutine1(final RemoveAndDeleteRoot<org.palladiosimulator.pcm.system.System> change, @Extension final RoutinesFacade _routinesFacade) {
      RemoveRootEObject<org.palladiosimulator.pcm.system.System> _removeChange = change.getRemoveChange();
      final org.palladiosimulator.pcm.system.System system = _removeChange.getOldValue();
      String _entityName = system.getEntityName();
      _routinesFacade.deleteJavaPackage(system, _entityName, "root_system");
      _routinesFacade.deleteJavaClassifier(system);
    }
  }
}
