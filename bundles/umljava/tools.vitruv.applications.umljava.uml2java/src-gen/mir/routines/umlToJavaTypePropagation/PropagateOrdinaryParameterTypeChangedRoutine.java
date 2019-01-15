package mir.routines.umlToJavaTypePropagation;

import java.io.IOException;
import mir.routines.umlToJavaTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateOrdinaryParameterTypeChangedRoutine extends AbstractRepairRoutineRealization {
  private PropagateOrdinaryParameterTypeChangedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parameter uParameter, final OrdinaryParameter jParameter, final ConcreteClassifier jType, @Extension final RoutinesFacade _routinesFacade) {
      ParameterDirectionKind _direction = uParameter.getDirection();
      boolean _tripleEquals = (_direction == ParameterDirectionKind.RETURN_LITERAL);
      if (_tripleEquals) {
        throw new IllegalStateException("An uml::Parameter with direction==RETURN_LITERAL cannot be synchronized with a java::Parameter.");
      } else {
        _routinesFacade.propagateTypedMultiplicityElementTypeChanged_defaultObject(uParameter, uParameter, jParameter, jType);
      }
    }
  }
  
  public PropagateOrdinaryParameterTypeChangedRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParameter, final OrdinaryParameter jParameter, final ConcreteClassifier jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaTypePropagation.PropagateOrdinaryParameterTypeChangedRoutine.ActionUserExecution(getExecutionState(), this);
    this.uParameter = uParameter;this.jParameter = jParameter;this.jType = jType;
  }
  
  private Parameter uParameter;
  
  private OrdinaryParameter jParameter;
  
  private ConcreteClassifier jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateOrdinaryParameterTypeChangedRoutine with input:");
    getLogger().debug("   uParameter: " + this.uParameter);
    getLogger().debug("   jParameter: " + this.jParameter);
    getLogger().debug("   jType: " + this.jType);
    
    userExecution.callRoutine1(uParameter, jParameter, jType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
