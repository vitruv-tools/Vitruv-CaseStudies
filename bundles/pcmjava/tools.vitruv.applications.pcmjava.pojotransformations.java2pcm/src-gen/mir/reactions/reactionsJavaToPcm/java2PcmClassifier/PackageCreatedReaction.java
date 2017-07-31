package mir.reactions.reactionsJavaToPcm.java2PcmClassifier;

import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;

@SuppressWarnings("all")
class PackageCreatedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> typedChange = (InsertRootEObject<org.emftext.language.java.containers.Package>)change;
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    mir.routines.java2PcmClassifier.RoutinesFacade routinesFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmClassifier.PackageCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.PackageCreatedReaction.ActionUserExecution(this.executionState, this);
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
    InsertRootEObject<org.emftext.language.java.containers.Package> typedChange = (InsertRootEObject<org.emftext.language.java.containers.Package>)change;
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final org.emftext.language.java.containers.Package newValue) {
    return ((!newValue.getName().contains("contracts")) && (!newValue.getName().contains("datatypes")));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package newValue, @Extension final RoutinesFacade _routinesFacade) {
      boolean _noCorrespondenceRepository = Java2PcmHelper.noCorrespondenceRepository(this.correspondenceModel);
      if (_noCorrespondenceRepository) {
        _routinesFacade.createPCMRepository(newValue, newValue.getName(), "package_root");
        _routinesFacade.createJavaSubPackages(newValue);
      } else {
        _routinesFacade.createArchitecturalElement(newValue, Java2PcmHelper.getLastPackageName(newValue.getName()), Java2PcmHelper.getRootPackageName(newValue.getName()));
      }
    }
  }
}
