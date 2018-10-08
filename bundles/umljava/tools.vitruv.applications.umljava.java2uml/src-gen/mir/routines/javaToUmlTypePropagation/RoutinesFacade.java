package mir.routines.javaToUmlTypePropagation;

import mir.routines.javaToUmlTypePropagation.PropagateTypeChangeToTypedMultiplicityElementRoutine;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
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
  
  public boolean propagateTypeChangeToTypedMultiplicityElement(final TypedElement uTyped, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypeChangeToTypedMultiplicityElementRoutine routine = new PropagateTypeChangeToTypedMultiplicityElementRoutine(_routinesFacade, _reactionExecutionState, _caller, uTyped, uMultiplicity, jElement);
    return routine.applyRoutine();
  }
}
