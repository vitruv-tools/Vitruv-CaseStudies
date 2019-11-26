package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return interface_;
    }
  }
  
  public OperationInterface_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterface_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.contractsPackage_ = contractsPackage_;
  }
  
  private Interface interface_;
  
  private org.eclipse.uml2.uml.Package contractsPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_DeleteMappingRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   contractsPackage_: " + this.contractsPackage_);
    
    deleteObject(userExecution.getElement1(interface_, contractsPackage_));
    
    postprocessElements();
    
    return true;
  }
}
