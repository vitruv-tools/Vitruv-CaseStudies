package mir.routines.javaToUmlTypePropagation;

import java.io.IOException;
import mir.routines.javaToUmlTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateParameterTypeChangeRoutine extends AbstractRepairRoutineRealization {
  private PropagateParameterTypeChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OrdinaryParameter jParam, final Parameter uParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.propagateTypeChangeToTypedMultiplicityElement(uParam, uParam, jParam);
    }
  }
  
  public PropagateParameterTypeChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam, final Parameter uParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlTypePropagation.PropagateParameterTypeChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.jParam = jParam;this.uParam = uParam;
  }
  
  private OrdinaryParameter jParam;
  
  private Parameter uParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateParameterTypeChangeRoutine with input:");
    getLogger().debug("   jParam: " + this.jParam);
    getLogger().debug("   uParam: " + this.uParam);
    
    userExecution.callRoutine1(jParam, uParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
