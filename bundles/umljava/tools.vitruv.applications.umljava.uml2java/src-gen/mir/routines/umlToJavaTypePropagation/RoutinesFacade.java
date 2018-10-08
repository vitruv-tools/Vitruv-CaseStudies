package mir.routines.umlToJavaTypePropagation;

import mir.routines.umlToJavaTypePropagation.PropagateTypedMultiplicityElementTypeChangedRoutine;
import mir.routines.umlToJavaTypePropagation.PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine;
import mir.routines.umlToJavaTypePropagation.PropagateTypedMultiplicityElementTypeChanged_defaultVoidRoutine;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.TypeReference;
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
  
  public boolean propagateTypedMultiplicityElementTypeChanged(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType, final TypeReference defaultReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypedMultiplicityElementTypeChangedRoutine routine = new PropagateTypedMultiplicityElementTypeChangedRoutine(_routinesFacade, _reactionExecutionState, _caller, uElement, uMultiplicity, jElement, jType, defaultReference);
    return routine.applyRoutine();
  }
  
  public boolean propagateTypedMultiplicityElementTypeChanged_defaultObject(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine routine = new PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine(_routinesFacade, _reactionExecutionState, _caller, uElement, uMultiplicity, jElement, jType);
    return routine.applyRoutine();
  }
  
  public boolean propagateTypedMultiplicityElementTypeChanged_defaultVoid(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypedMultiplicityElementTypeChanged_defaultVoidRoutine routine = new PropagateTypedMultiplicityElementTypeChanged_defaultVoidRoutine(_routinesFacade, _reactionExecutionState, _caller, uElement, uMultiplicity, jElement, jType);
    return routine.applyRoutine();
  }
}
