package mir.routines.javaToUmlTypePropagation;

import java.io.IOException;
import mir.routines.javaToUmlTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateMethodReturnTypeChangeRoutine extends AbstractRepairRoutineRealization {
  private PropagateMethodReturnTypeChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Method jMethod, final Parameter uReturnParameter, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.propagateTypeChangeToTypedMultiplicityElement(uReturnParameter, uReturnParameter, jMethod);
    }
  }
  
  public PropagateMethodReturnTypeChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMethod, final Parameter uReturnParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlTypePropagation.PropagateMethodReturnTypeChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.jMethod = jMethod;this.uReturnParameter = uReturnParameter;
  }
  
  private Method jMethod;
  
  private Parameter uReturnParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateMethodReturnTypeChangeRoutine with input:");
    getLogger().debug("   jMethod: " + this.jMethod);
    getLogger().debug("   uReturnParameter: " + this.uReturnParameter);
    
    userExecution.callRoutine1(jMethod, uReturnParameter, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
