package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Classifier classObject, final Classifier compObject) {
      return classObject;
    }
    
    public EObject getElement2(final Classifier classObject, final Classifier compObject) {
      return compObject;
    }
  }
  
  public RemoveCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier classObject, final Classifier compObject) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RemoveCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classObject = classObject;this.compObject = compObject;
  }
  
  private Classifier classObject;
  
  private Classifier compObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondenceRoutine with input:");
    getLogger().debug("   classObject: " + this.classObject);
    getLogger().debug("   compObject: " + this.compObject);
    
    removeCorrespondenceBetween(userExecution.getElement1(classObject, compObject), userExecution.getElement2(classObject, compObject), "");
    
    postprocessElements();
    
    return true;
  }
}
