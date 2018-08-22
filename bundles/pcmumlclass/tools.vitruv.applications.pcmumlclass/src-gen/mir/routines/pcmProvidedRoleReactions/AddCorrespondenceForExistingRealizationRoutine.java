package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingRealizationRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource2(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return umlRealization;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getElement2(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return umlRealization;
    }
    
    public String getTag1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
  }
  
  public AddCorrespondenceForExistingRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.AddCorrespondenceForExistingRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.umlRealization = umlRealization;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceRealization umlRealization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   umlRealization: " + this.umlRealization);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmProvided, umlRealization), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, umlRealization)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmProvided, umlRealization), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmProvided, umlRealization)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmProvided, umlRealization), userExecution.getElement2(pcmProvided, umlRealization), userExecution.getTag1(pcmProvided, umlRealization));
    
    postprocessElements();
    
    return true;
  }
}
