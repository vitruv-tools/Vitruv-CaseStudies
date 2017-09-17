package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PrimitiveType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePrimitiveDataTypeSelfCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePrimitiveDataTypeSelfCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PrimitiveType classType) {
      return classType;
    }
    
    public EObject getElement2(final PrimitiveType classType) {
      return classType;
    }
  }
  
  public CreatePrimitiveDataTypeSelfCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classType = classType;
  }
  
  private PrimitiveType classType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePrimitiveDataTypeSelfCorrespondenceRoutine with input:");
    getLogger().debug("   classType: " + this.classType);
    
    addCorrespondenceBetween(userExecution.getElement1(classType), userExecution.getElement2(classType), "");
    
    postprocessElements();
    
    return true;
  }
}
