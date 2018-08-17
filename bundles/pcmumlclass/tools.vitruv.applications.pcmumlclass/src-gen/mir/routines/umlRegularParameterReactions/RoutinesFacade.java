package mir.routines.umlRegularParameterReactions;

import mir.routines.umlRegularParameterReactions.AddCorrespondenceForExistingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.ChangeModifierOfCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.ChangeNameOfCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.CreateCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.DeleteCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.DetectOrCreateCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.InsertCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.MoveCorrespondingRegularParameterRoutine;
import mir.routines.umlRegularParameterReactions.RemoveCorrespondingRegularParameterRoutine;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
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
  
  public boolean insertCorrespondingRegularParameter(final Parameter umlParam, final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRegularParameterRoutine routine = new InsertCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingRegularParameter(final Parameter umlParam, final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingRegularParameterRoutine routine = new DetectOrCreateCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingRegularParameter(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingRegularParameterRoutine routine = new AddCorrespondenceForExistingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRegularParameter(final Parameter umlParameter, final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRegularParameterRoutine routine = new CreateCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingRegularParameter(final Parameter umlParam, final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingRegularParameterRoutine routine = new MoveCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingRegularParameter(final Parameter umlParam, final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingRegularParameterRoutine routine = new RemoveCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRegularParameter(final Parameter umlParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRegularParameterRoutine routine = new DeleteCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingRegularParameter(final Parameter umlParam, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingRegularParameterRoutine routine = new ChangeNameOfCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParam, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeModifierOfCorrespondingRegularParameter(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeModifierOfCorrespondingRegularParameterRoutine routine = new ChangeModifierOfCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
}
