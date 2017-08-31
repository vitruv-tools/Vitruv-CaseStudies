package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class JavaEnumCreatedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Enumeration> typedChange = ((CreateAndInsertNonRoot<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Enumeration>)change).getInsertChange();
    org.emftext.language.java.containers.CompilationUnit affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.classifiers.Enumeration newValue = typedChange.getNewValue();
    mir.routines.javaToUmlClassifier.RoutinesFacade routinesFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Enumeration> relevantChange = ((CreateAndInsertNonRoot<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Enumeration>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.containers.CompilationUnit)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("classifiers")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.classifiers.Enumeration)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
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
    
    public void callRoutine1(final CompilationUnit affectedEObject, final EReference affectedFeature, final Enumeration newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createUmlEnum(newValue, affectedEObject);
    }
  }
}
