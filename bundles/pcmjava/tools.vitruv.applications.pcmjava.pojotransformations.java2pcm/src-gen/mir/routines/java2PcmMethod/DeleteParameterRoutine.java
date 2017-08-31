package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.Parameter;
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
    
    public EObject getElement1(final OrdinaryParameter jParam, final Parameter pcmParam) {
      return pcmParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final OrdinaryParameter jParam) {
      return jParam;
    }
  }
  
  public DeleteParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.DeleteParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.jParam = jParam;
  }
  
  private OrdinaryParameter jParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteParameterRoutine with input:");
    getLogger().debug("   jParam: " + this.jParam);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParam(jParam), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (pcmParam == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParam);
    deleteObject(userExecution.getElement1(jParam, pcmParam));
    
    postprocessElements();
    
    return true;
  }
}
