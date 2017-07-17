package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
class CreatedFirstPackageReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> typedChange = (InsertRootEObject<org.emftext.language.java.containers.Package>)change;
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertRootEObject.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> relevantChange = (InsertRootEObject<org.emftext.language.java.containers.Package>)change;
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertRootEObject)) {
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
    
    public void callRoutine1(final org.emftext.language.java.containers.Package newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createRepositoryForFirstPackage(newValue);
    }
  }
}
