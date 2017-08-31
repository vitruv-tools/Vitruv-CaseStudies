package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.Final;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class JavaAttributeMadeNonFinalReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.emftext.language.java.members.Field, org.emftext.language.java.modifiers.Final> typedChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.members.Field, org.emftext.language.java.modifiers.Final>)change).getRemoveChange();
    org.emftext.language.java.members.Field affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.modifiers.Final oldValue = typedChange.getOldValue();
    mir.routines.javaToUmlAttribute.RoutinesFacade routinesFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.emftext.language.java.members.Field, org.emftext.language.java.modifiers.Final> relevantChange = ((RemoveAndDeleteNonRoot<org.emftext.language.java.members.Field, org.emftext.language.java.modifiers.Final>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.members.Field)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.emftext.language.java.modifiers.Final)) {
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
    
    public void callRoutine1(final Field affectedEObject, final EReference affectedFeature, final Final oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setUmlAttributeFinal(affectedEObject, Boolean.valueOf(false));
    }
  }
}
