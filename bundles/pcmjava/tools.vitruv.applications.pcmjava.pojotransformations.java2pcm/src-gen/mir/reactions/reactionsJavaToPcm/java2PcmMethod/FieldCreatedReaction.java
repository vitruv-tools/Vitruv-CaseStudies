package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

/**
 * *nCheck if Field has correspondence to CompositeDataType, ComposedProvidingRequiringEntity, nOperationInterface or RepositoryComponent and react accordingly.
 *  
 */
@SuppressWarnings("all")
class FieldCreatedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.Field> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.Field>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.members.Field newValue = typedChange.getNewValue();
    mir.routines.java2PcmMethod.RoutinesFacade routinesFacade = new mir.routines.java2PcmMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.Field> relevantChange = (InsertEReference<org.emftext.language.java.classifiers.Class, org.emftext.language.java.members.Field>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.members.Field)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final Field newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createInnerDeclaration(newValue.getContainingConcreteClassifier(), newValue);
      final Classifier type = Java2PcmHelper.getTargetClassifierFromImplementsReferenceAndNormalizeURI(newValue.getTypeReference());
      _routinesFacade.fieldCreatedCorrespondingToOperationInterface(type, newValue);
      _routinesFacade.fieldCreatedCorrespondingToRepositoryComponent(type, newValue);
      _routinesFacade.createAssemblyContext(newValue.getContainingConcreteClassifier(), newValue);
    }
  }
}
