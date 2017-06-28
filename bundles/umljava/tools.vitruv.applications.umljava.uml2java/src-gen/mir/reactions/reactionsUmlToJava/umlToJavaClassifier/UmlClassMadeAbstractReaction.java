package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class UmlClassMadeAbstractReaction extends AbstractReactionRealization {
  public UmlClassMadeAbstractReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Class, Boolean> typedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Class, Boolean>)change;
    org.eclipse.uml2.uml.Class affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    Boolean oldValue = typedChange.getOldValue();
    Boolean newValue = typedChange.getNewValue();
    mir.routines.umlToJavaClassifier.RoutinesFacade routinesFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Class, Boolean> relevantChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Class, Boolean>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("isAbstract")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof Boolean)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof Boolean)) {
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class affectedEObject, final EAttribute affectedFeature, final Boolean oldValue, final Boolean newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setJavaClassAbstract(affectedEObject);
    }
  }
}
