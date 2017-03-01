package mir.reactions.reactions5_1ToUML.pcmToUml;

import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedInnerDeclarationReaction extends AbstractReactionRealization {
  public DeletedInnerDeclarationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<CompositeDataType, InnerDeclaration> typedChange = (RemoveEReference<CompositeDataType, InnerDeclaration>)change;
    CompositeDataType affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    InnerDeclaration oldValue = typedChange.getOldValue();
    mir.routines.pcmToUml.RoutinesFacade routinesFacade = new mir.routines.pcmToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToUML.pcmToUml.DeletedInnerDeclarationReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedInnerDeclarationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<CompositeDataType, InnerDeclaration> relevantChange = (RemoveEReference<CompositeDataType, InnerDeclaration>)change;
    if (!(relevantChange.getAffectedEObject() instanceof CompositeDataType)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("innerDeclaration_CompositeDataType")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof InnerDeclaration)) {
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
    
    public void callRoutine1(final CompositeDataType affectedEObject, final EReference affectedFeature, final InnerDeclaration oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteInnerDeclaration(oldValue);
    }
  }
}
