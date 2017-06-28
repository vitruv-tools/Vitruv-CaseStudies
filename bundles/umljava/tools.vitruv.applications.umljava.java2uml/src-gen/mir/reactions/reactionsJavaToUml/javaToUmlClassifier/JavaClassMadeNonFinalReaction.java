package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.modifiers.Final;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaClassMadeNonFinalReaction extends AbstractReactionRealization {
  public JavaClassMadeNonFinalReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<org.emftext.language.java.classifiers.Class, Final> typedChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.classifiers.Class, Final>)change).getRemoveChange();
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Final oldValue = typedChange.getOldValue();
    mir.routines.javaToUmlClassifier.RoutinesFacade routinesFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.emftext.language.java.classifiers.Class, Final> relevantChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.classifiers.Class, Final>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof Final)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
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
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final Final oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setUmlClassFinal(affectedEObject, Boolean.valueOf(false));
    }
  }
}
