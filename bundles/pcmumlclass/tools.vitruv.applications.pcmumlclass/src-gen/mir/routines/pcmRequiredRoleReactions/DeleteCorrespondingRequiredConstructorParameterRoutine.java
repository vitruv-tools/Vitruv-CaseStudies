package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRequiredConstructorParameterRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRequired, final Parameter umlConstructorParameter) {
      return pcmRequired;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getElement2(final OperationRequiredRole pcmRequired, final Parameter umlConstructorParameter) {
      return umlConstructorParameter;
    }
    
    public EObject getElement3(final OperationRequiredRole pcmRequired, final Parameter umlConstructorParameter) {
      return umlConstructorParameter;
    }
    
    public String getTag1(final OperationRequiredRole pcmRequired, final Parameter umlConstructorParameter) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
  }
  
  public DeleteCorrespondingRequiredConstructorParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;
  }
  
  private OperationRequiredRole pcmRequired;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRequiredConstructorParameterRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    
    org.eclipse.uml2.uml.Parameter umlConstructorParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlConstructorParameter(pcmRequired), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired), 
    	false // asserted
    	);
    if (umlConstructorParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlConstructorParameter);
    removeCorrespondenceBetween(userExecution.getElement1(pcmRequired, umlConstructorParameter), userExecution.getElement2(pcmRequired, umlConstructorParameter), userExecution.getTag1(pcmRequired, umlConstructorParameter));
    
    deleteObject(userExecution.getElement3(pcmRequired, umlConstructorParameter));
    
    postprocessElements();
    
    return true;
  }
}
