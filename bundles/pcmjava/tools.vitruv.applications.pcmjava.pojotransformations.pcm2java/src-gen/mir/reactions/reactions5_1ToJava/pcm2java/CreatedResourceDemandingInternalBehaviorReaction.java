package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedResourceDemandingInternalBehaviorReaction extends AbstractReactionRealization {
  public CreatedResourceDemandingInternalBehaviorReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> typedChange = (CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof ResourceDemandingInternalBehaviour)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof BasicComponent)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("resourceDemandingInternalBehaviours__BasicComponent")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof ResourceDemandingInternalBehaviour)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> typedChange = (CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<BasicComponent, ResourceDemandingInternalBehaviour> _insertChange = change.getInsertChange();
      ResourceDemandingInternalBehaviour _newValue = _insertChange.getNewValue();
      _routinesFacade.createMethodForResourceDemandingBehavior(_newValue);
    }
  }
}
