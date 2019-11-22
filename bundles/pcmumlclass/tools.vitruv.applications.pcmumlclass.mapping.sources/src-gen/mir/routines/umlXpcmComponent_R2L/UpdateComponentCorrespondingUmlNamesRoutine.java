package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateComponentCorrespondingUmlNamesRoutine extends AbstractRepairRoutineRealization {
  private UpdateComponentCorrespondingUmlNamesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final RepositoryComponent component, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateComponentCorrespondingUmlNamesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.UpdateComponentCorrespondingUmlNamesRoutine.ActionUserExecution(getExecutionState(), this);
    this.component = component;this.repository = repository;
  }
  
  private RepositoryComponent component;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateComponentCorrespondingUmlNamesRoutine with input:");
    getLogger().debug("   component: " + this.component);
    getLogger().debug("   repository: " + this.repository);
    
    userExecution.executeAction1(component, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
