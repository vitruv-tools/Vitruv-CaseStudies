package mir.reactions.reactionsPcmToUml.pcmToUml;

import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
class ChangedParameterDirectionReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier> typedChange = (ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier>)change;
    org.palladiosimulator.pcm.repository.Parameter affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.ParameterModifier oldValue = typedChange.getOldValue();
    org.palladiosimulator.pcm.repository.ParameterModifier newValue = typedChange.getNewValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier> relevantChange = (ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.repository.Parameter, org.palladiosimulator.pcm.repository.ParameterModifier>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.Parameter)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("modifier__Parameter")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.ParameterModifier)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.ParameterModifier)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
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
    
    public void callRoutine1(final Parameter affectedEObject, final EAttribute affectedFeature, final ParameterModifier oldValue, final ParameterModifier newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeParameterDirection(affectedEObject);
    }
  }
}
