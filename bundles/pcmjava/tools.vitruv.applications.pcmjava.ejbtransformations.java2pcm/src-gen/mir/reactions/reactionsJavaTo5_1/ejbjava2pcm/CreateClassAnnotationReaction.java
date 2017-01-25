package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateClassAnnotationReaction extends AbstractReactionRealization {
  public CreateClassAnnotationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
    org.emftext.language.java.classifiers.Class _affectedEObject = _insertChange.getAffectedEObject();
    boolean _isEJBClass = EJBAnnotationHelper.isEJBClass(_affectedEObject);
    return _isEJBClass;
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof EObject)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
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
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change, @Extension final RoutinesFacade _routinesFacade) {
      final Repository repo = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
      org.emftext.language.java.classifiers.Class _affectedEObject = _insertChange.getAffectedEObject();
      _routinesFacade.createBasicComponent(repo, ((NamedElement) _affectedEObject));
    }
  }
}
