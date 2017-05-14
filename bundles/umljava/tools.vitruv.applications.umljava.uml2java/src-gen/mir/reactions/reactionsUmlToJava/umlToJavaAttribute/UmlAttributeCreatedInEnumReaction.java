package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
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
class UmlAttributeCreatedInEnumReaction extends AbstractReactionRealization {
  public UmlAttributeCreatedInEnumReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<DataType, Property> typedChange = ((CreateAndInsertNonRoot<DataType, Property>)change).getInsertChange();
    DataType affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Property newValue = typedChange.getNewValue();
    mir.routines.umlToJavaAttribute.RoutinesFacade routinesFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInEnumReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInEnumReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<DataType, Property> relevantChange = ((CreateAndInsertNonRoot<DataType, Property>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof DataType)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedAttribute")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof Property)) {
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
    
    public void callRoutine1(final DataType affectedEObject, final EReference affectedFeature, final Property newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaAttributeInEnum(affectedEObject, newValue);
    }
  }
}
