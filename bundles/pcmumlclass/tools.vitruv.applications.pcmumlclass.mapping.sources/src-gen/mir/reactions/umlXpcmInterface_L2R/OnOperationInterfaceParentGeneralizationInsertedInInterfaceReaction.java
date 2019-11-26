package mir.reactions.umlXpcmInterface_L2R;

import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class OnOperationInterfaceParentGeneralizationInsertedInInterfaceReaction extends AbstractReactionRealization {
  private InsertEReference<Classifier, Generalization> insertChange;
  
  private int currentlyMatchedChange;
  
  public OnOperationInterfaceParentGeneralizationInsertedInInterfaceReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Classifier affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.eclipse.uml2.uml.Generalization newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentGeneralizationInsertedInInterfaceReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentGeneralizationInsertedInInterfaceReaction.ActionUserExecution(this.executionState, this);
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
    	InsertEReference<org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Generalization> _localTypedChange = (InsertEReference<org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Generalization>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Classifier)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("generalization")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Generalization)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Generalization>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final Classifier affectedEObject, final EReference affectedFeature, final Generalization newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterfaceParent_ElementCreatedCheck(affectedEObject);
    }
  }
}
