package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeSelfCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeSelfCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType compType) {
      return compType;
    }
    
    public EObject getElement2(final DataType compType) {
      return compType;
    }
  }
  
  public CreateDataTypeSelfCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType compType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compType = compType;
  }
  
  private DataType compType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeSelfCorrespondenceRoutine with input:");
    getLogger().debug("   compType: " + this.compType);
    
    addCorrespondenceBetween(userExecution.getElement1(compType), userExecution.getElement2(compType), "");
    
    postprocessElements();
    
    return true;
  }
}
