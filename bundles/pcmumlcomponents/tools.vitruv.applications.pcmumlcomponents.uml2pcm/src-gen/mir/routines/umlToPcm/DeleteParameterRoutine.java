package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSourcePcmParameter(final Parameter umlParameter) {
      return umlParameter;
    }
  }
  
  public DeleteParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.DeleteParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParameter(umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmParameter == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParameter);
    deleteObject(userExecution.getElement1(umlParameter, pcmParameter));
    
    postprocessElements();
    
    return true;
  }
}
