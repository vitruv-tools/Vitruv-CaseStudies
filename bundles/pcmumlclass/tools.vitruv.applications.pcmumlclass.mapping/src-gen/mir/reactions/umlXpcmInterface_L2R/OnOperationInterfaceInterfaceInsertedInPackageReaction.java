package mir.reactions.umlXpcmInterface_L2R;

import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class OnOperationInterfaceInterfaceInsertedInPackageReaction extends AbstractReactionRealization {
  private InsertEReference<org.eclipse.uml2.uml.Package, Interface> insertChange;
  
  private int currentlyMatchedChange;
  
  public OnOperationInterfaceInterfaceInsertedInPackageReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Package affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.eclipse.uml2.uml.Interface newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceInterfaceInsertedInPackageReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceInterfaceInsertedInPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, this.getRoutinesFacade());
    
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
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Interface> _localTypedChange = (InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Package)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("packagedElement")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Interface>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final org.eclipse.uml2.uml.Package affectedEObject, final EReference affectedFeature, final Interface newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterface_ElementCreatedCheck(affectedEObject);
    }
  }
}
