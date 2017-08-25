package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
class UmlClassifierRenamedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Classifier, java.lang.String> typedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Classifier, java.lang.String>)change;
    org.eclipse.uml2.uml.Classifier affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    java.lang.String oldValue = typedChange.getOldValue();
    java.lang.String newValue = typedChange.getNewValue();
    mir.routines.umlToJavaClassifier.RoutinesFacade routinesFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Classifier, java.lang.String> relevantChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Classifier, java.lang.String>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Classifier)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("name")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof java.lang.String)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof java.lang.String)) {
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
    
    public void callRoutine1(final Classifier affectedEObject, final EAttribute affectedFeature, final String oldValue, final String newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameJavaClassifier(affectedEObject);
    }
  }
}
