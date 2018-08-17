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
public class DeleteCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParam, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return pcmParam;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParam) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter umlParam) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter umlParam) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter umlParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParam, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return umlParam;
    }
    
    public EObject getElement3(final Parameter umlParam, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return pcmParam;
    }
    
    public String getTag1(final Parameter umlParam, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
  }
  
  public DeleteCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.DeleteCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParam = umlParam;
  }
  
  private Parameter umlParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParam: " + this.umlParam);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParam), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParam)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParam(umlParam), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParam), 
    	false // asserted
    	);
    if (pcmParam == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParam);
    removeCorrespondenceBetween(userExecution.getElement1(umlParam, pcmParam), userExecution.getElement2(umlParam, pcmParam), userExecution.getTag1(umlParam, pcmParam));
    
    deleteObject(userExecution.getElement3(umlParam, pcmParam));
    
    postprocessElements();
    
    return true;
  }
}
