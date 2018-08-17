package mir.routines.umlProvidedRoleGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Generalization umlGeneralization, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public String getRetrieveTag1(final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getElement2(final Generalization umlGeneralization, final OperationProvidedRole pcmProvided) {
      return umlGeneralization;
    }
    
    public EObject getElement3(final Generalization umlGeneralization, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public String getTag1(final Generalization umlGeneralization, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final Generalization umlGeneralization) {
      return umlGeneralization;
    }
  }
  
  public DeleteCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization umlGeneralization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleGeneralizationReactions.DeleteCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlGeneralization = umlGeneralization;
  }
  
  private Generalization umlGeneralization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlGeneralization: " + this.umlGeneralization);
    
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmProvided = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmProvided(umlGeneralization), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlGeneralization), 
    	false // asserted
    	);
    if (pcmProvided == null) {
    	return false;
    }
    registerObjectUnderModification(pcmProvided);
    removeCorrespondenceBetween(userExecution.getElement1(umlGeneralization, pcmProvided), userExecution.getElement2(umlGeneralization, pcmProvided), userExecution.getTag1(umlGeneralization, pcmProvided));
    
    deleteObject(userExecution.getElement3(umlGeneralization, pcmProvided));
    
    postprocessElements();
    
    return true;
  }
}
