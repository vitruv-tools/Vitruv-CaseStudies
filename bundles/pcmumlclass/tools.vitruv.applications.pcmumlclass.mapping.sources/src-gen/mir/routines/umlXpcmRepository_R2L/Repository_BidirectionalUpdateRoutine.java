package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private Repository_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateRepoPackageName(repository_);
    }
  }
  
  public Repository_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.Repository_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository_ = repository_;
  }
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   repository_: " + this.repository_);
    
    userExecution.callRoutine1(repository_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
