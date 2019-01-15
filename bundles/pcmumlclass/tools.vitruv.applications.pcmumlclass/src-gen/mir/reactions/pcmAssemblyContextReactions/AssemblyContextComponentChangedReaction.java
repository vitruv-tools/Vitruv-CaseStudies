package mir.reactions.pcmAssemblyContextReactions;

import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class AssemblyContextComponentChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<AssemblyContext, RepositoryComponent> replaceChange;
  
  private int currentlyMatchedChange;
  
  public AssemblyContextComponentChangedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.composition.AssemblyContext affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.RepositoryComponent oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.RepositoryComponent newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmAssemblyContextReactions.AssemblyContextComponentChangedReaction.ActionUserExecution userExecution = new mir.reactions.pcmAssemblyContextReactions.AssemblyContextComponentChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(replaceChange, affectedEObject, affectedFeature, oldValue, newValue, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    replaceChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchReplaceChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchReplaceChange(final EChange change) {
    if (change instanceof ReplaceSingleValuedEReference<?, ?>) {
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.core.composition.AssemblyContext, org.palladiosimulator.pcm.repository.RepositoryComponent> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.core.composition.AssemblyContext, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.composition.AssemblyContext)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("encapsulatedComponent__AssemblyContext")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.RepositoryComponent)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.RepositoryComponent)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.core.composition.AssemblyContext, org.palladiosimulator.pcm.repository.RepositoryComponent>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final AssemblyContext affectedEObject, final EReference affectedFeature, final RepositoryComponent oldValue, final RepositoryComponent newValue) {
    RepositoryComponent _encapsulatedComponent__AssemblyContext = affectedEObject.getEncapsulatedComponent__AssemblyContext();
    return (_encapsulatedComponent__AssemblyContext == newValue);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final AssemblyContext affectedEObject, final EReference affectedFeature, final RepositoryComponent oldValue, final RepositoryComponent newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingAssemblyContextProperty(affectedEObject, newValue);
    }
  }
}
