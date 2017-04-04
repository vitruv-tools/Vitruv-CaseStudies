package mir.reactions.reactionsPcmToJava.pcm2ejbJava;

import mir.routines.pcm2ejbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
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
    InsertEReference<BasicComponent, ResourceDemandingInternalBehaviour> typedChange = ((CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change).getInsertChange();
    BasicComponent affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ResourceDemandingInternalBehaviour newValue = typedChange.getNewValue();
    mir.routines.pcm2ejbJava.RoutinesFacade routinesFacade = new mir.routines.pcm2ejbJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedResourceDemandingInternalBehaviorReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedResourceDemandingInternalBehaviorReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<BasicComponent, ResourceDemandingInternalBehaviour> relevantChange = ((CreateAndInsertNonRoot<BasicComponent, ResourceDemandingInternalBehaviour>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof BasicComponent)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("resourceDemandingInternalBehaviours__BasicComponent")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof ResourceDemandingInternalBehaviour)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
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
    
    public void callRoutine1(final BasicComponent affectedEObject, final EReference affectedFeature, final ResourceDemandingInternalBehaviour newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createMethodForResourceDemandingBehavior(newValue);
    }
  }
}
