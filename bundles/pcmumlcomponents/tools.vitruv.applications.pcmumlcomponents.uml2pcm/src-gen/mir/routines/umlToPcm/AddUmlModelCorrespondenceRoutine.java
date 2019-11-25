package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlModelCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddUmlModelCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model newModel) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getElement2(final Model newModel) {
      return newModel;
    }
  }
  
  public AddUmlModelCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model newModel) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.AddUmlModelCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.newModel = newModel;
  }
  
  private Model newModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlModelCorrespondenceRoutine with input:");
    getLogger().debug("   newModel: " + this.newModel);
    
    addCorrespondenceBetween(userExecution.getElement1(newModel), userExecution.getElement2(newModel), "");
    
    postprocessElements();
    
    return true;
  }
}
