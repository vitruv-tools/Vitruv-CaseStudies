package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.mapping.MappingUpdateUtils;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingRoutineUpdateHelper;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateSource;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateTarget;
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
    
    public void executeAction1(final Repository repository, final org.eclipse.uml2.uml.Package repositoryPkg, @Extension final RoutinesFacade _routinesFacade) {
      final MappingUpdateTarget packageTarget = MappingRoutineUpdateHelper.eObjectTarget(repositoryPkg, "name");
      final MappingUpdateSource repositorySource = MappingRoutineUpdateHelper.eObjectSource(repository, "entityName", MappingUpdateUtils.transformationFirstLower(true));
      MappingRoutineUpdateHelper.updateFromSources(packageTarget, repositorySource);
    }
    
    public String getRetrieveTag1(final Repository repository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceRepositoryPkg(final Repository repository) {
      return repository;
    }
  }
  
  public UpdateRepoPackageNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateRepoPackageNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRepoPackageNameRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    
    org.eclipse.uml2.uml.Package repositoryPkg = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPkg(repository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repository), 
    	true // asserted
    	);
    if (repositoryPkg == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryPkg);
    userExecution.executeAction1(repository, repositoryPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
