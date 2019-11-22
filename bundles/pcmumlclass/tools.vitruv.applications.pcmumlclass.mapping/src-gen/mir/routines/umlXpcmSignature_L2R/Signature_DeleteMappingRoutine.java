package mir.routines.umlXpcmSignature_L2R;

import java.io.IOException;
import mir.routines.umlXpcmSignature_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Signature_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private Signature_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return operationSignature_;
    }
    
    public EObject getElement2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return returnType_;
    }
  }
  
  public Signature_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature_ = operationSignature_;this.returnType_ = returnType_;this.operationInterface_ = operationInterface_;
  }
  
  private OperationSignature operationSignature_;
  
  private DataType returnType_;
  
  private OperationInterface operationInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_DeleteMappingRoutine with input:");
    getLogger().debug("   operationSignature_: " + this.operationSignature_);
    getLogger().debug("   returnType_: " + this.returnType_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    
    deleteObject(userExecution.getElement1(operationSignature_, returnType_, operationInterface_));
    
    deleteObject(userExecution.getElement2(operationSignature_, returnType_, operationInterface_));
    
    postprocessElements();
    
    return true;
  }
}
