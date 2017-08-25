package mir.reactions.reactionsPcmToUml.pcmToUml;

import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class RemovedCompositeDataTypeParentReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType> typedChange = (RemoveEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType>)change;
    org.palladiosimulator.pcm.repository.CompositeDataType affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.CompositeDataType oldValue = typedChange.getOldValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType> relevantChange = (RemoveEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.CompositeDataType)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("parentType_CompositeDataType")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.CompositeDataType)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
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
    
    public void callRoutine1(final CompositeDataType affectedEObject, final EReference affectedFeature, final CompositeDataType oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeCompositeDataTypeParent(affectedEObject, oldValue);
    }
  }
}
