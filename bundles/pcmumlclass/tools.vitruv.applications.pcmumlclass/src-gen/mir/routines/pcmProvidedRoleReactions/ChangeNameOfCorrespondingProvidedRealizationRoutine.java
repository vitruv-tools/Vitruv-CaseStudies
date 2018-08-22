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
public class ChangeNameOfCorrespondingProvidedRealizationRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingProvidedRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final String newName, final InterfaceRealization umlRealization) {
      return umlRealization;
    }
    
    public void update0Element(final OperationProvidedRole pcmProvided, final String newName, final InterfaceRealization umlRealization) {
      umlRealization.setName(newName);
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final String newName) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceUmlRealization(final OperationProvidedRole pcmProvided, final String newName) {
      return pcmProvided;
    }
  }
  
  public ChangeNameOfCorrespondingProvidedRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.ChangeNameOfCorrespondingProvidedRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.newName = newName;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingProvidedRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.InterfaceRealization umlRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRealization(pcmProvided, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, newName), 
    	false // asserted
    	);
    if (umlRealization == null) {
    	return false;
    }
    registerObjectUnderModification(umlRealization);
    // val updatedElement userExecution.getElement1(pcmProvided, newName, umlRealization);
    userExecution.update0Element(pcmProvided, newName, umlRealization);
    
    postprocessElements();
    
    return true;
  }
}
