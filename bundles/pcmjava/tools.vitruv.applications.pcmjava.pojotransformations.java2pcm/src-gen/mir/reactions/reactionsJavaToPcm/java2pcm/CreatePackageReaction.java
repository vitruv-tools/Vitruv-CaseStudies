package mir.reactions.reactionsJavaToPcm.java2pcm;

import mir.routines.java2pcm.RoutinesFacade;
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
class CreatePackageReaction extends AbstractReactionRealization {
  public CreatePackageReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> typedChange = ((CreateAndInsertRoot<org.emftext.language.java.containers.Package>)change).getInsertChange();
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    mir.routines.java2pcm.RoutinesFacade routinesFacade = new mir.routines.java2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2pcm.CreatePackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2pcm.CreatePackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> relevantChange = ((CreateAndInsertRoot<org.emftext.language.java.containers.Package>)change).getInsertChange();
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertRoot)) {
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
      final org.emftext.language.java.containers.Package javaPackage = newValue;
      _routinesFacade.createPCMRepository(javaPackage, javaPackage.getName(), "package_root");
      _routinesFacade.createJavaSubPackages(javaPackage);
    }
  }
}
