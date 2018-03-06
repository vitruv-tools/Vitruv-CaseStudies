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
public class CreateRepositorySubPackagesRoutine extends AbstractRepairRoutineRealization {
  private CreateRepositorySubPackagesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage(final Repository repository) {
      return repository;
    }
    
    public void callRoutine1(final Repository repository, final org.emftext.language.java.containers.Package repositoryPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaPackage(repository, repositoryPackage, "datatypes", "datatypes");
      _routinesFacade.createJavaPackage(repository, repositoryPackage, "contracts", "contracts");
    }
  }
  
  public CreateRepositorySubPackagesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateRepositorySubPackagesRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositorySubPackagesRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (repositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryPackage);
    userExecution.callRoutine1(repository, repositoryPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
