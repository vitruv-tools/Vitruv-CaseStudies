package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRepositoryPackagesRoutine extends AbstractRepairRoutineRealization {
  private CreateRepositoryPackagesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Repository repository) {
      return repository;
    }
    
    public void callRoutine1(final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaPackage(repository, null, repository.getEntityName(), "repository_root");
      _routinesFacade.createRepositorySubPackages(repository);
    }
  }
  
  public CreateRepositoryPackagesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateRepositoryPackagesRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositoryPackagesRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
