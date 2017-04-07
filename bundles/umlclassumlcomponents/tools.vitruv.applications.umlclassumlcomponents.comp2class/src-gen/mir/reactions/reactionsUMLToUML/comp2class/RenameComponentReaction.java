package mir.reactions.reactionsUmlToUml.comp2class;

import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class RenameComponentReaction extends AbstractReactionRealization {
  public RenameComponentReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<Component, String> typedChange = (ReplaceSingleValuedEAttribute<Component, String>)change;
    Component affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    String oldValue = typedChange.getOldValue();
    String newValue = typedChange.getNewValue();
    mir.routines.comp2class.RoutinesFacade routinesFacade = new mir.routines.comp2class.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<Component, String> relevantChange = (ReplaceSingleValuedEAttribute<Component, String>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Component)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("name")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof String)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof String)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Component affectedEObject, final EAttribute affectedFeature, final String oldValue, final String newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameClass(affectedEObject);
    }
  }
}
