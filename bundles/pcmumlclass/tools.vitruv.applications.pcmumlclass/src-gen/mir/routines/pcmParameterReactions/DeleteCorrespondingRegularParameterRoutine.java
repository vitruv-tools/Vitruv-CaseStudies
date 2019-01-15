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
public class DeleteCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return pcmParam;
    }
    
    public String getRetrieveTag1(final Parameter pcmParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getElement2(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlParameter;
    }
    
    public EObject getElement3(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlParameter;
    }
    
    public String getTag1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParam) {
      return pcmParam;
    }
  }
  
  public DeleteCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.DeleteCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;
  }
  
  private Parameter pcmParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam), 
    	false // asserted
    	);
    if (umlParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlParameter);
    removeCorrespondenceBetween(userExecution.getElement1(pcmParam, umlParameter), userExecution.getElement2(pcmParam, umlParameter), userExecution.getTag1(pcmParam, umlParameter));
    
    deleteObject(userExecution.getElement3(pcmParam, umlParameter));
    
    postprocessElements();
    
    return true;
  }
}
