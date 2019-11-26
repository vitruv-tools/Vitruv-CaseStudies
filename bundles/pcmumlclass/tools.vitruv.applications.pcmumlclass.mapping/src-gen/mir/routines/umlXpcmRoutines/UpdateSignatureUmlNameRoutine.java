package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSignatureUmlNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateSignatureUmlNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface, final Operation operation, @Extension final RoutinesFacade _routinesFacade) {
      operation.setName(operationSignature.getEntityName());
    }
    
    public String getRetrieveTag1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceOperation(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
      return operationSignature;
    }
  }
  
  public UpdateSignatureUmlNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateSignatureUmlNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature = operationSignature;this.returnType = returnType;this.operationInterface = operationInterface;
  }
  
  private OperationSignature operationSignature;
  
  private DataType returnType;
  
  private OperationInterface operationInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSignatureUmlNameRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    getLogger().debug("   returnType: " + this.returnType);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    
    org.eclipse.uml2.uml.Operation operation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperation(operationSignature, returnType, operationInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationSignature, returnType, operationInterface), 
    	false // asserted
    	);
    if (operation == null) {
    	return false;
    }
    registerObjectUnderModification(operation);
    userExecution.executeAction1(operationSignature, returnType, operationInterface, operation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
