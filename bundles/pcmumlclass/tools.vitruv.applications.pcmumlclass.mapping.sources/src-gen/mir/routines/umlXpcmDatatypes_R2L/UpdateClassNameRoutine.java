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
public class UpdateClassNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateClassNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final CompositeDataType type, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateClassNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.UpdateClassNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.type = type;this.repository = repository;
  }
  
  private CompositeDataType type;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateClassNameRoutine with input:");
    getLogger().debug("   type: " + this.type);
    getLogger().debug("   repository: " + this.repository);
    
    userExecution.executeAction1(type, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
