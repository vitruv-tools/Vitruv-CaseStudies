package mir.routines.umlIPREConstructorOperationReactions;

import mir.routines.umlIPREConstructorOperationReactions.ChangeNameOfCorrespondingIPRE_ConstructorRoutine;
import org.eclipse.uml2.uml.Operation;
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
  
  public boolean changeNameOfCorrespondingIPRE_Constructor(final Operation umlOperation, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingIPRE_ConstructorRoutine routine = new ChangeNameOfCorrespondingIPRE_ConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, newName);
    return routine.applyRoutine();
  }
}
