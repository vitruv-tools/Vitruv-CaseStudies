package mir.reactions.java2PcmClassifier;

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
public class PackageCreatedReaction extends AbstractReactionRealization {
  private InsertRootEObject<org.emftext.language.java.containers.Package> insertChange;
  
  private int currentlyMatchedChange;
  
  public PackageCreatedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.emftext.language.java.containers.Package newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(insertChange, newValue, index)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.java2PcmClassifier.PackageCreatedReaction.ActionUserExecution userExecution = new mir.reactions.java2PcmClassifier.PackageCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, newValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertRootEObject<?>) {
    	InsertRootEObject<org.emftext.language.java.containers.Package> _localTypedChange = (InsertRootEObject<org.emftext.language.java.containers.Package>) change;
    	if (!(_localTypedChange.getNewValue() instanceof org.emftext.language.java.containers.Package)) {
    		return false;
    	}
    	this.insertChange = (InsertRootEObject<org.emftext.language.java.containers.Package>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final InsertRootEObject insertChange, final org.emftext.language.java.containers.Package newValue, final int index) {
    return ((!newValue.getName().contains("contracts")) && (!newValue.getName().contains("datatypes")));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertRootEObject insertChange, final org.emftext.language.java.containers.Package newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createArchitecturalElement(newValue, Java2PcmHelper.getLastPackageName(newValue.getName()), Java2PcmHelper.getRootPackageName(newValue.getName()));
      _routinesFacade.createRepository(newValue, newValue.getName(), "package_root");
    }
  }
}
