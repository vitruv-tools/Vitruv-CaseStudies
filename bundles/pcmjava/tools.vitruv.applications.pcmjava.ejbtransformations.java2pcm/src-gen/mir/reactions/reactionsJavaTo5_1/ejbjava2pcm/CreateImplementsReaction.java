package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateImplementsReaction extends AbstractReactionRealization {
  public CreateImplementsReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateImplementsReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateImplementsReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof EObject)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("implements")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof TypeReference)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference> typedChange = (CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, TypeReference> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> _insertChange = change.getInsertChange();
      org.emftext.language.java.classifiers.Class _affectedEObject = _insertChange.getAffectedEObject();
      InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> _insertChange_1 = change.getInsertChange();
      TypeReference _newValue = _insertChange_1.getNewValue();
      _routinesFacade.createdImplements(_affectedEObject, _newValue);
    }
  }
}
