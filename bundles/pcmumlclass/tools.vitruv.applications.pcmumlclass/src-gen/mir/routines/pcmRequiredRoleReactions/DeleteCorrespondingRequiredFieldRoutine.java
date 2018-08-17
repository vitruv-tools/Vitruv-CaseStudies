package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRequiredFieldRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRequiredFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRequired, final Property umlRequiredField) {
      return pcmRequired;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getElement2(final OperationRequiredRole pcmRequired, final Property umlRequiredField) {
      return umlRequiredField;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getElement3(final OperationRequiredRole pcmRequired, final Property umlRequiredField) {
      return umlRequiredField;
    }
    
    public String getTag1(final OperationRequiredRole pcmRequired, final Property umlRequiredField) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
  }
  
  public DeleteCorrespondingRequiredFieldRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;
  }
  
  private OperationRequiredRole pcmRequired;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRequiredFieldRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    
    org.eclipse.uml2.uml.Property umlRequiredField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired), 
    	false // asserted
    	);
    if (umlRequiredField == null) {
    	return false;
    }
    registerObjectUnderModification(umlRequiredField);
    removeCorrespondenceBetween(userExecution.getElement1(pcmRequired, umlRequiredField), userExecution.getElement2(pcmRequired, umlRequiredField), userExecution.getTag1(pcmRequired, umlRequiredField));
    
    deleteObject(userExecution.getElement3(pcmRequired, umlRequiredField));
    
    postprocessElements();
    
    return true;
  }
}
