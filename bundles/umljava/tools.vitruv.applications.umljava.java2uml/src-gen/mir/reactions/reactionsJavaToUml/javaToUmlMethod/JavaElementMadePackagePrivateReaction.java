package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class JavaElementMadePackagePrivateReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.emftext.language.java.classifiers.Class, Modifier> typedChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.classifiers.Class, Modifier>)change).getRemoveChange();
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Modifier oldValue = typedChange.getOldValue();
    mir.routines.javaToUmlMethod.RoutinesFacade routinesFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.emftext.language.java.classifiers.Class, Modifier> relevantChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.classifiers.Class, Modifier>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof Modifier)) {
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
    RemoveEReference<org.emftext.language.java.classifiers.Class, Modifier> typedChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.classifiers.Class, Modifier>)change).getRemoveChange();
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Modifier oldValue = typedChange.getOldValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final Modifier oldValue) {
    return (((oldValue instanceof Private) || (oldValue instanceof Public)) || (oldValue instanceof Protected));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final Modifier oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeUmlNamedElementVisibility(affectedEObject, null);
    }
  }
}
