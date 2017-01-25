package mir.reactions.reactionsUMLToJava.umlToJava;

import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.uml2.uml.Model;
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
class CreatedUmlClassReaction extends AbstractReactionRealization {
  public CreatedUmlClassReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class> typedChange = (CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class>)change;
    mir.routines.umlToJava.RoutinesFacade routinesFacade = new mir.routines.umlToJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof Model)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("packagedElement")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class> typedChange = (CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CreateAndInsertNonRoot<Model, org.eclipse.uml2.uml.Class> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<Model, org.eclipse.uml2.uml.Class> _insertChange = change.getInsertChange();
      org.eclipse.uml2.uml.Class _newValue = _insertChange.getNewValue();
      _routinesFacade.createJavaClass(_newValue);
    }
  }
}
