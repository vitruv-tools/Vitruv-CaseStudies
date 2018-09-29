package mir.reactions.pcmDataTypePropagationReactions;

import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class UnsupportedPrimitiveTypeSetAtInnerDeclarationReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<InnerDeclaration, PrimitiveDataType> replaceChange;
  
  private int currentlyMatchedChange;
  
  public UnsupportedPrimitiveTypeSetAtInnerDeclarationReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.repository.InnerDeclaration affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.PrimitiveDataType oldValue = replaceChange.getOldValue();
    org.palladiosimulator.pcm.repository.PrimitiveDataType newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(replaceChange, affectedEObject, affectedFeature, oldValue, newValue)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetAtInnerDeclarationReaction.ActionUserExecution userExecution = new mir.reactions.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetAtInnerDeclarationReaction.ActionUserExecution(this.executionState, this);
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
    	ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.InnerDeclaration, org.palladiosimulator.pcm.repository.PrimitiveDataType> _localTypedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.InnerDeclaration, org.palladiosimulator.pcm.repository.PrimitiveDataType>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.InnerDeclaration)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("datatype_InnerDeclaration")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.PrimitiveDataType)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.repository.PrimitiveDataType)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.InnerDeclaration, org.palladiosimulator.pcm.repository.PrimitiveDataType>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final ReplaceSingleValuedEReference replaceChange, final InnerDeclaration affectedEObject, final EReference affectedFeature, final PrimitiveDataType oldValue, final PrimitiveDataType newValue) {
    return ((affectedEObject.getDatatype_InnerDeclaration() == newValue) && (!PcmUmlClassHelper.isSupportedPcmPrimitiveType(newValue)));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEReference replaceChange, final InnerDeclaration affectedEObject, final EReference affectedFeature, final PrimitiveDataType oldValue, final PrimitiveDataType newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.unsupportedPrimitiveTypeSetWarning(newValue, affectedEObject);
    }
  }
}
