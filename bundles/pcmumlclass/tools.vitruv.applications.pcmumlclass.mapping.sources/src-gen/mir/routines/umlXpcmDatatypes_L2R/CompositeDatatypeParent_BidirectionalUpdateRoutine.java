package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public void executeAction1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public CompositeDatatypeParent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.generalization_ = generalization_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    userExecution.executeAction1(class_, generalization_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
