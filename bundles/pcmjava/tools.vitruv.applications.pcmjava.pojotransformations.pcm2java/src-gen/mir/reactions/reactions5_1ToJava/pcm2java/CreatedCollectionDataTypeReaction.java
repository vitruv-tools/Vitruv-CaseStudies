package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedCollectionDataTypeReaction extends AbstractReactionRealization {
  public CreatedCollectionDataTypeReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<Repository, CollectionDataType> typedChange = (CreateAndInsertNonRoot<Repository, CollectionDataType>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.CreatedCollectionDataTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.CreatedCollectionDataTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<Repository, CollectionDataType> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof CollectionDataType)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof Repository)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("dataTypes__Repository")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof CollectionDataType)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<Repository, CollectionDataType> typedChange = (CreateAndInsertNonRoot<Repository, CollectionDataType>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<Repository, CollectionDataType> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<Repository, CollectionDataType> _insertChange = change.getInsertChange();
      CollectionDataType _newValue = _insertChange.getNewValue();
      _routinesFacade.createCollectionDataTypeImplementation(_newValue);
    }
  }
}
