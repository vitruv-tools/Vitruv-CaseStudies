package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateAnnotationForFieldReaction extends AbstractReactionRealization {
  public CreateAnnotationForFieldReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier> change) {
    InsertEReference<Field, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
    Field _affectedEObject = _insertChange.getAffectedEObject();
    boolean _hasEJBAnnotation = EJBAnnotationHelper.hasEJBAnnotation(_affectedEObject);
    return _hasEJBAnnotation;
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof EObject)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof Field)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof AnnotationInstanceOrModifier)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CreateAndInsertNonRoot<Field, AnnotationInstanceOrModifier> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<Field, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
      Field _affectedEObject = _insertChange.getAffectedEObject();
      _routinesFacade.createdAnnotationForField(_affectedEObject);
    }
  }
}
