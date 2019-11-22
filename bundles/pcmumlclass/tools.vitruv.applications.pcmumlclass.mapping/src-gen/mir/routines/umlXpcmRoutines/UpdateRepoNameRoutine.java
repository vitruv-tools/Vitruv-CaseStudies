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
public class UpdateRepoNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateRepoNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
      final MappingUpdateTarget repositoryTarget = MappingRoutineUpdateHelper.eObjectTarget(repository, "entityName");
      final MappingUpdateSource packageSource = MappingRoutineUpdateHelper.eObjectSource(repositoryPkg, "name", MappingUpdateUtils.transformationFirstUpper(true));
      MappingRoutineUpdateHelper.updateFromSources(repositoryTarget, packageSource);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceRepository(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
      return repositoryPkg;
    }
  }
  
  public UpdateRepoNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateRepoNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg = repositoryPkg;this.contractsPkg = contractsPkg;this.datatypesPkg = datatypesPkg;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg;
  
  private org.eclipse.uml2.uml.Package contractsPkg;
  
  private org.eclipse.uml2.uml.Package datatypesPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRepoNameRoutine with input:");
    getLogger().debug("   repositoryPkg: " + this.repositoryPkg);
    getLogger().debug("   contractsPkg: " + this.contractsPkg);
    getLogger().debug("   datatypesPkg: " + this.datatypesPkg);
    
    org.palladiosimulator.pcm.repository.Repository repository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository(repositoryPkg, contractsPkg, datatypesPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repositoryPkg, contractsPkg, datatypesPkg), 
    	false // asserted
    	);
    if (repository == null) {
    	return false;
    }
    registerObjectUnderModification(repository);
    userExecution.executeAction1(repositoryPkg, contractsPkg, datatypesPkg, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
