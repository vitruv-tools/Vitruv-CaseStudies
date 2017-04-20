package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class U2Reaction extends AbstractReactionRealization {
  public U2Reaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<LiteralUnlimitedNatural, Integer> typedChange = (ReplaceSingleValuedEAttribute<LiteralUnlimitedNatural, Integer>)change;
    LiteralUnlimitedNatural affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    Integer oldValue = typedChange.getOldValue();
    Integer newValue = typedChange.getNewValue();
    mir.routines.umlToJavaAttribute.RoutinesFacade routinesFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaAttribute.U2Reaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.U2Reaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<LiteralUnlimitedNatural, Integer> relevantChange = (ReplaceSingleValuedEAttribute<LiteralUnlimitedNatural, Integer>)change;
    if (!(relevantChange.getAffectedEObject() instanceof LiteralUnlimitedNatural)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("value")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof Integer)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof Integer)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
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
    
    public void callRoutine1(final LiteralUnlimitedNatural affectedEObject, final EAttribute affectedFeature, final Integer oldValue, final Integer newValue, @Extension final RoutinesFacade _routinesFacade) {
      Element _owner = affectedEObject.getOwner();
      _routinesFacade.handleMultiplicityForJavaAttribute(((Property) _owner));
    }
  }
}
