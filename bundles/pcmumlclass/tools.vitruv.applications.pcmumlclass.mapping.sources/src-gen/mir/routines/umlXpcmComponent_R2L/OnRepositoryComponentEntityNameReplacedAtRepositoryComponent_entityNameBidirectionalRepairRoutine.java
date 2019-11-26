package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine extends AbstractRepairRoutineRealization {
  private OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RepositoryComponent affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repositoryComponent_BidirectionalCheck(affectedEObject, "updateComponentCorrespondingUmlNames");
    }
  }
  
  public OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private RepositoryComponent affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
