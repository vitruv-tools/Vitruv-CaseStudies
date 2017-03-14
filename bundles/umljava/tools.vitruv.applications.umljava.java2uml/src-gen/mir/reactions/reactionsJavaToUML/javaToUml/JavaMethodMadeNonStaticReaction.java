package mir.reactions.reactionsJavaToUML.javaToUml;

import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.modifiers.Static;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaMethodMadeNonStaticReaction extends AbstractReactionRealization {
  public JavaMethodMadeNonStaticReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<ClassMethod, Static> typedChange = ((RemoveAndDeleteNonRoot<ClassMethod, Static>)change).getRemoveChange();
    ClassMethod affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Static oldValue = typedChange.getOldValue();
    mir.routines.javaToUml.RoutinesFacade routinesFacade = new mir.routines.javaToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonStaticReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonStaticReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<ClassMethod, Static> relevantChange = ((RemoveAndDeleteNonRoot<ClassMethod, Static>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof ClassMethod)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof Static)) {
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
    
    public void callRoutine1(final ClassMethod affectedEObject, final EReference affectedFeature, final Static oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setUmlMethodStatic(affectedEObject, Integer.valueOf(0));
    }
  }
}
