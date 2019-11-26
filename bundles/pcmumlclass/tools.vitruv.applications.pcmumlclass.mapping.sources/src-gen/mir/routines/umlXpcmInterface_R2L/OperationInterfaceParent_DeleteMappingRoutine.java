package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface interface_, final Generalization generalization_) {
      return generalization_;
    }
  }
  
  public OperationInterfaceParent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterfaceParent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.generalization_ = generalization_;
  }
  
  private Interface interface_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_DeleteMappingRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    deleteObject(userExecution.getElement1(interface_, generalization_));
    
    postprocessElements();
    
    return true;
  }
}
