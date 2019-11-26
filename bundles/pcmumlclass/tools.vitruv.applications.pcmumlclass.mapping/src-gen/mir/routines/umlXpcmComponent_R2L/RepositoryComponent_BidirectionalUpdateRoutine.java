package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RepositoryComponent component_, final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateComponentCorrespondingUmlNames(component_, repository_);
    }
  }
  
  public RepositoryComponent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.component_ = component_;this.repository_ = repository_;
  }
  
  private RepositoryComponent component_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   component_: " + this.component_);
    getLogger().debug("   repository_: " + this.repository_);
    
    userExecution.callRoutine1(component_, repository_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
