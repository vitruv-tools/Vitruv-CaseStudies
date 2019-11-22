package mir.routines.umlXpcmSignature_L2R;

import java.io.IOException;
import mir.routines.umlXpcmSignature_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public void callRoutine2(final Operation operation_, final Parameter returnParameter_, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateSignaturePcmReturnType(operation_, returnParameter_, interface_);
    }
    
    public void callRoutine1(final Operation operation_, final Parameter returnParameter_, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateSignaturePcmName(operation_, returnParameter_, interface_);
    }
  }
  
  public Signature_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.operation_ = operation_;this.returnParameter_ = returnParameter_;this.interface_ = interface_;
  }
  
  private Operation operation_;
  
  private Parameter returnParameter_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   operation_: " + this.operation_);
    getLogger().debug("   returnParameter_: " + this.returnParameter_);
    getLogger().debug("   interface_: " + this.interface_);
    
    userExecution.callRoutine1(operation_, returnParameter_, interface_, this.getRoutinesFacade());
    
    userExecution.callRoutine2(operation_, returnParameter_, interface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
