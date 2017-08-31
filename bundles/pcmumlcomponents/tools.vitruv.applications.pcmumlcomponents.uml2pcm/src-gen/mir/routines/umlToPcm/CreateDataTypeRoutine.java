package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType umlType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompositeDataType(umlType);
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private DataType umlType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   umlType: " + this.umlType);
    
    userExecution.callRoutine1(umlType, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
