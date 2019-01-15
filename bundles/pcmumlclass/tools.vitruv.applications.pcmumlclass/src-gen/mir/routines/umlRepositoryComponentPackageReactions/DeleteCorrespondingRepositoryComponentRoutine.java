package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlComponentPkg, final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.eclipse.uml2.uml.Package umlComponentPkg) {
      return umlComponentPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlComponentPkg) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlComponentPkg, final RepositoryComponent pcmComponent) {
      return umlComponentPkg;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package umlComponentPkg, final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
  }
  
  public DeleteCorrespondingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlComponentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.DeleteCorrespondingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlComponentPkg = umlComponentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlComponentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlComponentPkg: " + this.umlComponentPkg);
    
    org.palladiosimulator.pcm.repository.RepositoryComponent pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlComponentPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlComponentPkg), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    removeCorrespondenceBetween(userExecution.getElement1(umlComponentPkg, pcmComponent), userExecution.getElement2(umlComponentPkg, pcmComponent), "");
    
    deleteObject(userExecution.getElement3(umlComponentPkg, pcmComponent));
    
    postprocessElements();
    
    return true;
  }
}
