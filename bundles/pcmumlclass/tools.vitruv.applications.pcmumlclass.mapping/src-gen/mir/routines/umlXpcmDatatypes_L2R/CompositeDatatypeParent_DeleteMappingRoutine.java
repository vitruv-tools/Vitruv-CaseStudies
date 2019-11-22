package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
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
    
    public EObject getElement1(final CompositeDataType type_, final CompositeDataType parentType_) {
      return parentType_;
    }
  }
  
  public CompositeDatatypeParent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final CompositeDataType parentType_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.parentType_ = parentType_;
  }
  
  private CompositeDataType type_;
  
  private CompositeDataType parentType_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_DeleteMappingRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   parentType_: " + this.parentType_);
    
    deleteObject(userExecution.getElement1(type_, parentType_));
    
    postprocessElements();
    
    return true;
  }
}
