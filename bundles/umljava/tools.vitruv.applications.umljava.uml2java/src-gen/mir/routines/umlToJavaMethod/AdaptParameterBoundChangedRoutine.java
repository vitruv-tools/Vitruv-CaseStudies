package mir.routines.umlToJavaMethod;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdaptParameterBoundChangedRoutine extends AbstractRepairRoutineRealization {
  private AdaptParameterBoundChangedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Operation uOperation, final Parameter uParam, @Extension final RoutinesFacade _routinesFacade) {
      ParameterDirectionKind _direction = uParam.getDirection();
      boolean _equals = Objects.equal(_direction, ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        _routinesFacade.setJavaMethodReturnType(uOperation, uParam);
      } else {
        ParameterDirectionKind _direction_1 = uParam.getDirection();
        boolean _equals_1 = Objects.equal(_direction_1, ParameterDirectionKind.IN_LITERAL);
        if (_equals_1) {
          _routinesFacade.changeJavaParameterType(uParam, uParam.getType());
        }
      }
    }
  }
  
  public AdaptParameterBoundChangedRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation, final Parameter uParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.AdaptParameterBoundChangedRoutine.ActionUserExecution(getExecutionState(), this);
    this.uOperation = uOperation;this.uParam = uParam;
  }
  
  private Operation uOperation;
  
  private Parameter uParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdaptParameterBoundChangedRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    getLogger().debug("   uParam: " + this.uParam);
    
    userExecution.executeAction1(uOperation, uParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
