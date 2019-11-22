package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRepositoryRootRoutine extends AbstractRepairRoutineRealization {
  private CreateRepositoryRootRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package repositoryPkg, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
      this.persistProjectRelative(repositoryPkg, repository, DefaultLiterals.PCM_MODEL_FILE);
    }
  }
  
  public CreateRepositoryRootRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.CreateRepositoryRootRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg = repositoryPkg;this.repository = repository;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositoryRootRoutine with input:");
    getLogger().debug("   repositoryPkg: " + this.repositoryPkg);
    getLogger().debug("   repository: " + this.repository);
    
    userExecution.executeAction1(repositoryPkg, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
