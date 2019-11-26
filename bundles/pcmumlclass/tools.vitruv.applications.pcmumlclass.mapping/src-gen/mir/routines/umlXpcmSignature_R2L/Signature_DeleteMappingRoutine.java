package mir.routines.umlXpcmSignature_R2L;

import java.io.IOException;
import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
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
    
    public EObject getElement1(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return operation_;
    }
    
    public EObject getElement2(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return returnParameter_;
    }
  }
  
  public Signature_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.Signature_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operation_ = operation_;this.returnParameter_ = returnParameter_;this.interface_ = interface_;
  }
  
  private Operation operation_;
  
  private Parameter returnParameter_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_DeleteMappingRoutine with input:");
    getLogger().debug("   operation_: " + this.operation_);
    getLogger().debug("   returnParameter_: " + this.returnParameter_);
    getLogger().debug("   interface_: " + this.interface_);
    
    deleteObject(userExecution.getElement1(operation_, returnParameter_, interface_));
    
    deleteObject(userExecution.getElement2(operation_, returnParameter_, interface_));
    
    postprocessElements();
    
    return true;
  }
}
