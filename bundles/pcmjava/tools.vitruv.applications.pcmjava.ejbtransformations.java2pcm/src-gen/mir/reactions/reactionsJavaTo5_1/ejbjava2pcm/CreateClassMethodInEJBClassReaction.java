package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
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
class CreateClassMethodInEJBClassReaction extends AbstractReactionRealization {
  public CreateClassMethodInEJBClassReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod> change) {
    return (EJBAnnotationHelper.isEJBClass(change.getInsertChange().getAffectedEObject()) && 
      EJBJava2PcmHelper.overridesInterfaceMethod(change.getInsertChange().getNewValue(), change.getInsertChange().getAffectedEObject()));
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof ClassMethod)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof ClassMethod)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> _insertChange = change.getInsertChange();
      org.emftext.language.java.classifiers.Class _affectedEObject = _insertChange.getAffectedEObject();
      InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> _insertChange_1 = change.getInsertChange();
      ClassMethod _newValue = _insertChange_1.getNewValue();
      _routinesFacade.createdClassMethodInEJBClass(_affectedEObject, _newValue);
    }
  }
}
