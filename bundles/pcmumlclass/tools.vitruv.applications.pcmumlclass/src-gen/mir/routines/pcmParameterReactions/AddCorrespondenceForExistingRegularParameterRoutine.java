package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return pcmParam;
    }
    
    public EObject getCorrepondenceSource1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return pcmParam;
    }
    
    public EObject getCorrepondenceSource2(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getElement2(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return umlParam;
    }
    
    public String getTag1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
  }
  
  public AddCorrespondenceForExistingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.AddCorrespondenceForExistingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.umlParam = umlParam;
  }
  
  private Parameter pcmParam;
  
  private org.eclipse.uml2.uml.Parameter umlParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   umlParam: " + this.umlParam);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmParam, umlParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, umlParam)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmParam, umlParam), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmParam, umlParam)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmParam, umlParam), userExecution.getElement2(pcmParam, umlParam), userExecution.getTag1(pcmParam, umlParam));
    
    postprocessElements();
    
    return true;
  }
}
