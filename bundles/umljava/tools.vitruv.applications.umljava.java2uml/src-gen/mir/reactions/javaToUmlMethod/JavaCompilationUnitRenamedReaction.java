package mir.reactions.javaToUmlMethod;

import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
public class JavaCompilationUnitRenamedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEAttribute<CompilationUnit, String> replaceChange;
  
  private int currentlyMatchedChange;
  
  public JavaCompilationUnitRenamedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.emftext.language.java.containers.CompilationUnit affectedEObject = replaceChange.getAffectedEObject();
    EAttribute affectedFeature = replaceChange.getAffectedFeature();
    java.lang.String oldValue = replaceChange.getOldValue();
    java.lang.String newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.javaToUmlMethod.JavaCompilationUnitRenamedReaction.ActionUserExecution userExecution = new mir.reactions.javaToUmlMethod.JavaCompilationUnitRenamedReaction.ActionUserExecution(this.executionState, this);
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
    if (change instanceof ReplaceSingleValuedEAttribute<?, ?>) {
    	ReplaceSingleValuedEAttribute<org.emftext.language.java.containers.CompilationUnit, java.lang.String> _localTypedChange = (ReplaceSingleValuedEAttribute<org.emftext.language.java.containers.CompilationUnit, java.lang.String>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.containers.CompilationUnit)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("name")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof java.lang.String)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof java.lang.String)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEAttribute<org.emftext.language.java.containers.CompilationUnit, java.lang.String>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ReplaceSingleValuedEAttribute replaceChange, final CompilationUnit affectedEObject, final EAttribute affectedFeature, final String oldValue, final String newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameUmlNamedElementCorrespondingToCompilationUnit(affectedEObject);
    }
  }
}
