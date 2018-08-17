package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameCorrespondingOperationRoutine extends AbstractRepairRoutineRealization {
  private RenameCorrespondingOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final String newName, final Operation umlOperation) {
      return umlOperation;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final String newName, final Operation umlOperation) {
      umlOperation.setName(newName);
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final String newName) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final String newName) {
      return pcmSignature;
    }
  }
  
  public RenameCorrespondingOperationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.RenameCorrespondingOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.newName = newName;
  }
  
  private OperationSignature pcmSignature;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCorrespondingOperationRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, newName), 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    // val updatedElement userExecution.getElement1(pcmSignature, newName, umlOperation);
    userExecution.update0Element(pcmSignature, newName, umlOperation);
    
    postprocessElements();
    
    return true;
  }
}
