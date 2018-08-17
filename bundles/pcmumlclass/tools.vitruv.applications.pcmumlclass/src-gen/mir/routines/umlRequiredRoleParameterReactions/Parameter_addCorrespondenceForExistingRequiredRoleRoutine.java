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
public class Parameter_addCorrespondenceForExistingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_addCorrespondenceForExistingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource2(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return umlParameter;
    }
    
    public String getTag1(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
  }
  
  public Parameter_addCorrespondenceForExistingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_addCorrespondenceForExistingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.pcmRequired = pcmRequired;
  }
  
  private Parameter umlParameter;
  
  private OperationRequiredRole pcmRequired;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_addCorrespondenceForExistingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParameter, pcmRequired), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, pcmRequired)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlParameter, pcmRequired), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, pcmRequired)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlParameter, pcmRequired), userExecution.getElement2(umlParameter, pcmRequired), userExecution.getTag1(umlParameter, pcmRequired));
    
    postprocessElements();
    
    return true;
  }
}
