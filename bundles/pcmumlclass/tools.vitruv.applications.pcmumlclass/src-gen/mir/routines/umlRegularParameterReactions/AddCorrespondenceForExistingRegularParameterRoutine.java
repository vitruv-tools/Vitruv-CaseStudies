package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
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
    
    public EObject getElement1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return umlParameter;
    }
    
    public EObject getCorrepondenceSource2(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return umlParameter;
    }
    
    public String getTag1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
  }
  
  public AddCorrespondenceForExistingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.AddCorrespondenceForExistingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.pcmParameter = pcmParameter;
  }
  
  private Parameter umlParameter;
  
  private org.palladiosimulator.pcm.repository.Parameter pcmParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingRegularParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   pcmParameter: " + this.pcmParameter);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParameter, pcmParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, pcmParameter)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlParameter, pcmParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, pcmParameter)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlParameter, pcmParameter), userExecution.getElement2(umlParameter, pcmParameter), userExecution.getTag1(umlParameter, pcmParameter));
    
    postprocessElements();
    
    return true;
  }
}
