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
public class RenameCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private RenameCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final String newName, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public void update0Element(final Operation umlOperation, final String newName, final OperationSignature pcmSignature) {
      pcmSignature.setEntityName(umlOperation.getName());
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final String newName) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final String newName) {
      return umlOperation;
    }
  }
  
  public RenameCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.RenameCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.newName = newName;
  }
  
  private Operation umlOperation;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, newName), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    // val updatedElement userExecution.getElement1(umlOperation, newName, pcmSignature);
    userExecution.update0Element(umlOperation, newName, pcmSignature);
    
    postprocessElements();
    
    return true;
  }
}
