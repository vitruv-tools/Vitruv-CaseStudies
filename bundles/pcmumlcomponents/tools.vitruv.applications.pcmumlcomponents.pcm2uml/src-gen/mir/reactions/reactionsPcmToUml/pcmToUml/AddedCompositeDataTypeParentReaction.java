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
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class AddedCompositeDataTypeParentReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType> typedChange = (InsertEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType>)change;
    org.palladiosimulator.pcm.repository.CompositeDataType affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.CompositeDataType newValue = typedChange.getNewValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType> relevantChange = (InsertEReference<org.palladiosimulator.pcm.repository.CompositeDataType, org.palladiosimulator.pcm.repository.CompositeDataType>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.CompositeDataType)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("parentType_CompositeDataType")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.CompositeDataType)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
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
    
    public void callRoutine1(final CompositeDataType affectedEObject, final EReference affectedFeature, final CompositeDataType newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addCompositeDataTypeParent(affectedEObject, newValue);
    }
  }
}
