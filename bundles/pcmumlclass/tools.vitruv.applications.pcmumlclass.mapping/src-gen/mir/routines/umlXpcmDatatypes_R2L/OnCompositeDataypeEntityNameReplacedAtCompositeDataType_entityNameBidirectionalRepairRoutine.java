package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine extends AbstractRepairRoutineRealization {
  private OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CompositeDataType affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.compositeDataype_BidirectionalCheck(affectedEObject, "updateClassName");
    }
  }
  
  public OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private CompositeDataType affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
