package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingOperationRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return pcmSignature;
    }
    
    public EObject getElement4(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return umlReturnParam;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement5(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return umlOperation;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return umlOperation;
    }
    
    public EObject getElement3(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return pcmSignature;
    }
    
    public String getTag1(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlReturnParam(final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public String getTag2(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
  }
  
  public DeleteCorrespondingOperationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.DeleteCorrespondingOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingOperationRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature), 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    org.eclipse.uml2.uml.Parameter umlReturnParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlReturnParam(pcmSignature, umlOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, umlOperation), 
    	false // asserted
    	);
    if (umlReturnParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlReturnParam);
    removeCorrespondenceBetween(userExecution.getElement1(pcmSignature, umlOperation, umlReturnParam), userExecution.getElement2(pcmSignature, umlOperation, umlReturnParam), userExecution.getTag1(pcmSignature, umlOperation, umlReturnParam));
    
    removeCorrespondenceBetween(userExecution.getElement3(pcmSignature, umlOperation, umlReturnParam), userExecution.getElement4(pcmSignature, umlOperation, umlReturnParam), userExecution.getTag2(pcmSignature, umlOperation, umlReturnParam));
    
    deleteObject(userExecution.getElement5(pcmSignature, umlOperation, umlReturnParam));
    
    postprocessElements();
    
    return true;
  }
}
