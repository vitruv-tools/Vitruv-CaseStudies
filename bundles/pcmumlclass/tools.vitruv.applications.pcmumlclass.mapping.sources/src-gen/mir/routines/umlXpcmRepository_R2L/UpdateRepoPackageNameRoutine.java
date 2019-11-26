package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateRepoPackageNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateRepoPackageNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateRepoPackageNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.UpdateRepoPackageNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRepoPackageNameRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    
    userExecution.executeAction1(repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
