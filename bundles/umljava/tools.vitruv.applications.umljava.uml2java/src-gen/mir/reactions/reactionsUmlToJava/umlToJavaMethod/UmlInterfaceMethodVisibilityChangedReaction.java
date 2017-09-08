package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import com.google.common.base.Objects;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

@SuppressWarnings("all")
class UmlInterfaceMethodVisibilityChangedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind> typedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind>)change;
    org.eclipse.uml2.uml.Operation affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.VisibilityKind oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.VisibilityKind newValue = typedChange.getNewValue();
    mir.routines.umlToJavaMethod.RoutinesFacade routinesFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind> relevantChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Operation)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("visibility")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.VisibilityKind)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.eclipse.uml2.uml.VisibilityKind)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind> typedChange = (ReplaceSingleValuedEAttribute<org.eclipse.uml2.uml.Operation, org.eclipse.uml2.uml.VisibilityKind>)change;
    org.eclipse.uml2.uml.Operation affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.VisibilityKind oldValue = typedChange.getOldValue();
    org.eclipse.uml2.uml.VisibilityKind newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, oldValue, newValue)) {
    	return false;
    }
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final Operation affectedEObject, final EAttribute affectedFeature, final VisibilityKind oldValue, final VisibilityKind newValue) {
    return ((affectedEObject.eContainer() instanceof Interface) && (!Objects.equal(newValue, VisibilityKind.PUBLIC_LITERAL)));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Operation affectedEObject, final EAttribute affectedFeature, final VisibilityKind oldValue, final VisibilityKind newValue, @Extension final RoutinesFacade _routinesFacade) {
      UmlToJavaHelper.showMessage(this.userInteracting, (("Non-public operations in interface are not valid. Please set " + affectedEObject) + " to public"));
    }
  }
}
