package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
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
    
    public EObject getElement1(final Model umlCompModel) {
      return umlCompModel;
    }
    
    public EObject getElement2(final Model umlCompModel) {
      return umlCompModel;
    }
  }
  
  public CreateModelSelfCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlCompModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlCompModel = umlCompModel;
  }
  
  private Model umlCompModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateModelSelfCorrespondenceRoutine with input:");
    getLogger().debug("   umlCompModel: " + this.umlCompModel);
    
    addCorrespondenceBetween(userExecution.getElement1(umlCompModel), userExecution.getElement2(umlCompModel), "");
    
    postprocessElements();
    
    return true;
  }
}
