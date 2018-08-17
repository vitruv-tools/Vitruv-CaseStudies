package mir.routines.pcmParameterReactions;

import mir.routines.pcmParameterReactions.AddCorrespondenceForExistingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.ChangeDirectionOfCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.ChangeTypeOfCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.CreateCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.DeleteCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.DetectOrCreateRegularParameterCandidateRoutine;
import mir.routines.pcmParameterReactions.InsertCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.MoveCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.RemoveCorrespondingRegularParameterRoutine;
import mir.routines.pcmParameterReactions.RenameCorrespondingRegularParameterRoutine;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
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
    this.pcmDataTypePropagationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions")));
  }
  
  public final mir.routines.pcmDataTypePropagationReactions.RoutinesFacade pcmDataTypePropagationReactions;
  
  public boolean insertCorrespondingRegularParameter(final Parameter pcmParameter, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRegularParameterRoutine routine = new InsertCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateRegularParameterCandidate(final Parameter pcmParam, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateRegularParameterCandidateRoutine routine = new DetectOrCreateRegularParameterCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingRegularParameter(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingRegularParameterRoutine routine = new AddCorrespondenceForExistingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, umlParam);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRegularParameter(final Parameter pcmParam, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRegularParameterRoutine routine = new CreateCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingRegularParameter(final Parameter pcmParam, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingRegularParameterRoutine routine = new MoveCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingRegularParameter(final Parameter pcmParam, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingRegularParameterRoutine routine = new RemoveCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRegularParameter(final Parameter pcmParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRegularParameterRoutine routine = new DeleteCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingRegularParameter(final Parameter pcmParam, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingRegularParameterRoutine routine = new RenameCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeDirectionOfCorrespondingRegularParameter(final Parameter pcmParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeDirectionOfCorrespondingRegularParameterRoutine routine = new ChangeDirectionOfCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingRegularParameter(final Parameter pcmParam, final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingRegularParameterRoutine routine = new ChangeTypeOfCorrespondingRegularParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParam, pcmDataType);
    return routine.applyRoutine();
  }
}
