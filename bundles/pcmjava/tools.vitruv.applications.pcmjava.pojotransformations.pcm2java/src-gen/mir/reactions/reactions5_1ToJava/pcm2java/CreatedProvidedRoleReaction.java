package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedProvidedRoleReaction extends AbstractReactionRealization {
  public CreatedProvidedRoleReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole> typedChange = (CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.CreatedProvidedRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.CreatedProvidedRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof OperationProvidedRole)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof InterfaceProvidingEntity)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof OperationProvidedRole)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole> typedChange = (CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<InterfaceProvidingEntity, OperationProvidedRole> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<InterfaceProvidingEntity, OperationProvidedRole> _insertChange = change.getInsertChange();
      OperationProvidedRole _newValue = _insertChange.getNewValue();
      _routinesFacade.addProvidedRole(_newValue);
    }
  }
}
