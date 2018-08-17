package mir.routines.umlRequiredRolePropertyReactions;

import java.io.IOException;
import mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Property umlProperty) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return umlProperty;
    }
    
    public EObject getElement3(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public String getTag1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
  }
  
  public DeleteCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.DeleteCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    removeCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmRequired), userExecution.getElement2(umlProperty, pcmRequired), userExecution.getTag1(umlProperty, pcmRequired));
    
    deleteObject(userExecution.getElement3(umlProperty, pcmRequired));
    
    postprocessElements();
    
    return true;
  }
}
