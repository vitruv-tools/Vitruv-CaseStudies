package mir.reactions.reactionsJavaToUML.javaToUml;

import mir.routines.javaToUml.RoutinesFacade;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class LogreactionReaction extends AbstractReactionRealization {
  public LogreactionReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    EChange typedChange = (EChange)change;
    mir.routines.javaToUml.RoutinesFacade routinesFacade = new mir.routines.javaToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUML.javaToUml.LogreactionReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUML.javaToUml.LogreactionReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return EChange.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    EChange relevantChange = (EChange)change;
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof EChange)) {
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
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      PropertyConfigurator.configure("log4j.properties");
    }
  }
}
