package mir.reactions.pcmAssemblyContextReactions;

import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class AssemblyContextInsertedReaction extends AbstractReactionRealization {
  private InsertEReference<ComposedProvidingRequiringEntity, AssemblyContext> insertChange;
  
  private int currentlyMatchedChange;
  
  public AssemblyContextInsertedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.palladiosimulator.pcm.core.composition.AssemblyContext newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(insertChange, affectedEObject, affectedFeature, newValue, index)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmAssemblyContextReactions.AssemblyContextInsertedReaction.ActionUserExecution userExecution = new mir.reactions.pcmAssemblyContextReactions.AssemblyContextInsertedReaction.ActionUserExecution(this.executionState, this);
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
    	InsertEReference<org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity, org.palladiosimulator.pcm.core.composition.AssemblyContext> _localTypedChange = (InsertEReference<org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity, org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("assemblyContexts__ComposedStructure")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.core.composition.AssemblyContext)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity, org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final InsertEReference insertChange, final ComposedProvidingRequiringEntity affectedEObject, final EReference affectedFeature, final AssemblyContext newValue, final int index) {
    return affectedEObject.getAssemblyContexts__ComposedStructure().contains(newValue);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final ComposedProvidingRequiringEntity affectedEObject, final EReference affectedFeature, final AssemblyContext newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.insertCorrespondingAssemblyContextProperty(newValue, affectedEObject);
    }
  }
}
