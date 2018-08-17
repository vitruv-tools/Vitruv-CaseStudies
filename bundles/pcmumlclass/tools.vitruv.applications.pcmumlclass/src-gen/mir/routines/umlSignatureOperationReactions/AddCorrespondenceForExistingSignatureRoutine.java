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
public class AddCorrespondenceForExistingSignatureRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlOperation;
    }
    
    public EObject getCorrepondenceSource2(final Operation umlOperation, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement2(final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlOperation;
    }
    
    public String getTag1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
  }
  
  public AddCorrespondenceForExistingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.AddCorrespondenceForExistingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.pcmSignature = pcmSignature;
  }
  
  private Operation umlOperation;
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlOperation, pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlOperation, pcmSignature), userExecution.getElement2(umlOperation, pcmSignature), userExecution.getTag1(umlOperation, pcmSignature));
    
    postprocessElements();
    
    return true;
  }
}
