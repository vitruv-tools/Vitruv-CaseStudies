package mir.routines.javaToUmlTypePropagation;

import mir.routines.javaToUmlTypePropagation.PropagateAttributeTypeChangeRoutine;
import mir.routines.javaToUmlTypePropagation.PropagateMethodReturnTypeChangeRoutine;
import mir.routines.javaToUmlTypePropagation.PropagateParameterTypeChangeRoutine;
import mir.routines.javaToUmlTypePropagation.PropagateTypeChangeToTypedMultiplicityElementRoutine;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.TypedElement;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean propagateAttributeTypeChange(final Field jAtt, final Property uAtt) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateAttributeTypeChangeRoutine routine = new PropagateAttributeTypeChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, jAtt, uAtt);
    return routine.applyRoutine();
  }
  
  public boolean propagateMethodReturnTypeChange(final Method jMethod, final Parameter uReturnParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateMethodReturnTypeChangeRoutine routine = new PropagateMethodReturnTypeChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, jMethod, uReturnParameter);
    return routine.applyRoutine();
  }
  
  public boolean propagateParameterTypeChange(final OrdinaryParameter jParam, final Parameter uParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateParameterTypeChangeRoutine routine = new PropagateParameterTypeChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam, uParam);
    return routine.applyRoutine();
  }
  
  public boolean propagateTypeChangeToTypedMultiplicityElement(final TypedElement uTyped, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypeChangeToTypedMultiplicityElementRoutine routine = new PropagateTypeChangeToTypedMultiplicityElementRoutine(_routinesFacade, _reactionExecutionState, _caller, uTyped, uMultiplicity, jElement);
    return routine.applyRoutine();
  }
}
