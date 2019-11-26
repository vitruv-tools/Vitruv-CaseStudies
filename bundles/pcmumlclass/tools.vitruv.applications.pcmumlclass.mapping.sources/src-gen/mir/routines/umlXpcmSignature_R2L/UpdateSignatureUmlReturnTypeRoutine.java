package mir.routines.umlXpcmSignature_R2L;

import java.io.IOException;
import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSignatureUmlReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private UpdateSignatureUmlReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateSignatureUmlReturnTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.UpdateSignatureUmlReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature = operationSignature;this.returnType = returnType;this.operationInterface = operationInterface;
  }
  
  private OperationSignature operationSignature;
  
  private DataType returnType;
  
  private OperationInterface operationInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSignatureUmlReturnTypeRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    getLogger().debug("   returnType: " + this.returnType);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    
    userExecution.executeAction1(operationSignature, returnType, operationInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
