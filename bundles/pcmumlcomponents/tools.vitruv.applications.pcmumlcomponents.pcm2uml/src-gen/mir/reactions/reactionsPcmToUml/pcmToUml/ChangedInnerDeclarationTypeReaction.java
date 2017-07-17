package mir.reactions.reactionsPcmToUml.pcmToUml;

import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class ChangedInnerDeclarationTypeReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<InnerDeclaration, DataType> typedChange = (ReplaceSingleValuedEReference<InnerDeclaration, DataType>)change;
    InnerDeclaration affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    DataType oldValue = typedChange.getOldValue();
    DataType newValue = typedChange.getNewValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<InnerDeclaration, DataType> relevantChange = (ReplaceSingleValuedEReference<InnerDeclaration, DataType>)change;
    if (!(relevantChange.getAffectedEObject() instanceof InnerDeclaration)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("datatype_InnerDeclaration")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof DataType)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof DataType)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
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
    
    public void callRoutine1(final InnerDeclaration affectedEObject, final EReference affectedFeature, final DataType oldValue, final DataType newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeInnerDeclarationType(affectedEObject, newValue);
    }
  }
}
