package mir.routines.umlToJavaTypePropagation;

import java.io.IOException;
import mir.routines.umlToJavaTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateReturnParameterTypeChangedRoutine extends AbstractRepairRoutineRealization {
  private PropagateReturnParameterTypeChangedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parameter uReturnParameter, final Method jMethod, final ConcreteClassifier jType, @Extension final RoutinesFacade _routinesFacade) {
      ParameterDirectionKind _direction = uReturnParameter.getDirection();
      boolean _tripleNotEquals = (_direction != ParameterDirectionKind.RETURN_LITERAL);
      if (_tripleNotEquals) {
        throw new IllegalStateException("An uml::Parameter with direction!=RETURN_LITERAL cannot be synchronized with a java::Method.");
      }
      _routinesFacade.propagateTypedMultiplicityElementTypeChanged_defaultVoid(uReturnParameter, uReturnParameter, jMethod, jType);
    }
  }
  
  public PropagateReturnParameterTypeChangedRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uReturnParameter, final Method jMethod, final ConcreteClassifier jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaTypePropagation.PropagateReturnParameterTypeChangedRoutine.ActionUserExecution(getExecutionState(), this);
    this.uReturnParameter = uReturnParameter;this.jMethod = jMethod;this.jType = jType;
  }
  
  private Parameter uReturnParameter;
  
  private Method jMethod;
  
  private ConcreteClassifier jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateReturnParameterTypeChangedRoutine with input:");
    getLogger().debug("   uReturnParameter: " + this.uReturnParameter);
    getLogger().debug("   jMethod: " + this.jMethod);
    getLogger().debug("   jType: " + this.jType);
    
    userExecution.callRoutine1(uReturnParameter, jMethod, jType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
