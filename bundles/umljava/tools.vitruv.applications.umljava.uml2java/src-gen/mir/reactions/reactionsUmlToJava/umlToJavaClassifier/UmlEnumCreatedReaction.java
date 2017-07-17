package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class UmlEnumCreatedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Package, Enumeration> typedChange = ((CreateAndInsertNonRoot<org.eclipse.uml2.uml.Package, Enumeration>)change).getInsertChange();
    org.eclipse.uml2.uml.Package affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Enumeration newValue = typedChange.getNewValue();
    mir.routines.umlToJavaClassifier.RoutinesFacade routinesFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Package, Enumeration> relevantChange = ((CreateAndInsertNonRoot<org.eclipse.uml2.uml.Package, Enumeration>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Package)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("packagedElement")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof Enumeration)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package affectedEObject, final EReference affectedFeature, final Enumeration newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaEnum(newValue);
    }
  }
}
