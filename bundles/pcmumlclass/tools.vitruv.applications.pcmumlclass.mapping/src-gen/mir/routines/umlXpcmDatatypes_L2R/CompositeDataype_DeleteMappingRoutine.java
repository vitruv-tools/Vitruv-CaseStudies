package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType type_, final Repository repository_) {
      return type_;
    }
  }
  
  public CompositeDataype_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.repository_ = repository_;
  }
  
  private CompositeDataType type_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_DeleteMappingRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   repository_: " + this.repository_);
    
    deleteObject(userExecution.getElement1(type_, repository_));
    
    postprocessElements();
    
    return true;
  }
}
