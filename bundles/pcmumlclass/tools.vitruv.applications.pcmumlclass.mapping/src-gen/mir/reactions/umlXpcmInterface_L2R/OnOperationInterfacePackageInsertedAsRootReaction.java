package mir.reactions.umlXpcmInterface_L2R;

import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;

@SuppressWarnings("all")
public class OnOperationInterfacePackageInsertedAsRootReaction extends AbstractReactionRealization {
  private CreateEObject<org.eclipse.uml2.uml.Package> createChange;
  
  private int currentlyMatchedChange;
  
  public OnOperationInterfacePackageInsertedAsRootReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Package affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmInterface_L2R.OnOperationInterfacePackageInsertedAsRootReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfacePackageInsertedAsRootReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(createChange, affectedEObject, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<org.eclipse.uml2.uml.Package> _localTypedChange = (CreateEObject<org.eclipse.uml2.uml.Package>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Package)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.eclipse.uml2.uml.Package>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CreateEObject createChange, final org.eclipse.uml2.uml.Package affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterface_ElementCreatedCheck(affectedEObject);
    }
  }
}
