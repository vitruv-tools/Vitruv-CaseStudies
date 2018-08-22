package mir.routines.umlProvidedRoleRealizationReactions;

import java.io.IOException;
import mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource1(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource2(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return umlRealization;
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public String getRetrieveTag2(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getElement2(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return umlRealization;
    }
    
    public String getTag1(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
  }
  
  public AddCorrespondenceForExistingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.AddCorrespondenceForExistingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.pcmProvided = pcmProvided;
  }
  
  private InterfaceRealization umlRealization;
  
  private OperationProvidedRole pcmProvided;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingProvidedRoleRoutine with input:");
    getLogger().debug("   umlRealization: " + this.umlRealization);
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlRealization, pcmProvided), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlRealization, pcmProvided)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlRealization, pcmProvided), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlRealization, pcmProvided)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlRealization, pcmProvided), userExecution.getElement2(umlRealization, pcmProvided), userExecution.getTag1(umlRealization, pcmProvided));
    
    postprocessElements();
    
    return true;
  }
}
