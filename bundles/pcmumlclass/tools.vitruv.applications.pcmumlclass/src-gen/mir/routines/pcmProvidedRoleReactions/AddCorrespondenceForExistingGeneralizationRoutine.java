package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource2(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return umlGeneralization;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getElement2(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return umlGeneralization;
    }
    
    public String getTag1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
  }
  
  public AddCorrespondenceForExistingGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.AddCorrespondenceForExistingGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.umlGeneralization = umlGeneralization;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private Generalization umlGeneralization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingGeneralizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   umlGeneralization: " + this.umlGeneralization);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmProvided, umlGeneralization), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, umlGeneralization)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmProvided, umlGeneralization), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmProvided, umlGeneralization)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmProvided, umlGeneralization), userExecution.getElement2(pcmProvided, umlGeneralization), userExecution.getTag1(pcmProvided, umlGeneralization));
    
    postprocessElements();
    
    return true;
  }
}
