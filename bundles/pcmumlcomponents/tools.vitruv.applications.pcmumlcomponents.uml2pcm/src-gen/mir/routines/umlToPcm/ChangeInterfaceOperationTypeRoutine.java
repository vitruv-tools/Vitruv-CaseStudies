package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceOperationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInterfaceOperationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final Type umlType, final OperationSignature pcmOperation, final DataType pcmType) {
      return pcmOperation;
    }
    
    public void update0Element(final Operation umlOperation, final Type umlType, final OperationSignature pcmOperation, final DataType pcmType) {
      pcmOperation.setReturnType__OperationSignature(pcmType);
    }
    
    public EObject getCorrepondenceSourcePcmOperation(final Operation umlOperation, final Type umlType) {
      return umlOperation;
    }
    
    public EObject getCorrepondenceSourcePcmType(final Operation umlOperation, final Type umlType, final OperationSignature pcmOperation) {
      return umlType;
    }
  }
  
  public ChangeInterfaceOperationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Type umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlType = umlType;
  }
  
  private Operation umlOperation;
  
  private Type umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceOperationTypeRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOperation);
    getLogger().debug("   Type: " + this.umlType);
    
    OperationSignature pcmOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmOperation(umlOperation, umlType), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (pcmOperation == null) {
    	return;
    }
    initializeRetrieveElementState(pcmOperation);
    DataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlOperation, umlType, pcmOperation), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    initializeRetrieveElementState(pcmType);
    // val updatedElement userExecution.getElement1(umlOperation, umlType, pcmOperation, pcmType);
    userExecution.update0Element(umlOperation, umlType, pcmOperation, pcmType);
    
    postprocessElementStates();
  }
}
