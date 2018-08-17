package mir.routines.umlSignatureOperationReactions;

import java.io.IOException;
import mir.routines.umlSignatureOperationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public String getRetrieveTag1(final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement2(final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlOperation;
    }
    
    public EObject getElement3(final Operation umlOperation, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public String getTag1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation) {
      return umlOperation;
    }
  }
  
  public DeleteCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.DeleteCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;
  }
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    removeCorrespondenceBetween(userExecution.getElement1(umlOperation, pcmSignature), userExecution.getElement2(umlOperation, pcmSignature), userExecution.getTag1(umlOperation, pcmSignature));
    
    deleteObject(userExecution.getElement3(umlOperation, pcmSignature));
    
    postprocessElements();
    
    return true;
  }
}
