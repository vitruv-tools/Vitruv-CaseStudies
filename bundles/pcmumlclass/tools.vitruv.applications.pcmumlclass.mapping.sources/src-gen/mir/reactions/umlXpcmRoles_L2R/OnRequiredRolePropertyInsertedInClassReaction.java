package mir.reactions.umlXpcmRoles_L2R;

import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class OnRequiredRolePropertyInsertedInClassReaction extends AbstractReactionRealization {
  private InsertEReference<StructuredClassifier, Property> insertChange;
  
  private int currentlyMatchedChange;
  
  public OnRequiredRolePropertyInsertedInClassReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.StructuredClassifier affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.eclipse.uml2.uml.Property newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmRoles_L2R.OnRequiredRolePropertyInsertedInClassReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmRoles_L2R.OnRequiredRolePropertyInsertedInClassReaction.ActionUserExecution(this.executionState, this);
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
    	InsertEReference<org.eclipse.uml2.uml.StructuredClassifier, org.eclipse.uml2.uml.Property> _localTypedChange = (InsertEReference<org.eclipse.uml2.uml.StructuredClassifier, org.eclipse.uml2.uml.Property>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.StructuredClassifier)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("ownedAttribute")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Property)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.eclipse.uml2.uml.StructuredClassifier, org.eclipse.uml2.uml.Property>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final StructuredClassifier affectedEObject, final EReference affectedFeature, final Property newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_ElementCreatedCheck(affectedEObject);
    }
  }
}
