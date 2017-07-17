package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class JavaCompilationUnitRemovedFromPackageReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.emftext.language.java.containers.Package, CompilationUnit> typedChange = (RemoveEReference<org.emftext.language.java.containers.Package, CompilationUnit>)change;
    org.emftext.language.java.containers.Package affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    CompilationUnit oldValue = typedChange.getOldValue();
    mir.routines.javaToUmlClassifier.RoutinesFacade routinesFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.emftext.language.java.containers.Package, CompilationUnit> relevantChange = (RemoveEReference<org.emftext.language.java.containers.Package, CompilationUnit>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("compilationUnits")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof CompilationUnit)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
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
    
    public void callRoutine1(final org.emftext.language.java.containers.Package affectedEObject, final EReference affectedFeature, final CompilationUnit oldValue, @Extension final RoutinesFacade _routinesFacade) {
      EList<ConcreteClassifier> _classifiers = oldValue.getClassifiers();
      ConcreteClassifier _head = IterableExtensions.<ConcreteClassifier>head(_classifiers);
      _routinesFacade.removeUmlPackageOfClass(affectedEObject, _head);
    }
  }
}
