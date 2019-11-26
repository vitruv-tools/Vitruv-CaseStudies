package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ProvidedRole_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return interfaceRealization_;
    }
  }
  
  public ProvidedRole_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.ProvidedRole_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceRealization_ = interfaceRealization_;this.implementation_ = implementation_;this.interface_ = interface_;
  }
  
  private InterfaceRealization interfaceRealization_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_DeleteMappingRoutine with input:");
    getLogger().debug("   interfaceRealization_: " + this.interfaceRealization_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    
    deleteObject(userExecution.getElement1(interfaceRealization_, implementation_, interface_));
    
    postprocessElements();
    
    return true;
  }
}
