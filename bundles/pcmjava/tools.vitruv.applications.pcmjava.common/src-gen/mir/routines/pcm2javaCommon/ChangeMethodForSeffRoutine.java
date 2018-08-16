package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeMethodForSeffRoutine extends AbstractRepairRoutineRealization {
  private ChangeMethodForSeffRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final ResourceDemandingSEFF seff, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createSEFF(seff);
    }
    
    public void callRoutine1(final ResourceDemandingSEFF seff, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteMethodForSeff(seff);
    }
  }
  
  public ChangeMethodForSeffRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingSEFF seff) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.ChangeMethodForSeffRoutine.ActionUserExecution(getExecutionState(), this);
    this.seff = seff;
  }
  
  private ResourceDemandingSEFF seff;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodForSeffRoutine with input:");
    getLogger().debug("   seff: " + this.seff);
    
    userExecution.callRoutine1(seff, this.getRoutinesFacade());
    
    userExecution.callRoutine2(seff, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
