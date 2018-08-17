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
public class DeleteCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSourceUmlGeneralization(final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getElement2(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return umlGeneralization;
    }
    
    public EObject getElement3(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return umlGeneralization;
    }
    
    public String getTag1(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
  }
  
  public DeleteCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.DeleteCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;
  }
  
  private OperationProvidedRole pcmProvided;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingProvidedGeneralizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    
    org.eclipse.uml2.uml.Generalization umlGeneralization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlGeneralization(pcmProvided), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided), 
    	false // asserted
    	);
    if (umlGeneralization == null) {
    	return false;
    }
    registerObjectUnderModification(umlGeneralization);
    removeCorrespondenceBetween(userExecution.getElement1(pcmProvided, umlGeneralization), userExecution.getElement2(pcmProvided, umlGeneralization), userExecution.getTag1(pcmProvided, umlGeneralization));
    
    deleteObject(userExecution.getElement3(pcmProvided, umlGeneralization));
    
    postprocessElements();
    
    return true;
  }
}
