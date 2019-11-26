package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDatatypeParent_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final CompositeDataType type_, final CompositeDataType parentType_, @Extension final RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public CompositeDatatypeParent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final CompositeDataType parentType_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.parentType_ = parentType_;
  }
  
  private CompositeDataType type_;
  
  private CompositeDataType parentType_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   parentType_: " + this.parentType_);
    
    userExecution.executeAction1(type_, parentType_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
