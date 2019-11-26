package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CompositeDataType type_, final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateClassName(type_, repository_);
    }
  }
  
  public CompositeDataype_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.repository_ = repository_;
  }
  
  private CompositeDataType type_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   repository_: " + this.repository_);
    
    userExecution.callRoutine1(type_, repository_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
