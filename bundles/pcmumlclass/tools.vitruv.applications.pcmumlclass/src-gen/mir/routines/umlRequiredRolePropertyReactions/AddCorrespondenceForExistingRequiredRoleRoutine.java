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
public class AddCorrespondenceForExistingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource2(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
  }
  
  public AddCorrespondenceForExistingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final OperationRequiredRole pcmRequired) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.AddCorrespondenceForExistingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.pcmRequired = pcmRequired;
  }
  
  private Property umlProperty;
  
  private OperationRequiredRole pcmRequired;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, pcmRequired), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, pcmRequired)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlProperty, pcmRequired), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, pcmRequired)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmRequired), userExecution.getElement2(umlProperty, pcmRequired), userExecution.getTag1(umlProperty, pcmRequired));
    
    postprocessElements();
    
    return true;
  }
}
