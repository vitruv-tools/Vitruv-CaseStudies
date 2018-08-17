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
public class Parameter_changeNameOfCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_changeNameOfCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final String newName, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public void update0Element(final Parameter umlParameter, final String newName, final OperationRequiredRole pcmRequired) {
      pcmRequired.setEntityName(newName);
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter, final String newName) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final String newName) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
  }
  
  public Parameter_changeNameOfCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_changeNameOfCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.newName = newName;
  }
  
  private Parameter umlParameter;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_changeNameOfCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlParameter, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, newName), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    // val updatedElement userExecution.getElement1(umlParameter, newName, pcmRequired);
    userExecution.update0Element(umlParameter, newName, pcmRequired);
    
    postprocessElements();
    
    return true;
  }
}
