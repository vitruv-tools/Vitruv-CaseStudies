package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedInnerDeclarationReaction extends AbstractReactionRealization {
  public CreatedInnerDeclarationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration> typedChange = (CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.CreatedInnerDeclarationReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.CreatedInnerDeclarationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof InnerDeclaration)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof CompositeDataType)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("innerDeclaration_CompositeDataType")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof InnerDeclaration)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration> typedChange = (CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<CompositeDataType, InnerDeclaration> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<CompositeDataType, InnerDeclaration> _insertChange = change.getInsertChange();
      InnerDeclaration _newValue = _insertChange.getNewValue();
      _routinesFacade.createInnerDeclarationImplementation(_newValue);
    }
  }
}
