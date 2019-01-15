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
public class DeleteCorrespondingProvidedRealizationRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingProvidedRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return pcmProvided;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getElement2(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return umlRealization;
    }
    
    public EObject getElement3(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return umlRealization;
    }
    
    public String getTag1(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceUmlRealization(final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
  }
  
  public DeleteCorrespondingProvidedRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.DeleteCorrespondingProvidedRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;
  }
  
  private OperationProvidedRole pcmProvided;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingProvidedRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    
    org.eclipse.uml2.uml.InterfaceRealization umlRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRealization(pcmProvided), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided), 
    	false // asserted
    	);
    if (umlRealization == null) {
    	return false;
    }
    registerObjectUnderModification(umlRealization);
    removeCorrespondenceBetween(userExecution.getElement1(pcmProvided, umlRealization), userExecution.getElement2(pcmProvided, umlRealization), userExecution.getTag1(pcmProvided, umlRealization));
    
    deleteObject(userExecution.getElement3(pcmProvided, umlRealization));
    
    postprocessElements();
    
    return true;
  }
}
