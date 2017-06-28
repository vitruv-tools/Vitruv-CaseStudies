package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.EnumConstant;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaEnumConstantDeletedReaction extends AbstractReactionRealization {
  public JavaEnumConstantDeletedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<Enumeration, EnumConstant> typedChange = ((RemoveAndDeleteNonRoot<Enumeration, EnumConstant>)change).getRemoveChange();
    Enumeration affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    EnumConstant oldValue = typedChange.getOldValue();
    mir.routines.javaToUmlClassifier.RoutinesFacade routinesFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<Enumeration, EnumConstant> relevantChange = ((RemoveAndDeleteNonRoot<Enumeration, EnumConstant>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof Enumeration)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("constants")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof EnumConstant)) {
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
    
    public void callRoutine1(final Enumeration affectedEObject, final EReference affectedFeature, final EnumConstant oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteUmlEnumLiteral(oldValue);
    }
  }
}
