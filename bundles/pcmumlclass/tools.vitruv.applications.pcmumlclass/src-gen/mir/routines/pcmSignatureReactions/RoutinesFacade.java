package mir.routines.pcmSignatureReactions;

import mir.routines.pcmSignatureReactions.AttemptAddingCorrespondenceForExistingOperationCandidateRoutine;
import mir.routines.pcmSignatureReactions.AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine;
import mir.routines.pcmSignatureReactions.ChangeTypeOfCorrespondingReturnParameterRoutine;
import mir.routines.pcmSignatureReactions.CreateCorrespondingOperationRoutine;
import mir.routines.pcmSignatureReactions.CreateCorrespondingReturnParameterRoutine;
import mir.routines.pcmSignatureReactions.DeleteCorrespondingOperationRoutine;
import mir.routines.pcmSignatureReactions.DetectOrCreateOperationCandidateRoutine;
import mir.routines.pcmSignatureReactions.DetectOrCreateReturnParameterCandidateRoutine;
import mir.routines.pcmSignatureReactions.InsertCorrespondingOperationRoutine;
import mir.routines.pcmSignatureReactions.MoveCorrespondingOperationRoutine;
import mir.routines.pcmSignatureReactions.RemoveCorrespondingOperationRoutine;
import mir.routines.pcmSignatureReactions.RenameCorrespondingOperationRoutine;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
  
  public boolean insertCorrespondingOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingOperationRoutine routine = new InsertCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateOperationCandidate(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateOperationCandidateRoutine routine = new DetectOrCreateOperationCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateReturnParameterCandidate(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateReturnParameterCandidateRoutine routine = new DetectOrCreateReturnParameterCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean attemptAddingCorrespondenceForExistingOperationCandidate(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AttemptAddingCorrespondenceForExistingOperationCandidateRoutine routine = new AttemptAddingCorrespondenceForExistingOperationCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface, umlOperationCandidate);
    return routine.applyRoutine();
  }
  
  public boolean attemptAddingCorrespondenceForExistingReturnParamCandidate(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine routine = new AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, umlReturnParamCandidate);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingOperationRoutine routine = new CreateCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingReturnParameter(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingReturnParameterRoutine routine = new CreateCorrespondingReturnParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingOperationRoutine routine = new MoveCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingOperationRoutine routine = new RemoveCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingOperation(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingOperationRoutine routine = new DeleteCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingOperation(final OperationSignature pcmSignature, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingOperationRoutine routine = new RenameCorrespondingOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingReturnParameter(final OperationSignature pcmSignature, final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingReturnParameterRoutine routine = new ChangeTypeOfCorrespondingReturnParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmDataType);
    return routine.applyRoutine();
  }
}
