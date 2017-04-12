package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class UmlInterfaceImplementerChangedReaction extends AbstractReactionRealization {
  public UmlInterfaceImplementerChangedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<InterfaceRealization, org.eclipse.uml2.uml.Class> typedChange = (ReplaceSingleValuedEReference<InterfaceRealization, org.eclipse.uml2.uml.Class>)change;
    InterfaceRealization affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Class oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.Class newValue = typedChange.getNewValue();
    mir.routines.umlToJavaClassifier.RoutinesFacade routinesFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<InterfaceRealization, org.eclipse.uml2.uml.Class> relevantChange = (ReplaceSingleValuedEReference<InterfaceRealization, org.eclipse.uml2.uml.Class>)change;
    if (!(relevantChange.getAffectedEObject() instanceof InterfaceRealization)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("implementingClassifier")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.Class)) {
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
    
    public void callRoutine1(final InterfaceRealization affectedEObject, final EReference affectedFeature, final org.eclipse.uml2.uml.Class oldValue, final org.eclipse.uml2.uml.Class newValue, @Extension final RoutinesFacade _routinesFacade) {
      NamedElement _head = IterableExtensions.<NamedElement>head(affectedEObject.getSuppliers());
      _routinesFacade.deleteJavaImplementedInterface(((Interface) _head), oldValue);
      NamedElement _head_1 = IterableExtensions.<NamedElement>head(affectedEObject.getSuppliers());
      _routinesFacade.changeJavaImplementedInterface(((Interface) _head_1), null, newValue);
    }
  }
}
