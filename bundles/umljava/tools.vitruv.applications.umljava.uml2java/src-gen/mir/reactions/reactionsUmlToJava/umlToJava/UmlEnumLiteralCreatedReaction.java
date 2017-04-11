package mir.reactions.reactionsUmlToJava.umlToJava;

import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class UmlEnumLiteralCreatedReaction extends AbstractReactionRealization {
  public UmlEnumLiteralCreatedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<Enumeration, EnumerationLiteral> typedChange = ((CreateAndInsertNonRoot<Enumeration, EnumerationLiteral>)change).getInsertChange();
    Enumeration affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    EnumerationLiteral newValue = typedChange.getNewValue();
    mir.routines.umlToJava.RoutinesFacade routinesFacade = new mir.routines.umlToJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<Enumeration, EnumerationLiteral> relevantChange = ((CreateAndInsertNonRoot<Enumeration, EnumerationLiteral>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof Enumeration)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedLiteral")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof EnumerationLiteral)) {
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
    
    public void callRoutine1(final Enumeration affectedEObject, final EReference affectedFeature, final EnumerationLiteral newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaEnumConstant(newValue, affectedEObject);
    }
  }
}
