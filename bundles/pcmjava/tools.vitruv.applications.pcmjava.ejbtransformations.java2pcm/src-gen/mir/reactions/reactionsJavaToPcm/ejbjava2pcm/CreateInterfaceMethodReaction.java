package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbAnnotationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreateInterfaceMethodReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<Interface, Member> typedChange = (InsertEReference<Interface, Member>)change;
    Interface affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Member newValue = typedChange.getNewValue();
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<Interface, Member> relevantChange = (InsertEReference<Interface, Member>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Interface)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof Member)) {
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
    InsertEReference<Interface, Member> typedChange = (InsertEReference<Interface, Member>)change;
    Interface affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Member newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Interface affectedEObject, final EReference affectedFeature, final Member newValue) {
    return ((newValue instanceof InterfaceMethod) && EjbAnnotationHelper.isEjbBuisnessInterface(affectedEObject));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Interface affectedEObject, final EReference affectedFeature, final Member newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createdInterfaceMethod(affectedEObject, ((InterfaceMethod) newValue));
    }
  }
}
