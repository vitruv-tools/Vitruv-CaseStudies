package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingComponentConstructorRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingComponentConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource2(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return umlComponentConstructor;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return umlComponentConstructor;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public AddCorrespondenceForExistingComponentConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.umlComponentConstructor = umlComponentConstructor;
  }
  
  private RepositoryComponent pcmComponent;
  
  private Operation umlComponentConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingComponentConstructorRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   umlComponentConstructor: " + this.umlComponentConstructor);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, umlComponentConstructor), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, umlComponentConstructor)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmComponent, umlComponentConstructor), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentConstructor)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentConstructor), userExecution.getElement2(pcmComponent, umlComponentConstructor), userExecution.getTag1(pcmComponent, umlComponentConstructor));
    
    postprocessElements();
    
    return true;
  }
}
