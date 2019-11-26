package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent component_, final Repository repository_) {
      return component_;
    }
  }
  
  public RepositoryComponent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.RepositoryComponent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.component_ = component_;this.repository_ = repository_;
  }
  
  private RepositoryComponent component_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_DeleteMappingRoutine with input:");
    getLogger().debug("   component_: " + this.component_);
    getLogger().debug("   repository_: " + this.repository_);
    
    deleteObject(userExecution.getElement1(component_, repository_));
    
    postprocessElements();
    
    return true;
  }
}
