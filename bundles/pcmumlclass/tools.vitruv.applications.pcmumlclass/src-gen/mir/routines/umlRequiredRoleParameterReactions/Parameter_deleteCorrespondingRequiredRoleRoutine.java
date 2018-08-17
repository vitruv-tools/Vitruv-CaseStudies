package mir.routines.umlRequiredRoleParameterReactions;

import java.io.IOException;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Parameter_deleteCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_deleteCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return umlParameter;
    }
    
    public EObject getElement3(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public String getTag1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
  }
  
  public Parameter_deleteCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_deleteCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_deleteCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    removeCorrespondenceBetween(userExecution.getElement1(umlParameter, pcmRequired), userExecution.getElement2(umlParameter, pcmRequired), userExecution.getTag1(umlParameter, pcmRequired));
    
    deleteObject(userExecution.getElement3(umlParameter, pcmRequired));
    
    postprocessElements();
    
    return true;
  }
}
