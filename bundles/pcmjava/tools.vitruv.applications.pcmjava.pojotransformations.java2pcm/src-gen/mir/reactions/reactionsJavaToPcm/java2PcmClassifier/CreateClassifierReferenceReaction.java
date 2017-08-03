package mir.reactions.reactionsJavaToPcm.java2PcmClassifier;

import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.ClassifierReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreateClassifierReferenceReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassifierReference> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, ClassifierReference>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ClassifierReference newValue = typedChange.getNewValue();
    mir.routines.java2PcmClassifier.RoutinesFacade routinesFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassifierReferenceReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassifierReferenceReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassifierReference> relevantChange = (InsertEReference<org.emftext.language.java.classifiers.Class, ClassifierReference>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("implements")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof ClassifierReference)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
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
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassifierReference newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createOperationProvidedRole(newValue);
    }
  }
}
