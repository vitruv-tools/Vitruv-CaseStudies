package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateModelSelfCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateModelSelfCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model compModel) {
      return compModel;
    }
    
    public EObject getElement2(final Model compModel) {
      return compModel;
    }
  }
  
  public CreateModelSelfCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model compModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compModel = compModel;
  }
  
  private Model compModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateModelSelfCorrespondenceRoutine with input:");
    getLogger().debug("   compModel: " + this.compModel);
    
    addCorrespondenceBetween(userExecution.getElement1(compModel), userExecution.getElement2(compModel), "");
    
    postprocessElements();
    
    return true;
  }
}
