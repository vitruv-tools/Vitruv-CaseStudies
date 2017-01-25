package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedFirstPackageReaction extends AbstractReactionRealization {
  public CreatedFirstPackageReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertRoot<org.emftext.language.java.containers.Package> typedChange = (CreateAndInsertRoot<org.emftext.language.java.containers.Package>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedFirstPackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedFirstPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertRoot<org.emftext.language.java.containers.Package> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertRoot)) {
    	return false;
    }
    CreateAndInsertRoot<org.emftext.language.java.containers.Package> typedChange = (CreateAndInsertRoot<org.emftext.language.java.containers.Package>)change;
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
    
    public void callRoutine1(final CreateAndInsertRoot<org.emftext.language.java.containers.Package> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertRootEObject<org.emftext.language.java.containers.Package> _insertChange = change.getInsertChange();
      org.emftext.language.java.containers.Package _newValue = _insertChange.getNewValue();
      _routinesFacade.createRepositoryForFirstPackage(_newValue);
    }
  }
}
