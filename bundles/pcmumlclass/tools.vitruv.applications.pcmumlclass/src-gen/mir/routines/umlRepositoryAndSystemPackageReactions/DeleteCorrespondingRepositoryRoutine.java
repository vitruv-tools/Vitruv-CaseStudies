package mir.routines.umlRepositoryAndSystemPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRepositoryRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlRepositoryPkg, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return umlRepositoryPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlRepositoryPkg, final Repository pcmRepository) {
      return umlRepositoryPkg;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package umlRepositoryPkg, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlRepositoryPkg, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
  }
  
  public DeleteCorrespondingRepositoryRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRepositoryPkg = umlRepositoryPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlRepositoryPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRepositoryRoutine with input:");
    getLogger().debug("   umlRepositoryPkg: " + this.umlRepositoryPkg);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlRepositoryPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlRepositoryPkg), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    removeCorrespondenceBetween(userExecution.getElement1(umlRepositoryPkg, pcmRepository), userExecution.getElement2(umlRepositoryPkg, pcmRepository), userExecution.getTag1(umlRepositoryPkg, pcmRepository));
    
    deleteObject(userExecution.getElement3(umlRepositoryPkg, pcmRepository));
    
    postprocessElements();
    
    return true;
  }
}
