package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class UmlAttributeDeletedFromClassReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Property> typedChange = ((RemoveAndDeleteNonRoot<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Property>)change).getRemoveChange();
    org.eclipse.uml2.uml.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Property oldValue = typedChange.getOldValue();
    mir.routines.umlToJavaAttribute.RoutinesFacade routinesFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Property> relevantChange = ((RemoveAndDeleteNonRoot<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.Property>)change).getRemoveChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedAttribute")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Property)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteNonRoot)) {
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final Property oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaAttribute(oldValue);
    }
  }
}
