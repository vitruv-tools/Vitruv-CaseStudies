package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalRepairRoutine extends AbstractRepairRoutineRealization {
  private OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OperationRequiredRole affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_BidirectionalCheck(affectedEObject, "updateRequiredRoleCorrespondingUmlNames");
    }
  }
  
  public OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private OperationRequiredRole affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
