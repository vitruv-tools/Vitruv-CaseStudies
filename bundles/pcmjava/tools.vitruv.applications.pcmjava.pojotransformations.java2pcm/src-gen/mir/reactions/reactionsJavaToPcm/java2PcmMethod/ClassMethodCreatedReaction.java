package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class ClassMethodCreatedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.ClassMethod> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.ClassMethod>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.members.ClassMethod newValue = typedChange.getNewValue();
    mir.routines.java2PcmMethod.RoutinesFacade routinesFacade = new mir.routines.java2PcmMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.ClassMethod> relevantChange = (InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.ClassMethod>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.members.ClassMethod)) {
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
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassMethod newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createSeffFromImplementingInterfaces(newValue, affectedEObject);
    }
  }
}
