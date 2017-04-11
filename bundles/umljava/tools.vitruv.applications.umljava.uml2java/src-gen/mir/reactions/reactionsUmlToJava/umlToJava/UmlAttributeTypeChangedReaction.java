package mir.reactions.reactionsUmlToJava.umlToJava;

import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class UmlAttributeTypeChangedReaction extends AbstractReactionRealization {
  public UmlAttributeTypeChangedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<Property, Type> typedChange = (ReplaceSingleValuedEReference<Property, Type>)change;
    Property affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Type oldValue = typedChange.getOldValue();
    Type newValue = typedChange.getNewValue();
    mir.routines.umlToJava.RoutinesFacade routinesFacade = new mir.routines.umlToJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeTypeChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeTypeChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<Property, Type> relevantChange = (ReplaceSingleValuedEReference<Property, Type>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Property)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("type")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof Type)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof Type)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
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
    
    public void callRoutine1(final Property affectedEObject, final EReference affectedFeature, final Type oldValue, final Type newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeJavaAttributeType(affectedEObject, newValue);
    }
  }
}
