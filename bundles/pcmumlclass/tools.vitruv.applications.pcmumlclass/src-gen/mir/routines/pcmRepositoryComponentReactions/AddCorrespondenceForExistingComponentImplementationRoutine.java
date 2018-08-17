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
public class AddCorrespondenceForExistingComponentImplementationRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingComponentImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return umlComponentImplementation;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return umlComponentImplementation;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public AddCorrespondenceForExistingComponentImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.umlComponentImplementation = umlComponentImplementation;
  }
  
  private RepositoryComponent pcmComponent;
  
  private org.eclipse.uml2.uml.Class umlComponentImplementation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingComponentImplementationRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   umlComponentImplementation: " + this.umlComponentImplementation);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, umlComponentImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, umlComponentImplementation)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmComponent, umlComponentImplementation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentImplementation)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentImplementation), userExecution.getElement2(pcmComponent, umlComponentImplementation), userExecution.getTag1(pcmComponent, umlComponentImplementation));
    
    postprocessElements();
    
    return true;
  }
}
