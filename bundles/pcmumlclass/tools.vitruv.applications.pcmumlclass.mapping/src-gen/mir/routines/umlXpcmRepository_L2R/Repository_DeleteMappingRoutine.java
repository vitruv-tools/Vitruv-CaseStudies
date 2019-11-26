package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private Repository_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository repository_) {
      return repository_;
    }
  }
  
  public Repository_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.Repository_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository_ = repository_;
  }
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_DeleteMappingRoutine with input:");
    getLogger().debug("   repository_: " + this.repository_);
    
    deleteObject(userExecution.getElement1(repository_));
    
    postprocessElements();
    
    return true;
  }
}
