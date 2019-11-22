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
public class Signature_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private Signature_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateSignatureUmlReturnType(operationSignature_, returnType_, operationInterface_);
    }
    
    public void callRoutine1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateSignatureUmlName(operationSignature_, returnType_, operationInterface_);
    }
  }
  
  public Signature_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.Signature_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature_ = operationSignature_;this.returnType_ = returnType_;this.operationInterface_ = operationInterface_;
  }
  
  private OperationSignature operationSignature_;
  
  private DataType returnType_;
  
  private OperationInterface operationInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   operationSignature_: " + this.operationSignature_);
    getLogger().debug("   returnType_: " + this.returnType_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    
    userExecution.callRoutine1(operationSignature_, returnType_, operationInterface_, this.getRoutinesFacade());
    
    userExecution.callRoutine2(operationSignature_, returnType_, operationInterface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
