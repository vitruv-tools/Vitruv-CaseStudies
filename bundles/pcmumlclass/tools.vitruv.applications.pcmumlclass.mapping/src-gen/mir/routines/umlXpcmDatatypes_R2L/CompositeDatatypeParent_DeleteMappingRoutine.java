package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDatatypeParent_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return generalization_;
    }
  }
  
  public CompositeDatatypeParent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.generalization_ = generalization_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_DeleteMappingRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    deleteObject(userExecution.getElement1(class_, generalization_));
    
    postprocessElements();
    
    return true;
  }
}
