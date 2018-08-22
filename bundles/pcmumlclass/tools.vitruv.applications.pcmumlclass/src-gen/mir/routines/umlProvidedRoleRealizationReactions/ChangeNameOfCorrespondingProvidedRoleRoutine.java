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
public class ChangeNameOfCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization umlRealization, final String newName, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public void update0Element(final InterfaceRealization umlRealization, final String newName, final OperationProvidedRole pcmProvided) {
      pcmProvided.setEntityName(newName);
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final String newName) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final InterfaceRealization umlRealization, final String newName) {
      return umlRealization;
    }
  }
  
  public ChangeNameOfCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.ChangeNameOfCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.newName = newName;
  }
  
  private InterfaceRealization umlRealization;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlRealization: " + this.umlRealization);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmProvided = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmProvided(umlRealization, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlRealization, newName), 
    	false // asserted
    	);
    if (pcmProvided == null) {
    	return false;
    }
    registerObjectUnderModification(pcmProvided);
    // val updatedElement userExecution.getElement1(umlRealization, newName, pcmProvided);
    userExecution.update0Element(umlRealization, newName, pcmProvided);
    
    postprocessElements();
    
    return true;
  }
}
