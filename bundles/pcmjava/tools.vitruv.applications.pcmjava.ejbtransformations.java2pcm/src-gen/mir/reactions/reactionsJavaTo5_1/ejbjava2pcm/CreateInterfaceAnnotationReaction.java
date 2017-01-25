package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
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
class CreateInterfaceAnnotationReaction extends AbstractReactionRealization {
  public CreateInterfaceAnnotationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier> change) {
    InsertEReference<Interface, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
    Interface _affectedEObject = _insertChange.getAffectedEObject();
    boolean _isEJBBuisnessInterface = EJBAnnotationHelper.isEJBBuisnessInterface(_affectedEObject);
    return _isEJBBuisnessInterface;
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof EObject)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof Interface)) {
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
    CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier> typedChange = (CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<Interface, AnnotationInstanceOrModifier> change, @Extension final RoutinesFacade _routinesFacade) {
      final Repository repo = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      InsertEReference<Interface, AnnotationInstanceOrModifier> _insertChange = change.getInsertChange();
      Interface _affectedEObject = _insertChange.getAffectedEObject();
      _routinesFacade.createOperationInterface(repo, ((NamedElement) _affectedEObject));
    }
  }
}
