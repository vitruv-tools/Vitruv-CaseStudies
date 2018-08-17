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
public class AddCorrespondenceForExistingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return umlPkg;
    }
    
    public EObject getCorrepondenceSource2(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return umlPkg;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public AddCorrespondenceForExistingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.AddCorrespondenceForExistingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.pcmComponent = pcmComponent;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlPkg, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlPkg, pcmComponent)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlPkg, pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlPkg, pcmComponent)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlPkg, pcmComponent), userExecution.getElement2(umlPkg, pcmComponent), userExecution.getTag1(umlPkg, pcmComponent));
    
    postprocessElements();
    
    return true;
  }
}
