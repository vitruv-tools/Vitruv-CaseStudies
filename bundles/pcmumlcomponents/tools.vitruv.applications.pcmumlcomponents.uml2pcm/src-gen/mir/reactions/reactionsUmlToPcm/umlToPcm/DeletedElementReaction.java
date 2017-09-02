package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedElementReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement> typedChange = (RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement>)change;
    org.eclipse.uml2.uml.Model affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.PackageableElement oldValue = typedChange.getOldValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement> relevantChange = (RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Model)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("packagedElement")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.PackageableElement)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement> typedChange = (RemoveEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement>)change;
    org.eclipse.uml2.uml.Model affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.PackageableElement oldValue = typedChange.getOldValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue)) {
    	return false;
    }
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Model affectedEObject, final EReference affectedFeature, final PackageableElement oldValue) {
    return (!(oldValue instanceof DataType));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Model affectedEObject, final EReference affectedFeature, final PackageableElement oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteElement(oldValue);
    }
  }
}
