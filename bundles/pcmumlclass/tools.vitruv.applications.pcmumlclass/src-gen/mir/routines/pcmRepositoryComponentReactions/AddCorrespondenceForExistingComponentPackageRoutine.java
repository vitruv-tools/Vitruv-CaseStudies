package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return umlComponentPackage;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return umlComponentPackage;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public AddCorrespondenceForExistingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.umlComponentPackage = umlComponentPackage;
  }
  
  private RepositoryComponent pcmComponent;
  
  private org.eclipse.uml2.uml.Package umlComponentPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   umlComponentPackage: " + this.umlComponentPackage);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, umlComponentPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, umlComponentPackage)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmComponent, umlComponentPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentPackage)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentPackage), userExecution.getElement2(pcmComponent, umlComponentPackage), userExecution.getTag1(pcmComponent, umlComponentPackage));
    
    postprocessElements();
    
    return true;
  }
}
