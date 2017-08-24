package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import java.util.Set;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.commons.NamedElement;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
class JavaNamedElementRenamedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.emftext.language.java.commons.NamedElement, java.lang.String> typedChange = (ReplaceSingleValuedEAttribute<org.emftext.language.java.commons.NamedElement, java.lang.String>)change;
    org.emftext.language.java.commons.NamedElement affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    java.lang.String oldValue = typedChange.getOldValue();
    java.lang.String newValue = typedChange.getNewValue();
    mir.routines.java2PcmMethod.RoutinesFacade routinesFacade = new mir.routines.java2PcmMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.emftext.language.java.commons.NamedElement, java.lang.String> relevantChange = (ReplaceSingleValuedEAttribute<org.emftext.language.java.commons.NamedElement, java.lang.String>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.commons.NamedElement)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("name")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof java.lang.String)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof java.lang.String)) {
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
    
    public void callRoutine1(final NamedElement affectedEObject, final EAttribute affectedFeature, final String oldValue, final String newValue, @Extension final RoutinesFacade _routinesFacade) {
      final Set<EObject> a = Java2PcmHelper.getCorresponding(affectedEObject, this.correspondenceModel);
      _routinesFacade.renameNamedElement(affectedEObject);
    }
  }
}
