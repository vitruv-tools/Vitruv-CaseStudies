package mir.reactions.reactionsPcmToJava.pcm2javaCommon;

import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedSeffReaction extends AbstractReactionRealization {
  private RemoveEReference<BasicComponent, ServiceEffectSpecification> removeChange;
  
  private DeleteEObject<ServiceEffectSpecification> deleteChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.BasicComponent affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.palladiosimulator.pcm.seff.ServiceEffectSpecification oldValue = removeChange.getOldValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.pcm2javaCommon.RoutinesFacade routinesFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedSeffReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedSeffReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.palladiosimulator.pcm.seff.ServiceEffectSpecification> _localTypedChange = (DeleteEObject<org.palladiosimulator.pcm.seff.ServiceEffectSpecification>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.seff.ServiceEffectSpecification)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.palladiosimulator.pcm.seff.ServiceEffectSpecification>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    removeChange = null;
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.palladiosimulator.pcm.repository.BasicComponent, org.palladiosimulator.pcm.seff.ServiceEffectSpecification> _localTypedChange = (RemoveEReference<org.palladiosimulator.pcm.repository.BasicComponent, org.palladiosimulator.pcm.seff.ServiceEffectSpecification>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.BasicComponent)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("serviceEffectSpecifications__BasicComponent")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.seff.ServiceEffectSpecification)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.palladiosimulator.pcm.repository.BasicComponent, org.palladiosimulator.pcm.seff.ServiceEffectSpecification>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchRemoveChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchDeleteChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
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
    
    public void callRoutine1(final BasicComponent affectedEObject, final EReference affectedFeature, final ServiceEffectSpecification oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteMethodForSeff(oldValue);
    }
  }
}
