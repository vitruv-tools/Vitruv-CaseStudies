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
public class ChangeNameOfCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final String newName, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public void update0Element(final Property umlProperty, final String newName, final OperationRequiredRole pcmRequired) {
      pcmRequired.setEntityName(newName);
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Property umlProperty, final String newName) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final String newName) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
  }
  
  public ChangeNameOfCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.ChangeNameOfCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.newName = newName;
  }
  
  private Property umlProperty;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlProperty, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, newName), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    // val updatedElement userExecution.getElement1(umlProperty, newName, pcmRequired);
    userExecution.update0Element(umlProperty, newName, pcmRequired);
    
    postprocessElements();
    
    return true;
  }
}
