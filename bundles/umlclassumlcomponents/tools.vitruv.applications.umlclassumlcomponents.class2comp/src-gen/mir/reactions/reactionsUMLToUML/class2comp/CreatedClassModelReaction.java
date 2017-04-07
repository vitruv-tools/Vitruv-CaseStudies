package mir.reactions.reactionsUmlToUml.class2comp;

import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedClassModelReaction extends AbstractReactionRealization {
  public CreatedClassModelReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertRootEObject<Model> typedChange = ((CreateAndInsertRoot<Model>)change).getInsertChange();
    Model newValue = typedChange.getNewValue();
    mir.routines.class2comp.RoutinesFacade routinesFacade = new mir.routines.class2comp.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertRootEObject<Model> relevantChange = ((CreateAndInsertRoot<Model>)change).getInsertChange();
    if (!(relevantChange.getNewValue() instanceof Model)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertRoot)) {
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
    
    public void callRoutine1(final Model newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createComponentModel(newValue);
    }
  }
}
