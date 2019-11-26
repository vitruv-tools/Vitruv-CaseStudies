package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSignaturePcmReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private UpdateSignaturePcmReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Operation operation, final Parameter returnParameter, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.umlReturnAndRegularParameterTypeReactions.propagateTypeChange(returnParameter);
    }
  }
  
  public UpdateSignaturePcmReturnTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation operation, final Parameter returnParameter, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateSignaturePcmReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.operation = operation;this.returnParameter = returnParameter;this.interface_ = interface_;
  }
  
  private Operation operation;
  
  private Parameter returnParameter;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSignaturePcmReturnTypeRoutine with input:");
    getLogger().debug("   operation: " + this.operation);
    getLogger().debug("   returnParameter: " + this.returnParameter);
    getLogger().debug("   interface_: " + this.interface_);
    
    userExecution.callRoutine1(operation, returnParameter, interface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
